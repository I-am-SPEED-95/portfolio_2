package org.example.menu;

import org.example.Budget;
import org.example.DataStorage;
import org.example.Uitgaven;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UitgavenMenu {

    private Scanner scanner;
    private Budget budget;

    public UitgavenMenu(Scanner scanner, Budget budget) {
        this.scanner = scanner;
        this.budget = budget;
    }

    public void toonUitgavenMenu() throws IOException {
        int keuze;
        do {
            System.out.println("Uitgaven beheren voor budget: " + budget.getNaam());
            System.out.println("1. Uitgave toevoegen");
            System.out.println("2. Uitgave verwijderen");
            System.out.println("3. Terug naar budget menu");

            System.out.println("Maak uw keuze:");
            keuze = scanner.nextInt();
            scanner.nextLine(); // Vang de newline op

            switch (keuze) {
                case 1:
                    voegUitgaveToe();
                    break;
                case 2:
                    verwijderUitgave();
                    break;
                case 3:
                    System.out.println("Teruggaan naar het budgetmenu...");
                    break;
                default:
                    System.out.println("Ongeldige keuze, probeer opnieuw.");
            }
        } while (keuze != 3);
    }

    private void voegUitgaveToe() {
        List<String> standaardCategorieen = new ArrayList<>(List.of("Huur", "Reiskosten", "Boodschappen", "Verzekering"));
        System.out.println("Kies een standaardcategorie, voer een nieuwe in, of typ 'terug' om terug te gaan:");

        int index = 1;
        for (String categorie : standaardCategorieen) {
            System.out.println(index++ + ". " + categorie);
        }
        System.out.println(index + ". Voeg nieuwe categorie toe");

        String input = scanner.nextLine();
        if (input.equalsIgnoreCase("terug")) {
            return; // Gebruiker gaat terug naar het vorige menu
        }

        int keuze;
        try {
            keuze = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Ongeldige keuze, probeer opnieuw.");
            return; // Gebruiker heeft geen nummer ingevoerd
        }

        String categorie;
        if (keuze == index) {
            System.out.println("Voer de naam van de nieuwe categorie in:");
            categorie = scanner.nextLine();
        } else if (keuze > 0 && keuze <= standaardCategorieen.size()) {
            categorie = standaardCategorieen.get(keuze - 1);
        } else {
            System.out.println("Ongeldige keuze, probeer opnieuw.");
            return; // Gebruiker heeft een ongeldig nummer ingevoerd
        }

        System.out.println("Voer het bedrag van de uitgave in of typ 'terug' om terug te gaan:");
        String bedragInput = scanner.nextLine();
        if (bedragInput.equalsIgnoreCase("terug")) {
            return; // Gebruiker gaat terug naar het vorige menu
        }

        double bedrag;
        try {
            bedrag = Double.parseDouble(bedragInput);
        } catch (NumberFormatException e) {
            System.out.println("Ongeldig bedrag, probeer opnieuw.");
            return; // Gebruiker heeft geen geldig getal voor bedrag ingevoerd
        }

        Uitgaven uitgave = new Uitgaven(bedrag, categorie);
        budget.addUitgaven(uitgave);

        try {
            DataStorage.saveUitgaven(budget.getUitgavenList(), budget.getNaam());
            System.out.println("Uitgave succesvol toegevoegd aan de categorie '" + categorie + "'.");
        } catch (IOException e) {
            System.err.println("Er is een fout opgetreden bij het opslaan van de uitgave.");
        }
    }


    private void verwijderUitgave() throws IOException {
        List<Uitgaven> uitgavenList = DataStorage.loadUitgaven(budget.getNaam()); // Laad de huidige uitgaven van het budget

        if (uitgavenList.isEmpty()) {
            System.out.println("Er zijn geen uitgaven om te verwijderen.");
            return; // Als er geen uitgaven zijn, keer terug naar het vorige menu
        }

        System.out.println("Beschikbare categorieën en bedragen:");
        for (int i = 0; i < uitgavenList.size(); i++) {
            Uitgaven uitgave = uitgavenList.get(i);
            System.out.println((i + 1) + ". " + uitgave.getCategorie() + " - Bedrag: " + uitgave.getBedrag());
        }
        System.out.println((uitgavenList.size() + 1) + ". Terug naar het vorige menu");

        System.out.print("Kies een nummer om de categorie te verwijderen of ga terug: ");
        int keuze = scanner.nextInt();
        scanner.nextLine(); // Vang de newline op

        if (keuze == uitgavenList.size() + 1) {
            // Terug naar het vorige menu
        } else if (keuze > 0 && keuze <= uitgavenList.size()) {
            Uitgaven teVerwijderenUitgave = uitgavenList.get(keuze - 1);
            uitgavenList.remove(teVerwijderenUitgave); // Verwijder de gekozen uitgave
            DataStorage.saveUitgaven(uitgavenList, budget.getNaam()); // Sla de bijgewerkte lijst op
            System.out.println("Uitgave verwijderd: " + teVerwijderenUitgave.getCategorie() + " - Bedrag: " + teVerwijderenUitgave.getBedrag());
        }else {
            System.out.println("Ongeldige keuze, probeer opnieuw.");
        }
    }



}
