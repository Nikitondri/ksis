package com.zakharenko.cloud_storage.cloud_storage.repository;

import com.zakharenko.cloud_storage.cloud_storage.entity.Package;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PackageRepository extends JpaRepository<Package, Long> {
    void deleteById(long id);

    @Override
    Optional<Package> findById(Long aLong);

    List<Package> findPackagesByUserId(long id);
}
