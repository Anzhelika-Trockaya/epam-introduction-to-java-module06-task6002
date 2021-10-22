package by.epam.task6002.controller.impl.command.impl;

import by.epam.task6002.bean.Note;
import by.epam.task6002.controller.impl.command.Command;
import by.epam.task6002.service.NotebookService;
import by.epam.task6002.service.ServiceException;
import by.epam.task6002.service.ServiceProvider;
import by.epam.task6002.view.UserActionView;
import by.epam.task6002.view.ViewProvider;

import java.util.List;

public class SortAllNotesCommand implements Command {
    @Override
    public String execute(String[] params) {
        ServiceProvider serviceProvider;
        NotebookService notebookService;
        ViewProvider viewProvider;
        UserActionView userActionView;

        String parameterNameRegex;
        String parameterDescendingSortRegex;
        String title;
        String parameterName;
        boolean descendingSort;
        String response;
        List<Note> allNotes;
        List<Note> sortedNotes;

        parameterNameRegex = "sortsParam=\"(id|subject|dateOfCreation|email|text)\"";
        parameterDescendingSortRegex = "descendingSort=\"(true|false)\"";

        serviceProvider = ServiceProvider.getInstance();
        viewProvider = ViewProvider.getInstance();
        notebookService = serviceProvider.getNotebookService();
        userActionView = viewProvider.getUserActionView();

        if (params[0].matches(parameterNameRegex) && params[1].matches(parameterDescendingSortRegex)) {

            title = "Sorted notes";
            parameterName = params[0].substring(12, params[0].length() - 1);
            descendingSort = Boolean.parseBoolean(params[1].substring(16, params[1].length() - 1));
            allNotes = notebookService.getAllNotes();

            if (!allNotes.isEmpty()) {

                try {
                    sortedNotes = notebookService.sortNotes(allNotes, parameterName, descendingSort);
                    response = userActionView.getViewNotesResponse(title, sortedNotes);

                } catch (ServiceException serviceException) {
                    response = userActionView.getExceptionResponse(serviceException);
                }

            } else {
                response = userActionView.getNotesNotFoundResponse();
            }

        } else {
            response = userActionView.getIncorrectParamsResponse(params);
        }

        return response;
    }
}
