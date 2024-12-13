package org.example;
import org.apache.log4j.Logger;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class MovieTickets {
    private static final Logger logger = Logger.getLogger(MovieTickets.class);
    private Scanner sc = new Scanner(System.in);
    private String name;
    private String phone;

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public void welcome() {
        System.out.println("Welcome to PVR Cinemas!");
        System.out.print("Enter your Name: ");
        name = sc.nextLine();
        System.out.print("Enter your Phone Number: ");
        phone = sc.nextLine();
        saveToExcel();
        logger.info("User registered: Name = " + name + ", Phone = " + phone);
    }

    private void saveToExcel() {
        try (FileWriter writer = new FileWriter("customerDetails.csv", true)) {
            writer.append(name).append(",").append(phone).append("\n");
            System.out.println("Details saved successfully!");
        } catch (IOException e) {
            System.out.println("Error saving details to file: " + e.getMessage());
            logger.error("Error saving details to file: " + e.getMessage());
        }
    }

    public void updatePhoneNumber() {
        System.out.print("Enter your new phone number: ");
        phone = sc.nextLine();
        logger.info("Phone number updated for " + name + ": New Phone = " + phone);
        System.out.println("Phone number updated successfully!");
    }
}