package Library;

import java.time.Year;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Book {
    private String name;
    private String writer;
    private int yearCreated;
    private int numberOfVolumes;
    private static ArrayList<Book> bookList = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    static class BookNotFoundException extends Exception {
        public BookNotFoundException(String message) {
            super(message);
        }
    }

    static class InvalidBookOperationException extends Exception {
        public InvalidBookOperationException(String message) {
            super(message);
        }
    }

    static class InvalidInputException extends Exception {
        public InvalidInputException(String message) {
            super(message);
        }
    }

    static class BookOutOfStockException extends Exception {
        public BookOutOfStockException(String message) {
            super(message);
        }
    }

    public Book(String name, String writer, int yearCreated, int numberOfVolumes) {
        this.name = name;
        this.writer = writer;
        this.yearCreated = yearCreated;
        this.numberOfVolumes = numberOfVolumes;
    }

    public Book() {
        // Default constructor
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws InvalidInputException {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidInputException("Book name cannot be empty");
        }
        this.name = name.trim();
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) throws InvalidInputException {
        if (writer == null || writer.trim().isEmpty()) {
            throw new InvalidInputException("Writer name cannot be empty");
        }
        this.writer = writer.trim();
    }

    public int getYearCreated() {
        return yearCreated;
    }

    public void setYearCreated(int yearCreated) throws InvalidInputException {
        int currentYear = Year.now().getValue();
        if (yearCreated <= 0 || yearCreated > currentYear) {
            throw new InvalidInputException("Year must be between 1 and " + currentYear);
        }
        this.yearCreated = yearCreated;
    }

    public int getNumberOfVolumes() {
        return numberOfVolumes;
    }

    public void setNumberOfVolumes(int numberOfVolumes) throws InvalidInputException {
        if (numberOfVolumes < 0) {
            throw new InvalidInputException("Number of volumes cannot be negative");
        }
        this.numberOfVolumes = numberOfVolumes;
    }

    public static void addBook() {
        try {
            System.out.println("\n=== Add New Book(s) ===");
            int bookNumber = getPositiveIntegerInput("How many books you want to add?");

            for (int i = 0; i < bookNumber; i++) {
                System.out.printf("\nAdding Book #%d:\n", i + 1);
                Book newBook = createNewBook();
                bookList.add(newBook);
                System.out.printf("'%s' added successfully!\n", newBook.getName());
            }
            System.out.printf("\nSuccessfully added %d book(s).\n", bookNumber);
        } catch (InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static Book createNewBook() throws InvalidInputException {
        Book book = new Book();

        book.setName(getNonEmptyInput("Enter the name of the book:"));
        book.setWriter(getNonEmptyInput("Enter the name of its writer:"));
        book.setYearCreated(getValidYearInput("Enter the year the book was written:"));
        book.setNumberOfVolumes(getNonNegativeIntegerInput("Enter the number of volumes:"));

        return book;
    }

    public static void showDetails() {
        try {
            System.out.println("\n=== Book Details ===");
            Book book = findBook();
            displayBookInfo(book);
        } catch (BookNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void editDetails() {
        try {
            System.out.println("\n=== Edit Book ===");
            Book book = findBook();
            displayBookInfo(book);

            System.out.println("\nWhich information would you like to edit?");
            System.out.println("1. Name");
            System.out.println("2. Writer's Name");
            System.out.println("3. Year Written");
            System.out.println("4. Number of Volumes");
            System.out.println("5. Cancel");

            int choice = getIntegerInput("Enter your choice (1-5):", 1, 5);

            switch (choice) {
                case 1:
                    book.setName(getNonEmptyInput("Enter the new name:"));
                    System.out.println("Book name updated successfully!");
                    break;
                case 2:
                    book.setWriter(getNonEmptyInput("Enter the new writer's name:"));
                    System.out.println("Writer's name updated successfully!");
                    break;
                case 3:
                    book.setYearCreated(getValidYearInput("Enter the new year:"));
                    System.out.println("Year updated successfully!");
                    break;
                case 4:
                    book.setNumberOfVolumes(getNonNegativeIntegerInput("Enter the new number of volumes:"));
                    System.out.println("Number of volumes updated successfully!");
                    break;
                case 5:
                    System.out.println("Edit cancelled.");
                    break;
            }
        } catch (BookNotFoundException | InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void removeBook() {
        try {
            System.out.println("\n=== Remove Book ===");
            int index = findBookIndex();
            Book removedBook = bookList.remove(index);
            System.out.printf("'%s' by %s has been removed successfully.\n",
                    removedBook.getName(), removedBook.getWriter());
        } catch (BookNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void borrowedBook() {
        try {
            System.out.println("\n=== Borrow Book ===");
            Book book = findBook();

            if (book.getNumberOfVolumes() <= 0) {
                throw new BookOutOfStockException("Sorry, this book is currently out of stock.");
            }

            book.setNumberOfVolumes(book.getNumberOfVolumes() - 1);
            System.out.printf("You have successfully borrowed '%s'. %d copies remain.\n",
                    book.getName(), book.getNumberOfVolumes());
        } catch (BookNotFoundException | BookOutOfStockException | InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void returnedBook() {
        try {
            System.out.println("\n=== Return Book ===");
            Book book = findBook();

            if (book.getNumberOfVolumes() >= 5) {
                throw new InvalidBookOperationException("Cannot accept more returns. Inventory at maximum capacity.");
            }

            book.setNumberOfVolumes(book.getNumberOfVolumes() + 1);
            System.out.printf("Thank you for returning '%s'. There are now %d copies available.\n",
                    book.getName(), book.getNumberOfVolumes());
        } catch (BookNotFoundException | InvalidBookOperationException | InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static Book findBook() throws BookNotFoundException {
        System.out.println("Search by:\n1. Book Name\n2. Writer's Name");
        int searchChoice = getIntegerInput("Enter your choice (1-2):", 1, 2);

        String searchTerm = getNonEmptyInput(searchChoice == 1 ?
                "Enter the book name:" : "Enter the writer's name:");

        for (Book book : bookList) {
            if ((searchChoice == 1 && book.getName().equalsIgnoreCase(searchTerm)) ||
                    (searchChoice == 2 && book.getWriter().equalsIgnoreCase(searchTerm))) {
                return book;
            }
        }
        throw new BookNotFoundException("No book found matching '" + searchTerm + "'");
    }

    private static int findBookIndex() throws BookNotFoundException {
        Book book = findBook();
        return bookList.indexOf(book);
    }

    private static void displayBookInfo(Book book) {
        System.out.println("\nBook Details:");
        System.out.println("Title: " + book.getName());
        System.out.println("Author: " + book.getWriter());
        System.out.println("Publication Year: " + book.getYearCreated());
        System.out.println("Available Volumes: " + book.getNumberOfVolumes());
    }

    private static String getNonEmptyInput(String prompt) {
        while (true) {
            System.out.println(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Error: Input cannot be empty. Please try again.");
        }
    }

    private static int getIntegerInput(String prompt) {
        while (true) {
            try {
                System.out.println(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid number.");
            }
        }
    }

    private static int getIntegerInput(String prompt, int min, int max) {
        while (true) {
            int input = getIntegerInput(prompt);
            if (input >= min && input <= max) {
                return input;
            }
            System.out.printf("Error: Please enter a number between %d and %d.\n", min, max);
        }
    }

    private static int getPositiveIntegerInput(String prompt) throws InvalidInputException {
        int input = getIntegerInput(prompt);
        if (input <= 0) {
            throw new InvalidInputException("Number must be positive");
        }
        return input;
    }

    private static int getNonNegativeIntegerInput(String prompt) throws InvalidInputException {
        int input = getIntegerInput(prompt);
        if (input < 0) {
            throw new InvalidInputException("Number cannot be negative");
        }
        return input;
    }

    private static int getValidYearInput(String prompt) throws InvalidInputException {
        int year = getIntegerInput(prompt);
        int currentYear = Year.now().getValue();
        if (year <= 0 || year > currentYear) {
            throw new InvalidInputException("Year must be between 1 and " + currentYear);
        }
        return year;
    }

    @Override
    public String toString() {
        return String.format("%s by %s (%d) [%d volumes]",
                name, writer, yearCreated, numberOfVolumes);
    }

    public static void listAllBooks() {
        System.out.println("\n=== All Books in Library ===");
        if (bookList.isEmpty()) {
            System.out.println("No books available in the library.");
            return;
        }

        for (int i = 0; i < bookList.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, bookList.get(i));
        }
    }
}