package org.example.dataBeheer;
import org.example.budgetBeheer.Inkomsten;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataStorageInkomsten {
    private static final String INKOMSTEN_FILENAME = "C:\\Users\\Gebruiker\\Documents\\School\\intelJ projects\\PORTFOLIO2opdr\\src\\main\\java\\org\\example\\dataBeheer\\inkomsten.txt";

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
}
