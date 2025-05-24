package com.company.fileupload_micro.service;

import com.company.fileupload_micro.dto.FileMetadata;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传接口
 * */
public interface FileStorageService {

    FileMetadata storeFile(MultipartFile file) throws JSchException, SftpException;

}
