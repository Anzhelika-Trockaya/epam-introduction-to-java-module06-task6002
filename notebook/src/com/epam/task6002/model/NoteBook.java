package com.epam.task6002.model;

import java.util.ArrayList;

public class NoteBook {
    private final ArrayList<Note> notes;

    public NoteBook(ArrayList<Note> notes) {
        this.notes = notes;
    }

    public ArrayList<Note> getNotes() {
        return notes;
    }

    public void addNote(Note note) {
        notes.add(note);
    }


}
