package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoteService {

    private NoteMapper noteMapper;
    private AuthenticationUserService authenticationUserService;

    public NoteService(NoteMapper noteMapper, AuthenticationUserService authenticationUserService) {
        this.noteMapper = noteMapper;
        this.authenticationUserService = authenticationUserService;
    }

    public List<Note> getNotes(Integer userId) {
        List<Note> noteList;
        try {
            noteList = noteMapper.getNotes(authenticationUserService.getLoggedInUserId());
        } catch (NullPointerException e) {
            e.printStackTrace();
            throw e;
        }
        return noteList;
    }

    public Note getNote(Integer noteId) {
        Note note;
        try {
            note = noteMapper.getNote(noteId);
        } catch (NullPointerException e) {
            e.printStackTrace();
            throw e;
        }
        return note;
    }

    public int addNote(NoteForm noteForm) {
        return noteMapper.addNote(new Note(0,noteForm.getNoteTitle(),noteForm.getNoteDescription(), authenticationUserService.getLoggedInUserId()));
    }

    public void editNote(NoteForm noteForm) {
        Note note = noteMapper.getNote(noteForm.getNoteId());
        note.setNoteTitle(noteForm.getNoteTitle());
        note.setNoteDescription(noteForm.getNoteDescription());
        noteMapper.editNote(note);
    }

    public void deleteNote(Integer noteId) {
        noteMapper.deleteNote(noteId);
    }
}
