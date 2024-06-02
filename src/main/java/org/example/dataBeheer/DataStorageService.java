package org.example.dataBeheer;

import org.example.budgetBeheer.Budget;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataStorageService {
    private static final String FILE_PATH = "C:\\Users\\Gebruiker\\Documents\\School\\intelJ projects\\PORTFOLIO2opdr\\src\\main\\java\\org\\example\\budgetBeheer\\budgets.txt";
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
                budgets.add(new Budget(naam, beginDatum, eindDatum, bedrag));
            }
        } catch (ParseException e) {
            System.out.println("Fout bij het parseren van datums.");
        }
        return budgets;
    }
}
