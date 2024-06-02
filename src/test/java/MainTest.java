import org.example.budgetBeheer.Budget;
import org.example.menu.TransactiesMenu;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest {

    @Test
    public void testBudgetAanmakenEnOphalen() {
        // Creëer Budget object met testdata
        Date start = new Date();
        Date eind = new Date();
        // Budget budget = new Budget(start, eind, 1000.0);

        // Verifieer de waarden van Budget object
       // assertEquals(start, budget.getBeginDatum());
        //assertEquals(eind, budget.getEindDatum());
       // assertEquals(1000.0, budget.getBudgetBedrag());
    }

    @Test
    public void testInkomstenAanmakenEnOphalen() {
        // Creëer Inkomsten object met testdata
        //Inkomsten inkomst = new Inkomsten("Werk", 2000.0);

        // Verifieer de waarden van Inkomsten object
      //  assertEquals("Werk", inkomst.getBron());
       // assertEquals(2000.0, inkomst.getBedrag());
    }

    @Test
    public void testUitgavenAanmakenEnOphalen() {
        // Creëer Uitgaven object met testdata
        //Uitgaven uitgave = new Uitgaven("Boodschappen", 150.0);

        // Verifieer de waarden van Uitgaven object
        //assertEquals("Boodschappen", uitgave.getCategorie());
        //assertEquals(150.0, uitgave.getBedrag());
    }

    @Nested
    @ExtendWith(MockitoExtension.class)
    class TransactiesMenuTest {

        private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        private final PrintStream originalOut = System.out;

        @Mock
        private Budget budget;

        @BeforeEach
        public void setUp() {
            System.setOut(new PrintStream(outContent));
        }

        @AfterEach
        public void restore() {
            System.setOut(originalOut);
        }

        @Test
        public void testToonTransactiesMenu() throws IOException {
            // Arrange
            String input = "2\n"; // De gebruiker selecteert optie 2 om terug te gaan naar het hoofdmenu
            System.setIn(new ByteArrayInputStream(input.getBytes()));
            Scanner scanner = new Scanner(System.in);
            TransactiesMenu transactiesMenu = new TransactiesMenu(scanner, budget);

            // Act
            transactiesMenu.toonTransactiesMenu();

            // Assert
            assertTrue(outContent.toString().contains("Teruggaan naar het hoofdmenu..."));
        }
    }
}
