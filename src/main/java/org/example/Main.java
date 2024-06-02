package org.example;

import org.example.menu.ConsoleMenu;

import java.io.IOException;
import java.text.ParseException;

public class Main {
    public static void main(String[] args) throws ParseException, IOException {

        ConsoleMenu menu = new ConsoleMenu();
        menu.logo();
        menu.MainMenu();

    }
}