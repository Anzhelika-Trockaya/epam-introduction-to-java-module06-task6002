package by.epam.task6002.main;

public class NotesParseException extends Exception{
    public NotesParseException() {
        super();
    }

    public NotesParseException(String message) {
        super(message);
    }

    public NotesParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotesParseException(Throwable cause) {
        super(cause);
    }
}
