package com.company.fileupload_micro.service.impl;

import com.company.fileupload_micro.config.FileStorageConfig;
import com.company.fileupload_micro.dto.FileMetadata;
import com.company.fileupload_micro.entity.FileMetadataEntity;
import com.company.fileupload_micro.exception.FileStorageException;
import com.company.fileupload_micro.repository.FileMetadataRepository;
import com.company.fileupload_micro.service.FileStorageService;
import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @descriptionw
 * @author: RicksonYu
 * @create: 2025年-05月-24日--16:30
 */
@Service
public class FileStorageServiceImpl implements FileStorageService{

    private static final Logger logger = LoggerFactory.getLogger(FileStorageServiceImpl.class);
    private final FileStorageConfig fileStorageConfig;
    private final FileMetadataRepository fileMetadataRepository;

    // 构造初始化
    public FileStorageServiceImpl(FileStorageConfig fileStorageConfig, FileMetadataRepository fileMetadataRepository) {
        this.fileStorageConfig = fileStorageConfig;
        this.fileMetadataRepository = fileMetadataRepository;
    }

    @Override
    public FileMetadata storeFile(MultipartFile file) {

        logger.info("Starting file upload: {}", file.getOriginalFilename());

        // 校验文件
        if(file.isEmpty()){

            logger.error("File is empty failed to store");
            // v-1版本  后续版本独立单独的日志服务进数据库 LoggerService.save(exception);
            throw new FileStorageException("can not store empty file");
        }

        String contentType = file.getContentType();
        if(!fileStorageConfig.getAllowedTypes().contains(contentType)){

            logger.error("Invalid file type :{}", contentType);
            throw new FileStorageException("Invalid file type: " + contentType + ". Allowed types: " + fileStorageConfig.getAllowedTypes());
        }

        // 生成唯一文件名
        String originalFileName = file.getOriginalFilename();
        String fileExtension = getFileExtension(originalFileName);
        String fileName  = UUID.randomUUID() + "." + fileExtension;
        String remotePath = fileStorageConfig.getSftp().getRemoteDir() + "/" + fileName;

        //SFTP 上传
        Session session = null;
        ChannelSftp sftp = null;
        try
        {
            JSch jsch = new JSch();
            session = jsch.getSession(fileStorageConfig.getSftp().getUsername(),
                    fileStorageConfig.getSftp().getHost(),
                    fileStorageConfig.getSftp().getPort());
            session.setPassword(fileStorageConfig.getSftp().getPassword());
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            sftp = (ChannelSftp) session.openChannel("sftp");
            sftp.connect();

            //确认远程目录的存在
            try {
                sftp.cd(fileStorageConfig.getSftp().getRemoteDir());
            } catch (SftpException e) {
                logger.info("Creating remote directory: {}", fileStorageConfig.getSftp().getRemoteDir());
                sftp.mkdir(fileStorageConfig.getSftp().getRemoteDir());
                sftp.cd(fileStorageConfig.getSftp().getRemoteDir());
            }

            //上传文件
            try (InputStream inputStream = file.getInputStream())
            {
                sftp.put(inputStream,fileName);
                logger.info("File uploaded to SFTP: {}", remotePath);
            }
        }  catch (JSchException | SftpException | IOException e)
        {
            logger.error("Failed to upload file to SFTP: {}", fileName, e);
            throw new FileStorageException("Could not upload file to SFTP: " + fileName, e);
        } finally {
            if (sftp != null) {
                sftp.disconnect();
            }
            if (session != null) {
                session.disconnect();
            }
        }

        // 保存元数据到H2
        FileMetadataEntity fileEntity = new FileMetadataEntity();
        fileEntity.setFileName(fileName);
        fileEntity.setOriginalFileName(originalFileName);
        fileEntity.setFileType(contentType);
        fileEntity.setFileSize(file.getSize());
        fileEntity.setStoragePath(remotePath);
        fileEntity.setUploadTime(LocalDateTime.now());
        fileMetadataRepository.save(fileEntity);

        // 返回 DTO
        FileMetadata metadata = new FileMetadata();
        metadata.setId(fileEntity.getId());
        metadata.setFileName(fileName);
        metadata.setOriginalFileName(originalFileName);
        metadata.setFileType(contentType);
        metadata.setFileSize(file.getSize());
        metadata.setStoragePath(remotePath);
        metadata.setUploadTime(fileEntity.getUploadTime());

        return metadata;

    }

    /**
     *  获取文件扩展名
     * **/
    private String getFileExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return "";
        }

        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

}
