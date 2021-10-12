package by.epam.task6002.delete.data;

import by.epam.task6002.bean.Note;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataUtil {
    private static final String FILE_NAME = "notebook/src/com/epam/task6002/data/notes.txt";

    public static void loadData(ArrayList<Note> notes) throws IOException {
        String currentNoteString;
        Note currentNote;

        Pattern notePattern = Pattern.compile("^\\{\\s" +
                "\"id\":\\s(\\d{13,}),\\s" +
                "\"dateOfCreation\":\\s\"(.+)\",\\s" +
                "\"subject\":\\s\"(.*)\",\\s" +
                "\"email\":\\s\"(.+)\",\\s" +
                "\"text\":\\s\"((\\n|.)*)\"" +
                "\\s}\\n?");
        Matcher matcher;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            while (reader.ready()) {
                currentNoteString = reader.readLine();
                if (currentNoteString.equals("\n")) {
                    continue;
                }
                matcher = notePattern.matcher(currentNoteString);
                while (!matcher.find()) {
                    currentNoteString += "\n" + reader.readLine();
                    matcher = notePattern.matcher(currentNoteString);
                }
                currentNote = parseNote(matcher);
                notes.add(currentNote);
            }
        }
    }

    private static Note parseNote(Matcher matcher) {
        Note note;
        long id;
        LocalDate dateOfCreation;
        String email;
        String subject;
        String text;
        id = Long.parseLong(matcher.group(1));
        dateOfCreation = LocalDate.parse(matcher.group(2));
        subject = matcher.group(3).replaceAll("\\\\\\{", "{")
                .replaceAll("\\\\}", "}")
                .replaceAll("\\\\\"", "\"");
        email = matcher.group(4).replaceAll("\\\\\\{", "{")
                .replaceAll("\\\\}", "}")
                .replaceAll("\\\\\"", "\"");
        text = matcher.group(5).replaceAll("\\\\\\{", "{")
                .replaceAll("\\\\}", "}")
                .replaceAll("\\\\\"", "\"");
        note = new Note();
        note.setId(id);
        note.setEmail(email);
        note.setDateOfCreation(dateOfCreation);
        note.setSubject(subject);
        note.setText(text);
        return note;
    }

    public static void unloadData(ArrayList<Note> notes) throws IOException {
        String noteString;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Note note : notes) {
                noteString = convertNoteToString(note);
                writer.write(noteString);
                writer.newLine();
            }
        }
    }

    private static String convertNoteToString(Note note) {
        return "{ " +
                "\"id\": " + note.getId() + ", " +
                "\"dateOfCreation\": \"" + note.getDateOfCreation().toString() + "\", " +
                "\"subject\": \"" + note.getSubject().replaceAll("}", "\\\\}")
                + "\", " +
                "\"email\": \"" + note.getEmail().replaceAll("}", "\\\\}") + "\", " +
                "\"text\": \"" + note.getText().replaceAll("}", "\\\\}") + "\"" +
                " }";
    }

}
