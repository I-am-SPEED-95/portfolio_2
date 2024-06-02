package org.example.budgetBeheer;

import java.util.ArrayList;
import java.util.List;

public class UitgavenManager {
    private List<Uitgaven> uitgavenList = new ArrayList<>();

    // Methode om een uitgave toe te voegen
    public void addUitgaven(Uitgaven uitgave) {
        this.uitgavenList.add(uitgave);
    }

    // Methode om een uitgave te verwijderen
    public boolean deleteUitgave(String categorie) {
        return this.uitgavenList.removeIf(uitgave -> uitgave.getCategorie().equals(categorie));
    }

    public List<Uitgaven> getUitgavenList() {
        return uitgavenList;
    }
}
