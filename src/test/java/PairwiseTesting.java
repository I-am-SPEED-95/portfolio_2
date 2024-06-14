import org.example.budgetBeheer.Budget;
import org.example.budgetBeheer.Inkomsten;
import org.example.budgetBeheer.Uitgaven;
import org.junit.jupiter.api.Test;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

public class PairwiseTesting {

    @Test
    void testPairwiseCombinaties() {
        Budget budget = new Budget("PairwiseTesting", new Date(), new Date(), 1000.0);

        // Voeg testdata toe aan inkomsten en uitgaven en test de pairwise combinaties

        // Combinatie 1
        budget.addInkomsten(new Inkomsten(500, "Werk"));
        budget.addUitgaven(new Uitgaven(150, "Huur"));
        assertFalse(budget.isBudgetOverschreden(600, 100));
        budget.getInkomstenList().clear();
        budget.getUitgavenList().clear();

        // Combinatie 2
        budget.addInkomsten(new Inkomsten(500, "Werk"));
        budget.addUitgaven(new Uitgaven(200, "Huur"));
        assertTrue(budget.isBudgetOverschreden(600, 250));
        budget.getInkomstenList().clear();
        budget.getUitgavenList().clear();

        // Combinatie 3
        budget.addInkomsten(new Inkomsten(500, "Werk"));
        budget.addUitgaven(new Uitgaven(150, "Huur"));
        assertFalse(budget.isBudgetOverschreden(800, 100));
        budget.getInkomstenList().clear();
        budget.getUitgavenList().clear();

        // Combinatie 4
        budget.addInkomsten(new Inkomsten(700, "Werk"));
        budget.addUitgaven(new Uitgaven(100, "Huur"));
        assertTrue(budget.isBudgetOverschreden(800, 250));
        budget.getInkomstenList().clear();
        budget.getUitgavenList().clear();

        // Combinatie 5
        budget.addInkomsten(new Inkomsten(700, "Werk"));
        budget.addUitgaven(new Uitgaven(200, "Huur"));
        assertTrue(budget.isBudgetOverschreden(600, 150));
        budget.getInkomstenList().clear();
        budget.getUitgavenList().clear();

        // Combinatie 6
        budget.addInkomsten(new Inkomsten(700, "Werk"));
        budget.addUitgaven(new Uitgaven(200, "Huur"));
        assertTrue(budget.isBudgetOverschreden(800, 250));
        budget.getInkomstenList().clear();
        budget.getUitgavenList().clear();

        // Combinatie 7
        budget.addInkomsten(new Inkomsten(900, "Werk"));
        budget.addUitgaven(new Uitgaven(200, "Huur"));
        assertTrue(budget.isBudgetOverschreden(600, 150));
        budget.getInkomstenList().clear();
        budget.getUitgavenList().clear();

        // Combinatie 8
        budget.addInkomsten(new Inkomsten(900, "Werk"));
        budget.addUitgaven(new Uitgaven(100, "Huur"));
        assertTrue(budget.isBudgetOverschreden(800, 250));
        budget.getInkomstenList().clear();
        budget.getUitgavenList().clear();
    }
}
