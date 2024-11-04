import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

public class Library {
    public static void main(String[] args) {
        Library app = new Library();
        ArrayList<User> users = readUsersFromFile("USERS.csv");
        ArrayList<Item> items = readItemsFromFile("ITEMS.csv");
        ArrayList<Loan> loans = readLoansFromFile("LOANS.csv");
        app.start(users, items, loans);
    }

    public void start(ArrayList<User> users, ArrayList<Item> items, ArrayList<Loan> loans) {
        System.out.println("Welcome to the Library Management System");
        menu(users, items, loans);
    }

    public void menu(ArrayList<User> users, ArrayList<Item> items, ArrayList<Loan> loans) {
        Scanner input = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            displayMenu();
            try {
                int choice = input.nextInt();
                input.nextLine(); // Consume newline character

                switch (choice) {
                    case 1: issueItem(users, items, loans); break;
                    case 2: renewLoan(items, loans); break;
                    case 3: returnItem(items, loans); break;
                    case 4: viewAllLoans(loans); break;
                    case 5: searchLoan(loans, items); break;
                    case 6: generateLoanReport(loans, items); break;
                    case 7: exit = true; break; // Exit the program
                    default: System.out.println("\nInvalid choice. Please enter a number from 1 to 7.\n");
                }
            } catch (InputMismatchException e) {
                System.out.println("\nInvalid input. Please enter a valid option.\n");
                input.nextLine(); // Clear invalid input
            }
        }

