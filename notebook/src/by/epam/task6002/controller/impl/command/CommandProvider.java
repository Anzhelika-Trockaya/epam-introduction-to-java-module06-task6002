package by.epam.task6002.controller.impl.command;

import by.epam.task6002.controller.impl.command.impl.*;

import java.util.HashMap;
import java.util.Map;

public class CommandProvider {
    private static final CommandProvider instance=new CommandProvider();
    private final Map<String, Command> commands;

    private CommandProvider(){
        commands=new HashMap<>();

        commands.put("viewAllNotes", new ViewAllNotesCommand());
        commands.put("addNote", new AddNoteCommand());
        commands.put("removeNote", new RemoveNoteCommand());
        commands.put("sortNotes", new SortNotesCommand());
        commands.put("filterNotes", new FilterNotesCommand());
        commands.put("noCommand", new NoCommandCommand());
    }

    public static CommandProvider getInstance(){
        return instance;
    }

    public Command getCommand(String commandName){
        Command command;

        if(commands.containsKey(commandName)){
            command=commands.get(commandName);
        } else{
            command=commands.get("noCommand");
        }

        return command;
    }
}
