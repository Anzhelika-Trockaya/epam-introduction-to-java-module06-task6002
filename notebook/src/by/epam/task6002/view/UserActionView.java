package by.epam.task6002.view;

import by.epam.task6002.bean.Note;

import java.util.List;

public interface UserActionView {
    String getExceptionToUnloadDataResponse();

    String getDataUnloadedResponse();

    String getViewNotesResponse(String title, List<Note> notes);

    String getNotesNotFoundResponse();

    String getExceptionResponse(String message);

    String getExceptionResponse(Exception e);

    String getIncorrectParamsResponse(String[] params);

    String getNoteAddedResponse();

    String getNoteRemovedResponse();

    String getIncorrectRequestResponse(String request);
}
