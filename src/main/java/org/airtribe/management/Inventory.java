package org.airtribe.management;

import org.airtribe.entity.Book;
import org.airtribe.util.logging;

import java.util.*;
import java.util.stream.Collectors;

public class Inventory {
    private static final Map<String, Book> books = new HashMap<>();
    private static final Map<String, Integer> availableBooks = new HashMap<>();
    private static final logging log = new logging();


    // Add a book to the inventory
    public void addBook(String title,String author,String isbn,int year,int quantity) {
        log.logInfo("Inventory addBook START");
        if(!books.containsKey(isbn)){
            books.put(isbn, new Book(title, author, isbn, year));
            availableBooks.put(isbn, quantity);
            log.logInfo("Book added successfully.");
        }
        else {
            log.logError("Book already Exits with isbn :"+isbn);
        }

        log.logInfo("Inventory addBook END");
    }

    // Remove a book from the inventory
    public void removeBook(String isbn) {
        log.logInfo("Inventory removeBook START");
        if(books.containsKey(isbn)) {
            books.remove(isbn);
            availableBooks.remove(isbn);
            log.logInfo("Book removed successfully.");
        }
        else{
            log.logError("Book with ISBN : "+isbn+" doesn't exits.");
        }
        log.logInfo("Inventory removeBook END");
    }

    // Search for a book by title
    public List<String> searchByTitle(String title) {
        List<String> result = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                result.add(book.toString());
            }
        }
        return result;
    }

    // Search for a book by author
    public List<String> searchByAuthor(String author) {
        List<String> result = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.getAuthor().equalsIgnoreCase(author)) {
                result.add(book.toString());
            }
        }
        return result;
    }

    // Search for a book by ISBN
    public Book searchByIsbn(String isbn) {
        return books.get(isbn);
    }

    // Get all books
    public Collection<Book> getAllBooks() {
        return books.values();
    }

    // Get available quantity of a book
    public int getAvailableQuantity(String isbn) {
        return availableBooks.getOrDefault(isbn, 0);
    }

    public void updateAvailableQuantity(String isbn, int quantity){
        log.logInfo("Inventory updateAvailableQuantity START");

        if(books.get(isbn) != null)
        {
            if(availableBooks.get(isbn) + quantity>=0)
            {
                availableBooks.put(isbn,availableBooks.get(isbn) + quantity);
            }
            else {
                log.logError("error updating book quantity. Current Quantity :"+availableBooks.get(isbn));
            }

        }
        else {
            log.logError("no book with isbn :"+isbn);
        }
        log.logInfo("Inventory updateAvailableQuantity END");
    }

    // Get list of available books
    public Map<String, Integer> getAvailableBooks() {
        return availableBooks.entrySet()
                .stream()
                .filter(entry -> entry.getValue() > 0)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public void viewAvailableBooks(){
        log.logInfo("Inventory printAvailableBooks START");

        System.out.println("Available Books:");
        for (Map.Entry<String, Integer> entry : getAvailableBooks().entrySet()) {
            String isbn = entry.getKey();
            int quantity = entry.getValue();
            Book book = searchByIsbn(isbn);

            System.out.println(book + " - Available quantity: " + quantity);

        }
        log.logInfo("Inventory printAvailableBooks END");
    }
}

