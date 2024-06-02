package org.example.menu;

import org.example.budgetBeheer.Budget;
import org.example.dataBeheer.DataStorageInkomsten;
import org.example.budgetBeheer.Inkomsten;
import org.example.budgetBeheer.Uitgaven;
import org.example.dataBeheer.DataStorageUitgaven;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class TransactiesMenu {

    private Scanner scanner;
    private Budget budget; // Verwijzing naar het huidige budget

    public TransactiesMenu(Scanner scanner, Budget budget) {
        this.scanner = scanner;
        this.budget = budget;
    }

    public void toonTransactiesMenu() throws IOException {
        System.out.println("Overzicht voor budget: " + budget.getNaam());
        System.out.println("Periode: " + budget.getBeginDatum() + " - " + budget.getEindDatum());
        System.out.println("Totaal budget: " + budget.getBudgetBedrag());
        System.out.println("Totaal gesaved: " + budget.berekenGesaved());
        System.out.println("Totaal gespendeerd: " + budget.berekenGespendeerd());
        System.out.println("----------------------------------------------------------------");

        // Hier logica om alle transacties te tonen
        toonAlleTransacties();

        System.out.println("1. Exporteer naar CSV");
        System.out.println("2. Terug naar budget menu");
        System.out.println("Maak uw keuze:");

        int keuze = scanner.nextInt();
        scanner.nextLine(); // Consumeer de resterende newline

        switch (keuze) {
            case 1:
                exporteerNaarCSV();
                break;
            case 2:
                System.out.println("Teruggaan naar het hoofdmenu...");
                break;
            default:
                System.out.println("Ongeldige keuze, probeer opnieuw.");
        }
    }

    private void toonAlleTransacties() throws IOException {
        // Eerst laden we de inkomsten en uitgaven van het budget
        List<Inkomsten> inkomstenList = DataStorageInkomsten.loadInkomsten(budget.getNaam());
        List<Uitgaven> uitgavenList = DataStorageUitgaven.loadUitgaven(budget.getNaam());

        System.out.println("Inkomsten:");
        for (Inkomsten inkomst : inkomstenList) {
            System.out.println(inkomst.getBron() + " - " + inkomst.getBedrag());
        }

        System.out.println("\nUitgaven:");
        for (Uitgaven uitgave : uitgavenList) {
            System.out.println(uitgave.getCategorie() + " - " + uitgave.getBedrag());
        }
    }

    private void exporteerNaarCSV() throws IOException {
        String csvBestandPath = "C:\\Users\\Gebruiker\\Documents\\School\\intelJ projects\\PORTFOLIO2opdr\\src\\main\\java\\org\\example\\csvDownloads\\transacties.csv";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvBestandPath))) {
            // Schrijf eerst de headers
            writer.write("Type, Categorie/Bron, Bedrag\n");

            // Schrijf inkomsten
            for (Inkomsten inkomst : DataStorageInkomsten.loadInkomsten(budget.getNaam())) {
                writer.write("Inkomst," + inkomst.getBron() + "," + inkomst.getBedrag() + "\n");
            }

            // Schrijf uitgaven
            for (Uitgaven uitgave : DataStorageUitgaven.loadUitgaven(budget.getNaam())) {
                writer.write("Uitgave," + uitgave.getCategorie() + "," + uitgave.getBedrag() + "\n");
            }
        }

        System.out.println("Transacties zijn geëxporteerd naar: " + csvBestandPath);
    }
}

