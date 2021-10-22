package by.epam.task6002.controller.impl.command.impl;

import by.epam.task6002.bean.Note;
import by.epam.task6002.controller.ControllerException;
import by.epam.task6002.controller.impl.command.Command;
import by.epam.task6002.service.NotebookService;
import by.epam.task6002.service.ServiceException;
import by.epam.task6002.service.ServiceProvider;
import by.epam.task6002.view.UserActionView;
import by.epam.task6002.view.ViewProvider;

import java.util.Arrays;
import java.util.List;

public class FilterAndSortNotesCommand implements Command {

    @Override
    public String execute(String[] params) {
        ViewProvider viewProvider;
        UserActionView userActionView;

        FilterNotesCommand filterNotesCommand;
        List<Note> filteredNotes;
        List<Note> sortedNotes;

        String[] filtersParams;
        String[] sortsParams;

        String title;
        String response;

        viewProvider = ViewProvider.getInstance();
        userActionView = viewProvider.getUserActionView();
        filterNotesCommand = new FilterNotesCommand();

        title = "Sorted notes";

        try {
            filtersParams = takeFiltersParams(params);
            filteredNotes = filterNotesCommand.filterNotes(filtersParams);
            sortsParams = takeSortsParams(params);
            sortedNotes = sortNotes(filteredNotes, sortsParams);

            if (!sortedNotes.isEmpty()) {
                response = userActionView.getViewNotesResponse(title, sortedNotes);
            } else {
                response = userActionView.getNotesNotFoundResponse();
            }

        } catch (ControllerException | ServiceException controllerException) {
            response = userActionView.getExceptionResponse(controllerException);
        }

        return response;
    }

    private String[] takeSortsParams(String[] params) throws ControllerException {
        String[] sortsParams;
        int sortParamsArrayLength;
        int minParamsNumber;

        minParamsNumber = 3;
        if (params.length >= minParamsNumber) {

            sortParamsArrayLength = 2;
            sortsParams = new String[sortParamsArrayLength];
            sortsParams[0] = params[params.length - 2];
            sortsParams[1] = params[params.length - 1];

            return sortsParams;

        } else {
            throw new ControllerException("Incorrect params: " + Arrays.toString(params));
        }
    }

    private String[] takeFiltersParams(String[] params) throws ControllerException {
        String[] filtersParams;
        int filtersParamsArrayLength;
        int sortParamsArrayLength;
        int minParamsNumber;

        minParamsNumber = 3;
        if (params.length >= minParamsNumber) {
            sortParamsArrayLength = 2;
            filtersParamsArrayLength = params.length - sortParamsArrayLength;

            filtersParams = new String[filtersParamsArrayLength];
            System.arraycopy(params, 0, filtersParams, 0, filtersParamsArrayLength);

            return filtersParams;

        } else {
            throw new ControllerException("Incorrect params of filter end sort: " + Arrays.toString(params));
        }
    }

    private List<Note> sortNotes(List<Note> notes, String[] sortsParams) throws ControllerException, ServiceException {
        ServiceProvider serviceProvider;
        NotebookService notebookService;

        String parameterName;
        boolean parameterDescendingSort;

        List<Note> sortedNotes;

        serviceProvider = ServiceProvider.getInstance();
        notebookService = serviceProvider.getNotebookService();

        if (areSortsParamsCorrect(sortsParams)) {
            parameterName = sortsParams[0].substring(12, sortsParams[0].length() - 1);
            parameterDescendingSort = Boolean.parseBoolean(sortsParams[1].substring(16, sortsParams[1].length() - 1));

            sortedNotes = notebookService.sortNotes(notes, parameterName, parameterDescendingSort);

            return sortedNotes;
        } else {
            throw new ControllerException("Incorrect sorts params: " + Arrays.toString(sortsParams));
        }
    }

    private boolean areSortsParamsCorrect(String[] sortsParams) {
        String parameterNameRegex;
        String parameterDescendingSortRegex;

        parameterNameRegex = "sortsParam=\"(id|subject|dateOfCreation|email|text)\"";
        parameterDescendingSortRegex = "descendingSort=\"(true|false)\"";

        return sortsParams[0].matches(parameterNameRegex) && sortsParams[1].matches(parameterDescendingSortRegex);
    }
}
