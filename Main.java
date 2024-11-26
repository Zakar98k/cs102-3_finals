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
            System.out.println("[4] Exit");
            System.out.print("Please choose an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();

            // Prettify the UI, add extra line
            System.out.print("\n");

            switch (choice) {
                case 1:
                    customerRecordMenu(scanner);
                    break;
                case 2:
                    reservationRecordMenu(scanner);
                    break;
                case 3:
                    billOutMenu(scanner);
                    break;
                case 4:
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
            System.out.println("[4] Return to Main Menu");
            System.out.print("Please choose an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();

            System.out.print("\n");

            switch (choice) {
                case 1:
                    createCustomerRecord(scanner);
                    break;
                case 2:
                    updateCustomerRecord(scanner);
                    break;
                case 3:
                    viewCustomerRecords(scanner);
                    break;
                case 4:
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
        
        try (FileWriter writer = new FileWriter("Customer_Records.txt", true)) {
            writer.write(name+","+contact+","+date+","+time+","+number);
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

    }

    public static void billOutMenu(Scanner scanner) {

    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        navigationMenu(scanner);
    }
}
