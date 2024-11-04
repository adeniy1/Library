import org.junit.jupiter.api.Test; // Importing JUnit 5 Test annotation
import static org.junit.jupiter.api.Assertions.*; // Importing assertion methods from JUnit

// Test class for the Item class
class ItemTest {

    // Test method to verify the functionality of the getStatus() method
    @Test
    public void testGetStatus() {
        // Arrange: Set up the test data and environment
        // Creating a new Book object as an example of an Item
        Item item = new Book("B00447489", "Elka Glazebrook", "non mattis pulvinar nulla pede",
                "Book", 2008, "867041599-2", "available");

        // Act: Execute the method to be tested
        item.setStatus("On Loan"); // Update the status of the item to "On Loan"

        // Assert: Verify that the result is as expected
        // Check if the status was updated correctly
        assertEquals("On Loan", item.getStatus());
    }
}
