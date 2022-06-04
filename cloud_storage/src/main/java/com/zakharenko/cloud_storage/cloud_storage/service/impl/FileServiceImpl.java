package com.zakharenko.cloud_storage.cloud_storage.service.impl;


import com.zakharenko.cloud_storage.cloud_storage.entity.MyFile;
import com.zakharenko.cloud_storage.cloud_storage.repository.FileRepository;
import com.zakharenko.cloud_storage.cloud_storage.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    @Autowired
    public FileServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public MyFile addFile(MyFile myFile) {
//        return trackDao.create(track);
        return fileRepository.save(myFile);
    }

    @Override
    public void uploadFile(MultipartFile file, String path) {
        try {
            byte[] bytes = file.getBytes();
            BufferedOutputStream stream = new BufferedOutputStream(
                    new FileOutputStream(
                            path
                    )
            );
            stream.write(bytes);
            stream.close();
        } catch (IOException e) {
//            throw new ServiceException(e);
        }
    }

    @Override
    public void setPath(MyFile file, String path) {
        file.setPath(path);
//        try {
//            trackDao.update(myFile);
        fileRepository.save(file);
//        } catch (DaoException e) {
////            throw new ServiceException(e);
//        }
    }

    @Override
    public MyFile findFileById(long id) {
//        try {
//        return trackDao.findOne(id);
        Optional<MyFile> file = fileRepository.findById(id);
        if(file.isPresent()){
            return file.get();
        } else {
            //TODO: add exception
            return null;
        }
//        return fileRepository.findById(id);
//        } catch (DaoException e) {
//            throw new ServiceException(e);
//        }
    }

    @Override
    public void deleteFile(MyFile file) {
        try {
            Files.delete(Paths.get(file.getPath()));
//            trackDao.delete(track);
            fileRepository.delete(file);
        } catch (IOException e) {
//            throw new ServiceException(e);
        }
    }

    @Override
    public MyFile updateFile(MyFile file) {
//        try {
        return fileRepository.save(file);
//        } catch (DaoException e) {
//            throw new ServiceException(e);
//        }
    }
}
