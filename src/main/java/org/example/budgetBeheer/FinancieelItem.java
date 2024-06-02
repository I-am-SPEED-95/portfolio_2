package org.example.budgetBeheer;

abstract class FinancieelItem implements Transactie {
    protected double bedrag;

    // Constructor, getters, setters

    public FinancieelItem(double bedrag) {
        this.bedrag = bedrag;
    }

    @Override
    public double getBedrag() {
        return bedrag;
    }

    @Override
    public void setBedrag(double bedrag) {
        this.bedrag = bedrag;
    }

    @Override
    public String toString() {
        // Overriding de toString methode
        return String.format("Bedrag: %.2f", bedrag);
    }
}
