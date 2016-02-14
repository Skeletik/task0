package com.company;

public class Message {
    public String id;
    public String author;
    public String timestamp;
    public String message;

    public Message (String id, String author, String timestamp, String message) {
        this.id = id;
        this.author = author;
        this.timestamp = timestamp;
        this.message = message;
    }

    @Override
    public String toString() {
        return (new StringBuilder(id + " " + author + " " + timestamp + " " + message).toString());
    }

    public String getId() { return id; }
    public String getAuthor() { return author; }
    public String getTimestamp() { return timestamp; }
    public String getMessage() { return message; }
}
