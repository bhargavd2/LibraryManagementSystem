# LibraryManagementSystem

## Overview

Tasked with designing and implementing a Library Management System in Java. 
This system should help librarians manage books, patrons, and lending processes efficiently. 
the solution should demonstrate understanding of Object-Oriented Programming (OOP), SOLID principles, and relevant design patterns.

## Core Features

### Book Management
- **Book Class**: Represents a book with attributes title, author, ISBN, and publication year.
- **Inventory Management**: 
  - responable for track available books.
  - Add, remove, update, and search books by title, author, or ISBN.
  - while removing it checks is the book is not borrowed by any Patron

### Patron Management
- **Patron Class**: Represents library members.
- **Patron Management**: 
  - responable for track Patrons.
  - Add, update , remove patrons.
  - while removing it checks is Patron has not borrowed by Book

### Lending Management System
- **LendingSystem class**: tracks all borrowed books by all Patrons
  - Manage book checkouts and returns
  - check if book id borrowed before book is returned
  - check if no more than 3 more are borrowed by patron

### Logging Management
- **Logging**: Implement a logging framework to log important events and errors.
  - used singleton approach to have only one logger instance

## Requirements
- **Java 17** or higher
- **Maven 4.x**

## How to Run
1. **Clone the Repository**:
   ```bash
   git clone https://github.com/bhargavd2/LibraryManagementSystem.git
   cd LibraryManagementSystem
2. mvn clean package
3. Run `Main` class to interact with the system and follow the instruction.

## Future Plans
- **Persistence**: Integrate with a database for persistent storage.
- **Advanced Features**: Implement multi-branch support and notification.
- **Optional Extensions**
  - validator : as of now no validation is done. eg : 1002 is also a phone number
  - Multi-branch Support: Extend the system to support multiple library branches.
  - Transfer Book: to support above branching and help transfer books between library
