package org.airtribe.management;

import org.airtribe.entity.Book;
import org.airtribe.entity.Patron;
import org.airtribe.util.logging;

import java.util.*;

public class LendingSystem {
    private final Inventory  inventory;
    private final Map<String, Patron> patrons = new HashMap<>();
    private final Map<Patron, List<Book>> borrowedBooksMap = new HashMap<>();
    private static final logging log = new logging();

    public LendingSystem(Inventory  inventory) {
        this.inventory = inventory;
    }

    public void addPatron(String name,int number,String patronId) {
        log.logInfo("LendingSystem  addBook START");

        if(!patrons.containsKey(patronId)) {
            patrons.put(patronId, new Patron(name,number,patronId));
            borrowedBooksMap.put(patrons.get(patronId), new ArrayList<>());
            log.logInfo("Patron added successfully.");
        }
        else{
            log.logError("Patron already exits with patronId: "+patronId);
        }

        log.logInfo("LendingSystem  addBook END");

    }

    public void updatePatron(int number,String patronId) {
        log.logInfo("LendingSystem  updatePatron START");

        if(patrons.containsKey(patronId)) {
            Patron patron = patrons.get(patronId);
            patron.setNumber(number);
            patrons.put(patronId, patron);
            log.logInfo("Patron updated successfully.");
        }
        else{
            log.logError("Patron doesn't exits with patronId: "+patronId);
        }

        log.logInfo("LendingSystem  updatePatron END");

    }

    public void removePatron(String patronId) {
        log.logInfo("LendingSystem  removePatron START");

        if(patrons.containsKey(patronId)) {
            if(borrowedBooksMap.get(patrons.get(patronId)).isEmpty()) {

                borrowedBooksMap.remove(patrons.get(patronId));
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

        log.logInfo("LendingSystem  removePatron END");

    }

    public void checkoutBook(String isbn, String patronId) {
        log.logInfo("LendingSystem  addBook START");

        Book book = inventory.searchByIsbn(isbn);
        Patron patron = patrons.get(patronId);

        if (book != null && patron != null) {
            List<Book> borrowedBooks = borrowedBooksMap.get(patron);

            if (inventory.getAvailableQuantity(isbn)>0) {
                if (borrowedBooks.size() < 3) {
                    inventory.updateAvailableQuantity(isbn,-1);
                    borrowedBooks.add(book);
                   log.logInfo("Book checked out: " + book);
                } else {
                    log.logError("Checkout failed: Patron has already borrowed 3 books.");
                }
            } else {
                log.logError("Checkout failed: Book may be unavailable.");
            }
        } else {
            log.logError("Checkout failed: Book or patron not found.");
        }

        log.logInfo("LendingSystem  addBook END");
    }

    public void returnBook(String isbn, String patronId) {
        log.logInfo("LendingSystem  addBook START");

        Book book = inventory.searchByIsbn(isbn);
        Patron patron = patrons.get(patronId);

        if (book != null && patron != null) {
            List<Book> borrowedBooks = borrowedBooksMap.get(patron);

            if (borrowedBooks.remove(book)) {
                inventory.updateAvailableQuantity(isbn,1);
                log.logInfo("Book returned: " + book + "by: "+patron);
            } else {
                log.logError("Book was not borrowed by this patron or not found.");
            }
        } else {
            log.logError("Book or patron not found.");
        }

        log.logInfo("LendingSystem  addBook END");
    }

    public boolean isBookBorrowed(String isbn) {
        for (List<Book> booksList : borrowedBooksMap.values()) {
            for (Book book : booksList) {
                if (book.getIsbn().equals(isbn)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void viewBorrowedBooksByPatron(String patronId)
    {
        log.logInfo("LendingSystem  viewBorrowedBooksByPatron START");
        if(patrons.containsKey(patronId))
        {
            if(!borrowedBooksMap.get(patrons.get(patronId)).isEmpty()){
                borrowedBooksMap.get(patrons.get(patronId)).forEach(System.out::println);
            }
            else{
                log.logInfo("No Borrowed Books");
            }
        }
        else{
            log.logError("patron not found with patronId: "+patronId);
        }
        log.logInfo("LendingSystem  viewBorrowedBooksByPatron END");
    }


    public void viewBorrowedBooks() {
        log.logInfo("LendingSystem  addBook START");

        if(!borrowedBooksMap.isEmpty()) {
            System.out.println("Borrowed Books:");

            for (Map.Entry<Patron, List<Book>> entry : borrowedBooksMap.entrySet()) {
                Patron patron = entry.getKey();
                List<Book> books = entry.getValue();
                if (!books.isEmpty()) {
                    System.out.println(patron + " has borrowed:");
                    int i = 0;
                    for (Book book : books) {
                        System.out.println("    " + (i++) + ":" + book);
                    }
                }
            }
        }
        else{
            log.logError("NO Borrowed Books");
        }

        log.logInfo("LendingSystem  addBook END");
    }
}

