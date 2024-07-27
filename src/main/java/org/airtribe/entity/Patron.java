package org.airtribe.entity;

import java.util.*;

public class Patron
{
    private String name;
    private String patronId;
    private int number;

    public Patron(String name,int number, String patronId) {
        this.name = name;
        this.patronId = patronId;
        this.number = number;
        List<Book> borrowedBooks = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getPatronId() {
        return patronId;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return name + " (ID: " + patronId + ")";
    }
}
