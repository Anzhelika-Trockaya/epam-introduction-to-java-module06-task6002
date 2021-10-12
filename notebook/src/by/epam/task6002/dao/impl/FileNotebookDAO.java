package by.epam.task6002.dao.impl;

import by.epam.task6002.bean.Note;
import by.epam.task6002.dao.DAOException;
import by.epam.task6002.dao.NotebookDAO;
import by.epam.task6002.dao.impl.string.NoteStringConverter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileNotebookDAO implements NotebookDAO {
    private final String booksFileName = getClass().getResource("/by/epam/task6002/resource/notes.txt")
            .toString()
            .substring(6);

    @Override
    public List<Note> getAllNotes() throws DAOException {
        List<Note> allNotes;
        String currentNoteString;
        NoteStringConverter noteStringConverter;
        Note currentNote;

        allNotes = new ArrayList<>();
        noteStringConverter = new NoteStringConverter();

        try (BufferedReader reader = new BufferedReader(new FileReader(booksFileName))) {

            while (reader.ready()) {
                currentNoteString = readNotesString(reader);
                currentNote = noteStringConverter.parseNote(currentNoteString);
                allNotes.add(currentNote);

            }

        } catch (IOException ioException) {
            throw new DAOException(ioException);
        }

        return allNotes;
    }

    private String readNotesString(BufferedReader reader) throws DAOException, IOException {
        StringBuilder currentNoteStringBuilder;
        String currentNoteString;

        currentNoteStringBuilder = new StringBuilder();
        currentNoteStringBuilder.append(reader.readLine());

        if (currentNoteStringBuilder.length() < 2) {
            throw new DAOException("Incorrect file content! File: " + booksFileName);
        }

        while ((currentNoteStringBuilder.charAt(currentNoteStringBuilder.length() - 1) != '}'
                || currentNoteStringBuilder.charAt(currentNoteStringBuilder.length() - 2) != ' ')
                && reader.ready()) {

            currentNoteStringBuilder.append('\n');
            currentNoteStringBuilder.append(reader.readLine());
        }

        currentNoteString = currentNoteStringBuilder.toString();

        return currentNoteString;
    }

    @Override
    public void writeNotes(List<Note> notes) throws DAOException {
        NoteStringConverter noteStringConverter;
        String string;
        int i;

        noteStringConverter = new NoteStringConverter();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(booksFileName))) {

            if(notes.size()>0){
                i=0;
                string = noteStringConverter.noteToString(notes.get(i));
                writer.write(string);
            }

            for (i=1; i<notes.size(); i++) {
                string = noteStringConverter.noteToString(notes.get(i));
                writer.newLine();
                writer.write(string);
            }

            writer.flush();

        } catch (IOException ioException) {
            throw new DAOException(ioException);
        }
    }
}
