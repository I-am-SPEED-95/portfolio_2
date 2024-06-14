import org.example.budgetBeheer.Budget;
import org.example.budgetBeheer.Inkomsten;
import org.example.budgetBeheer.Uitgaven;
import org.junit.jupiter.api.Test;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

public class DecisionCoverage {

    @Test
    void testIsBudgetOverschredenDecisionCoverage() {
        Budget budget = new Budget("DecisionCoverageTest", new Date(), new Date(), 1000.0);

        // Testdata toevoegen aan inkomsten en uitgaven
        budget.addInkomsten(new Inkomsten(500, "Werk"));
        budget.addUitgaven(new Uitgaven(200, "Huur"));

        // Voorwaarde A: false, Voorwaarde B: false → D: false
        assertFalse(budget.isBudgetOverschreden(600, 100));

        // Voorwaarde A: false, Voorwaarde B: true → D: true
        assertTrue(budget.isBudgetOverschreden(600, 300));

        // Voorwaarde A: true, Voorwaarde B: false → D: true
        assertTrue(budget.isBudgetOverschreden(400, 100));

        // Voorwaarde A: true, Voorwaarde B: true → D: true
        assertTrue(budget.isBudgetOverschreden(400, 300));
    }
}
