package by.epam.task6002.bean;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class Note implements Serializable {

    private long id;
    private LocalDate dateOfCreation;
    private String subject;
    private String email;
    private String text;

    public Note() {
        id = 0L;
        dateOfCreation = LocalDate.now();
        subject = "";
        email = "";
        text = "";
    }

    public Note(String subject, String email, String text) {
        dateOfCreation = LocalDate.now();
        id = new Date().getTime();
        this.subject = subject;
        this.email = email;
        this.text = text;
    }

    public Note(long id, LocalDate dateOfCreation, String subject, String email, String text) {
        this.dateOfCreation = dateOfCreation;
        this.id = id;
        this.subject = subject;
        this.email = email;
        this.text = text;
    }

    public long getId() {
        return id;
    }

    public LocalDate getDateOfCreation() {
        return dateOfCreation;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getEmail() {
        return email;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDateOfCreation(LocalDate dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Note{" +
                " id=" + id +
                ", dateOfCreation=" + dateOfCreation +
                ", subject='" + subject + '\'' +
                ", email='" + email + '\'' +
                ", text='" + text + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Note)) return false;
        Note note = (Note) o;
        return id == note.id &&
                dateOfCreation.equals(note.dateOfCreation) &&
                subject.equals(note.subject) &&
                email.equals(note.email) &&
                text.equals(note.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateOfCreation, subject, email, text);
    }
}
