package org.airtribe.management;

import org.airtribe.entity.Patron;
import org.airtribe.util.logging;
import org.airtribe.management.LendingSystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PatronService {
    private final Map<String, Patron> patrons = new HashMap<>();
    private static final logging log = new logging();
    private static LendingSystem lendingSystem;

    public PatronService(LendingSystem  lendingSystem) {
        this.lendingSystem = lendingSystem;
    }
    
    public void addPatron(String name,int number,String patronId) {
        log.logInfo("patronService addPatron START");

        if(!patrons.containsKey(patronId)) {
            patrons.put(patronId, new Patron(name,number,patronId));
            lendingSystem.addEmptyborrowedBooksMap(patrons.get(patronId));
            log.logInfo("Patron added successfully.");
        }
        else{
            log.logError("Patron already exits with patronId: "+patronId);
        }

        log.logInfo("patronService addPatron END");

    }

    public void updatePatron(int number,String patronId) {
        log.logInfo("patronService  updatePatron START");

        if(patrons.containsKey(patronId)) {
            Patron patron = patrons.get(patronId);
            patron.setNumber(number);
            patrons.put(patronId, patron);
            log.logInfo("Patron updated successfully.");
        }
        else{
            log.logError("Patron doesn't exits with patronId: "+patronId);
        }

        log.logInfo("patronService  updatePatron END");

    }

    public void removePatron(String patronId) {
        log.logInfo("patronService  removePatron START");

        if(patrons.containsKey(patronId)) {
            if(lendingSystem.getBookBorrowedByByPatron(patrons.get(patronId)).isEmpty()) {
                lendingSystem.removeBorrowedBooksByPatron(patrons.get(patronId));
                patrons.remove(patronId);
                log.logInfo("Patron remove successfully.");
            }
            else{
                log.logError("Kindly return all Borrowed book to remove Patron");
            }
        }
        else{
            log.logError("Patron doesn't exits with patronId: "+patronId);
        }

        log.logInfo("patronService  removePatron END");

    }

    public Patron getPatron(String patronId)
    {
        return patrons.getOrDefault(patronId,null);
    }

    public boolean isPatronExits(String patronId){
        return patrons.containsKey(patronId);
    }
}
