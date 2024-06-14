import org.example.budgetBeheer.Budget;
import org.example.budgetBeheer.Inkomsten;
import org.example.budgetBeheer.Uitgaven;
import org.junit.jupiter.api.Test;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

public class ModifiedConditionDecisionCoverage {

    @Test
    void testIsBudgetOverschredenMC_DC() {
        Budget budget = new Budget("ModifiedConditionDecisionCoverageTest", new Date(), new Date(), 1000.0);

        // Voeg testdata toe aan inkomsten en uitgaven
        budget.addInkomsten(new Inkomsten(500, "Werk"));
        budget.addUitgaven(new Uitgaven(200, "Huur"));

        // Testgeval 1: Voorwaarde A: false, Voorwaarde B: false → D: false
        assertFalse(budget.isBudgetOverschreden(600, 100));

        // Testgeval 2: Voorwaarde A: false, Voorwaarde B: true → D: true
        assertTrue(budget.isBudgetOverschreden(600, 300));

        // Testgeval 3: Voorwaarde A: true, Voorwaarde B: false → D: true
        assertTrue(budget.isBudgetOverschreden(400, 100));

        // Testgeval 4: Voorwaarde A: true, Voorwaarde B: true → D: true
        assertTrue(budget.isBudgetOverschreden(400, 300));

        // Testgeval 5: Voorwaarde A: true, Voorwaarde B: false → D: true
        assertTrue(budget.isBudgetOverschreden(400, 100));

        // Testgeval 6: Voorwaarde A: false, Voorwaarde B: true → D: true
        assertTrue(budget.isBudgetOverschreden(600, 300));
    }
}
