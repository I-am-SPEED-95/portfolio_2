package org.example.dataBeheer;
import org.example.budgetBeheer.Uitgaven;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataStorageUitgaven {
    private static final String UITGAVEN_FILENAME = "C:\\Users\\Gebruiker\\Documents\\School\\intelJ projects\\PORTFOLIO2opdr\\src\\main\\java\\org\\example\\dataBeheer\\uitgaven.txt";

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

    // Methode om uitgaven te loaden
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

