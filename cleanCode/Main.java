package com.company;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args)  {
        try {
            //PrintWriter logFileOut = new PrintWriter(new BufferedWriter(new FileWriter("log.txt")));
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
                             "9 — Выкл\n");
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
                        return;
                }
            }
        } catch (IOException a) {
            System.out.println("Error!");
        }
    }
}