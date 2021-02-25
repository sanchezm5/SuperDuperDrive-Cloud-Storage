package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {

    private FileMapper fileMapper;
    private UserService currentUser;

    public FileService(FileMapper fileMapper, UserService currentUser) {
        this.fileMapper = fileMapper;
        this.currentUser = currentUser;
    }

    public boolean isFileNameAvailable(String fileName) {
        return fileMapper.getFile(currentUser.getUserId(), fileName) == null;
    }

    public int uploadAFile(MultipartFile multipartFile) {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        try {
            File file = new File(
                    fileName,
                    multipartFile.getContentType(),
                    Long.toString(multipartFile.getSize()),
                    currentUser.getUserId(),
                    multipartFile.getBytes());
            return fileMapper.insertFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public File loadAsFile(String fileName){
        File file;
        try {
            file = fileMapper.getFile(currentUser.getUserId(), fileName);
        } catch (NullPointerException e) {
            e.printStackTrace();
            throw e;
        }
        return file;
    }

    public List<File> loadFiles() {
        List<File> files = new ArrayList<>();
        try {
            files = fileMapper.getFiles(currentUser.getUserId());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return files;
    }

    public void deleteFile(String fileName) {
        fileMapper.deleteFile(fileName);
    }
}
