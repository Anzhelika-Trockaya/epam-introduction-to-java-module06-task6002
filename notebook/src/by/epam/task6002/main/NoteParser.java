package by.epam.task6002.main;

import by.epam.task6002.bean.Note;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NoteParser {

    public List<Note> parseNotes(String notesString) throws NotesParseException {
        List<Note> notes;
        Note currentNote;


        String[] lines;

        String notesStartRegex;
        String notesEnd;


        notes = new ArrayList<>();

        notesStartRegex = "^Note\\{\\s\"id\":\\s\"(\\d{13,})\",\\s\"dateOfCreation\":\\s\"(.+)\",\\s\"subject\":\\s\"(.*)\",\\s" +
                "\"email\":\\s\"(.+)\",\\s\"text\":\\s(\").*";
        notesEnd = "\" }";


        lines = notesString.split("\n");

        for (int i = 0; i < lines.length; i++) {

            if (lines[i].matches(notesStartRegex)) {
                currentNote = parseNote(lines[i]);

                while (!lines[i].endsWith(notesEnd)) {
                    i++;
                    if (i < lines.length) {
                        currentNote = addText(currentNote, lines[i]);
                    } else {
                        throw new NotesParseException("Incorrect data: " + notesString + " line: " + lines[i - 1] + " is last!");
                    }
                }

                notes.add(currentNote);

            } else {
                throw new NotesParseException("Incorrect data: " + notesString + "line: " + lines[i] + "don't matches note regex!");
            }

        }


        return notes;
    }

    private Note parseNote(String line) throws NotesParseException {
        String notesStartRegex;
        String dateRegex;

        Pattern notePattern;
        Matcher noteMatcher;

        long id;
        LocalDate dateOfCreation;
        String email;
        String subject;
        String text;

        dateRegex = "([12][0-9][0-9][0-9]-(((0[13578]|1[02])-(0[1-9]|1[0-9]|2[0-9]|3[01]))" +
                "|((0[469]|11)-(0[1-9]|1[0-9]|2[0-9]|3[0]))" +
                "|((02)(-(0[1-9]|1[0-9]|2[0-8])))))" +
                "|(([12][0-9]([02468][048]|[13579][26]))-(02)-(29))";

        notesStartRegex = "^Note\\{\\s\"id\":\\s\"(\\d{13,})\",\\s\"dateOfCreation\":\\s\"(.+)\",\\s\"subject\":\\s\"(.*)\",\\s" +
                "\"email\":\\s\"(.+)\",\\s\"text\":\\s(\")";

        notePattern = Pattern.compile(notesStartRegex);
        noteMatcher = notePattern.matcher(line);

        if (noteMatcher.find() && noteMatcher.group(2).matches(dateRegex)) {
            id = Long.parseLong(noteMatcher.group(1));
            dateOfCreation = LocalDate.parse(noteMatcher.group(2));
            subject = noteMatcher.group(3)
                    .replaceAll("\\\\}", "}")
                    .replaceAll("\\\\\"", "\"");
            email = noteMatcher.group(4)
                    .replaceAll("\\\\}", "}")
                    .replaceAll("\\\\\"", "\"");
            text = takeText(line, noteMatcher.end());

            return new Note(id, dateOfCreation, subject, email, text);
        } else {
            throw new NotesParseException("Incorrect notes line:" + line);
        }
    }

    private Note addText(Note note, String line) {
        String notesEnd;

        notesEnd = "\" }";

        if (line.endsWith(notesEnd)) {
            note.setText(note.getText() + "\n" + line.substring(0, line.length() - 3));
        } else {
            note.setText(note.getText() + "\n" + line);
        }

        return note;
    }

    private String takeText(String line, int startPosition) {
        String text;
        String notesEnd;

        notesEnd = "\" }";

        if (line.endsWith(notesEnd)) {
            text = line.substring(startPosition, line.length() - 3);
        } else {
            text = line.substring(startPosition);
        }

        text = text.replaceAll("\\\\}", "}")
                .replaceAll("\\\\\"", "\"");

        return text;
    }

}
