package com.company.fileupload_micro.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @description 文件元数据 DTO
 * @author: RicksonYu
 * @create: 2025年-05月-24日--16:18
 */
@Data
public class FileMetadata implements Serializable {

    private Long id;
    private String fileName;
    private String originalFileName;
    private String fileType;
    private Long fileSize;
    private String storagePath;
    private LocalDateTime uploadTime;
}
