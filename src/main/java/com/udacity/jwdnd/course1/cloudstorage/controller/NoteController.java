package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/note")
public class NoteController {

    private NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping("/add")
    public String addNote(@ModelAttribute("noteForm") NoteForm noteForm, Authentication authentication, RedirectAttributes redirectAttributes) {
        if(noteForm.getNoteId() == null) {
            noteService.addNote(noteForm);
        } else {
            noteService.editNote(noteForm);
            redirectAttributes.addFlashAttribute("editNoteSuccess", "Your note has successfully been edited.");
        }
        return "redirect:/result";
    }
}
