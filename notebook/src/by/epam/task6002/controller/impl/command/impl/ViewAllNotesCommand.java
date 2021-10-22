package by.epam.task6002.controller.impl.command.impl;

import by.epam.task6002.bean.Note;
import by.epam.task6002.controller.impl.command.Command;
import by.epam.task6002.service.NotebookService;
import by.epam.task6002.service.ServiceProvider;
import by.epam.task6002.view.UserActionView;
import by.epam.task6002.view.ViewProvider;

import java.util.List;

public class ViewAllNotesCommand implements Command {
    @Override
    public String execute(String[] params) {
        ServiceProvider serviceProvider;
        NotebookService notebookService;
        ViewProvider viewProvider;
        UserActionView userActionView;
        String title;
        String response;

        List<Note> allNotes;

        serviceProvider = ServiceProvider.getInstance();
        viewProvider = ViewProvider.getInstance();
        notebookService = serviceProvider.getNotebookService();
        userActionView = viewProvider.getUserActionView();

        title = "All notes";
        allNotes = notebookService.getAllNotes();

        if (!allNotes.isEmpty()) {
            response = userActionView.getViewNotesResponse(title, allNotes);
        } else {
            response = userActionView.getNotesNotFoundResponse();
        }

        return response;
    }
}
