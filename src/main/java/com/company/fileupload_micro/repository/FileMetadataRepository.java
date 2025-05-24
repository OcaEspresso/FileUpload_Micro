package com.company.fileupload_micro.repository;

import com.company.fileupload_micro.entity.FileMetadataEntity;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * jpa 提供的CRUD
 * **/
public interface FileMetadataRepository extends JpaRepository<FileMetadataEntity, Long> {
}
