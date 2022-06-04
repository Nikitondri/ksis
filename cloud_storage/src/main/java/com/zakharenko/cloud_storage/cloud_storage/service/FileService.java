package com.zakharenko.cloud_storage.cloud_storage.service;


import com.zakharenko.cloud_storage.cloud_storage.entity.MyFile;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    void uploadFile(MultipartFile file, String path);
    MyFile addFile(MyFile track);
    void setPath(MyFile track, String path);
    MyFile findFileById(long id);
    void deleteFile(MyFile track);
    MyFile updateFile(MyFile track);
}
