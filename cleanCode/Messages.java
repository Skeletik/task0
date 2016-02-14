package com.company;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Messages {
    private List <Message> messages = new ArrayList<>();

    public void readJSON() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("input.json"));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) sb.append(line);
        String json = sb.toString();
        Gson gson = new Gson();
        Type type = new TypeToken<List<Message>>(){}.getType();
        messages = gson.fromJson(json, type);
        for (Message item : messages)
            System.out.println(item);
    }

    public void addMessage() {
        Scanner sc = new Scanner(System.in);
        /*String a = "hello";
MessageDigest md = MessageDigest.getInstance("SHA-1")
System.out.println(Arrays.toString(a.getBytes()));*/
        String id = "92dff7ee-00d7-41e5-a3db-e7189963ee3e";
        System.out.print("Ваше имя: ");
        String author = sc.next();
        String timestamp = String.valueOf(System.currentTimeMillis());
        System.out.print("Ваш текст: ");
        String message = sc.next();
        messages.add(new Message(id, author, timestamp, message));
    }

    public void showMessagesInChronologicalOrder() {
        List <Message> temp = new ArrayList<>(messages);
        System.out.println(temp);
        //Как здесь реализовать сравнение лонгов, а не строк? Быстрее вдеь.
        //Collections.sort(temp, (a, b) -> Long.parseLong(a.getTimestamp()) - (Long.parseLong(b.getTimestamp())));
        Collections.sort(temp, new Comparator<Message>() {
            @Override public int compare(Message a, Message b) {
                return a.getTimestamp().compareTo(b.getTimestamp());
            }
        });
        for (Message item : temp)
            System.out.println(item);
    }

    public void deleteMessageById() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Введите идентификатор сообщения, которое нужно удалить: ");
        String id = sc.next();
        for (int i = 0; i < messages.size(); i++) {
            if (id.equals(messages.get(i).getId())) {
                messages.remove(i);
            } else { System.out.print("\nНет сообщения с таким идентификатором"); }
        }
    }

    public void writeJSON() throws IOException {
        Gson gson = new Gson();
        String s = gson.toJson(messages);
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("input.json")));
        pw.print(s);
        pw.close();
    }

    public void findByAuthor() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Введите имя того, кто написал сообщение: ");
        String author = sc.next();
        for (Message item : messages) {
            if (item.getAuthor().equals(author)) // We can compare by ==, can't we?
                System.out.println("true");
            else
                System.out.println("false");
        }
    }

    public void findByKeyword() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Введите строку для поиска: ");
        String str = sc.next();
        for (int i = 0; i < messages.size(); i++) {
            if (messages.get(i).getMessage().contains(str)) {
                System.out.println(messages.get(i));
            }
            else System.out.println("false");
        }
    }

    public void findByRegExp() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Введите РВ для поиска: ");
        String patternString = sc.next();
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher;
        for (Message item : messages) {
            matcher = pattern.matcher(item.getMessage());
            if (matcher.matches()) System.out.println("true");
            else System.out.println("false");
        }
    }

    public void findByPeriodOfTime() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Введите период времени для поиска\n" + "От: ");
        long from = sc.nextLong();
        System.out.println("До: ");
        long till = sc.nextLong();
        for (Message item : messages) {
            if (Long.parseLong(item.getTimestamp()) >= from && Long.parseLong(item .getTimestamp()) <= till)  System.out.println("true");
            else System.out.println("false");
        }
    }
}
