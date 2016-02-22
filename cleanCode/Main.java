package com.company;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Main {

    public static void main(String[] args)  {
        PrintWriter logFilePw = null;
        try {
            logFilePw = new PrintWriter(new BufferedWriter(new FileWriter("log.txt")));
            Messages messages = new Messages();
            System.out.println("Загрузка истории сообщений...");
            messages.readJSON();
            messages.showInterface();
        } catch (IOException e) {
            logFilePw.println("\n" + e.getMessage());
            e.printStackTrace();
        }
    }
}