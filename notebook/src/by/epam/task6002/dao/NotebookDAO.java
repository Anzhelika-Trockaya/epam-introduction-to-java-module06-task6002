package by.epam.task6002.dao;

import by.epam.task6002.bean.Note;

import java.util.List;

public interface NotebookDAO {
    void readAllNotes() throws DAOException;

    List<Note> getAllNotes();

    void writeNotes() throws DAOException;

    void addNote(Note note);

    void removeNote(long id);
}
