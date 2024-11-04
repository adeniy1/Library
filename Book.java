import java.time.LocalDate;

public class Book extends Item {

    // Constructor to initialize a Book object with specific details
    public Book(String barcode, String authorArtist, String title, String type, int year, String isbn, String status) {
        // Call the constructor of the superclass (Item) to initialize inherited properties
        super(barcode, authorArtist, title, type, year, isbn, status);
    }

    // Method to calculate the due date for returning the book
    @Override
    public LocalDate calculateDueDate() {
        // Due date for a book is set to 4 weeks after the issue date
        return calculateIssueDate().plusWeeks(4);
    }

    // Method to calculate the issue date when the book is borrowed
    @Override
    public LocalDate calculateIssueDate() {
        // The issue date for a book is the current date when it is borrowed
        return LocalDate.now();
    }

    // Method to provide a string representation of the Book object for display
    @Override
    public String toString() {
        // Format the output for better readability, including all relevant book details
        return "Book\n" +
                "Author: " + getAuthorArtist() + "\n" +
                "Title: " + getTitle() + "\n" +
                "Year: " + getYear() + "\n" +
                "ISBN: " + getIsbn() + "\n" +
                "Status: " + getStatus() + "\n";
    }
}
