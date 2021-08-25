package com.epam.task6002.logic;

import com.epam.task6002.model.Note;
import com.epam.task6002.model.NoteBook;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.util.stream.Collectors;

public class NoteBookProcessor {
    private final Scanner scanner;
    private final NoteBook notebook;
    private String input;
    private boolean closeSelected;

    public NoteBookProcessor(NoteBook notebook) {
        scanner = new Scanner(System.in);
        this.closeSelected = false;
        this.notebook = notebook;
    }

    public void start() {
        while (!closeSelected) {
            MenuShowerUtil.showMainMenu();
            input = scanner.nextLine();
            switch (input) {
                case "1" -> processAddingNote();
                case "2" -> processViewingNotes("All notes", notebook.getNotes(), true);
                case "3" -> processSearchingNotes();
                case "0" -> closeSelected = true;
            }
        }
    }

    private void processViewingNotes(String category, ArrayList<Note> notes, boolean createIndent) {
        if (createIndent) {
            System.out.println(MenuShowerUtil.INDENT);
        }
        System.out.println("\n\n\n----------------------------   " + category + "   ----------------------------\n");
        if (!notes.isEmpty()) {
            for (Note note : notes) {
                note.print();
            }
            System.out.println("\n\n");
            System.out.println(" \"sort\" - \"1\"");
            System.out.println(" Enter any other symbol to return");
            input = scanner.nextLine();
            if (input.equals("1")) {
                processSortingNotes(notes);
                processViewingNotes(category, notes, createIndent);
            }
        } else {
            System.out.println(" - Notes not found! - ");
            System.out.println(" Enter any symbol to return");
            scanner.nextLine();
        }

    }

    private void processSortingNotes(ArrayList<Note> notes) {
        MenuShowerUtil.showMenuOfSortingNote();
        input = scanner.nextLine();
        switch (input) {
            case "1" -> notes.sort(Comparator.comparingLong(Note::getId));
            case "01" -> notes.sort(Comparator.comparingLong(Note::getId).reversed());
            case "2" -> notes.sort(Comparator.comparing(Note::getSubject));
            case "02" -> notes.sort(Comparator.comparing(Note::getSubject).reversed());
            case "3" -> notes.sort(Comparator.comparing(Note::getDateOfCreation));
            case "03" -> notes.sort(Comparator.comparing(Note::getDateOfCreation).reversed());
            case "4" -> notes.sort(Comparator.comparing(Note::getEmail));
            case "04" -> notes.sort(Comparator.comparing(Note::getEmail).reversed());
            case "5" -> notes.sort(Comparator.comparing(Note::getText));
            case "05" -> notes.sort(Comparator.comparing(Note::getText).reversed());
        }
    }

    private void processAddingNote() {
        Note note;

        MenuShowerUtil.showTitleOfAddingNote();

        try {
            note = readNote();
            notebook.addNote(note);
        } catch (IllegalArgumentException exception) {
            System.out.println("Note not added! " + exception.getMessage());
        }

        MenuShowerUtil.showExtraMenuOfAddingNote();
        input = scanner.nextLine();
        if (input.equals("1")) {
            processAddingNote();
        }
    }

    private Note readNote() {
        String subject;
        String email;
        StringBuilder text = new StringBuilder();
        System.out.println("Subject: ");
        subject = scanner.nextLine();
        System.out.println("Email: ");
        email = scanner.nextLine();
        System.out.println("! Enter \" to complete input !");

        System.out.println("Text: \n\"");
        input = scanner.nextLine();
        while (!input.equals("\"")) {
            text.append(input);
            input = scanner.nextLine();
        }
        return new Note(subject, email, text.toString());
    }

    private void processSearchingNotes() {
        ArrayList<Note> filteredNotes = notebook.getNotes();
        boolean inputEnded = false;

        MenuShowerUtil.showMenuOfSearchingNote();
        input = scanner.nextLine();
        while (!inputEnded) {
            try {
                switch (input) {
                    case "1" -> filteredNotes = filterNotesById(filteredNotes);
                    case "2" -> filteredNotes = filterNotesBySubject(filteredNotes);
                    case "3" -> filteredNotes = filterNotesByEmail(filteredNotes);
                    case "4" -> filteredNotes = filterNotesByDateOfCreation(filteredNotes);
                    case "5" -> filteredNotes = filterNotesByText(filteredNotes);
                    default -> inputEnded = true;
                }
                if (!inputEnded) {
                    processViewingNotes("Founded notes", filteredNotes, false);
                    if (!filteredNotes.isEmpty()) {
                        MenuShowerUtil.showExtraMenuOfSearchingNote();
                        input = scanner.nextLine();
                    }
                }
            } catch (IllegalArgumentException exception) {
                System.out.println("\n ! " + exception.getMessage());
                MenuShowerUtil.showExtraMenuOfSearchingNote();
                input = scanner.nextLine();
            }
        }
    }

