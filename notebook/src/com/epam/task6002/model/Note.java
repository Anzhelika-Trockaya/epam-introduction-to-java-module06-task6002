package com.epam.task6002.model;

import java.time.LocalDate;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Note {
    private final String emailRegex = "^(([\\w\\d-_!#$%&'*+/=?^`{|}~]+" +
            "(\\.[\\w\\d-_!#$%&'*+/=?^`{|}~]+)*" +
            "(\\.\"([\\w\\d-_!#$%&'*+/=?^`{|}~(),:;<>@\\[\\] \\\\.]|(\\\\\"))+\"\\.)*)+|" +
            "(\"([\\w\\d-_!#$%&'*+/=?^`{|}~(),:;<>@\\[\\] \\\\.]|(\\\\\"))+\"))" +
            "@(\\w+(-\\w+)?\\.)+\\w{2,}$";
    private long id;
    private LocalDate dateOfCreation;
    private String subject = "";
    private String email;
    private String text = "";

    public Note(String subject, String email, String text) {
        if (email.matches(emailRegex)) {
            if (subject.length() <= 75 || !subject.contains("\n")) {
                dateOfCreation = LocalDate.now();
                id = new Date().getTime();
                this.subject = subject;
                this.email = email;
                this.text = text;
            } else {
                throw new IllegalArgumentException("Incorrect subject!");
            }
        } else {
            throw new IllegalArgumentException("Incorrect email!");
        }
    }

    public Note() {
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
        if (subject != null) {
            if (subject.length() <= 75 || !subject.contains("\n")) {
                this.subject = subject;
            } else {
                throw new IllegalArgumentException("Incorrect subject!");
            }
        } else {
            throw new IllegalArgumentException("Subject cannot be null string!");
        }
    }

    public String getEmail() {
        return email;
    }

    public void setId(long id) {
        if (id > 0) {
            this.id = id;
        } else throw new IllegalArgumentException("Id must be positive number!");
    }

    public void setDateOfCreation(LocalDate dateOfCreation) {
        if (dateOfCreation == null) {
            throw new IllegalArgumentException("Date of creation cannot be null!");
        }
        if (!dateOfCreation.isAfter(LocalDate.now())) {
            this.dateOfCreation = dateOfCreation;
        } else {
            throw new IllegalArgumentException("Creation date later than current!");
        }
    }

    public void setEmail(String email) {
        if (email.matches(emailRegex)) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("Incorrect email!");
        }
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        if (text != null) {
            this.text = text;
        } else {
            throw new IllegalArgumentException("Text cannot be null string!");
        }
    }

    public void print() {
        System.out.println(" - Note id:" + id + " - ");
        System.out.println("Created: " + dateOfCreation);
        System.out.println("Email: " + email);
        System.out.println("Subject: " + subject);
        formattedPrintText();
        System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
    }

    private void formattedPrintText() {
        Pattern pattern = Pattern.compile(".{1,74}(\\p{P}|\\s|\\n)");
        Matcher matcher = pattern.matcher(text);
        int start = 0;
        while (matcher.find(start)) {
            System.out.println(matcher.group());
            start = matcher.end();
        }
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
}
