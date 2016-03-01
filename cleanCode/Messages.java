package com.company;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
// Нормальный ввод даты для 8-го пункта

public class Messages {

    private List<Message> messages = new ArrayList<>();
    private static PrintWriter logFilePw;

    static {
        try {
            logFilePw = new PrintWriter(new BufferedWriter(new FileWriter("log.txt")));
        } catch (IOException e) {
            logFilePw.println("\n" + e.getMessage());
            e.printStackTrace();
        }
    }

    public void showInterface() throws IOException, ParseException {
        System.out.print("\n1 — Добавить сообщение\n" +
                "2 — Просмотреть историю сообщений(в хронологическом порядке)\n" +
                "3 — Удалить сообщение по идентификатору\n" +
                "4 — Сохранить сообщения в файл\n" +
                "5 — Поиск в истории сообщений по автору\n" +
                "6 — Поиск в истории сообщений по ключевому слову\n" +
                "7 — Поиск в истории сообщений по регулярному выражению\n" +
                "8 — Выборка истории сообщений по периоду времени\n" +
                "9 — Выключить Уютный Чаткик\n");
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("\nВыберите действие: ");
            switch (sc.nextInt()) {
                case 1:
                    addMessage();
                    break;
                case 2:
                    showMessagesInChronologicalOrder();
                    break;
                case 3:
                    deleteMessageById();
                    break;
                case 4:
                    writeJSON();
                    break;
                case 5:
                    findByAuthor();
                    break;
                case 6:
                    findByKeyword();
                    break;
                case 7:
                    findByRegExp();
                    break;
                case 8:
                    findByPeriodOfTime();
                    break;
                case 9:
                    closeLogFileStream();
                    System.out.println("Уютный Чатик завершает работу..");
                    return;
            }
        }
    }

    public void readJSON() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("input.json"));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        String json = sb.toString();
        Gson gson = new Gson();
        Type type = new TypeToken<List<Message>>() {}.getType();
        messages = gson.fromJson(json, type);
        logFilePw.println("В Уютный Чатик загружено " + messages.size() + " сообщения из input.json:");
        for (Message item : messages) {
            System.out.println(item);
            logFilePw.println(item);
        }
    }

    public void addMessage() {
        Scanner sc = new Scanner(System.in);
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();
        System.out.print("Ваше имя: ");
        String author = sc.nextLine();
        String timestamp = String.valueOf(System.currentTimeMillis());
        System.out.print("Ваш текст: ");
        String message = sc.nextLine();
        System.out.println("id нового сообщения: " + id);
        System.out.println("Время добавления: " + new Date(Long.valueOf(timestamp)));
        messages.add(new Message(id, author, timestamp, message));

        logFilePw.println("\nДобавлено сообщение в messages list:");
        logFilePw.println(messages.get(messages.size() - 1));
    }

    public void showMessagesInChronologicalOrder() {
        List<Message> temp = new ArrayList<>(messages);
        Collections.sort(temp, (a, b) -> Long.valueOf(a.getTimestamp()).compareTo(Long.valueOf(b.getTimestamp())));
        for (Message item : temp)
            System.out.println(item);

        logFilePw.println("\nВызван метод showMessagesInChronologicalOrder()");
    }

    public void deleteMessageById() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Введите идентификатор сообщения, которое нужно удалить: ");
        String id = sc.next();
        int deletedMessagesCounter = 0;
        for (int i = 0; i < messages.size(); i++) {
            if (id.equals(messages.get(i).getId())) {
                messages.remove(i);
                System.out.print("\nИз messages list удалено сообщение с id:\n" + id + "\n");
                logFilePw.println("\nИз messages list удалено сообщение с id:\n" + id + "\n");
                deletedMessagesCounter++;
            }
        }
        if (deletedMessagesCounter == 0) {
            System.out.print("\nНет сообщения с таким идентификатором\n");
            logFilePw.println("\nНе получилось удалить сообщение из messages list с id:\n" + id);
        }
    }

    public void writeJSON() throws IOException {
        Gson gson = new Gson();
        String s = gson.toJson(messages);
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("input.json")));
        pw.print(s);
        pw.close();

        logFilePw.println("\nВызван метод writeJSON");
    }

    public void findByAuthor() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Введите имя того, кто написал сообщение: ");
        String author = sc.nextLine();
        int appropriateMessagesCounter = 0;
        for (Message item : messages) {
            if (item.getAuthor().equals(author)) { // We cannot compare by ==.
                if (appropriateMessagesCounter == 0) {
                    System.out.println("\n\"" + author + "\" писал в Уютный Чатик:" + "\n" + item);
                    logFilePw.println("\n\"" + author + "\" писал в Уютный Чатик:" + "\n" + item);
                } else {
                    logFilePw.println(item);
                }
                ++appropriateMessagesCounter;
            }
        }
        if (appropriateMessagesCounter == 0) {
            System.out.println("\nВ Уютном Чатике не найдено ни одного сообщения автора: \"" + author + "\"");
            logFilePw.println("\nВ Уютном Чатике не найдено ни одного сообщения автора: \"" + author + "\"");
        }
    }

    public void findByKeyword() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Введите, что вы хотите найти в сообщениях Уютного Чатика: ");
        String keyword = sc.nextLine();
        int appropriateMessagesCounter = 0;
        for (Message message : messages) {
            if (message.getMessage().contains(keyword)) {
                if (appropriateMessagesCounter == 0) {
                    System.out.println("\nОтрывок " + keyword + " найден в сообщении " + message);
                    logFilePw.println("\nОтрывок " + keyword + " найден в сообщении " + message);
                }
                ++appropriateMessagesCounter;
            }
        }
        if (appropriateMessagesCounter == 0) {
            System.out.println("\nВ Уютном Чатике не найдено ни одного сообщения, содержащего отрывок: \"" + keyword + "\"");
            logFilePw.println("\nВ Уютном Чатике не найдено ни одного сообщения, содержащего отрывок: \"" + keyword + "\"");
        }
    }

    public void findByRegExp() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Введите РВ для поиска: ");
        String patternString = sc.nextLine();
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher;
        for (Message item : messages) {
            matcher = pattern.matcher(item.getMessage());
            if (matcher.matches()) {
                System.out.println("\nРВ \"" + patternString + "\" найден в сообщении " + item.getMessage());
                logFilePw.println("\nРВ \"" + patternString + "\" найден в сообщении " + item.getMessage());
            }
        }
    }

    public void findByPeriodOfTime() throws ParseException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Введите период времени для поиска\n" + "От: ");
        String from = sc.nextLine();
        System.out.print("До: ");
        String till = sc.nextLine();

        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("dd.MM.yyyy hh:mm");
        Date fromDate = format.parse(from);
        Date toDate = format.parse(till);

        for (Message item : messages) {
            Date timestampDate = new Date(Long.valueOf(item.getTimestamp()));
            if (timestampDate.compareTo(fromDate) >= 0 && timestampDate.compareTo(toDate) <= 0) {
                System.out.println("\nСообщение \"" + item.getMessage() + "\" попало в промежуток");
                logFilePw.println("\nСообщение \"" + item.getMessage() + "\" попало в промежуток");
            }
        }
    }

    public void closeLogFileStream() {
        logFilePw.close();
    }
}
