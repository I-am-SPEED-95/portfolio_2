import org.example.budgetBeheer.Budget;
import org.example.budgetBeheer.Inkomsten;
import org.example.budgetBeheer.Uitgaven;
import org.junit.jupiter.api.Test;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

public class EquivalentieKlassen {

    @Test
    void testEquivalentieKlassen() {
        Budget budget = new Budget("EquivalentieKlassenTest", new Date(), new Date(), 1000.0);

        // Voeg testdata toe aan inkomsten en uitgaven
        budget.addInkomsten(new Inkomsten(500, "Werk"));
        budget.addUitgaven(new Uitgaven(200, "Huur"));

        //Logische test
        // Equivalentieklasse 1: totaalInkomen ≤ maxInkomen && totaalUitgaven >= maxUitgaven
        assertFalse(budget.isBudgetOverschreden(600, 100));

        // Equivalentieklasse 2: totaalInkomen > maxInkomen
        assertTrue(budget.isBudgetOverschreden(400, 100));

        // Equivalentieklasse 3: totaalUitgaven < maxUitgaven
        assertTrue(budget.isBudgetOverschreden(600, 300));

        //Fysiek test
        // Equivalentieklasse 1: totaalInkomen ≤ maxInkomen && totaalUitgaven >= maxUitgaven
        // Verwacht resultaat: false
        budget.addInkomsten(new Inkomsten(100, "Werk"));
        budget.addUitgaven(new Uitgaven(500, "Huur"));
        assertFalse(budget.isBudgetOverschreden(600, 100));
        budget.getInkomstenList().clear();
        budget.getUitgavenList().clear();

        // Equivalentieklasse 2: totaalInkomen > maxInkomen
        // Verwacht resultaat: true
        budget.addInkomsten(new Inkomsten(700, "Werk"));
        budget.addUitgaven(new Uitgaven(200, "Huur"));
        assertTrue(budget.isBudgetOverschreden(600, 300));
        budget.getInkomstenList().clear();
        budget.getUitgavenList().clear();

        // Equivalentieklasse 3: totaalUitgaven < maxUitgaven
        // Verwacht resultaat: true
        budget.addInkomsten(new Inkomsten(500, "Werk"));
        budget.addUitgaven(new Uitgaven(50, "Huur"));
        assertTrue(budget.isBudgetOverschreden(600, 100));

    }
}
