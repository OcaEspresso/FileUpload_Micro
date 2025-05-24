package com.company.fileupload_micro.entity;

import lombok.Data;

import javax.persistence.*;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * @description 文件元数据实体
 * @author: RicksonYu
 * @create: 2025年-05月-24日--16:18
 */
@Data
@Entity
@Table(name = "file_metadata")
public class FileMetadataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String originalFileName;

    @Column(nullable = false)
    private String fileType;

    @Column(nullable = false)
    private Long fileSize;

    @Column(nullable = false)
    private String storagePath;

    @Column(nullable = false)
    private LocalDateTime uploadTime;

}
