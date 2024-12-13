package org.example;

import org.apache.log4j.Logger;
import org.apache.log4j.BasicConfigurator;
import java.util.Scanner;



// Main Class
public class MovieBookingSystem {
    static Logger logger = Logger.getLogger(MovieBookingSystem.class);

    public static void main(String[] args) {
        BasicConfigurator.configure(); // Configures log4j
        Scanner sc = new Scanner(System.in);

        MovieTickets movieTickets = new MovieTickets();
        SelectScreen selectScreen = new SelectScreen();
        BookTickets bookTickets = new BookTickets(selectScreen);
        CancelAndRefund cancelAndRefund = new CancelAndRefund(bookTickets);
        ShowBookedTickets showBookedTickets = new ShowBookedTickets(bookTickets);

        while (true) {
            System.out.println("\nAre you a:");
            System.out.println("1. User");
            System.out.println("2. Admin");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int userType = sc.nextInt();

            switch (userType) {
                case 1: // User Flow
                    movieTickets.welcome();
                    while (true) {
                        System.out.println("\nUser Menu:");
                        System.out.println("1. View Available Seats for a Screen");
                        System.out.println("2. Book Tickets");
                        System.out.println("3. Cancel Tickets");
                        System.out.println("4. Show Booked Tickets");
                        System.out.println("5. Update Phone Number");
                        System.out.println("6. Back to Main Menu");
                        System.out.print("Enter your choice: ");
                        int userChoice = sc.nextInt();

                        switch (userChoice) {
                            case 1:
                                selectScreen.viewSeats();
                                break;
                            case 2:
                                bookTickets.bookTickets();
                                break;
                            case 3:
                                cancelAndRefund.cancelTickets();
                                break;
                            case 4:
                                showBookedTickets.displayBookedTickets();
                                break;
                            case 5:
                                movieTickets.updatePhoneNumber();
                                break;
                            case 6:
                                logger.info("User exited to the main menu.");
                                break;
                            default:
                                System.out.println("Invalid choice. Try again.");
                        }
                        if (userChoice == 6) break;
                    }
                    break;

                case 2: // Admin Flow
                    AdminAccess adminAccess = new AdminAccess(bookTickets, movieTickets);
                    adminAccess.adminMenu();
                    break;

                case 3: // Exit
                    System.out.println("Thank you for using PVR Cinemas!");
                    logger.info("System exited.");
                    return;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
