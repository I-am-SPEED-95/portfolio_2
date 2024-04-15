package org.example.menu;

import org.example.Budget;
import org.example.DataStorage;
import org.example.menu.BudgetMenu;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ConsoleMenu {
    private Scanner scanner;
    private List<Budget> budgets;

    public ConsoleMenu() {
        this.scanner = new Scanner(System.in);
        this.budgets = new ArrayList<>();

    }

    public void logo() {
        System.out.println("************************************");
        System.out.println("*           Budget Buddy           *");
        System.out.println("************************************");
    }

    public void MainMenu() throws ParseException, IOException {
        while (true) {
            System.out.println("\nHoofdmenu:");
            System.out.println("1. Maak een nieuw budget");
            System.out.println("2. Toon alle budgetten");
            System.out.println("3. Pas een budget aan");
            System.out.println("4. Verwijder een budget");
            System.out.println("5. Sluit programma");
            System.out.println("Maak uw keuze: ");

            int keuze = scanner.nextInt();
            switch (keuze) {
                case 1:
                    maakNieuwBudget();
                    break;
                case 2:
                    selecteerBudget();
                    break;
                case 3:
                     pasBudgetAan();
                    break;
                case 4:
                     verwijderBudget();
                    break;
                case 5:
                    System.out.println("Programma wordt afgesloten.");
                    return;
                default:
                    System.out.println("Ongeldige invoer, probeer opnieuw.");
            }
        }
    }

    private double vraagDouble(String vraag) {
        while (true) {
            System.out.println(vraag + " of typ '-1' om terug te gaan:");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("terug")) {
                return -1;
            }
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Dit is geen geldig getal. Probeer opnieuw.");
            }
        }
    }

    private Date vraagDatum(String vraag) {
        while (true) {
            System.out.println(vraag + " (dd-mm-jjjj) of typ 'terug' om terug te gaan:");
            String datumStr = scanner.nextLine();
            if (datumStr.equalsIgnoreCase("terug")) {
                return null;
            }
            try {
                return new SimpleDateFormat("dd-MM-yyyy").parse(datumStr);
            } catch (ParseException e) {
                System.out.println("Dit is geen geldige datum. Probeer opnieuw.");
            }
        }
    }

    public void maakNieuwBudget() throws IOException {
        scanner.nextLine();
        // Laad de bestaande budgetten
        budgets = DataStorage.loadData();

        System.out.println("Voer de naam van het budget in of typ 'terug' om terug te gaan naar het hoofdmenu:");
        String naam = scanner.nextLine();
        if (naam.equalsIgnoreCase("terug")) {
            return;
        }

        // Controleer of de budgetnaam al bestaat
        for (Budget budget : budgets) {
            if (budget.getNaam().equalsIgnoreCase(naam)) {
                System.out.println("Een budget met de naam \"" + naam + "\" bestaat al. Probeer een andere naam.");
                return;
            }
        }

        double bedrag = vraagDouble("Voer het totale budgetbedrag in");
        if (bedrag == -1) {
            return;
        }

        Date beginDatum = vraagDatum("Voer de begin datum in");
        if (beginDatum == null) {
            return;
        }

        Date eindDatum = vraagDatum("Voer de eind datum in");
        if (eindDatum == null) {
            return;
        }

        if (beginDatum.after(eindDatum)) {
            System.out.println("De begin datum kan niet na de eind datum zijn. Probeer opnieuw.");
            return;
        }

        Budget nieuwBudget = new Budget(naam, beginDatum, eindDatum, bedrag);
        budgets.add(nieuwBudget);

        DataStorage.saveData(budgets);
        System.out.println("Een nieuw budget met de naam \"" + naam + "\" en een bedrag van " + bedrag + " is aangemaakt.");
    }

    public boolean toonAlleBudgetten() {
        scanner.nextLine();
        if (budgets.isEmpty()) {
            System.out.println("Er zijn geen budgetten om weer te geven.");
            return false;
        }

        System.out.println("Beschikbare budgetten:");
        for (int i = 0; i < budgets.size(); i++) {
            Budget budget = budgets.get(i);
            String beginDatumStr = new SimpleDateFormat("dd-MM-yyyy").format(budget.getBeginDatum());
            String eindDatumStr = new SimpleDateFormat("dd-MM-yyyy").format(budget.getEindDatum());
            System.out.printf("%d. %s - Begin Datum: %s, Eind Datum: %s, Bedrag: %.2f%n",
                    i + 1, budget.getNaam(), beginDatumStr, eindDatumStr, budget.getBudgetBedrag());
        }
        return true;
    }

    public void selecteerBudget() throws IOException {
        budgets = DataStorage.loadData();

        if (!toonAlleBudgetten()) {
            return;
        }

        System.out.println("Selecteer het budget nummer om aan te werken of typ 'terug':");
        String input = scanner.nextLine();
        if (input.equalsIgnoreCase("terug")) {
            return;
        }

        int keuze;
        try {
            keuze = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Dat is geen geldig nummer. Probeer opnieuw.");
            return;
        }

        if (keuze < 1 || keuze > budgets.size()) {
            System.out.println("Ongeldige keuze, probeer opnieuw.");
            return;
        }

        Budget geselecteerdBudget = budgets.get(keuze - 1);
        BudgetMenu budgetMenu = new BudgetMenu(geselecteerdBudget);
        budgetMenu.toonBudgetMenu();
        scanner.nextLine();
    }


    public void pasBudgetAan() throws IOException {
        scanner.nextLine();
        // Eerst laden de budgetten van het tekstbestand
        budgets = DataStorage.loadData();

        // Als er geen budgetten zijn, stop dan de methode
        if (!toonAlleBudgetten()) {
            return; // Terug naar het hoofdmenu
        }

        System.out.println("Kies het nummer van het budget dat u wilt aanpassen of typ 'terug' om terug te gaan:");
        String input = scanner.nextLine();

        if(input.equalsIgnoreCase("terug")) {
            return;
        }

        int keuze;
        try {
            keuze = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Dat is geen geldig nummer. Probeer opnieuw.");
            return;
        }

        if (keuze < 1 || keuze > budgets.size()) {
            System.out.println("Ongeldige keuze, probeer opnieuw.");
            return;
        }

        Budget geselecteerdBudget = budgets.get(keuze - 1);

        System.out.println("U gaat het volgende budget aanpassen: " + geselecteerdBudget.getNaam() +
                ". Als u wilt stoppen, typ dan 'terug'.");

        System.out.println("Voer de nieuwe naam van het budget in of typ 'terug':");
        String naam = scanner.nextLine();
        if (naam.equalsIgnoreCase("terug")) {
            return;
        }
        if (!naam.isEmpty()) {
            geselecteerdBudget.setNaam(naam);
        }

        double bedrag = vraagDouble("Voer het nieuwe budgetbedrag in of typ 'terug' om terug te gaan:");
        if (bedrag == -1) {
            return;
        }
        geselecteerdBudget.setBudgetBedrag(bedrag);

        Date beginDatum = vraagDatum("Voer de nieuwe begin datum in of typ 'terug' om terug te gaan:");
        if (beginDatum == null) {
            return;
        }
        geselecteerdBudget.setBeginDatum(beginDatum);

        Date eindDatum = vraagDatum("Voer de nieuwe eind datum in of typ 'terug' om terug te gaan:");
        if (eindDatum == null) {
            return;
        }
        geselecteerdBudget.setEindDatum(eindDatum);

        // Sla de bijgewerkte budgettenlijst op
        DataStorage.saveData(budgets);
        System.out.println("Budget '" + geselecteerdBudget.getNaam() + "' is bijgewerkt en opgeslagen.");
    }


    public void verwijderBudget() throws IOException {
        scanner.nextLine();
        // Eerst laden de budgetten van het tekstbestand
        budgets = DataStorage.loadData();

        // Als er geen budgetten zijn, stop dan de methode
        if (!toonAlleBudgetten()) {
            return; // Terug naar het hoofdmenu
        }

        System.out.println("Kies het nummer van het budget dat u wilt verwijderen of typ 'terug' om terug te gaan:");
        String input = scanner.nextLine();

        if(input.equalsIgnoreCase("terug")) {
            return;
        }

        int keuze;
        try {
            keuze = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Dat is geen geldig nummer. Probeer opnieuw.");
            return;
        }

        if (keuze < 1 || keuze > budgets.size()) {
            System.out.println("Ongeldige keuze, probeer opnieuw.");
            return;
        }

        // Verwijder het geselecteerde budget
        Budget teVerwijderenBudget = budgets.remove(keuze - 1);
        System.out.println("Budget '" + teVerwijderenBudget.getNaam() + "' is verwijderd.");

        // Opslaan van de bijgewerkte budgettenlijst
        DataStorage.saveData(budgets);
    }



}
