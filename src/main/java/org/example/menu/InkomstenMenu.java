package org.example.menu;

import org.example.budgetBeheer.Budget;
import org.example.dataBeheer.DataStorageInkomsten;
import org.example.budgetBeheer.Inkomsten;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class InkomstenMenu {

    private Scanner scanner;
    private Budget budget; // Verwijzing naar het huidige budget

    public InkomstenMenu(Scanner scanner, Budget budget) {
        this.scanner = scanner;
        this.budget = budget;
    }

    public void toonInkomstenMenu() {
        int keuze;
        do {
            System.out.println("Inkomsten beheren voor budget: " + budget.getNaam());
            System.out.println("1. Inkomst toevoegen");
            System.out.println("2. Inkomst verwijderen");
            System.out.println("3. Terug naar budget menu");

            System.out.println("Maak uw keuze:");
            keuze = scanner.nextInt();
            scanner.nextLine(); // Catch the newline

            switch (keuze) {
                case 1:
                    voegInkomstToe();
                    break;
                case 2:
                    verwijderInkomst();
                    break;
                case 3:
                    System.out.println("Teruggaan naar het budgetmenu...");
                    break;
                default:
                    System.out.println("Ongeldige keuze, probeer opnieuw.");
            }
        } while (keuze != 3);
    }

    private void voegInkomstToe() {
        System.out.println("Voer de bron van inkomsten in of typ 'terug' om terug te gaan:");
        String bron = scanner.nextLine();
        if (bron.equalsIgnoreCase("terug")) {
            System.out.println("Teruggaan naar het inkomsten menu...");
            return; // Vroegtijdig terugkeren als de gebruiker 'terug' invoert
        }

        System.out.println("Voer het bedrag in of typ '-1' om terug te gaan:");
        double bedrag;
        try {
            bedrag = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Ongeldige invoer. Voer een geldig getal in.");
            return; // Terug naar het menu als de invoer geen geldig getal is
        }

        if (bedrag == -1) {
            System.out.println("Teruggaan naar het inkomsten menu...");
            return; // Vroegtijdig terugkeren als de gebruiker '-1' invoert
        }

        Inkomsten inkomst = new Inkomsten(bedrag, bron);
        List<Inkomsten> inkomstenList = budget.getInkomstenList();
        inkomstenList.add(inkomst); // Voeg inkomsten toe aan het budget

        try {
            DataStorageInkomsten.saveInkomsten(inkomstenList, budget.getNaam()); // Opslaan in een tekstbestand
            System.out.println("Inkomsten succesvol toegevoegd.");
        } catch (IOException e) {
            System.err.println("Er is een fout opgetreden bij het opslaan van de inkomsten.");
        }
    }


    private void verwijderInkomst() {
        List<Inkomsten> inkomstenList;
        try {
            inkomstenList = DataStorageInkomsten.loadInkomsten(budget.getNaam());
        } catch (IOException e) {
            System.err.println("Er is een fout opgetreden bij het laden van de inkomsten.");
            return;
        }

        if (inkomstenList.isEmpty()) {
            System.out.println("Er zijn geen inkomsten om te verwijderen.");
            return;
        }

        System.out.println("Beschikbare inkomsten:");
        int index = 1;
        for (Inkomsten inkomst : inkomstenList) {
            System.out.println(index++ + ". " + inkomst.getBron() + " - Bedrag: " + inkomst.getBedrag());
        }
        System.out.println("Typ 'terug' om naar het vorige menu te gaan.");

        System.out.print("Kies het nummer van de inkomsten die u wilt verwijderen of ga terug: ");
        String input = scanner.nextLine();

        if (input.equalsIgnoreCase("terug")) {
            System.out.println("Terugkeren naar het vorige menu...");
            return; // Vroegtijdig terugkeren als de gebruiker 'terug' invoert
        }

        int keuze;
        try {
            keuze = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Ongeldige invoer. Voer een geldig getal in of typ 'terug'.");
            return; // Gebruiker heeft geen nummer ingevoerd
        }

        if (keuze < 1 || keuze > inkomstenList.size()) {
            System.out.println("Ongeldige keuze, probeer opnieuw.");
            return; // Gebruiker heeft een ongeldig nummer gekozen
        }

        Inkomsten teVerwijderenInkomst = inkomstenList.get(keuze - 1);
        inkomstenList.remove(teVerwijderenInkomst); // Verwijder de gekozen inkomst

        try {
            DataStorageInkomsten.saveInkomsten(inkomstenList, budget.getNaam()); // Update het bestand na het verwijderen van de inkomst
            System.out.println("Inkomsten van " + teVerwijderenInkomst.getBron() + " succesvol verwijderd.");
        } catch (IOException e) {
            System.err.println("Er is een fout opgetreden bij het bijwerken van de inkomsten.");
        }
    }

}
