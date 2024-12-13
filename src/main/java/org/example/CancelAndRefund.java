package org.example;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import org.apache.log4j.Logger;

class CancelAndRefund {
    private static final Logger logger = Logger.getLogger(CancelAndRefund.class); // Logger instance
    private BookTickets bookTickets;

    public CancelAndRefund(BookTickets bookTickets) {
        this.bookTickets = bookTickets;
        logger.info("CancelAndRefund initialized with BookTickets instance.");
    }

    public void cancelTickets() {
        Scanner sc = new Scanner(System.in);
        Map<String, List<String>> userBookings = bookTickets.getUserBookings();

        if (userBookings.isEmpty()) {
            System.out.println("No tickets booked.");
            logger.warn("No tickets available to cancel.");
            return;
        }

        logger.info("Displaying booked movies for cancellation.");
        System.out.println("Your Booked Movies:");
        for (String movie : userBookings.keySet()) {
            System.out.println("Movie: " + movie + ", Seats: " + userBookings.get(movie));
            logger.debug("Movie: " + movie + ", Seats: " + userBookings.get(movie));
        }

        System.out.print("Enter the movie for which you want to cancel tickets: ");
        String movieToCancel = sc.nextLine();

        if (!userBookings.containsKey(movieToCancel)) {
            System.out.println("Invalid movie. Try again.");
            logger.error("Invalid movie entered: " + movieToCancel);
            return;
        }

        List<String> seats = userBookings.get(movieToCancel);
        System.out.print("Enter the seat to cancel: ");
        String seatToCancel = sc.next().toUpperCase();

        if (!seats.contains(seatToCancel)) {
            System.out.println("Invalid seat. Try again.");
            logger.error("Invalid seat entered: " + seatToCancel + " for movie: " + movieToCancel);
            return;
        }

        seats.remove(seatToCancel);
        logger.info("Seat " + seatToCancel + " successfully cancelled for movie: " + movieToCancel);
        System.out.println("Cancellation successful!");
    }
}
