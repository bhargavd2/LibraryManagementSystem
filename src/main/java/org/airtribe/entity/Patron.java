package org.airtribe.entity;

import java.util.*;

public class Patron
{
    private String name;
    private String patronId;
    private int phoneNumber;

    public Patron(String name,int phoneNumber, String patronId) {
        this.name = name;
        this.patronId = patronId;
        this.phoneNumber = phoneNumber;
        List<Book> borrowedBooks = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getPatronId() {
        return patronId;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int number) {
        this.phoneNumber = number;
    }

    @Override
    public String toString() {
        return name + " (ID: " + patronId + ")";
    }
}
