package org.airtribe.management;

import org.airtribe.entity.Book;
import org.airtribe.entity.Patron;
import org.airtribe.util.logging;

import java.util.*;

public class LendingSystem {
    private final Inventory  inventory;
    private final Map<Patron, List<Book>> borrowedBooksMap = new HashMap<>();
    private static final logging log = new logging();

    public LendingSystem(Inventory  inventory) {
        this.inventory = inventory;
    }

    public void addEmptyborrowedBooksMap(Patron patron){
        borrowedBooksMap.put(patron, new ArrayList<>());
    }

    public void checkoutBook(String isbn, Patron patron) {
        log.logInfo("LendingSystem  addBook START");

        Book book = inventory.searchByIsbn(isbn);

        if (book != null ) {
            List<Book> borrowedBooks = borrowedBooksMap.get(patron);

            if (inventory.getAvailableQuantity(isbn)>0) {
                if (borrowedBooks.size() <= 3) {
                    inventory.updateAvailableQuantity(isbn,-1);
                    borrowedBooks.add(book);
                    borrowedBooksMap.put(patron,borrowedBooks);
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

    public void returnBook(String isbn, Patron patron) {
        log.logInfo("LendingSystem  addBook START");

        Book book = inventory.searchByIsbn(isbn);

        if (book != null && patron != null) {
            List<Book> borrowedBooks = borrowedBooksMap.get(patron);

            if (borrowedBooks.remove(book)) {
                inventory.updateAvailableQuantity(isbn,1);
                borrowedBooks.remove(book);
                borrowedBooksMap.put(patron,borrowedBooks);
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

    public List<Book>  getBookBorrowedByByPatron(Patron patron) {

        return borrowedBooksMap.getOrDefault(patron,new ArrayList<>());
    }

    public void viewBorrowedBooksByPatron(Patron patron)
    {
        log.logInfo("LendingSystem  viewBorrowedBooksByPatron START");
        if(patron != null)
        {
            if(!borrowedBooksMap.get(patron).isEmpty()){
                borrowedBooksMap.get(patron).forEach(System.out::println);
            }
            else{
                log.logInfo("No Borrowed Books");
            }
        }
        else{
            log.logError("patron not found ");
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

    public void removeBorrowedBooksByPatron(Patron patron){
        log.logInfo("LendingSystem  removeBorrowedBooksByPatron START");
        borrowedBooksMap.remove(patron);
        log.logInfo("LendingSystem  removeBorrowedBooksByPatron END");
    }
}