    private ArrayList<Note> filterNotesById(ArrayList<Note> notes) {
        String idString;
        long id;
        System.out.print("Enter id or \"0\" to return: ");
        idString = scanner.nextLine();
        if (idString.matches("^\\d+$") && !idString.equals("0")) {
            id = Long.parseLong(idString);
            notes = (ArrayList<Note>) notes.stream()
                    .filter((note) -> (note.getId() == id))
                    .collect(Collectors.toList());
        } else {
            System.out.println(" Incorrect id!");
            System.out.print("Enter any symbol to return: ");
            scanner.nextLine();
            throw new IllegalArgumentException("Incorrect id!");
        }
        return notes;
    }

    private ArrayList<Note> filterNotesBySubject(ArrayList<Note> notes) {
        System.out.print("Enter subject(part of subject) or press \"enter\" to return: ");
        input = scanner.nextLine();
        if (!input.isEmpty()) {
            notes = (ArrayList<Note>) notes.stream()
                    .filter((note) -> (note.getSubject().contains(input)))
                    .collect(Collectors.toList());
        } else {
            throw new IllegalArgumentException("The subject cannot be an empty string!");
        }
        return notes;
    }

    private ArrayList<Note> filterNotesByEmail(ArrayList<Note> notes) {
        System.out.print("Enter email(part of email) or press \"enter\" to return: ");
        input = scanner.nextLine();
        if (!input.isEmpty()) {
            notes = (ArrayList<Note>) notes.stream()
                    .filter((note) -> (note.getEmail().contains(input)))
                    .collect(Collectors.toList());
        } else {
            throw new IllegalArgumentException("The word cannot be an empty string!");
        }
        return notes;
    }

    private ArrayList<Note> filterNotesByDateOfCreation(ArrayList<Note> notes) {
        LocalDate dateFrom;
        LocalDate dateTo;

        System.out.print("Date of creation FROM (yyyy-mm-dd): ");
        dateFrom = readDate();
        System.out.print("Date of creation TO (yyyy-mm-dd): ");
        dateTo = readDate();

        if (!dateFrom.isAfter(dateTo)) {
            notes = (ArrayList<Note>) notes.stream()
                    .filter((note) -> (!note.getDateOfCreation().isAfter(dateTo)))
                    .filter((note) -> (!note.getDateOfCreation().isBefore(dateFrom)))
                    .collect(Collectors.toList());
        } else {
            throw new IllegalArgumentException("Date TO cannot be before Date FROM!");
        }
        return notes;
    }

    private LocalDate readDate() {
        String dateRegex = "([12][0-9][0-9][0-9]-(" +
                "((0[13578]|1[02])-(0[1-9]|1[0-9]|2[0-9]|3[01]))" +
                "|((0[469]|11)-(0[1-9]|1[0-9]|2[0-9]|3[0]))" +
                "|((02)(-(0[1-9]|1[0-9]|2[0-8])))))" +
                "|(([12][0-9]([02468][048]|[13579][26]))-(02)-(29))";
        input = scanner.nextLine();
        if (input.matches(dateRegex)) {
            return LocalDate.parse(input);
        } else {
            throw new IllegalArgumentException("Incorrect date!");
        }
    }

    private ArrayList<Note> filterNotesByText(ArrayList<Note> notes) {
        System.out.print("Enter the word in the text or press \"enter\" to return: ");
        input = scanner.nextLine();
        if (!input.isEmpty()) {
            notes = (ArrayList<Note>) notes.stream()
                    .filter((note) -> (note.getText().toLowerCase().contains(input.toLowerCase())))
                    .collect(Collectors.toList());
        } else {
            throw new IllegalArgumentException("The word cannot be an empty string!");
        }
        return notes;
    }
}
