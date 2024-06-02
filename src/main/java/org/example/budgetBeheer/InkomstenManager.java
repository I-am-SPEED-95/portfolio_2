package org.example.budgetBeheer;

import java.util.ArrayList;
import java.util.List;

public class InkomstenManager {
    private List<Inkomsten> inkomstenList = new ArrayList<>();

    //Methode om een inkomst toe te voegen
    public void addInkomsten(Inkomsten inkomst) {
        this.inkomstenList.add(inkomst);
    }

    // Methode om een inkomst te verwijderen
    public boolean deleteInkomst(String bron) {
        return this.inkomstenList.removeIf(inkomst -> inkomst.getBron().equalsIgnoreCase(bron));
    }

    public List<Inkomsten> getInkomstenList() {
        return inkomstenList;
    }
}
