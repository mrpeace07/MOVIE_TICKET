package org.example;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import org.apache.log4j.Logger;

public class AdminAccess {
    private static final Logger logger = Logger.getLogger(AdminAccess.class); // Log4j Logger
    private static final String ADMIN_PASSWORD = "iamadmin";
    private static final String LOG_FILE_PATH = "logs/application.log";
    private BookTickets bookTickets;
    private MovieTickets movieTickets;

    public AdminAccess(BookTickets bookTickets, MovieTickets movieTickets) {
        this.bookTickets = bookTickets;
        this.movieTickets = movieTickets;
        logger.info("AdminAccess initialized with BookTickets and MovieTickets instances.");
    }

    public boolean validatePassword(String inputPassword) {
        return ADMIN_PASSWORD.equals(inputPassword);
    }

    // Main Admin Menu
    public void adminMenu() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Admin Password: ");
        String enteredPassword = sc.nextLine();

        if (ADMIN_PASSWORD.equals(enteredPassword)) {
            logger.info("Admin access granted.");
            System.out.println("\nAccess Granted! Welcome Admin.");
            while (true) {
                System.out.println("\n1. View All Bookings");
                System.out.println("2. View Logs");
                System.out.println("3. Reject User Tickets");
                System.out.println("4. Exit");
                System.out.print("Choose an option: ");

                int choice = sc.nextInt();
                sc.nextLine(); // Consume newline
                switch (choice) {
                    case 1:
                        viewAllBookings();
                        break;
                    case 2:
                        displayLogs();
                        break;
                    case 3:
                        rejectUserTickets();
                        break;
                    case 4:
                        System.out.println("Exiting Admin Menu.");
                        logger.info("Admin logged out.");
                        return;
                    default:
                        System.out.println("Invalid choice. Try again.");
                        logger.warn("Admin entered an invalid choice: " + choice);
                }
            }
        } else {
            System.out.println("Access Denied! Incorrect password.");
            logger.error("Failed admin login attempt.");
        }
    }

    // View all bookings
    public void viewAllBookings() {
        logger.info("Admin is viewing all bookings.");
        System.out.println("\n--- All Booked Tickets ---");
        Map<String, List<String>> userBookings = bookTickets.getUserBookings();

        if (userBookings.isEmpty()) {
            System.out.println("No tickets have been booked yet.");
            logger.info("No bookings to display.");
        } else {
            for (String movie : userBookings.keySet()) {
                System.out.println("Movie: " + movie + ", Seats: " + userBookings.get(movie));
                logger.debug("Booking details - Movie: " + movie + ", Seats: " + userBookings.get(movie));
            }
        }
    }

    // Display logs from application.log
    private void displayLogs() {
        logger.info("Admin is viewing application logs.");
        System.out.println("\n--- Log Records ---");
        try (BufferedReader reader = new BufferedReader(new FileReader(LOG_FILE_PATH))) {
            String line;
            boolean hasLogs = false;
            while ((line = reader.readLine()) != null) {
                hasLogs = true;
                System.out.println(line);
            }
            if (!hasLogs) {
                System.out.println("No logs available.");
                logger.info("No logs found in log file.");
            }
        } catch (IOException e) {
            System.out.println("Error reading log file: " + e.getMessage());
            logger.error("Error reading log file: " + e.getMessage(), e);
        }
    }

    // Reject user tickets
    private void rejectUserTickets() {
        logger.info("Admin is rejecting user tickets.");
        Scanner sc = new Scanner(System.in);
        System.out.println("\n--- Reject User Tickets ---");

        Map<String, List<String>> userBookings = bookTickets.getUserBookings();
        if (userBookings.isEmpty()) {
            System.out.println("No bookings to reject.");
            logger.info("No bookings available to reject.");
            return;
        }

        System.out.println("Current Bookings:");
        int index = 1;
        List<String> movies = new ArrayList<>(userBookings.keySet());
        for (String movie : movies) {
            System.out.println(index + ". Movie: " + movie + ", Seats: " + userBookings.get(movie));
            logger.debug("Booking option " + index + " - Movie: " + movie + ", Seats: " + userBookings.get(movie));
            index++;
        }

        System.out.print("Enter the number of the booking you want to reject: ");
        int choice = sc.nextInt();
        sc.nextLine(); // Consume newline

        if (choice > 0 && choice <= movies.size()) {
            String movieToRemove = movies.get(choice - 1);
            userBookings.remove(movieToRemove);
            System.out.println("Booking for movie '" + movieToRemove + "' has been rejected.");
            logger.info("Booking for movie '" + movieToRemove + "' has been rejected.");
        } else {
            System.out.println("Invalid choice.");
            logger.warn("Invalid choice entered by admin: " + choice);
        }
    }
}
