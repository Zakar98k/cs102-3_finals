import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;

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
