package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileStorageService {

    private FileMapper fileMapper;
    private UserService currentUser;

    public FileStorageService(FileMapper fileMapper, UserService currentUser) {
        this.fileMapper = fileMapper;
        this.currentUser = currentUser;
    }

    public List<File> loadAllFiles() {
        List<File> files = new ArrayList<>();
        try {
            files = fileMapper.getFiles(currentUser.getUserId());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return files;
    }

    public int uploadAFile(MultipartFile multipartFile) throws IOException {
        File file = new File(multipartFile.getOriginalFilename(),
                multipartFile.getContentType(), Long.toString(multipartFile.getSize()),
                currentUser.getUserId(), multipartFile.getBytes());

        return fileMapper.insertFile(file);
    }

    public File loadAsFile(String fileName){
        return fileMapper.getFile(currentUser.getUserId(), fileName);
    }

    public void deleteFile(String fileName) {
        fileMapper.deleteFile(fileName);
    }

    public boolean isFileNameAvailable(String fileName) {
        return fileMapper.getFile(currentUser.getUserId(), fileName) == null;
    }
}
