package by.epam.task6002.controller.impl.command.impl;

import by.epam.task6002.bean.Note;
import by.epam.task6002.controller.ControllerException;
import by.epam.task6002.controller.impl.command.Command;
import by.epam.task6002.service.NotebookService;
import by.epam.task6002.service.ServiceProvider;
import by.epam.task6002.view.UserActionView;
import by.epam.task6002.view.ViewProvider;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class FilterNotesCommand implements Command {
    @Override
    public String execute(String[] params) {
        ViewProvider viewProvider;
        UserActionView userActionView;

        String title;
        String response;
        List<Note> filteredNotes;

        viewProvider = ViewProvider.getInstance();
        userActionView = viewProvider.getUserActionView();


        try {

            filteredNotes = filterNotes(params);

            if (!filteredNotes.isEmpty()) {
                title = "Founded notes";
                response = userActionView.getViewNotesResponse(title, filteredNotes);
            } else {
                response = userActionView.getNotesNotFoundResponse();
            }

        } catch (ControllerException controllerException) {
            response = userActionView.getExceptionResponse(controllerException);
        }


        return response;
    }

    public List<Note> filterNotes(String[] params) throws ControllerException {
        ServiceProvider serviceProvider;
        NotebookService notebookService;
        List<Note> filteredNotes;

        serviceProvider = ServiceProvider.getInstance();
        notebookService = serviceProvider.getNotebookService();

        if (areParamsCorrect(params)) {
            filteredNotes = notebookService.getAllNotes();

            for (String param : params) {
                filteredNotes = filterNotes(notebookService, filteredNotes, param);
            }

            return filteredNotes;

        } else {
            throw new ControllerException("Incorrect params: " + Arrays.toString(params));
        }
    }

    private boolean areParamsCorrect(String[] params) {
        String[] paramsRegexes;
        String dateRegex;
        int maxNumberOfParams;
        boolean isCurrentParamCorrect;

        maxNumberOfParams = 6;
        paramsRegexes = new String[maxNumberOfParams];

        dateRegex = "([12][0-9][0-9][0-9]-(((0[13578]|1[02])-(0[1-9]|1[0-9]|2[0-9]|3[01]))" +
                "|((0[469]|11)-(0[1-9]|1[0-9]|2[0-9]|3[0]))" +
                "|((02)(-(0[1-9]|1[0-9]|2[0-8])))))" +
                "|(([12][0-9]([02468][048]|[13579][26]))-(02)-(29))";

        paramsRegexes[0] = "id=\"\\d{13,}\"";
        paramsRegexes[1] = "subjectPart=\".+\"";
        paramsRegexes[2] = "emailPart=\".+\"";
        paramsRegexes[3] = "dateFrom=\".+\"";
        paramsRegexes[4] = "dateTo=\".+\"";
        paramsRegexes[5] = "textPart=\".+\"";

        for (int i = 0; i < params.length; i++) {

            isCurrentParamCorrect = false;

            for (int j = i; j < paramsRegexes.length; j++) {

                if (params[i].matches(paramsRegexes[j])) {
                    isCurrentParamCorrect = true;
                    break;
                }

            }

            if (!isCurrentParamCorrect) {
                return false;
            }

        }

        return true;
    }

    private List<Note> filterNotes(NotebookService notebookService, List<Note> notes, String param) throws ControllerException {
        List<Note> filteredNotes;
        String[] paramParts;
        String paramName;
        String paramValue;
        long id;
        LocalDate date;

        paramParts = splitParamsString(param);
        paramName = paramParts[0];
        paramValue = paramParts[1];

        switch (paramName) {
            case "id" -> {
                id = Long.parseLong(paramValue);
                filteredNotes = notebookService.filterNotesById(notes, id);
            }
            case "subjectPart" -> filteredNotes = notebookService.filterNotesBySubject(notes, paramValue);
            case "emailPart" -> filteredNotes = notebookService.filterNotesByEmail(notes, paramValue);
            case "dateFrom" -> {
                date = LocalDate.parse(paramValue);
                filteredNotes = notebookService.filterNotesByDateOfCreation(notes, date, LocalDate.now());
            }
            case "dateTo" -> {
                date = LocalDate.parse(paramValue);
                filteredNotes = notebookService.filterNotesByDateOfCreation(notes, LocalDate.of(1900, 1, 1), date);
            }
            case "textPart" -> filteredNotes = notebookService.filterNotesByText(notes, paramValue);
            default -> throw new ControllerException("Incorrect params name: " + paramName);
        }

        return filteredNotes;
    }

    private String[] splitParamsString(String paramsString) {
        int i;
        String[] paramArray;
        StringBuilder paramNameBuilder;
        StringBuilder paramValueBuilder;

        paramArray = new String[2];
        paramNameBuilder = new StringBuilder();
        paramValueBuilder = new StringBuilder();

        i = 0;
        while (paramsString.charAt(i) != '=') {
            paramNameBuilder.append(paramsString.charAt(i));
            i++;
        }

        for (i = i + 1; i < paramsString.length(); i++) {
            paramValueBuilder.append(paramsString.charAt(i));
        }

        paramArray[0] = paramNameBuilder.toString();
        paramArray[1] = paramValueBuilder.substring(1, paramValueBuilder.length() - 1).replaceAll("\\\\\"", "\"");

        return paramArray;
    }
}
