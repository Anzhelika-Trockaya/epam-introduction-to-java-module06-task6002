package com.epam.task6002.logic;

import com.epam.task6002.data.DataUtil;
import com.epam.task6002.model.Note;
import com.epam.task6002.model.NoteBook;

import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        ArrayList<Note> notes = new ArrayList<>();
        try {
            DataUtil.loadData(notes);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        NoteBook notebook = new NoteBook(notes);
        NoteBookProcessor noteBookProcessor = new NoteBookProcessor(notebook);
        noteBookProcessor.start();


        try {
            DataUtil.unloadData(notes);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
