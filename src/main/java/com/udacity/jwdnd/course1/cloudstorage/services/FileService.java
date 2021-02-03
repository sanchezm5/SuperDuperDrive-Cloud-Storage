package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {

    private FileMapper fileMapper;
    private UserMapper userMapper;
    private AuthenticationUserService authenticatedUser;

    public FileService(FileMapper fileMapper, UserMapper userMapper, AuthenticationUserService authenticatedUser) {
        this.fileMapper = fileMapper;
        this.userMapper = userMapper;
        this.authenticatedUser = authenticatedUser;
    }

    public boolean isFileNameAvailable(String fileName) {
        return fileMapper.getFile(authenticatedUser.getLoggedInUserId(), fileName) == null;
    }

    public int uploadAFile(MultipartFile multipartFile) {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        try {
            File file = new File(
                    fileName,
                    multipartFile.getContentType(),
                    Long.toString(multipartFile.getSize()),
                    authenticatedUser.getLoggedInUserId(),
                    multipartFile.getBytes());
            return fileMapper.insertFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public File loadFile(String fileName){
        File file;
        try {
            file = fileMapper.getFile(authenticatedUser.getLoggedInUserId(), fileName);
        } catch (NullPointerException e) {
            e.printStackTrace();
            throw e;
        }
        return file;
    }

    public List<File> loadFiles() {
        List<File> files;
        try {
            files = fileMapper.getFiles(authenticatedUser.getLoggedInUserId());
        } catch (NullPointerException e) {
            e.printStackTrace();
            throw e;
        }
        return files;
    }
}
