package by.epam.task6002.service;

import by.epam.task6002.bean.Note;

import java.time.LocalDate;
import java.util.List;

public interface NotebookService {
    void readAllNotes() throws ServiceException;
    List<Note> getAllNotes();
    void writeNotes(List<Note> notes) throws ServiceException;
    void addNote(Note note);
    void removeNote(long id);
    List<Note> sortNotes(List<Note> notes, String parameterName, boolean descendingSort) throws ServiceException;
    List<Note> filterNotesById(List<Note> notes, long id);
    List<Note> filterNotesBySubject(List<Note> notes, String subjectPart);
    List<Note> filterNotesByEmail(List<Note> notes, String emailPart);
    List<Note> filterNotesByDateOfCreation(List<Note> notes, LocalDate from, LocalDate to);
    List<Note> filterNotesByText(List<Note> notes, String textPart);
}
