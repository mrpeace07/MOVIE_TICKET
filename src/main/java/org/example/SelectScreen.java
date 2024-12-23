package org.example;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import org.apache.log4j.Logger;

public class SelectScreen {
    private static final Logger logger = Logger.getLogger(SelectScreen.class);

    private Map<Integer, String> movies = new HashMap<>();
    private Map<Integer, int[][]> seatLayouts = new HashMap<>();
    private int selectedScreen;

    public SelectScreen() {
        logger.info("Initializing SelectScreen with default movies and seat layouts.");

        // Initialize movies and seat layouts
        movies.put(1, "Avatar (International, $150)");
        movies.put(2, "Inception (International, $150)");
        movies.put(3, "Pushpa (Local, $80)");
        movies.put(4, "Kantara (Local, $80)");

        // Create seat layouts for each screen
        for (int i = 1; i <= 4; i++) {
            seatLayouts.put(i, new int[5][10]); // 5 rows and 10 columns
        }

        logger.debug("Default movies and seat layouts initialized successfully.");
    }

    public void showMovies() {
        logger.info("Displaying available movies and screens.");
        System.out.println("\nAvailable Movies and Screens:");
        for (Map.Entry<Integer, String> entry : movies.entrySet()) {
            System.out.println(entry.getKey() + ". Screen " + entry.getKey() + ": " + entry.getValue());
        }
    }

    public void viewSeats() {
        Scanner sc = new Scanner(System.in);
        logger.info("Prompting user to select a screen to view seats.");

        System.out.println("Select a screen to view seats:");
        showMovies();

        try {
            selectedScreen = sc.nextInt();
            logger.debug("User selected screen: " + selectedScreen);
        } catch (Exception e) {
            System.out.println("Invalid input. Please enter a valid screen number.");
            logger.error("Invalid screen selection input.", e);

            sc.nextLine(); // Clear invalid input
            return;
        }

        if (!movies.containsKey(selectedScreen)) {
            logger.warn("User selected an invalid screen: " + selectedScreen);
            System.out.println("Invalid screen. Try again.");
            return;
        }

        logger.info("Displaying seat layout for screen " + selectedScreen);
        displaySeatLayout(seatLayouts.get(selectedScreen));
    }

    public void displaySeatLayout(int[][] seats) {
        logger.debug("Displaying seat layout for the selected screen.");
        System.out.println("\nSeat Layout (O: Available, X: Booked):");
        System.out.print("   "); // Padding for column headers

        for (int col = 1; col <= 10; col++) {
            System.out.print(col + "  ");
        }
        System.out.println();

        for (int i = 0; i < 5; i++) { // 5 rows
            System.out.print((char) ('A' + i) + " "); // Row labels (A, B, C, etc.)
            for (int j = 0; j < 10; j++) { // 10 columns
                System.out.print((seats[i][j] == 1 ? "X" : "O") + "  ");
            }
            System.out.println();
        }
    }

    // Getters for selected screen, seat layout, and movie details
    public int getSelectedScreen() {
        return selectedScreen;
    }

    public int[][] getSeatLayout(int screen) {
        return seatLayouts.get(screen);
    }

    public String getMovieDetails(int screen) {
        return movies.get(screen);
    }
}
