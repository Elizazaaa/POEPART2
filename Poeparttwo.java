/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package poeparttwo;

import java.util.Scanner;

/**
 *
 * @author Student
 */
public class Poeparttwo {

    private static final Scanner SCAN = Message.scanner;

    public static void main(String[] args) {

        if (!registerAndLogin()) {
            return;
        }

        System.out.println("Welcome to QuickChat.");

        int numberOfMessages = askNumberOfMessages();
        runMenu(numberOfMessages);
    }

    /** Registers the user and logs them in. Returns true if login succeeded. */
    private static boolean registerAndLogin() {

        Registration registration = new Registration();
        Login login = new Login();

        System.out.println("\n===== Register =====");
        String firstName = prompt("Please enter your first name:");
        String lastName = prompt("Please enter your last name:");
        String username = prompt("Please enter Username:");
        String password = prompt("Enter Password:");
        String cellphone = prompt("Enter Your Cellphone:");

        String registrationMessage =
                registration.registerUser(username, password, cellphone);
        System.out.println(registrationMessage);

        if (!registrationMessage.equals("User successfully registered.")) {
            return false;
        }

        System.out.println("\n===== Login =====");
        String loginUsername = prompt("Please enter Username:");
        String loginPassword = prompt("Please enter Password:");

        boolean loggedIn =
                login.loginUser(loginUsername, loginPassword, registration);
        System.out.println(login.returnLoginStatus(loggedIn, firstName, lastName));

        return loggedIn;
    }

    /** Asks how many messages the user wants to enter (0 or more). */
    private static int askNumberOfMessages() {

        while (true) {
            String input = prompt("How many messages do you want to enter?");

            try {
                int number = Integer.parseInt(input.trim());

                if (number >= 0) {
                    return number;
                }
                System.out.println("Please enter 0 or a positive number.");

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    /** Shows the menu until the user chooses Quit (3). */
    private static void runMenu(int numberOfMessages) {

        boolean messagesEntered = false;
        int option = 0;

        while (option != 3) {

            System.out.println("\nSelect any option below");
            System.out.println("1. Send Messages");
            System.out.println("2. Show recently sent messages");
            System.out.println("3. Quit");

            try {
                option = Integer.parseInt(prompt("Enter your choice:").trim());
            } catch (NumberFormatException e) {
                option = 0;
            }

            switch (option) {
                case 1 -> {
                    if (messagesEntered) {
                        System.out.println("You have already entered your "
                                + numberOfMessages + " message(s).");
                    } else {
                        enterMessages(numberOfMessages);
                        messagesEntered = true;
                    }
                }
                case 2 -> System.out.println("Coming Soon.");
                case 3 -> System.out.println("Goodbye.");
                default -> System.out.println("Invalid option. Please enter 1, 2 or 3.");
            }
        }
    }

    /** Lets the user enter exactly the number of messages they chose. */
    private static void enterMessages(int numberOfMessages) {

        for (int i = 0; i < numberOfMessages; i++) {

            System.out.println("\nMessage " + (i + 1) + " of " + numberOfMessages);

            String recipient = askRecipient();
            String text = askMessageText();

            // i is the loop counter, so it is also the message number
            Message message = new Message(i, recipient, text);

            System.out.println(message.messageIDGenerated());

            String result = message.SentMessage();
            System.out.println(result);

            if (result.equals(Message.MSG_SENT)) {
                System.out.println(message.getDetails());

            } else if (result.equals(Message.MSG_DISREGARD)) {
                waitForZero();
                System.out.println("Message deleted.");
            }
        }

        System.out.println("\nTotal messages sent: " + Message.returnTotalMessages());
    }

    /** Keeps asking until the recipient number is valid. */
    private static String askRecipient() {

        while (true) {
            String recipient = prompt("Enter recipient cell number:").trim();
            String result = Message.validateRecipient(recipient);
            System.out.println(result);

            if (result.equals(Message.RECIPIENT_OK)) {
                return recipient;
            }
        }
    }

    /** Keeps asking until the message is not empty and not over 250 characters. */
    private static String askMessageText() {

        while (true) {
            String text = prompt("Enter your message:");
            String result = Message.validateMessageLength(text);
            System.out.println(result);

            if (result.equals(Message.LENGTH_OK)) {
                return text;
            }
        }
    }

    /** Waits until the user presses 0 to delete the message. */
    private static void waitForZero() {

        while (!prompt("").trim().equals("0")) {
            System.out.println(Message.MSG_DISREGARD);
        }
    }

    private static String prompt(String text) {
        if (!text.isEmpty()) {
            System.out.println(text);
        }
        return SCAN.nextLine();
    }
}

        
       
