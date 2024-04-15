package org.example.menu;

import org.example.Budget;

import java.io.IOException;
import java.util.Scanner;

public class BudgetMenu {
    private Budget budget;
    private Scanner scanner;

    public BudgetMenu(Budget budget) {
        this.budget = budget;
        this.scanner = new Scanner(System.in);
    }

    public void toonBudgetMenu() throws IOException {
        int keuze;
        do {
            // Toon budgetdetails
            System.out.println("Budget: " + budget.getNaam() + " | Begin Datum: " + budget.getBeginDatum() + " | Eind Datum: " + budget.getEindDatum() + " | Bedrag: " + budget.getBudgetBedrag());
            System.out.println("Totaal Gesaved: " + budget.berekenGesaved() + " | Totaal Gespendeerd: " + budget.berekenGespendeerd());
            System.out.println("----------------------------------------------------------------");

            // Toon menuopties
            System.out.println("1. Inkomsten");
            System.out.println("2. Uitgaven");
            System.out.println("3. Bekijk transacties");
            System.out.println("4. Terug naar hoofdmenu");
            System.out.println("Maak uw keuze:");

            // Verwerk de keuze van de gebruiker
            try {
                keuze = Integer.parseInt(scanner.nextLine());

                switch (keuze) {
                    case 1:
                        InkomstenMenu inkomstenMenu = new InkomstenMenu(scanner, budget);
                        inkomstenMenu.toonInkomstenMenu();
                        break;
                    case 2:
                        UitgavenMenu uitgavenMenu = new UitgavenMenu(scanner, budget);
                        uitgavenMenu.toonUitgavenMenu();
                        break;
                    case 3:
                        TransactiesMenu transactiesMenu = new TransactiesMenu(scanner, budget);
                        transactiesMenu.toonTransactiesMenu();
                        break;
                    case 4:
                        return; // Terug naar hoofdmenu
                    default:
                        System.out.println("Ongeldige keuze, probeer opnieuw.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Ongeldige invoer, voer alstublieft een nummer in.");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } while (true);
    }

}
