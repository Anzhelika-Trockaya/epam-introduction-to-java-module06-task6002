package by.epam.task6002.main.menu;

import by.epam.task6002.bean.Note;
import by.epam.task6002.controller.Controller;
import by.epam.task6002.main.NoteParser;
import by.epam.task6002.main.NoteToFormattedStringConverter;
import by.epam.task6002.main.NotesParseException;
import by.epam.task6002.main.UserInput;

import java.util.List;

public class Menu {
    private final UserInput userInput;
    private final Controller notebookController;

    public Menu(UserInput userInput, Controller notebookController) {
        this.userInput = userInput;
        this.notebookController = notebookController;
    }

    public void startMainMenu() {
        String input;

        MenuShowerUtil.showMainMenu();
        input = userInput.readLine("Enter here: ");

        switch (input) {
            case "1" -> addNote();
            case "2" -> viewAllNotes();
            case "3" -> searchNotes();
            case "4" -> removeNote();
            case "0" -> exit();
        }
    }

    private void viewAllNotes() {
        List<Note> allNotes;
        String title;
        String response;

        allNotes = null;
        title = "All notes";

        response = notebookController.doAction("viewAllNotes");

        try {
            if (response.startsWith("0 All notes\n")) {
                allNotes = takeNotes(response, 12);
            }

            showNotes(title, allNotes);

            if (allNotes != null && !allNotes.isEmpty()) {
                sortAllNotes();
            } else {
                userInput.readLine("Enter any symbol to return!");
            }

        } catch (NotesParseException e) {
            MenuShowerUtil.showMessage("Error! Incorrect response!");
            userInput.readLine("Enter any symbol to return!");
        }

    }

    private List<Note> takeNotes(String response, int startPosition) throws NotesParseException {
        List<Note> allNotes;
        NoteParser noteParser;

        noteParser = new NoteParser();

        allNotes = noteParser.parseNotes(response.substring(startPosition));

        return allNotes;
    }

    private void showNotes(String title, List<Note> notes) {
        NoteToFormattedStringConverter noteToFormattedStringConverter;

        noteToFormattedStringConverter = new NoteToFormattedStringConverter();

        MenuShowerUtil.showTitle(title);
        if (notes != null && !notes.isEmpty()) {

            System.out.println(noteToFormattedStringConverter.notesToFormattedString(notes));

        } else {
            System.out.println(" - Notes not found! - ");
        }
    }

    private void sortAllNotes() {
        String request;
        String sortParamsString;
        String response;
        List<Note> sortedNotes;

        sortParamsString = generateSortNotesRequestsParamsString();

        while (sortParamsString != null) {
            request = "sortAllNotes " + sortParamsString;
            response = notebookController.doAction(request);

            if (response.startsWith("0 Sorted notes\n")) {

                try {
                    sortedNotes = takeNotes(response, 15);
                    showNotes("Sorted notes", sortedNotes);

                    sortParamsString = generateSortNotesRequestsParamsString();
                } catch (NotesParseException e) {
                    MenuShowerUtil.showMessage("Error! Incorrect response!");
                    userInput.readLine("Enter any symbol to return: ");
                }

            } else {
                System.out.println(response);
                userInput.readLine("Enter any symbol to return: ");
            }

        }

    }

    private String generateSortNotesRequestsParamsString() {
        String parameterName;
        String descendingSort;
        StringBuilder requestsParamsStringBuilder;
        String requestsParamsString;
        String input;

        MenuShowerUtil.showMenuOfSortingNote();
        input = userInput.readLine("Enter option number or any other symbol to return in main menu:");

        requestsParamsStringBuilder = new StringBuilder();

        if (input.length() == 2 && input.charAt(0) == '0') {
            descendingSort = "true";
        } else if (input.length() == 1) {
            descendingSort = "false";
        } else {
            return null;
        }

        switch (input.charAt(input.length() - 1)) {
            case '1' -> parameterName = "id";
            case '2' -> parameterName = "subject";
            case '3' -> parameterName = "dateOfCreation";
            case '4' -> parameterName = "email";
            case '5' -> parameterName = "text";
            default -> {
                return null;
            }
        }

        requestsParamsStringBuilder.append("sortsParam=\"").append(parameterName).append("\"")
                .append(" descendingSort=\"").append(descendingSort).append("\"");

        requestsParamsString = requestsParamsStringBuilder.toString();

        return requestsParamsString;
    }

    private void addNote() {
        String request;
        String response;
        String input;
        String subject;
        String email;
        String text;

        MenuShowerUtil.showTitleOfAddingNote();
        subject = "\"" + userInput.readLine("Enter subject: ").replaceAll("\"", "\\\\\"") + "\"";
        email = "\"" + userInput.readLine("Enter email: ").replaceAll("\"", "\\\\\"") + "\"";
        text = "\"" + readText().replaceAll("\"", "\\\\\"") + "\"";

        request = "addNote subject=" + subject + " email=" + email + " text=" + text;
        response = notebookController.doAction(request);

        MenuShowerUtil.showMessage(response);

        MenuShowerUtil.showExtraMenuOfAddingNote();
        input = userInput.readLine("Enter here: ");

        if (input.equals("1")) {
            addNote();
        }
    }

