import java.time.LocalDate; // Importing LocalDate for handling date objects

public class Loan {
    private String userId;      // Unique identifier for the user who borrowed the item
    private String barcode;      // Barcode of the item being loaned
    private LocalDate issueDate; // Date when the item was issued
    private LocalDate dueDate;   // Date when the item is due to be returned
    private int renewCount;      // Number of times the loan has been renewed

    // Constructor to initialize a Loan object with the provided values
    public Loan(String userId, String barcode, LocalDate issueDate, LocalDate dueDate, int renewCount) {
        this.userId = userId;       // Set the user ID
        this.barcode = barcode;     // Set the item's barcode
        this.issueDate = issueDate; // Set the issue date
        this.dueDate = dueDate;     // Set the due date
        this.renewCount = renewCount; // Set the initial renewal count
    }

    // Getter method to retrieve the user ID
    public String getUserId() {
        return userId; // Return the user ID
    }

    // Getter method to retrieve the item's barcode
    public String getBarcode() {
        return barcode; // Return the item's barcode
    }

    // Getter method to retrieve the issue date
    public LocalDate getIssueDate() {
        return issueDate; // Return the issue date
    }

    // Setter method to update the issue date
    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate; // Update the issue date
    }

    // Getter method to retrieve the due date
    public LocalDate getDueDate() {
        return dueDate; // Return the due date
    }

    // Setter method to update the due date
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate; // Update the due date
    }

    // Getter method to retrieve the renewal count
    public int getRenewCount() {
        return renewCount; // Return the number of times the loan has been renewed
    }

    // Setter method to update the renewal count
    public void setRenewCount(int renewCount) {
        this.renewCount = renewCount; // Update the renewal count
    }
}

// Override toString method to provide a s
