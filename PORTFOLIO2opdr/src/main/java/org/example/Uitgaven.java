package org.example;

public class Uitgaven extends FinancieelItem{
    private String categorie;

    // Constructor

    public Uitgaven(double bedrag, String categorie) {
        super(bedrag);
        this.categorie = categorie;
    }


    // Getters en Setters


    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String toFileString() {
        return getCategorie() + ";" + getBedrag();
    }

}
