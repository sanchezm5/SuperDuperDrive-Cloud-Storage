package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.AuthenticationUserService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
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
    private AuthenticationUserService authenticationUserService;
    @Autowired
    private EncryptionService encryptionService;

    @GetMapping("/home")
    public String getHomePage(@ModelAttribute("noteForm") NoteForm noteForm, Authentication authentication, Model model) {
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

        model.addAttribute("files", filesList);
        model.addAttribute("fileSize", filesList.size());
        model.addAttribute("notes", noteList);
        model.addAttribute("noteSize", noteList.size());
        model.addAttribute("encryptService", encryptionService);

        return "home";
    }
}
