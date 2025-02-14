package org.vsskh.rabbitmqdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vsskh.rabbitmqdemo.model.BatchUpload;
@Repository
public interface BatchUploadRepository extends JpaRepository<BatchUpload, Long> {
}
