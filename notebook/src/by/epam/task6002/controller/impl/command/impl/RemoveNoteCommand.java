package by.epam.task6002.controller.impl.command.impl;

import by.epam.task6002.controller.impl.command.Command;
import by.epam.task6002.service.NotebookService;
import by.epam.task6002.service.ServiceProvider;
import by.epam.task6002.view.UserActionView;
import by.epam.task6002.view.ViewProvider;

public class RemoveNoteCommand implements Command {
    @Override
    public String execute(String[] params) {
        ServiceProvider serviceProvider;
        NotebookService notebookService;
        ViewProvider viewProvider;
        UserActionView userActionView;

        String response;
        long id;

        serviceProvider = ServiceProvider.getInstance();
        viewProvider = ViewProvider.getInstance();
        notebookService = serviceProvider.getNotebookService();
        userActionView = viewProvider.getUserActionView();

        if (params.length == 1 && params[0].matches("id=\"\\d{13,}\"")) {

            id = Long.parseLong(params[0].substring(4, params[0].length() - 1));
            notebookService.removeNote(id);
            response = userActionView.getNoteRemovedResponse();

        } else {
            response = userActionView.getIncorrectParamsResponse(params);
        }


        return response;
    }
}
