package com.zakharenko.cloud_storage.cloud_storage.service.impl;

import com.zakharenko.cloud_storage.cloud_storage.entity.Package;
import com.zakharenko.cloud_storage.cloud_storage.repository.PackageRepository;
import com.zakharenko.cloud_storage.cloud_storage.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PackageServiceImpl implements PackageService {

    private final PackageRepository packageRepository;

    @Autowired
    public PackageServiceImpl(PackageRepository packageRepository) {
        this.packageRepository = packageRepository;
    }

    @Override
    public Package addPackage(Package aPackage){
//        try {
//            return playlistDao.create(playlist);
        return packageRepository.save(aPackage);
//        } catch (DaoException e) {
//            throw new ServiceException(e);
//        }
    }

    @Override
    public Package findPackage(long id) {
//        try {
//            Playlist playlist = playlistDao.findOne(id);
            Optional<Package> packageOptional = packageRepository.findById(id);
            if(packageOptional.isPresent()){
                return packageOptional.get();
            } else {
                return null;
                //TODO: add exception
            }
//            return playlist;
//        } catch (DaoException e) {
//            throw new ServiceException(e);
//        }
    }

    @Override
    public void deletePackage(long id, String pathFolder) {
//        try {
            deleteFolder(new File(pathFolder));
//            playlistDao.deleteById(id);
            packageRepository.deleteById(id);
//        } catch (DaoException e) {
//            throw new ServiceException(e);
//        }
    }

    private void deleteFolder(File file){
        if (!file.exists())
            return;

        //если это папка, то идем внутрь этой папки и вызываем рекурсивное удаление всего, что там есть
        if (file.isDirectory()) {
            for (File f : Objects.requireNonNull(file.listFiles())) {
                // рекурсивный вызов
                deleteFolder(f);
            }
        }
        // вызываем метод delete() для удаления файлов и пустых(!) папок
        file.delete();
    }

    @Override
    public Package updatePackage(Package aPackage) {
//        try {
//            return playlistDao.update(playlist);
            return packageRepository.save(aPackage);
//        } catch (DaoException e) {
//            throw new ServiceException(e);
//        }
    }

    @Override
    public List<Package> findPackageByUserId(long userId){
//        try {
//            return playlistDao.createQuery("FROM Playlist WHERE user_id = " + userId);
            return packageRepository.findPackagesByUserId(userId);
//        } catch (DaoException e) {
//            throw new ServiceException(e);
//        }
    }
}
