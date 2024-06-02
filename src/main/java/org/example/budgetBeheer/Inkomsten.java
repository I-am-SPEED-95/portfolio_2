package org.example.budgetBeheer;
public class Inkomsten extends FinancieelItem{

        private String bron;


        // Constructor
    public Inkomsten(double bedrag, String bron) {
        super(bedrag);
        this.bron = bron;
    }

    // Getters en Setters
    public String getBron() {
        return bron;
    }

    public void setBron(String bron) {
        this.bron = bron;
    }

    // Methode om inkomsten om te zetten naar een string voor opslag
    public String toFileString() {
        return getBron() + ";" + getBedrag();
    }
}
