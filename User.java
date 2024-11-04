public class User {
    private String userId;    // Unique identifier for the user
    private String firstName;  // User's first name
    private String lastName;   // User's last name
    private String email;      // User's email address

    // Constructor to initialize a new User object with provided values
    public User(String userId, String firstName, String lastName, String email) {
        this.userId = userId;          // Set the user ID
        this.firstName = firstName;    // Set the first name
        this.lastName = lastName;      // Set the last name
        this.email = email;            // Set the email address
    }

    // Getter method to retrieve the user ID
    public String getUserId() {
        return this.userId;
    }

    // Getter method to retrieve the user's first name
    public String getFirstName() {
        return this.firstName;
    }

    // Getter method to retrieve the user's last name
    public String getLastName() {
        return this.lastName;
    }

    // Getter method to retrieve the user's email address
    public String getEmail() {
        return this.email;
    }

    // Override the toString method to provide a string representation of the User object
    @Override
    public String toString() {
        return "ID: " + this.userId +
                ", First Name: " + this.firstName +
                ", Last Name: " + this.lastName +
                ", Email: " + this.email; // Format the output for better readability
    }
}
