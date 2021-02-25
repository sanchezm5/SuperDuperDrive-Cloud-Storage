package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private EncryptionService encryptionService;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private NoteService noteService;

    @Autowired
    private CredentialService credentialService;

    @GetMapping("/home")
    public String getHomePage(@ModelAttribute("noteForm") NoteForm noteForm,
                              @ModelAttribute("credentialForm") CredentialForm credentialForm,
                              Authentication authentication, Model model) {

        model.addAttribute("name", userService.getUsersName());
        model.addAttribute("encryptService", encryptionService);
        model.addAttribute("files", fileStorageService.loadAllFiles());
        model.addAttribute("fileSize", fileStorageService.loadAllFiles().size());
        model.addAttribute("notes", noteService.getAllNotes());
        model.addAttribute("noteSize", noteService.getAllNotes().size());
        model.addAttribute("credentials", credentialService.getAllCredentials());
        model.addAttribute("credentialSize", credentialService.getAllCredentials().size());

        return "home";
    }
}
