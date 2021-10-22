package by.epam.task6002.main;

import by.epam.task6002.bean.Note;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NoteToFormattedStringConverter {

    public String noteToFormattedString(Note note) {
        StringBuilder formattedNoteBuilder;

        formattedNoteBuilder = new StringBuilder();
        formattedNoteBuilder.append(" - Note id:").append(note.getId()).append(" - \n");
        formattedNoteBuilder.append("Created: ").append(note.getDateOfCreation()).append("\n");
        formattedNoteBuilder.append("Email: ").append(note.getEmail()).append("\n");
        formattedNoteBuilder.append("Subject: ").append(note.getSubject()).append("\n");
        formattedNoteBuilder.append("\n").append(formatText(note.getText())).append("\n");
        formattedNoteBuilder.append("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -").append("\n");

        return formattedNoteBuilder.toString();
    }

    private String formatText(String text) {
        Pattern pattern;
        Matcher matcher;
        int start;
        StringBuilder formattedTextBuilder;

        pattern = Pattern.compile("(.{0,74}(\\p{P}|\\s|\\n))|(.{1,74}$)");
        matcher = pattern.matcher(text);
        formattedTextBuilder = new StringBuilder();

        start = 0;
        while (matcher.find(start)) {
            formattedTextBuilder.append(matcher.group());

            if (matcher.group(2)==null || !matcher.group(2).equals("\n")) {
                formattedTextBuilder.append("\n");
            }

            start = matcher.end();
        }

        return formattedTextBuilder.toString();
    }

    public String notesToFormattedString(List<Note> notes) {
        StringBuilder notesStringBuilder;

        notesStringBuilder = new StringBuilder();

        for (Note note : notes) {
            notesStringBuilder.append(noteToFormattedString(note));
        }

        return notesStringBuilder.toString();
    }
}
