package com.zakharenko.cloud_storage.cloud_storage.service;

import com.zakharenko.cloud_storage.cloud_storage.entity.Package;

import java.util.List;

public interface PackageService {
    Package addPackage(Package playlist);
    Package findPackage(long id);
    void deletePackage(long id, String pathFolder);
    Package updatePackage(Package playlist);
    List<Package> findPackageByUserId(long userId);
//    List<Playlist> findAllPlaylist(User user) throws ServiceException;
}
