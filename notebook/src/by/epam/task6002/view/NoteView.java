package by.epam.task6002.view;

import by.epam.task6002.bean.Note;

import java.util.List;

public class NoteView {

    public String view(List<Note> notes) {
        StringBuilder notesStringBuilder;

        notesStringBuilder = new StringBuilder();

        if (!notes.isEmpty()) {
            notesStringBuilder.append(view(notes.get(0)));
        }

        for (int i = 1; i < notes.size(); i++) {
            notesStringBuilder.append('\n').append(view(notes.get(i)));
        }

        return notesStringBuilder.toString();

    }

    public String view(Note note) {
        StringBuilder notesStringBuilder;
        String notesString;

        notesStringBuilder = new StringBuilder("Note{ ");
        notesStringBuilder.append("\"id\": \"").append(note.getId()).append("\", ");
        notesStringBuilder.append("\"dateOfCreation\": \"").append(note.getDateOfCreation().toString()).append("\", ");
        notesStringBuilder.append("\"subject\": \"").append(note.getSubject()
                .replaceAll("}", "\\\\}")
                .replaceAll("\"", "\\\\\""));
        notesStringBuilder.append("\", ");
        notesStringBuilder.append("\"email\": \"").append(note.getEmail().replaceAll("}", "\\\\}")
                .replaceAll("\"", "\\\\\""));
        notesStringBuilder.append("\", ");
        notesStringBuilder.append("\"text\": \"").append(note.getText().replaceAll("}", "\\\\}")
                .replaceAll("\"", "\\\\\""));
        notesStringBuilder.append("\"");
        notesStringBuilder.append(" }");

        notesString = notesStringBuilder.toString();

        return notesString;
    }
}
