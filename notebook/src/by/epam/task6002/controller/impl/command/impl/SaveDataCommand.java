package by.epam.task6002.controller.impl.command.impl;

import by.epam.task6002.controller.impl.command.Command;
import by.epam.task6002.service.NotebookService;
import by.epam.task6002.service.ServiceException;
import by.epam.task6002.service.ServiceProvider;
import by.epam.task6002.view.UserActionView;
import by.epam.task6002.view.ViewProvider;

public class SaveDataCommand implements Command {
    @Override
    public String execute(String[] params) {
        ServiceProvider serviceProvider;
        NotebookService notebookService;
        ViewProvider viewProvider;
        UserActionView userActionView;

        String response;

        serviceProvider = ServiceProvider.getInstance();
        notebookService = serviceProvider.getNotebookService();
        viewProvider = ViewProvider.getInstance();
        userActionView = viewProvider.getUserActionView();

        try {
            notebookService.writeNotes(notebookService.getAllNotes());
            response = userActionView.getDataUnloadedResponse();
        } catch (ServiceException serviceException) {
            response = userActionView.getExceptionToUnloadDataResponse();
        }

        return response;
    }
}
