package by.epam.task6002.controller.impl.command.impl;

import by.epam.task6002.controller.impl.command.Command;

public class NoCommandCommand implements Command {
    @Override
    public String execute(String[] params) {
        return null;
    }
}