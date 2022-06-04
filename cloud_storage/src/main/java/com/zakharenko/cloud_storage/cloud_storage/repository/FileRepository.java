package com.zakharenko.cloud_storage.cloud_storage.repository;

import com.zakharenko.cloud_storage.cloud_storage.entity.MyFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileRepository extends JpaRepository<MyFile, Long> {
    void deleteById(long id);

    Optional<MyFile> findById(Long id);
}
