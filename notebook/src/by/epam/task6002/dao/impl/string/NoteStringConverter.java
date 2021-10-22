package by.epam.task6002.dao.impl.string;

import by.epam.task6002.bean.Note;
import by.epam.task6002.dao.DAOException;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NoteStringConverter {
    public Note parseNote(String string) throws DAOException {
        Note note;
        long id;
        LocalDate dateOfCreation;
        String email;
        String subject;
        String text;

        String endOfNoteString;
        String noteWithoutTextRegex;
        String dateRegex;
        Pattern notePattern;
        Matcher matcher;

        endOfNoteString = "\" }";
        dateRegex = "([12][0-9][0-9][0-9]-(((0[13578]|1[02])-(0[1-9]|1[0-9]|2[0-9]|3[01]))" +
                "|((0[469]|11)-(0[1-9]|1[0-9]|2[0-9]|3[0]))" +
                "|((02)(-(0[1-9]|1[0-9]|2[0-8])))))" +
                "|(([12][0-9]([02468][048]|[13579][26]))-(02)-(29))";

        noteWithoutTextRegex = "^Note\\{\\s\"id\":\\s(\\d{13,}),\\s\"dateOfCreation\":\\s\"(.+)\",\\s\"subject\":\\s\"(.*)\",\\s" +
                "\"email\":\\s\"(.+)\",\\s\"text\":\\s(\")";
        notePattern = Pattern.compile(noteWithoutTextRegex);
        matcher = notePattern.matcher(string);

        if (matcher.find() && matcher.group(2).matches(dateRegex) && string.endsWith(endOfNoteString)) {

            id = Long.parseLong(matcher.group(1));
            dateOfCreation = LocalDate.parse(matcher.group(2));
            subject = matcher.group(3)
                    .replaceAll("\\\\}", "}")
                    .replaceAll("\\\\\"", "\"");
            email = matcher.group(4)
                    .replaceAll("\\\\}", "}")
                    .replaceAll("\\\\\"", "\"");
            text = takeText(matcher.end(), string);

            note = new Note();

            note.setId(id);
            note.setEmail(email);
            note.setDateOfCreation(dateOfCreation);
            note.setSubject(subject);
            note.setText(text);

        } else {
            throw new DAOException("Impossible to parse note!\n Note:" + string);
        }

        return note;
    }

    private String takeText(int startPosition, String noteString) {
        String text;

        text = noteString.substring(startPosition, noteString.length() - 3);
        text = text.replaceAll("\\\\}", "}")
                .replaceAll("\\\\\"", "\"");

        return text;
    }

    public String noteToString(Note note) {
        StringBuilder notesStringBuilder;
        String notesString;

        notesStringBuilder = new StringBuilder("Note{ ");

        notesStringBuilder.append("\"id\": ").append(note.getId()).append(", ");
        notesStringBuilder.append("\"dateOfCreation\": \"").append(note.getDateOfCreation().toString()).append("\", ");
        notesStringBuilder.append("\"subject\": \"").append(note.getSubject()
                .replaceAll("}", "\\\\}")
                .replaceAll("\"", "\\\\\""));
        notesStringBuilder.append("\", ");
        notesStringBuilder.append("\"email\": \"").append(note.getEmail()
                .replaceAll("}", "\\\\}")
                .replaceAll("\"", "\\\\\""));
        notesStringBuilder.append("\", ");
        notesStringBuilder.append("\"text\": \"").append(note.getText()
                .replaceAll("}", "\\\\}")
                .replaceAll("\"", "\\\\\""));
        notesStringBuilder.append("\"");
        notesStringBuilder.append(" }");

        notesString = notesStringBuilder.toString();

        return notesString;
    }
}
