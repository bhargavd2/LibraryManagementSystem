package org.airtribe;

import org.airtribe.util.seed;
import org.airtribe.util.logging;
import org.airtribe.management.Inventory;
import org.airtribe.management.LendingSystem;
import org.airtribe.management.PatronService;
import java.util.*;

public class Main {
    private static final Inventory inventory = new Inventory();
    private static final LendingSystem lendingSystem = new LendingSystem(inventory);
    private static final Scanner scanner = new Scanner(System.in);
    private static final logging log = new logging();
    private static final PatronService patronService = new PatronService(lendingSystem);

    public static void main(String[] args) {
        log.logInfo("Library Management System START");

        seed.seed(inventory,patronService);

        boolean flag = true;
        while (flag) {
            try {
                System.out.println("Library Management System");
                System.out.println("1. Book Management");
                System.out.println("2. Patron Management");
                System.out.println("3. Checkout Book");
                System.out.println("4. Return Book");
                System.out.println("5. View Available Books");
                System.out.println("6. View Borrowed Books");
                System.out.println("7. Check Book Availablity by ISBN");
                System.out.println("0. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        bookManagement();
                        break;
                    case 2:
                        patronManagement();
                        break;
                    case 3:
                        checkoutBook();
                        break;
                    case 4:
                        returnBook();
                        break;
                    case 5:
                        viewAvailableBooks();
                        break;
                    case 6:
                        viewBorrowedBooks();
                        break;
                    case 7:
                        checkBookAvalibality();
                        break;
                    case 0:
                        System.out.println("Exiting...");
                        flag = false;
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
            catch (Exception e){
                log.logError(e.getMessage());
            }
        }

        log.logInfo("Library Management System END");
    }

    private static void bookManagement()
    {
        log.logInfo("Library Management System bookManagement START");

        System.out.println("Library Management System");
        System.out.println("1. Add new Book");
        System.out.println("2. Remove Book");
        System.out.println("3. Search Book");
        System.out.println("4. Add Book quantity");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1:
                addBook();
                break;
            case 2:
                removeBook();
                break;
            case 3:
                searchBook();
                break;
            case 4:
                updateBookQty();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }


        log.logInfo("Library Management System bookManagement END");
    }

    private static void patronManagement()
    {
        log.logInfo("Library Management System patronManagement START");

        System.out.println("Library Management System");
        System.out.println("1. Add Patron");
        System.out.println("2. Remove Patron");
        System.out.println("3. Update Patron");
        System.out.println("4. View Borrowed Patron");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1:
                addPatron();
                break;
            case 2:
                removePatron();
                break;
            case 3:
                updatePatron();
                break;
            case 4:
                viewBorrowedBooksByPatron();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }


        log.logInfo("Library Management System patronManagement END");
    }

    private static void addBook() {

        log.logInfo("Library Management System addBook START");

        System.out.print("Enter book title: ");
        String title = scanner.nextLine();

        System.out.print("Enter book author: ");
        String author = scanner.nextLine();

        System.out.print("Enter book ISBN: ");
        String isbn = scanner.nextLine();

        System.out.print("Enter book publication year: ");
        int year = scanner.nextInt();

        System.out.print("Enter book quantity: ");
        int quantity = scanner.nextInt();

        inventory.addBook(title, author, isbn, year, quantity);

        log.logInfo("Library Management System addBook END");
    }

    private static void removeBook() {
        log.logInfo("Library Management System removeBook START");

        System.out.print("Enter book ISBN to remove: ");
        String isbn = scanner.nextLine();

        if(!lendingSystem.isBookBorrowed(isbn)){
            inventory.removeBook(isbn);
        }
        else{
            System.out.println("Cannot remove book with ISBN " + isbn + " as it is currently borrowed.");
        }

        log.logInfo("Library Management System removeBook END");
    }

    private static void searchBook() {
        log.logInfo("Library Management System searchBook START");

        System.out.println("Search by: ");
        System.out.println("1. Title");
        System.out.println("2. Author");
        System.out.println("3. ISBN");
        System.out.print("Enter your choice: ");
        int searchChoice = scanner.nextInt();
        scanner.nextLine();

        switch (searchChoice) {
            case 1:
                System.out.print("Enter book title: ");
                String title = scanner.nextLine();

                List<String> booksByTitle = inventory.searchByTitle(title);

                if (booksByTitle.isEmpty()) {
                    System.out.println("No books found with the given title.");
                } else {
                    booksByTitle.forEach(System.out::println);
                }
                break;

            case 2:
                System.out.print("Enter book author: ");
                String author = scanner.nextLine();

                List<String> booksByAuthor = inventory.searchByAuthor(author);

                if (booksByAuthor.isEmpty()) {
                    System.out.println("No books found with the given author.");
                } else {
                    booksByAuthor.forEach(System.out::println);
                }
                break;

            case 3:
                System.out.print("Enter book ISBN: ");
                String isbn = scanner.nextLine();

                if (inventory.searchByIsbn(isbn) != null) {
                    System.out.println(inventory.searchByIsbn(isbn).toString());
                } else {
                    System.out.println("No book found with the given ISBN.");
                }
                break;
            default:
                System.out.println("Invalid Search choice.");
        }
        log.logInfo("Library Management System searchBook END");
    }

