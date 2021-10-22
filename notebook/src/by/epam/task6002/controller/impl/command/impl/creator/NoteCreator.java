package by.epam.task6002.controller.impl.command.impl.creator;

import by.epam.task6002.bean.Note;
import by.epam.task6002.controller.ControllerException;

import java.time.LocalDate;
import java.util.Date;

public class NoteCreator {
    public Note createNote(String[] params) throws ControllerException {
        Note note;
        long id;
        String subject;
        String email;
        String text;

        if (areParamsCorrect(params)) {
            id = new Date().getTime();
            subject = params[0].substring(9, params[0].length() - 1).replaceAll("\\\\\"", "\"");
            email = params[1].substring(7, params[1].length() - 1).replaceAll("\\\\\"", "\"");
            text = params[2].substring(6, params[2].length() - 1).replaceAll("\\\\\"", "\"");
            note = new Note(id, LocalDate.now(), subject, email, text);

            return note;
        } else {
            throw new ControllerException("Impossible to create Note! Incorrect params!");
        }
    }

    private boolean areParamsCorrect(String[] params) {
        int numberOfParams;
        String[] paramsRegexes;

        numberOfParams = 3;
        paramsRegexes = new String[numberOfParams];

        paramsRegexes[0] = "subject=\".+\"";
        paramsRegexes[1] = "email=\".+\"";
        paramsRegexes[2] = "text=\"(\n|.)*\"";

        return params != null
                && params.length == 3
                && params[0].matches(paramsRegexes[0])
                && params[1].matches(paramsRegexes[1])
                && params[2].matches(paramsRegexes[2]);
    }
}
