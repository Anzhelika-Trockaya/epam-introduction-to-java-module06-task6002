package by.epam.task6002.dao;

import by.epam.task6002.bean.Note;

import java.util.List;

public interface NotebookDAO {
    List<Note> getAllNotes() throws DAOException;
    void writeNotes(List<Note> notes) throws DAOException;
}