    private static void updateBookQty()
    {
        log.logInfo("Library Management System updateBookQty START");
        System.out.print("Enter book ISBN: ");
        String isbn = scanner.nextLine();

        System.out.print("Enter book quantity: ");
        int quantity = scanner.nextInt();

        inventory.updateAvailableQuantity(isbn,quantity);

        log.logInfo("Library Management System updateBookQty END");
    }

    private static void addPatron() {
        log.logInfo("Library Management System addPatron START");

        System.out.print("Enter patron ID: ");
        String patronId = scanner.nextLine();

        System.out.print("Enter patron name: ");
        String name = scanner.nextLine();

        System.out.print("Enter patron Phone Number: ");
        int number =scanner.nextInt();
        scanner.nextLine();

        patronService.addPatron(name,number, patronId);

        log.logInfo("Library Management System addPatron END");
    }

    private static void updatePatron() {
        log.logInfo("Library Management System updatePatron START");

        System.out.print("Enter patron ID: ");
        String patronId = scanner.nextLine();

        System.out.print("Enter patron Phone Number: ");
        int number = scanner.nextInt();
        scanner.nextLine();

        patronService.updatePatron(number, patronId);
        log.logInfo("Library Management System updatePatron END");
    }

    private static void removePatron() {
        log.logInfo("Library Management System removePatron START");

        System.out.print("Enter patron ID: ");
        String patronId = scanner.nextLine();

        patronService.removePatron(patronId);
        log.logInfo("Library Management System removePatron END");
    }

    private static void viewBorrowedBooksByPatron() {
        log.logInfo("Library Management System viewBorrowedBooksByPatron START");

        System.out.print("Enter patron ID: ");
        String patronId = scanner.nextLine();
        if(patronService.isPatronExits(patronId))
        {
            lendingSystem.viewBorrowedBooksByPatron(patronService.getPatron(patronId));
        }
        else {
            log.logError("Patron doesn't exits with patronId: "+patronId);
        }

        log.logInfo("Library Management System viewBorrowedBooksByPatron END");
    }

    private static void checkoutBook() {
        log.logInfo("Library Management System checkoutBook START");
        System.out.print("Enter book ISBN to checkout: ");
        String isbn = scanner.nextLine();

        System.out.print("Enter patron ID: ");
        String patronId = scanner.nextLine();
        if(patronService.isPatronExits(patronId))
        {
            lendingSystem.checkoutBook(isbn, patronService.getPatron(patronId));
        }
        else {
            log.logError("Patron doesn't exits with patronId: "+patronId);
        }

        log.logInfo("Library Management System checkoutBook END");
    }

    private static void returnBook() {
        log.logInfo("Library Management System returnBook START");
        System.out.print("Enter book ISBN to return: ");
        String isbn = scanner.nextLine();

        System.out.print("Enter patron ID: ");
        String patronId = scanner.nextLine();
        if(patronService.isPatronExits(patronId)) {
            lendingSystem.returnBook(isbn, patronService.getPatron(patronId));
        }else {
            log.logError("Patron doesn't exits with patronId: "+patronId);
        }
        log.logInfo("Library Management System returnBook END");
    }

    private static void viewAvailableBooks() {
        log.logInfo("Library Management System viewAvailableBooks START");
        inventory.viewAvailableBooks();
        log.logInfo("Library Management System viewAvailableBooks END");
    }

    private static void viewBorrowedBooks() {
        log.logInfo("Library Management System viewBorrowedBooks START");
        lendingSystem.viewBorrowedBooks();
        log.logInfo("Library Management System viewBorrowedBooks END");
    }

    private static void checkBookAvalibality(){
        log.logInfo("Library Management System checkBookAvalibality START");
        System.out.print("Enter book ISBN to remove: ");
        String isbn = scanner.nextLine();

        System.out.println("the available quantity of book with ISBN: "+isbn+ " is qty: "+inventory.getAvailableQuantity(isbn));

        log.logInfo("Library Management System checkBookAvalibality END");
    }
}
