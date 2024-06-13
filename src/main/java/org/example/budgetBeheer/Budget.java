package org.example.budgetBeheer;

import org.example.Subject;
import org.example.dataBeheer.DataStorageInkomsten;
import org.example.dataBeheer.DataStorageUitgaven;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Budget extends Subject {
    private String naam;
    private Date beginDatum;
    private Date eindDatum;
    private Double budgetBedrag;
    private List<Inkomsten> inkomstenList;
    private List<Uitgaven> uitgavenList;

    // Constructor
    public Budget(String naam, Date beginDatum, Date eindDatum, double budgetBedrag) {
        this.naam = naam;
        this.beginDatum = beginDatum;
        this.eindDatum = eindDatum;
        this.budgetBedrag = budgetBedrag;
        this.inkomstenList = new ArrayList<>();
        this.uitgavenList = new ArrayList<>();
    }

    // Getters en Setters

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
        notifyObservers();
    }

    public Date getBeginDatum() {
        return beginDatum;
    }

    public void setBeginDatum(Date beginDatum) {
        this.beginDatum = beginDatum;
        notifyObservers();
    }

    public Date getEindDatum() {
        return eindDatum;
    }

    public void setEindDatum(Date eindDatum) {
        this.eindDatum = eindDatum;
        notifyObservers();
    }

    public double getBudgetBedrag() {
        return budgetBedrag;
    }

    public void setBudgetBedrag(double budgetBedrag) {
        this.budgetBedrag = budgetBedrag;
        notifyObservers();
    }

    public List<Inkomsten> getInkomstenList() {
        return inkomstenList;
    }

    public void setInkomstenList(List<Inkomsten> inkomstenList) {
        this.inkomstenList = inkomstenList;
        notifyObservers();
    }

    public List<Uitgaven> getUitgavenList() {
        return uitgavenList;
    }

    public void setUitgavenList(List<Uitgaven> uitgavenList) {
        this.uitgavenList = uitgavenList;
        notifyObservers();
    }

    // Methode om een inkomst toe te voegen
    public void addInkomsten(Inkomsten inkomst) {
        this.inkomstenList.add(inkomst);
        notifyObservers();
    }

    // Methode om een uitgave toe te voegen
    public void addUitgaven(Uitgaven uitgave) {
        this.uitgavenList.add(uitgave);
        notifyObservers();
    }

    // Methode om een inkomst te verwijderen
    public boolean deleteInkomst(String bron) {
        boolean result = this.inkomstenList.removeIf(inkomst -> inkomst.getBron().equalsIgnoreCase(bron));
        if (result) {
            notifyObservers();
        }
        return result;
    }

    // Methode om een uitgave te verwijderen
    public boolean deleteUitgave(String categorie) {
        boolean result = this.uitgavenList.removeIf(uitgave -> uitgave.getCategorie().equalsIgnoreCase(categorie));
        if (result) {
            notifyObservers();
        }
        return result;
    }

    // Bereken het totaal gesaved bedrag
    public double berekenGesaved() throws IOException {
        // Laad de huidige inkomsten en uitgaven van dit budget
        List<Inkomsten> inkomstenList = DataStorageInkomsten.loadInkomsten(this.naam);
        List<Uitgaven> uitgavenList = DataStorageUitgaven.loadUitgaven(this.naam);

        double totaalGesaved = budgetBedrag; // Begin met het totale budget

        // Trek de bedragen van alle uitgaven af van het gesaved bedrag
        for (Uitgaven uitgave : uitgavenList) {
            totaalGesaved -= uitgave.getBedrag();
        }

        // Voeg de bedragen van alle inkomsten toe aan het gesaved bedrag
        for (Inkomsten inkomst : inkomstenList) {
            totaalGesaved += inkomst.getBedrag();
        }

        return totaalGesaved;
    }

    // Bereken het totaal gespendeerde bedrag
    public double berekenGespendeerd() throws IOException {
        // Laad de huidige uitgaven van dit budget
        List<Uitgaven> uitgavenList = DataStorageUitgaven.loadUitgaven(this.naam);

        double totaalGespendeerd = 0;

        // Tel de bedragen van alle uitgaven op
        for (Uitgaven uitgave : uitgavenList) {
            totaalGespendeerd += uitgave.getBedrag();
        }

        return totaalGespendeerd;
    }
}
