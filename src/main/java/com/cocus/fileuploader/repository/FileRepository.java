package com.cocus.fileuploader.repository;

import com.cocus.fileuploader.model.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
    FileEntity findTopByOrderByCreatedAtDesc();
}
