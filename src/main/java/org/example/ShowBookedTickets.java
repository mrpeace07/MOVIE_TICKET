package org.example;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

public class ShowBookedTickets {
    private static final Logger logger = Logger.getLogger(ShowBookedTickets.class); // Logger instance
    private BookTickets bookTickets;

    public ShowBookedTickets(BookTickets bookTickets) {
        this.bookTickets = bookTickets;
        logger.info("ShowBookedTickets initialized with BookTickets instance.");
    }

    public void displayBookedTickets() {
        logger.info("Displaying booked tickets.");

        Map<String, List<String>> userBookings = bookTickets.getUserBookings();
        Map<String, Double> bookingAmounts = bookTickets.getBookingAmounts();

        if (userBookings.isEmpty()) {
            System.out.println("No tickets booked.");
            logger.warn("No tickets have been booked yet.");
        } else {
            System.out.println("Your Booked Tickets:");
            logger.debug("User has booked tickets. Displaying details...");

            for (Map.Entry<String, List<String>> entry : userBookings.entrySet()) {
                String movie = entry.getKey();
                List<String> seats = entry.getValue();
                double amount = bookingAmounts.getOrDefault(movie, 0.0);

                logger.debug("Processing booking for movie: " + movie);
                logger.debug("Seats booked: " + seats + ", Total amount: $" + amount);

                System.out.println("Movie: " + movie);
                System.out.println("Seats: " + seats);
                System.out.println("Total Amount: $" + amount);
                System.out.println();
            }
            logger.info("Successfully displayed all booked tickets.");
        }
    }
}
