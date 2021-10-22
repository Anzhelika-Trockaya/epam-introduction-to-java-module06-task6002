package by.epam.task6002.controller.impl.command.impl;

import by.epam.task6002.controller.impl.command.Command;
import by.epam.task6002.view.UserActionView;
import by.epam.task6002.view.ViewProvider;

public class NoCommandCommand implements Command {
    @Override
    public String execute(String[] params) {
        ViewProvider viewProvider;
        UserActionView userActionView;

        viewProvider=ViewProvider.getInstance();
        userActionView=viewProvider.getUserActionView();

        return userActionView.getExceptionResponse("Incorrect command name!");
    }
}