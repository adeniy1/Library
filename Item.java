import java.time.LocalDate;

// Abstract class representing a general item in the library system
public abstract class Item {
    // Private attributes representing the properties of an item
    private String barcode;        // Unique identifier for the item
    private String authorArtist;   // Author or artist of the item
    private String title;          // Title of the item
    private String type;           // Type of the item (e.g., book, multimedia)
    private int year;              // Year of publication or release
    private String isbn;           // ISBN number (for books)
    private String status;         // Current status of the item (e.g., available, on loan)

    // Constructor to initialize an Item object with specific details
    public Item(String barcode, String authorArtist, String title, String type, int year, String isbn, String status) {
        this.barcode = barcode;               // Set the barcode
        this.authorArtist = authorArtist;     // Set the author or artist
        this.title = title;                   // Set the title
        this.type = type;                     // Set the type
        this.year = year;                     // Set the year
        this.isbn = isbn;                     // Set the ISBN
        this.status = status;                 // Set the status
    }

    // Getter method for accessing the barcode
    public String getBarcode() {
        return barcode;
    }

    // Getter method for accessing the author or artist
    public String getAuthorArtist() {
        return authorArtist;
    }

    // Getter method for accessing the title
    public String getTitle() {
        return title;
    }

    // Getter method for accessing the type
    public String getType() {
        return type;
    }

    // Getter method for accessing the year
    public int getYear() {
        return year;
    }

    // Getter method for accessing the ISBN
    public String getIsbn() {
        return isbn;
    }

    // Getter method for accessing the status
    public String getStatus() {
        return status;
    }

    // Setter method for updating the item's status
    public void setStatus(String status) {
        this.status = status; // Update the status of the item
    }

    // Abstract method for calculating the issue date, to be implemented by subclasses
    public abstract LocalDate calculateIssueDate();

    // Abstract method for calculating the due date, to be implemented by subclasses
    public abstract LocalDate calculateDueDate();

    // String representation of the Item object for easy display
    @Override
    public String toString() {
        return "Item{" +
                "barcode='" + barcode + '\'' +
                ", authorArtist='" + authorArtist + '\'' +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", year=" + year +
                ", isbn='" + isbn + '\'' +
                ", status='" + status + '\'' +
                '}'; // Format the output for better readability
    }
}
