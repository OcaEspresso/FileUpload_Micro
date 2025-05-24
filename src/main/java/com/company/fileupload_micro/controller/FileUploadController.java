package com.company.fileupload_micro.controller;

import com.company.fileupload_micro.dto.FileMetadata;
import com.company.fileupload_micro.entity.FileMetadataEntity;
import com.company.fileupload_micro.repository.FileMetadataRepository;
import com.company.fileupload_micro.service.FileStorageService;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
*  @description
*  @author: RicksonYu
*  @create: 2025年-05月-24日--17:51
*/
@RestController
@RequestMapping("/api/files")
public class FileUploadController {

    private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);
    private final FileStorageService fileStorageService;

    public FileUploadController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;

    }

    @PostMapping("/upload")
    public ResponseEntity<FileMetadata> uploadFile(@RequestParam("file")MultipartFile file) throws JSchException, SftpException {
        logger.info("Received file upload request: {}", file.getOriginalFilename());
        FileMetadata metadata = fileStorageService.storeFile(file);
        return ResponseEntity.ok(metadata);
    }

}
