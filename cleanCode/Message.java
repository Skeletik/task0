package com.company;

import java.util.Date;

public class Message {
    private String id;
    private String author;
    private String timestamp;
    private String message;

    public Message (String id, String author, String timestamp, String message) {
        this.id = id;
        this.author = author;
        this.timestamp = timestamp;
        this.message = message;
    }

    public String getId() { return id; }
    public String getAuthor() { return author; }
    public String getTimestamp() { return timestamp; }
    public String getMessage() { return message; }

    @Override
    public String toString() {
        return (new StringBuilder(id + " " + author + " " + new Date(Long.valueOf(timestamp)) + " " + message).toString());
    }
}
