package com.company;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

// Как правильно писать информацию в log.txt? Чтобы не пришлось открывать новый поток в каждом методе только для того, чтобы скинуть инфу в log.txt?
// PrintWriter logFilePw = new PrintWriter(new BufferedWriter(new FileWriter("log.txt")));

// Как правильно генерировать хэш для сообщения?
// Какие есть общие замечания по программе?

public class Main {

    public static void main(String[] args)  {
        PrintWriter logFilePw = null;
        try {
            logFilePw = new PrintWriter(new BufferedWriter(new FileWriter("log.txt")));
            Messages messages = new Messages();
            System.out.println("Загрузка истории сообщений...");
            messages.readJSON();

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
                        messages.addMessage();
                        break;
                    case 2:
                        messages.showMessagesInChronologicalOrder();
                        break;
                    case 3:
                        messages.deleteMessageById();
                        break;
                    case 4:
                        messages.writeJSON();
                        break;
                    case 5:
                        messages.findByAuthor();
                        break;
                    case 6:
                        messages.findByKeyword();
                        break;
                    case 7:
                        messages.findByRegExp();
                        break;
                    case 8:
                        messages.findByPeriodOfTime();
                        break;
                    case 9:
                        messages.closeLogFileStream();
                        return;
                }
            }
        } catch (IOException e) {
            logFilePw.println("\n" + e.getMessage());
            e.printStackTrace();
        }
    }
}