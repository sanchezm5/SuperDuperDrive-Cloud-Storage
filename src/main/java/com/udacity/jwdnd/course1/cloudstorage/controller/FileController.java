package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("/file")
public class FileController  {

    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping("/{fileName}")
    @ResponseBody
    public ResponseEntity<Resource> viewFile(@PathVariable String fileName) {
        File fileToView = fileStorageService.loadAsFile(fileName);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileToView.getFileName() + "\"")
                .body(new ByteArrayResource(fileToView.getFileData()));
    }

    @GetMapping("/delete/{fileName}")
    public String deleteFile(@PathVariable String fileName, Authentication authentication, RedirectAttributes redirectAttributes) {
        fileStorageService.deleteFile(fileName);
        redirectAttributes.addFlashAttribute("message", "Successfully deleted " + fileName);
        return "redirect:/home";
    }

    @PostMapping("/")
    public String uploadFile(@RequestParam("file") MultipartFile multipartFile, Authentication authentication, RedirectAttributes redirectAttributes) throws IOException {
        if (multipartFile.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please choose a file to upload!");
            return "redirect:/home";
        } else if (!fileStorageService.isFileNameAvailable(multipartFile.getOriginalFilename())) {
            redirectAttributes.addFlashAttribute("message", "File name already exists! Try again.");
            return "redirect:/home";
        }
        fileStorageService.uploadAFile(multipartFile);
        redirectAttributes.addFlashAttribute("message", "Successfully uploaded " + multipartFile.getOriginalFilename());
        return "redirect:/home";
    }
}