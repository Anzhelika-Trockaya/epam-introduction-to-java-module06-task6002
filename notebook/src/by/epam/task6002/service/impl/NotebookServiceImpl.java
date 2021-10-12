package by.epam.task6002.service.impl;

import by.epam.task6002.bean.Note;
import by.epam.task6002.dao.DAOException;
import by.epam.task6002.dao.DAOProvider;
import by.epam.task6002.dao.NotebookDAO;
import by.epam.task6002.service.NotebookService;
import by.epam.task6002.service.ServiceException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class NotebookServiceImpl implements NotebookService {
    private final DAOProvider daoProvider;
    private final List<Note> allNotes=new ArrayList<>();

    public NotebookServiceImpl() {
        daoProvider = DAOProvider.getInstance();
    }

    @Override
    public void readAllNotes() throws ServiceException {
        List<Note> notes;
        NotebookDAO notebookDAO;

        notebookDAO = daoProvider.getNotebookDAO();

        try {
            allNotes.addAll(notebookDAO.getAllNotes());

        } catch (DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public List<Note> getAllNotes() {
        return allNotes;
    }

    @Override
    public void writeNotes(List<Note> notes) throws ServiceException {
        NotebookDAO notebookDAO;

        notebookDAO = daoProvider.getNotebookDAO();

        try {
            notebookDAO.writeNotes(notes);
        } catch (DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    @Override
    public void addNote(Note note) {
        allNotes.add(note);
    }

    @Override
    public void removeNote(long id) {
        allNotes.removeIf(note -> id == note.getId());
    }

    @Override
    public List<Note> sortNotes(List<Note> notes, String parameterName, boolean descendingSort) throws ServiceException {

        switch (parameterName) {
            case "id" -> {
                if (!descendingSort) {
                    notes.sort(Comparator.comparingLong(Note::getId));
                } else {
                    notes.sort(Comparator.comparingLong(Note::getId).reversed());
                }
            }
            case "subject" -> {
                if (!descendingSort) {
                    notes.sort(Comparator.comparing(Note::getSubject));
                } else {
                    notes.sort(Comparator.comparing(Note::getSubject).reversed());
                }
            }
            case "dateOfCreation" -> {
                if (!descendingSort) {
                    notes.sort(Comparator.comparing(Note::getDateOfCreation));
                } else {
                    notes.sort(Comparator.comparing(Note::getDateOfCreation).reversed());
                }
            }
            case "email" -> {
                if (!descendingSort) {
                    notes.sort(Comparator.comparing(Note::getEmail));
                } else {
                    notes.sort(Comparator.comparing(Note::getEmail).reversed());
                }
            }
            case "text" -> {
                if (!descendingSort) {
                    notes.sort(Comparator.comparing(Note::getText));
                } else {
                    notes.sort(Comparator.comparing(Note::getText).reversed());
                }
            }
            default -> throw new ServiceException("Incorrect parameter name " + parameterName);
        }

        return notes;
    }

    @Override
    public List<Note> filterNotesById(List<Note> notes, long id) {
        List<Note> filteredNotes;

        filteredNotes = new ArrayList<>();

        for (Note note : notes) {
            if (id == note.getId()) {
                filteredNotes.add(note);
            }
        }

        return filteredNotes;
    }

    @Override
    public List<Note> filterNotesBySubject(List<Note> notes, String subjectPart) {
        List<Note> filteredNotes;

        filteredNotes = new ArrayList<>();

        for (Note note : notes) {
            if (note.getSubject().toLowerCase().contains(subjectPart.toLowerCase())) {
                filteredNotes.add(note);
            }
        }

        return filteredNotes;
    }

    @Override
    public List<Note> filterNotesByEmail(List<Note> notes, String emailPart) {
        List<Note> filteredNotes;

        filteredNotes = new ArrayList<>();

        for (Note note : notes) {
            if (note.getEmail().contains(emailPart)) {
                filteredNotes.add(note);
            }
        }

        return filteredNotes;
    }

    @Override
    public List<Note> filterNotesByDateOfCreation(List<Note> notes, LocalDate from, LocalDate to) {
        List<Note> filteredNotes;
        LocalDate notesDate;

        filteredNotes = new ArrayList<>();

        for (Note note : notes) {
            notesDate = note.getDateOfCreation();

            if (!from.isAfter(notesDate) && !to.isBefore(notesDate)) {
                filteredNotes.add(note);
            }
        }

        return filteredNotes;
    }

    @Override
    public List<Note> filterNotesByText(List<Note> notes, String textPart) {
        List<Note> filteredNotes;

        filteredNotes = new ArrayList<>();

        for (Note note : notes) {
            if (note.getText().toLowerCase().contains(textPart.toLowerCase())) {
                filteredNotes.add(note);
            }
        }

        return filteredNotes;
    }
}