    private String readText() {
        String input;
        StringBuilder textBuilder;

        textBuilder = new StringBuilder();

        input = userInput.readLine("! Enter \" to end !\nEnter text:\n\"\n");

        if (!input.equals("\"")) {
            textBuilder.append(input);
            input = userInput.readLine("");

            while (!input.equals("\"")) {
                textBuilder.append("\n").append(input);
                input = userInput.readLine("");
            }
        }

        return textBuilder.toString();
    }

    private void searchNotes() {
        List<Note> filteredNotes;
        String request;
        String paramsString;
        String response;

        paramsString = generateSearchNotesRequestsParamsString();

        if (paramsString != null) {
            request = "filterNotes " + paramsString;
            response = notebookController.doAction(request);

            if (response.startsWith("0 Founded notes\n")) {

                try {
                    filteredNotes = takeNotes(response, 16);
                    showNotes("Founded notes", filteredNotes);
                    sortNotes(paramsString);
                } catch (NotesParseException e) {
                    MenuShowerUtil.showMessage("Error! Incorrect response!");
                    userInput.readLine("Enter any symbol to return!");
                }

            } else {
                MenuShowerUtil.showMessage(response);
                userInput.readLine("Enter any symbol to return: ");
            }
        }

    }

    private String generateSearchNotesRequestsParamsString() {
        String input;
        StringBuilder requestsParamsStringBuilder;
        String requestsParamsString;
        boolean isInputEnded;

        MenuShowerUtil.showMenuOfSearchingNote();
        input = userInput.readLine("Enter option number or any other symbol to return in main menu: ");
        isInputEnded = false;
        requestsParamsStringBuilder = new StringBuilder();

        while (!isInputEnded) {

            switch (input) {
                case "1" -> {
                    requestsParamsStringBuilder.append(" id=\"");
                    requestsParamsStringBuilder.append(userInput.readLine("Enter id: "));
                    requestsParamsStringBuilder.append("\"");
                }
                case "2" -> {
                    requestsParamsStringBuilder.append(" subjectPart=\"");
                    requestsParamsStringBuilder.append(
                            userInput.readLine("Enter subject: ").replaceAll("\"", "\\\"")
                    );
                    requestsParamsStringBuilder.append("\"");
                }
                case "3" -> {
                    requestsParamsStringBuilder.append(" emailPart=\"");
                    requestsParamsStringBuilder.append(
                            userInput.readLine("Enter email: ").replaceAll("\"", "\\\"")
                    );
                    requestsParamsStringBuilder.append("\"");
                }
                case "4" -> {
                    requestsParamsStringBuilder.append(" dateFrom=\"");
                    requestsParamsStringBuilder.append(userInput.readLine("Enter date FROM (YYYY-MM-DD): "));
                    requestsParamsStringBuilder.append("\"");
                    requestsParamsStringBuilder.append(" dateTo=\"");
                    requestsParamsStringBuilder.append(userInput.readLine("Enter date TO (YYYY-MM-DD): "));
                    requestsParamsStringBuilder.append("\"");
                }
                case "5" -> {
                    requestsParamsStringBuilder.append(" textPart=\"");
                    requestsParamsStringBuilder.append(
                            userInput.readLine("Enter text: ").replaceAll("\"", "\\\"")
                    );
                    requestsParamsStringBuilder.append("\"");
                }
                case "6" -> isInputEnded = true;
                default -> {
                    return null;
                }
            }

            if (!isInputEnded) {
                MenuShowerUtil.showExtraMenuOfSearchingNote();
                input = userInput.readLine("Enter option number or any other symbol to return in main menu: ");
            }
        }

        requestsParamsString = requestsParamsStringBuilder.substring(1);

        return requestsParamsString;
    }


    private void sortNotes(String filtersParamsString) {
        List<Note> sortedNotes;
        String request;
        String sortParams;
        String response;

        sortParams = generateSortNotesRequestsParamsString();

        while (sortParams != null) {

            request = "filterAndSortNotes " + filtersParamsString + " " + sortParams;
            response = notebookController.doAction(request);

            if (response.startsWith("0 Sorted notes\n")) {

                try {
                    sortedNotes = takeNotes(response, 15);
                    showNotes("Sorted notes", sortedNotes);

                    sortParams = generateSortNotesRequestsParamsString();
                } catch (NotesParseException e) {
                    MenuShowerUtil.showMessage("Error! Incorrect response!");
                }

            } else {
                MenuShowerUtil.showMessage(response);
                userInput.readLine("Enter any symbol to return: ");
                return;
            }

        }

    }

    private void removeNote() {
        String request;
        String response;
        String input;
        String id;

        MenuShowerUtil.showTitleOfRemovingNote();
        id = userInput.readLine("Enter id: ");

        request = "removeNote id=\"" + id + "\"";
        response = notebookController.doAction(request);

        MenuShowerUtil.showMessage(response);

        MenuShowerUtil.showExtraMenuOfRemovingNote();
        input = userInput.readLine("Enter here: ");

        if (input.equals("1")) {
            removeNote();
        }
    }

    private void exit() {
        MenuShowerUtil.showMessage(notebookController.doAction("saveData"));
        System.exit(0);
    }


}