        System.out.println("Thank you for using the Library Management System. Goodbye!");
        input.close(); // Close the scanner resource
    }

    private void displayMenu() {
        System.out.println("Please select an option:");
        System.out.println("1. Issue an item");
        System.out.println("2. Renew a loan");
        System.out.println("3. Return an item");
        System.out.println("4. View all items on loan");
        System.out.println("5. Search for an item by barcode");
        System.out.println("6. Report details about loaned items");
        System.out.println("7. Exit");
    }

    public void issueItem(ArrayList<User> users, ArrayList<Item> items, ArrayList<Loan> loans) {
        Scanner input = new Scanner(System.in);
        String userId, barcode;

        while (true) {
            System.out.println("To loan an item, please enter your user ID and the item's barcode:");
            System.out.print("User ID: ");
            userId = input.nextLine().toUpperCase(); // Read user ID and convert to uppercase
            if (!isUserIdValid(userId, users)) {
                System.out.println("Invalid user ID entered. Please try again.");
                continue; // Continue to the next iteration if user ID is invalid
            }

            System.out.print("Barcode: ");
            barcode = input.nextLine().toUpperCase(); // Read barcode and convert to uppercase
            if (issueItemToUser(userId, barcode, items, loans)) break; // Attempt to issue item
        }
    }

    private boolean isUserIdValid(String userId, ArrayList<User> users) {
        return users.stream().anyMatch(user -> userId.equals(user.getUserId()));
    }

    private boolean issueItemToUser(String userId, String barcode, ArrayList<Item> items, ArrayList<Loan> loans) {
        for (Item item : items) {
            if (barcode.equals(item.getBarcode())) {
                if (item.getStatus().equals("available")) {
                    LocalDate issueDate = LocalDate.now(); // Set current date as issue date
                    LocalDate dueDate = item.calculateDueDate(); // Calculate due date
                    item.setStatus("On Loan"); // Update item status
                    loans.add(new Loan(userId, barcode, issueDate, dueDate, 0)); // Create a new loan record
                    writeLoansToFile(loans, "LOANS.csv");
                    writeItemsToFile(items, "ITEMS.csv");
                    System.out.println("Item issued successfully.");
                    return true; // Successfully issued the item
                } else {
                    System.out.println("Item is not available for loan.");
                    return false; // Item is not available
                }
            }
        }
        System.out.println("Invalid barcode entered. Please try again.");
        return false; // Barcode does not match any item
    }

    public void renewLoan(ArrayList<Item> items, ArrayList<Loan> loans) {
        Scanner input = new Scanner(System.in);
        String barcode;

        while (true) {
            System.out.print("To renew a loaned item, please enter the item barcode: ");
            barcode = input.nextLine().toUpperCase(); // Read barcode and convert to uppercase
            if (renewLoanIfValid(barcode, items, loans)) break; // Attempt to renew the loan
        }
    }

    private boolean renewLoanIfValid(String barcode, ArrayList<Item> items, ArrayList<Loan> loans) {
        for (Loan loan : loans) {
            if (barcode.equals(loan.getBarcode())) {
                Item item = items.stream().filter(i -> barcode.equals(i.getBarcode())).findFirst().orElse(null);
                if (item != null && canRenewLoan(item, loan)) {
                    loan.setDueDate(loan.getDueDate().plusWeeks(item.getType().equals("Book") ? 2 : 1));
                    loan.setRenewCount(loan.getRenewCount() + 1); // Increment renew count
                    writeLoansToFile(loans, "LOANS.csv");
                    System.out.println("Item renewed successfully.");
                    return true; // Successfully renewed the loan
                }
                System.out.println("Item cannot be renewed. Maximum number of attempts reached.");
                return false; // Cannot renew due to maximum attempts
            }
        }
        System.out.println("Invalid barcode entered, please try again.");
        return false; // Barcode does not match any loan
    }

    private boolean canRenewLoan(Item item, Loan loan) {
        return (item.getType().equals("Book") && loan.getRenewCount() < 3) ||
                (item.getType().equals("Multimedia") && loan.getRenewCount() < 2);
    }

    public void returnItem(ArrayList<Item> items, ArrayList<Loan> loans) {
        Scanner input = new Scanner(System.in);
        String barcode;

        while (true) {
            System.out.print("To return an item, please enter the item barcode: ");
            barcode = input.nextLine().toUpperCase(); // Read barcode and convert to uppercase
            if (returnItemIfValid(barcode, items, loans)) break; // Attempt to return the item
        }
    }

    private boolean returnItemIfValid(String barcode, ArrayList<Item> items, ArrayList<Loan> loans) {
        Iterator<Loan> iterator = loans.iterator();
        while (iterator.hasNext()) {
            Loan loan = iterator.next();
            if (barcode.equals(loan.getBarcode())) {
                items.stream()
                        .filter(item -> barcode.equals(item.getBarcode()))
                        .findFirst()
                        .ifPresent(item -> {
                            item.setStatus("available");
                            iterator.remove(); // Remove the loan from the list
                            System.out.println("Item returned successfully, thank you.");
                            writeLoansToFile(loans, "LOANS.csv");
                            writeItemsToFile(items, "ITEMS.csv");
                        });
                return true; // Successfully returned the item
            }
        }
        System.out.println("Invalid barcode entered, please try again.");
        return false; // Barcode does not match any loan
    }

    public void viewAllLoans(ArrayList<Loan> loans) {
        System.out.println("All items currently on loan:");
        if (loans.isEmpty()) {
            System.out.println("No loans found."); // Check if there are no loans
        } else {
            loans.forEach(System.out::println);
        }
    }

    public void searchLoan(ArrayList<Loan> loans, ArrayList<Item> items) {
        Scanner input = new Scanner(System.in);
        String barcode;

        while (true) {
            System.out.print("Please enter the barcode of the item to search for: ");
            barcode = input.nextLine().toUpperCase(); // Read barcode and convert to uppercase
            if (searchLoanIfValid(barcode, loans, items)) break; // Attempt to search for the loan
        }
    }

    private boolean searchLoanIfValid(String barcode, ArrayList<Loan> loans, ArrayList<Item> items) {
        for (Loan loan : loans) {
            if (barcode.equals(loan.getBarcode())) {
                Item item = items.stream().filter(i -> barcode.equals(i.getBarcode())).findFirst().orElse(null);
                System.out.println("Loan details:");
                if (item != null) {
                    System.out.println(item); // Display item details
                }
                System.out.println(loan); // Display loan details
                return true; // Successfully found the loan
            }
        }
        System.out.println("Invalid barcode entered, please try again.");
        return false; // Barcode does not match any loan
    }

    public void generateLoanReport(ArrayList<Loan> loans, ArrayList<Item> items) {
        System.out.println("Generating loan report...");
        if (loans.isEmpty()) {
            System.out.println("No loans to report."); // Check if there are no loans
            return;
        }
        loans.forEach(loan -> {
            Item item = items.stream().filter(i -> loan.getBarcode().equals(i.getBarcode())).findFirst().orElse(null);
            if (item != null) {
                System.out.println("Loaned Item: " + item);
                System.out.println("Loan Details: " + loan);
            }
        });
    }

    // Read data from USERS.csv and convert it into User objects
    public static ArrayList<User> readUsersFromFile(String fileName) {
        return readDataFromFile(fileName, data -> new User(data[0], data[1], data[2], data[3]));
    }

    // Read data from LOANS.csv and convert it into Loan objects
    public static ArrayList<Loan> readLoansFromFile(String fileName) {
        return readDataFromFile(fileName, data -> new Loan(data[0], data[1], LocalDate.parse(data[2]), LocalDate.parse(data[3]), Integer.parseInt(data[4])));
    }

    // Read data from ITEMS.csv and convert it into Item objects
    public static ArrayList<Item> readItemsFromFile(String fileName) {
        return readDataFromFile(fileName, data -> {
            String itemType = data[2]; // Assuming the type is in the third column
            switch (itemType) {
                case "Book":
                    return new Book(data[0], data[1], data[2], data[3], Integer.parseInt(data[4]), data[5], data[6]);
                case "Multimedia":
                    return new Multimedia(data[0], data[1], data[2], data[3], Integer.parseInt(data[4]), data[5], data[6]);
                // Add more cases for other subclasses if needed
                default:
                    throw new IllegalArgumentException("Unknown item type: " + itemType);
            }
        });
    }

    // Generic method to read data from a CSV file and create a list of objects
    private static <T> ArrayList<T> readDataFromFile(String fileName, DataFactory<T> factory) {
        ArrayList<T> dataList = new ArrayList<>();
        try (Scanner fileScanner = new Scanner(new FileReader(fileName))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                dataList.add(factory.create(line.split(","))); // Split line by comma and create object
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return dataList; // Return the list of objects
    }


    // Write loans data to LOANS.csv
    private static void writeLoansToFile(ArrayList<Loan> loans, String fileName) {
        writeDataToFile(loans, fileName);
    }

    // Write items data to ITEMS.csv
    private static void writeItemsToFile(ArrayList<Item> items, String fileName) {
        writeDataToFile(items, fileName);
    }

    // Generic method to write a list of objects to a CSV file
    private static <T> void writeDataToFile(ArrayList<T> dataList, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            for (T data : dataList) {
                writer.write(data.toString() + "\n"); // Convert object to string and write to file
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    // Functional interface for creating objects from string arrays
    interface DataFactory<T> {
        T create(String[] data); // Method to create an object from a string array
    }
}
