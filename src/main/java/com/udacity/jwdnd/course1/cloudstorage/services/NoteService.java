package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private NoteMapper noteMapper;
    private UserService currentUser;

    public NoteService(NoteMapper noteMapper, UserService currentUser) {
        this.noteMapper = noteMapper;
        this.currentUser = currentUser;
    }

    public List<Note> getAllNotes() {
        return noteMapper.getNotes(currentUser.getUserId());
    }

    public Note getNote(Integer noteId) {
        return noteMapper.getNote(noteId);
    }

    public int addNote(NoteForm noteForm) {
        return noteMapper.insertNote(new Note(0,noteForm.getNoteTitle(),noteForm.getNoteDescription(), currentUser.getUserId()));
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
