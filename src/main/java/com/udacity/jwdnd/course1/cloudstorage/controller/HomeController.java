package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private FileService fileService;
    @Autowired
    private NoteService noteService;
    @Autowired
    private CredentialService credentialService;
    @Autowired
    private AuthenticationUserService authenticationUserService;
    @Autowired
    private EncryptionService encryptionService;

    @GetMapping("/home")
    public String getHomePage(@ModelAttribute("noteForm") NoteForm noteForm, @ModelAttribute("credentialForm") CredentialForm credentialForm, Authentication authentication, Model model) {
        model.addAttribute("name", authenticationUserService.getLoggedInName());

        List<File> filesList;
        try {
            filesList = fileService.loadFiles();
        } catch (NullPointerException e) {
            filesList = new ArrayList<>();
        }

        List<Note> noteList;
        try {
            noteList = noteService.getNotes(authenticationUserService.getLoggedInUserId());
        } catch (NullPointerException e) {
            noteList = new ArrayList<>();
        }

        List<Credential> credentialList;
        try {
            credentialList = credentialService.getCredentials(authenticationUserService.getLoggedInUserId());
        } catch (NullPointerException e) {
            credentialList = new ArrayList<>();
        }

        model.addAttribute("files", filesList);
        model.addAttribute("fileSize", filesList.size());
        model.addAttribute("notes", noteList);
        model.addAttribute("noteSize", noteList.size());
        model.addAttribute("credentials", credentialList);
        model.addAttribute("credentialSize", credentialList.size());
        model.addAttribute("encryptService", encryptionService);

        return "home";
    }
}
