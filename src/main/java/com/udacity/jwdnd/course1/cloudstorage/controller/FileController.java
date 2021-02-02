package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class FileController {
    private FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/file/upload")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile multipartFile, Authentication authentication, RedirectAttributes redirectAttributes) {
        if (multipartFile.isEmpty()) {
            redirectAttributes.addFlashAttribute("message","Please upload a file!");
            return "redirect:/home";
        } else if (!fileService.isFileNameAvailable(multipartFile.getOriginalFilename())){
            redirectAttributes.addFlashAttribute("message","File with this name already exists!");
            return "redirect:/home";
        }
        int row = fileService.uploadAFile(multipartFile);
        if (row < 0) {
            redirectAttributes.addFlashAttribute("message","Please try again!");
            return "redirect:/home";
        }
        redirectAttributes.addFlashAttribute("message","Succesfully uploaded " + multipartFile.getOriginalFilename());
        return "redirect:/home";
    }
}
