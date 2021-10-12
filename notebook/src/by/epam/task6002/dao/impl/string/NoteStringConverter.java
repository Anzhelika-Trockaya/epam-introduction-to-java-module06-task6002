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

        String noteRegex;
        Pattern notePattern;
        Matcher matcher;

        noteRegex = "^Note\\{\\s\"id\":\\s(\\d{13,}),\\s\"dateOfCreation\":\\s\"(.+)\",\\s\"subject\":\\s\"(.*)\",\\s" +
                "\"email\":\\s\"(.+)\",\\s\"text\":\\s\"((\n|.)*)\"\\s}$";
        notePattern = Pattern.compile(noteRegex);
        matcher = notePattern.matcher(string);

        if (matcher.find()) {

            id = Long.parseLong(matcher.group(1));
            dateOfCreation = LocalDate.parse(matcher.group(2));
            subject = matcher.group(3).replaceAll("\\\\}", "}");
            email = matcher.group(4).replaceAll("\\\\}", "}");
            text = matcher.group(5).replaceAll("\\\\}", "}");

            note = new Note();

            note.setId(id);
            note.setEmail(email);
            note.setDateOfCreation(dateOfCreation);
            note.setSubject(subject);
            note.setText(text);

        } else {
            throw new DAOException("Impossible to parse note!\n Note:"+string);
        }

        return note;
    }

    public String noteToString(Note note) {
        StringBuilder notesStringBuilder;
        String notesString;

        notesStringBuilder = new StringBuilder("Note{ ");
        notesStringBuilder.append("\"id\": ").append(note.getId()).append(", ");
        notesStringBuilder.append("\"dateOfCreation\": \"").append(note.getDateOfCreation().toString()).append("\", ");
        notesStringBuilder.append("\"subject\": \"").append(note.getSubject().replaceAll("}", "\\\\}"));
        notesStringBuilder.append("\", ");
        notesStringBuilder.append("\"email\": \"").append(note.getEmail().replaceAll("}", "\\\\}"));
        notesStringBuilder.append("\", ");
        notesStringBuilder.append("\"text\": \"").append(note.getText().replaceAll("}", "\\\\}"));
        notesStringBuilder.append("\"");
        notesStringBuilder.append(" }");

        notesString = notesStringBuilder.toString();

        return notesString;
    }
}
