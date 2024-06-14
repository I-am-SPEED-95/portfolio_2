import org.example.budgetBeheer.Budget;
import org.example.budgetBeheer.Inkomsten;
import org.example.budgetBeheer.Uitgaven;
import org.junit.jupiter.api.Test;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

    public class ConditionCoverage {

        @Test
        void testIsBudgetOverschredenConditionCoverage() {
            Budget budget = new Budget("ConditionCoverageTest", new Date(), new Date(), 1000.0);

            // Voeg testdata toe aan inkomsten en uitgaven
            budget.addInkomsten(new Inkomsten(500, "Werk"));
            budget.addUitgaven(new Uitgaven(200, "Huur"));

            // Voorwaarde A: false, Voorwaarde B: false
            assertFalse(budget.isBudgetOverschreden(600, 100));

            // Voorwaarde A: false, Voorwaarde B: true
            assertTrue(budget.isBudgetOverschreden(600, 300));

            // Voorwaarde A: true, Voorwaarde B: false
            assertTrue(budget.isBudgetOverschreden(400, 100));

            // Voorwaarde A: true, Voorwaarde B: true
            assertTrue(budget.isBudgetOverschreden(400, 300));
        }
    }

