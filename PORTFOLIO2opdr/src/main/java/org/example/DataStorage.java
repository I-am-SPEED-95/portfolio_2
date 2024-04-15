package org.example;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataStorage {

    private static final String FILE_PATH = "C:\\Users\\Gebruiker\\Documents\\School\\intelJ projects\\PORTFOLIO2opdr\\src\\main\\java\\org\\example\\budgets.txt";
    private static final String INKOMSTEN_FILENAME = "C:\\Users\\Gebruiker\\Documents\\School\\intelJ projects\\PORTFOLIO2opdr\\src\\main\\java\\org\\example\\menu\\inkomsten.txt";
    private static final String UITGAVEN_FILENAME = "C:\\Users\\Gebruiker\\Documents\\School\\intelJ projects\\PORTFOLIO2opdr\\src\\main\\java\\org\\example\\menu\\uitgaven.txt";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public static void saveData(List<Budget> budgets) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Budget budget : budgets) {
                String line = String.format("%s,%s,%s,%s%n",
                        budget.getNaam(),
                        dateFormat.format(budget.getBeginDatum()),
                        dateFormat.format(budget.getEindDatum()),
                        budget.getBudgetBedrag());
                writer.write(line);
            }
        }
    }

    public static List<Budget> loadData() throws IOException {
        List<Budget> budgets = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String naam = parts[0];
                Date beginDatum = dateFormat.parse(parts[1]);
                Date eindDatum = dateFormat.parse(parts[2]);
                double bedrag = Double.parseDouble(parts[3]);
                budgets.add(new Budget(naam, beginDatum, eindDatum,bedrag));
            }
        } catch (ParseException e) {
            System.out.println("Fout bij het parseren van datums.");
        }
        return budgets;
    }

    // Methode om inkomsten op te slaan
    public static void saveInkomsten(List<Inkomsten> inkomstenList, String budgetNaam) throws IOException {
        List<String> allLines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(INKOMSTEN_FILENAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.startsWith(budgetNaam)) {
                    allLines.add(line);
                }
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(INKOMSTEN_FILENAME))) {
            for (String line : allLines) {
                writer.write(line);
                writer.newLine();
            }
            for (Inkomsten inkomst : inkomstenList) {
                writer.write(budgetNaam + ";" + inkomst.toFileString());
                writer.newLine();
            }
        }
    }


    // Methode om inkomsten te laden voor een specifiek budget
    public static List<Inkomsten> loadInkomsten(String budgetNaam) throws IOException {
        List<Inkomsten> inkomstenList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(INKOMSTEN_FILENAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts[0].equals(budgetNaam)) {
                    // Maak een nieuwe Inkomsten object en voeg toe aan de lijst
                    String bron = parts[1];
                    double bedrag = Double.parseDouble(parts[2]);
                    inkomstenList.add(new Inkomsten(bedrag, bron));
                }
            }
        }
        return inkomstenList;
    }


    // Methode om uitgaven op te slaan
    public static void saveUitgaven(List<Uitgaven> uitgavenList, String budgetNaam) throws IOException {
        // Maak een lijst om alle regels die niet tot dit budget behoren op te slaan.
        List<String> allLines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(UITGAVEN_FILENAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.startsWith(budgetNaam + ";")) {
                    allLines.add(line);
                }
            }
        }

        // Voeg de huidige uitgaven voor dit budget toe aan de lijst.
        for (Uitgaven uitgave : uitgavenList) {
            String line = String.format("%s;%s;%.2f", budgetNaam, uitgave.getCategorie(), uitgave.getBedrag());
            allLines.add(line);
        }

        // Schrijf de nieuwe complete lijst naar het bestand.
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(UITGAVEN_FILENAME))) {
            for (String line : allLines) {
                writer.write(line);
                writer.newLine();
            }
        }
    }
    public static List<Uitgaven> loadUitgaven(String budgetNaam) throws IOException {
        List<Uitgaven> uitgavenList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(UITGAVEN_FILENAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts[0].equals(budgetNaam)) {
                    double bedrag = Double.parseDouble(parts[2]);
                    String categorie = parts[1];
                    uitgavenList.add(new Uitgaven(bedrag, categorie));
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Fout bij het lezen van uitgaven: ongeldig bedrag.");
        }
        return uitgavenList;
    }

    // Methode om uitgaven te verwijderen
    public static void deleteUitgave(String budgetNaam, String categorie) throws IOException {
        // Lees alle regels en verwijder degene die overeenkomen met de gegeven categorie van het gegeven budget.
        List<String> allLines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(UITGAVEN_FILENAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.startsWith(budgetNaam + ";" + categorie)) {
                    allLines.add(line);
                }
            }
        }

        // Schrijf de bijgewerkte lijst terug naar het bestand.
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(UITGAVEN_FILENAME))) {
            for (String line : allLines) {
                writer.write(line);
                writer.newLine();
            }
        }
    }
}





