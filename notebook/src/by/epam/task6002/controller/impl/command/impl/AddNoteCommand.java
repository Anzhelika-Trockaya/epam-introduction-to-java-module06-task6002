package by.epam.task6002.controller.impl.command.impl;

import by.epam.task6002.bean.Note;
import by.epam.task6002.controller.ControllerException;
import by.epam.task6002.controller.impl.command.Command;
import by.epam.task6002.controller.impl.command.impl.creator.NoteCreator;
import by.epam.task6002.service.NotebookService;
import by.epam.task6002.service.ServiceProvider;
import by.epam.task6002.view.UserActionView;
import by.epam.task6002.view.ViewProvider;

public class AddNoteCommand implements Command {
    @Override
    public String execute(String[] params) {
        ServiceProvider serviceProvider;
        NotebookService notebookService;
        ViewProvider viewProvider;
        UserActionView userActionView;
        NoteCreator noteCreator;

        String response;
        Note note;

        serviceProvider = ServiceProvider.getInstance();
        viewProvider = ViewProvider.getInstance();
        notebookService = serviceProvider.getNotebookService();
        userActionView = viewProvider.getUserActionView();
        noteCreator = new NoteCreator();

        try {
            note = noteCreator.createNote(params);
            notebookService.addNote(note);
            response = userActionView.getNoteAddedResponse();

        } catch (ControllerException controllerException) {
            response = userActionView.getExceptionResponse(controllerException);
        }

        return response;
    }

}
