import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ArrayList;

public class Main {

    public static void navigationMenu(Scanner scanner) {
        boolean running = true;

        while (running) {
            System.out.println(" <-+ Welcome to the Hotel Management System +->");
            System.out.println("[1] Manage Customer Records");
            System.out.println("[2] Manage Reservation Records");
            System.out.println("[3] Bill Out");
            System.out.println("[X] Exit");
            System.out.print("Please choose an option: ");
            
            String choice = scanner.nextLine().toUpperCase();

            // Prettify the UI, add extra line
            System.out.print("\n");

            switch (choice) {
                case "1":
                    customerRecordMenu(scanner);
                    break;
                case "2":
                    reservationRecordMenu(scanner);
                    break;
                case "3":
                    billOutMenu(scanner);
                    break;
                case "X":
                    System.out.println("Exiting the system...");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }

        scanner.close();
        System.exit(0);

    }

    public static void customerRecordMenu(Scanner scanner) {
        boolean running = true;

        while (running) {
            System.out.println(" <-+ Manage Customer Records +->");
            System.out.println("[1] Create Customer Record");
            System.out.println("[2] Update Customer Record");
            System.out.println("[3] View Customer Record");
            System.out.println("[X] Return to Main Menu");
            System.out.print("Please choose an option: ");
            
            String choice = scanner.nextLine().toUpperCase();

            System.out.print("\n");

            switch (choice) {
                case "1":
                    createCustomerRecord(scanner);
                    break;
                case "2":
                    updateCustomerRecord(scanner);
                    break;
                case "3":
                    viewCustomerRecords(scanner);
                    break;
                case "X":
                    System.out.println("Returning to main menu...\n");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
    }

    public static void createCustomerRecord(Scanner scanner) {
        String name, contact, date, time, number;
        System.out.println(" <-+ Create a Customer Record +->");

        System.out.print("Name of customer: ");
        name = scanner.nextLine();
        System.out.print("Contact No (xxx-xxxx-xxx): ");
        contact = scanner.nextLine();
        System.out.print("Date of reservation (YYYY-MM-DD): ");
        date = scanner.nextLine();
        System.out.print("Time of reservation (00:00 AM/PM): ");
        time = scanner.nextLine();
        System.out.print("Number of guests: ");

        number = scanner.nextLine();

        System.out.print("\n");
        
        try (FileWriter writer = new FileWriter("Customer_Records.txt", true)) {
            writer.write(name+","+contact+","+date+","+time+","+number);
            writer.write("\n");
        } catch (IOException error) {
            System.out.println(error.getStackTrace());
        }
        
    }

    public static void updateCustomerRecord(Scanner scanner) {
        System.out.println(" <-+ Update a Customer Record +->");
        System.out.println("[1] Search by name");
        System.out.println("[2] Search by contact");
        System.out.println("[3] Search by date");
        System.out.println("[4] Search by time");
        System.out.println("[5] Search by number of guests");
        System.out.print("Search by field: ");
        int field = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter search term: ");
        String search = scanner.nextLine();

        System.out.println();

        switch (field) {
            case 1:
            updateByField(0, search, scanner);
            break;
            case 2:
            updateByField(1, search, scanner);
            break;
            case 3:
            updateByField(2, search, scanner);
            break;
            case 4:
            updateByField(3, search, scanner);
            break;
            case 5:
            updateByField(4, search, scanner);
            break;
            default:
            System.out.println("Exiting, invalid option");
            return;
        }


    }

    public static void updateByField(int fieldIndex, String search, Scanner scanner) {
        ArrayList<String> records = new ArrayList<>();
        boolean updated = false;

        try {
            // Read all lines from the file into the list
            BufferedReader reader = new BufferedReader(new FileReader("Customer_Records.txt"));
            String line;


            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                String current = fields[fieldIndex];
                // System.out.println(line);

                Pattern pattern = Pattern.compile(search, Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(current);

                // Check if the current line matches the ID to update
                if (matcher.matches()) {
                    // Update the record with the new information
                    System.out.println("<-+ Update Record +->");
                    System.out.print("Enter new name: ");
                    String newName = scanner.nextLine();
                    System.out.print("Enter new contact number: ");
                    String newContact = scanner.nextLine();
                    System.out.print("Enter new date of reservation: ");
                    String newDate = scanner.nextLine();
                    System.out.print("Enter new time of reservation: ");
                    String newTime = scanner.nextLine();
                    System.out.print("Enter new number of guests: ");
                    String newGuests = scanner.nextLine();

                    String updatedRecord = newName + "," + newContact + "," + newDate + "," + newTime + "," + newGuests;
                    records.add(updatedRecord); // Add the updated record to the list
                    updated = true;
                } else {
                    // Otherwise, add the original record to the list
                    records.add(line);
                }
            }
            reader.close();

            // If the record was updated, write the new records back to the file
            if (updated) {
                BufferedWriter writer = new BufferedWriter(new FileWriter("Customer_Records.txt"));
                for (String record : records) {
                    writer.write(record);
                    writer.newLine(); // Write each record on a new line
                }
                writer.close();
            } else {
                System.out.println("Record with the search term " + search + " not found.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void viewCustomerRecords(Scanner scanner) {
        System.out.println(" <-+ View All Customer Records +->\n");
        try (BufferedReader br = new BufferedReader(new FileReader("Customer_Records.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                System.out.println("Name: " + data[0]);
                System.out.println("Phone Number: " + data[1]);
                System.out.println("Date of Reservation: " + data[2]);
                System.out.println("Time of Reservation: " + data[3]);
                System.out.println("Number of Guests: " + data[4] + "\n");
            } 
        } catch (IOException error) {
            System.out.println(error.getStackTrace());
        } 
    }

    public static void reservationRecordMenu(Scanner scanner) {
        String choice;
        do {
            System.out.println("<-+ Reservation Record Module +->");
            System.out.println("[1] Create Orders Record");
            System.out.println("[2] View Orders Record");
            System.out.println("[X] Return to Main Menu");
            System.out.print("Select an option: ");
            choice = scanner.nextLine().toUpperCase();

            System.out.print("\n");

            switch (choice) {
                case "1":
                    createOrdersRecord(scanner);
                    break;
                case "2":
                    viewOrdersRecord();
                    break;
                case "X":
                    System.out.println("Returning to Main Menu. . .\n");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (choice != "X");
    }

    public static void createOrdersRecord(Scanner scanner) {
        while (true) {
            System.out.println("<-+ Create Orders Record +->");
            System.out.println("[1] Meals Menu");
            System.out.println("[2] Drink Menu");
            System.out.println("[3] Dessert Menu");
            System.out.println("[4] Side Dishes Menu");
            System.out.println("[5] Bundled Menu");
            System.out.println("[X] Return to Main Menu");
            System.out.print("Select a menu: ");

            String menuChoice = scanner.nextLine().toUpperCase();

            System.out.print("\n");

            switch (menuChoice) {
                case "1":
                    orderFromMenu("Meals", new String[][]{
                            {"Signature-Chicken", "150.00"},
                            {"Sinigang", "120.00"},
                            {"Lechon", "250.00"},
                            {"Angus Beef Steak", "300.00"},
                            {"Grilled Bangus", "180.00"}
                    }, scanner);
                    break;
                case "2":
                    orderFromMenu("Drinks", new String[][]{
                            {"Water", "20.00"},
                            {"Soda in Can", "30.00"},
                            {"Buko Juice", "50.00"},
                            {"Milk Tea", "60.00"},
                            {"Black Coffee", "40.00"}
                    }, scanner);
                    break;
                case "3":
                    orderFromMenu("Desserts", new String[][]{
                            {"Ice Cream", "50.00"},
                            {"Halo-Halo", "80.00"},
                            {"Brownies", "40.00"},
                            {"Egg Pie", "30.00"},
                            {"Leche Flan", "45.00"}
                    }, scanner);
                    break;
                case "4":
                    orderFromMenu("Side Dishes", new String[][]{
                            {"Fried Bananas", "30.00"},
                            {"Sweet Kamote Fries", "35.00"},
                            {"Buko Salad", "40.00"},
                            {"Onion Rings", "35.00"},
                            {"Sopas", "50.00"}
                    }, scanner);
                    break;
                case "5":
                    orderFromMenu("Bundled", new String[][]{
                            {"Breakfast Combo", "150.00"},
                            {"Grilled Combo", "250.00"},
                            {"Family Pack", "500.00"},
                            {"Party Pack", "800.00"},
                            {"Kids Jamboree Meal", "100.00"}
                    }, scanner);
                    break;
                case "X":
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    public static void orderFromMenu(String menuName, String[][] items, Scanner scanner) {
        System.out.println("\n" + menuName + " Menu:");
        for (int i = 0; i < items.length; i++) {
            System.out.printf("[%d] %s - %.2f\n", i + 1, items[i][0], Double.parseDouble(items[i][1]));
        }
        System.out.print("Select an item (1-" + items.length + ") or 0 to return: ");
        int itemChoice = Integer.parseInt(scanner.nextLine());

        if (itemChoice == 0) {
            return;
        } else if (itemChoice > 0 && itemChoice <= items.length) {
            System.out.print("Enter quantity for " + items[itemChoice - 1][0] + ": ");
            int quantity = Integer.parseInt(scanner.nextLine());
            String order = items[itemChoice - 1][0] + " x" + quantity;
            saveOrderToFile(order);
            System.out.println("Added " + items[itemChoice - 1][0] + " x" + quantity + " to your orders.\n");
        } else {
            System.out.println("Invalid choice. Returning to menu.\n");
        }
    }

    public static void saveOrderToFile(String order) {
        try (FileWriter writer = new FileWriter("Customer_Orders.txt", true)) {
            writer.write(order + "\n");
        } catch (IOException e) {
            System.out.println("An error occurred while saving the order.");
        }
    }

    public static void viewOrdersRecord() {
        System.out.println("Orders Record:");
        try (BufferedReader reader = new BufferedReader(new FileReader("Customer_Orders.txt"))) {
            String line;
            int lineNumber = 1;
            boolean isEmpty = true;
            while ((line = reader.readLine()) != null) {
                System.out.println(lineNumber++ + ". " + line);
                isEmpty = false;
            }
            if (isEmpty) {
                System.out.println("No orders have been made.");
            }
        } catch (IOException e) {
            System.out.println("No orders record found.");
        }
        System.out.println();
    }
    
    public static double getPrice(String itemName) {
        String[][] allItems = new String[][]{
            {"Signature-Chicken", "150.00"},
            {"Sinigang", "120.00"},
            {"Lechon", "250.00"},
            {"Angus Beef Steak", "300.00"},
            {"Grilled Bangus", "180.00"},
            {"Water", "20.00"},
            {"Soda in Can", "30.00"},
            {"Buko Juice", "50.00"},
            {"Milk Tea", "60.00"},
            {"Black Coffee", "40.00"},
            {"Ice Cream", "50.00"},
            {"Halo-Halo", "80.00"},
            {"Brownies", "40.00"},
            {"Egg Pie", "30.00"},
            {"Leche Flan", "45.00"},
            {"Fried Bananas", "30.00"},
            {"Sweet Kamote Fries", "35.00"},
            {"Buko Salad", "40.00"},
            {"Onion Rings", "35.00"},
            {"Sopas", "50.00"},
            {"Breakfast Combo", "150.00"},
            {"Grilled Combo", "250.00"},
            {"Family Pack", "500.00"},
            {"Party Pack", "800.00"},
            {"Kids Jamboree Meal", "100.00"}
        };

        for (String[] currentItem : allItems) {
            Pattern pattern = Pattern.compile(itemName, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(currentItem[0]);

            if (matcher.matches()) {
                return Double.parseDouble(currentItem[1]);
            }
        }

        return 0.0;
    }

    public static void billOutMenu(Scanner scanner) {
        System.out.println("<-+ Billing Out +->");
        double totalAmount = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader("Customer_Orders.txt"))) {
            String line;
            boolean isEmpty = true;

            while ((line = reader.readLine()) != null) {
                isEmpty = false;
                String[] parts = line.split(" x");
                String itemName = parts[0];
                int quantity = Integer.parseInt(parts[1]);

                double price = getPrice(itemName);
                double itemTotal = price * quantity;
                totalAmount += itemTotal;

                System.out.printf("%s (x%d): ₱%.2f\n", itemName, quantity, itemTotal);
            }
            if (isEmpty) {
                System.out.println("No orders to bill out.");
            } else {
                System.out.printf("\nTotal Amount: ₱%.2f\n", totalAmount);

                System.out.print("Payment amount: ₱");
                Double payment = scanner.nextDouble();
                scanner.nextLine();

                Double change = payment - totalAmount;

                if (change < 0.0) {
                    System.out.printf("Insufficient payment, you are short by ₱%.2f\n", Math.abs(change));
                    return;
                } 

                System.out.printf("Recieved a change of ₱%.2f\n\n", change);
                System.out.println("PAYMENT SUCCESSFUL\n");
            }
        } catch (IOException e) {
            System.out.println("No orders record found.\n");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        navigationMenu(scanner);
    }
}
