package org.example;
import org.apache.log4j.Logger;
import java.util.*;

class BookTickets {
    private static final Logger logger = Logger.getLogger(BookTickets.class);
    private Scanner sc = new Scanner(System.in);
    private SelectScreen selectScreen;
    private List<String> bookedTickets = new ArrayList<>();
    private Map<String, List<String>> userBookings = new HashMap<>();
    private Map<String, Double> bookingAmounts = new HashMap<>(); // Store amounts for each movie
    private double totalAmount;
    private String movieDetails;

    public BookTickets(SelectScreen selectScreen) {
        this.selectScreen = selectScreen;
    }

    public void bookTickets() {
        selectScreen.showMovies();
        System.out.print("Select the screen for the movie you want to book: ");
        int screenChoice = sc.nextInt();

        if (!selectScreen.getMovieDetails(screenChoice).equals(selectScreen.getMovieDetails(screenChoice))) {
            System.out.println("Invalid screen selected. Try again.");
            logger.warn("Invalid screen selected: " + screenChoice);
            return;
        }

        movieDetails = selectScreen.getMovieDetails(screenChoice);
        System.out.println("Selected Movie: " + movieDetails);

        int[][] currentSeats = selectScreen.getSeatLayout(screenChoice);
        System.out.print("Enter number of tickets to book: ");
        int ticketCount = sc.nextInt();
        double pricePerTicket = movieDetails.contains("International") ? 150 : 80;

        selectScreen.displaySeatLayout(currentSeats);

        bookedTickets.clear();
        for (int i = 0; i < ticketCount; i++) {
            System.out.print("Enter seat " + (i + 1) + " (e.g., A1): ");
            String seat = sc.next().toUpperCase();
            if (!selectSeat(currentSeats, seat)) {
                i--;
            } else {
                bookedTickets.add(seat);
            }
        }

        totalAmount = bookedTickets.size() * pricePerTicket;
        System.out.println("\nBooking Confirmed!");
        System.out.println("Movie: " + movieDetails);
        System.out.println("Seats: " + bookedTickets);
        System.out.println("Total Amount: $" + totalAmount);

        userBookings.put(movieDetails, new ArrayList<>(bookedTickets));
        bookingAmounts.put(movieDetails, totalAmount); // Store total amount for this booking

        logger.info("Booking Details: ");
        logger.info("Movie: " + movieDetails);
        logger.info("Seats: " + bookedTickets);
        logger.info("Total Amount: $" + totalAmount);
    }

    private boolean selectSeat(int[][] seats, String seat) {
        try {
            int row = seat.charAt(0) - 'A';
            int col = Integer.parseInt(seat.substring(1)) - 1;

            if (row < 0 || row >= 5 || col < 0 || col >= 10 || seats[row][col] == 1) {
                System.out.println("Invalid or already booked seat. Try again.");
                return false;
            }

            seats[row][col] = 1;
            return true;
        } catch (Exception e) {
            System.out.println("Invalid seat format. Try again.");
            logger.error("Error in selecting seat: " + seat, e);
            return false;
        }
    }

    public void updateSeatLayoutAfterCancellation(int screenChoice, String seatToCancel) {
        int[][] seatLayout = selectScreen.getSeatLayout(screenChoice);
        try {
            int row = seatToCancel.charAt(0) - 'A';
            int col = Integer.parseInt(seatToCancel.substring(1)) - 1;

            if (seatLayout[row][col] == 1) {
                seatLayout[row][col] = 0; // Mark the seat as available
            }
        } catch (Exception e) {
            logger.error("Error updating seat layout for cancellation: " + seatToCancel, e);
        }
    }


    public Map<String, List<String>> getUserBookings() {
        return userBookings;
    }

    public Map<String, Double> getBookingAmounts() {
        return bookingAmounts;
    }
}
