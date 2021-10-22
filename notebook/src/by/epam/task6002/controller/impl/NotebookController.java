package by.epam.task6002.controller.impl;

import by.epam.task6002.controller.Controller;
import by.epam.task6002.controller.impl.command.Command;
import by.epam.task6002.controller.impl.command.CommandProvider;
import by.epam.task6002.service.NotebookService;
import by.epam.task6002.service.ServiceException;
import by.epam.task6002.service.ServiceProvider;
import by.epam.task6002.view.UserActionView;
import by.epam.task6002.view.ViewProvider;

import java.util.ArrayList;
import java.util.List;

public class NotebookController implements Controller {
    private final CommandProvider commandProvider = CommandProvider.getInstance();

    public NotebookController() {
        ServiceProvider serviceProvider;
        NotebookService notebookService;

        serviceProvider = ServiceProvider.getInstance();
        notebookService = serviceProvider.getNotebookService();

        try {
            notebookService.readAllNotes();
        } catch (ServiceException serviceException) {
            System.exit(-1);
        }
    }

    @Override
    public String doAction(String request) {
        String commandName;
        Command currentCommand;
        String response;
        String[] params;

        ViewProvider viewProvider;
        UserActionView userActionView;

        if (request != null) {

            commandName = takeCommandName(request);
            currentCommand = commandProvider.getCommand(commandName);
            params = takeParams(request);

            response = currentCommand.execute(params);

        } else {
            viewProvider = ViewProvider.getInstance();
            userActionView = viewProvider.getUserActionView();

            response = userActionView.getIncorrectRequestResponse(request);
        }

        return response;
    }

    private String takeCommandName(String request) {
        StringBuilder commandNameBuilder;

        commandNameBuilder = new StringBuilder();

        int i = 0;

        while (i < request.length() && request.charAt(i) != ' ') {
            commandNameBuilder.append(request.charAt(i));
            i++;

        }

        return commandNameBuilder.toString();
    }

    private String[] takeParams(String request) {
        String[] paramsArray;
        List<String> paramsList;

        paramsArray = null;
        paramsList = takeParamsList(request);

        if (paramsList.size() > 0) {
            paramsArray = paramsList.toArray(new String[0]);
        }

        return paramsArray;
    }

    private List<String> takeParamsList(String request) {
        List<String> paramsList;
        StringBuilder parameterBuilder;
        char currentChar;
        char prevChar;
        boolean isQuotationMarkOpen;

        paramsList = new ArrayList<>();
        parameterBuilder = new StringBuilder();

        isQuotationMarkOpen = false;

        for (int i = foundStartIndexOfParams(request); i < request.length(); i++) {

            currentChar = request.charAt(i);

            if (currentChar == '"') {
                prevChar = request.charAt(i - 1);

                if (prevChar != '\\') {
                    isQuotationMarkOpen = !isQuotationMarkOpen;
                }
            }

            if (isQuotationMarkOpen || currentChar != ' ') {
                parameterBuilder.append(currentChar);
            } else {
                paramsList.add(parameterBuilder.toString());
                parameterBuilder = new StringBuilder();
            }

        }

        if (!parameterBuilder.isEmpty()) {
            paramsList.add(parameterBuilder.toString());
        }

        return paramsList;
    }

    private int foundStartIndexOfParams(String request) {
        int i = 1;

        while (i < request.length() && request.charAt(i - 1) != ' ') {
            i++;
        }

        return i;
    }
}
