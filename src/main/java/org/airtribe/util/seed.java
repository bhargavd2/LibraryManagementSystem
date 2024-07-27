package org.airtribe.util;

import org.airtribe.management.Inventory;
import org.airtribe.management.LendingSystem;
import org.airtribe.management.PatronService;

public class seed {

    public static void seed(Inventory inventory, PatronService patronService){

        inventory.addBook("To Kill a Mockingbird", "Harper Lee", "9", 1960,1);
        inventory.addBook("1984", "George Orwell", "7", 1949,10);
        inventory.addBook("The Great Gatsby", "F. Scott Fitzgerald", "8", 1925,2);
        inventory.addBook("Moby Dick", "Herman Melville", "5", 1851,3);
        inventory.addBook("Pride and Prejudice", "Jane Austen", "2", 1813,5);

        patronService.addPatron("Alice Smith", 1001,"P001");
        patronService.addPatron("Bob Johnson",1002, "P002");
        patronService.addPatron("Carol Davis", 1003,"P003");
        patronService.addPatron("David Wilson", 1004,"P004");
        patronService.addPatron("Eve Brown", 1005,"P005");
    }
}
