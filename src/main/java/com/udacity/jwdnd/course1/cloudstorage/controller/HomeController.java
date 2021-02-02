package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.AuthenticationUserService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private FileService fileService;
    @Autowired
    private AuthenticationUserService authenticationUserService;
    @Autowired
    private EncryptionService encryptionService;

    @GetMapping("/home")
    public String getHomePage(Authentication authentication, Model model) {
        model.addAttribute("name", authenticationUserService.getLoggedInName());

        List<File> filesList;
        try {
            filesList = fileService.loadFiles();
        } catch (NullPointerException e) {
            filesList = new ArrayList<>();
        }

        model.addAttribute("files", filesList);
        model.addAttribute("fileSize", filesList.size());
        model.addAttribute("encryptService", encryptionService);

        return "home";
    }
}
