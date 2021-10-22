package by.epam.task6002.view;

import by.epam.task6002.bean.Note;

import java.util.Arrays;
import java.util.List;

public class UserActionViewImpl implements UserActionView {

    @Override
    public String getExceptionToUnloadDataResponse() {
        return "-1 Failed to save data to file!";
    }

    @Override
    public String getDataUnloadedResponse() {
        return "0 Data are loaded successful!";
    }

    @Override
    public String getViewNotesResponse(String title, List<Note> notes) {
        String response;
        NoteView noteView;

        noteView = new NoteView();
        response = "0 " + title + "\n" + noteView.view(notes);

        return response;
    }

    public String getNotesNotFoundResponse() {
        return "1 Notes not found!";
    }

    @Override
    public String getExceptionResponse(String message) {
        return "-1 " + message;
    }

    @Override
    public String getExceptionResponse(Exception e) {
        return "-1 " + e.getMessage();
    }

    @Override
    public String getIncorrectParamsResponse(String[] params) {
        return "-1 Incorrect params: " + Arrays.toString(params);
    }

    @Override
    public String getNoteAddedResponse() {
        return "0 Note is added successful.";
    }

    @Override
    public String getNoteRemovedResponse() {
        return "0 Note is removed.";
    }

    @Override
    public String getIncorrectRequestResponse(String request) {
        return "-1 Incorrect request: " + request;
    }


}
