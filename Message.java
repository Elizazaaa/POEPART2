/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poeparttwo;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Student
 */
public class Message {

   
    public static final Scanner scanner = new Scanner(System.in);

    public static final String RECIPIENT_OK = "Cell phone number successfully captured.";
    public static final String RECIPIENT_BAD = "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
    public static final String LENGTH_OK = "Message ready to send.";
    public static final String MSG_SENT = "Message successfully sent.";
    public static final String MSG_DISREGARD = "Press 0 to delete the message.";
    public static final String MSG_STORED = "Message successfully stored.";

    private static final int MAX_LENGTH = 250;

    private static final ArrayList<Message> sentMessages = new ArrayList<>();
    private static final ArrayList<Message> storedMessages = new ArrayList<>();
    private static int totalSent = 0;

    private final String messageID;
    private final int numMessagesSent;
    private final String recipient;
    private final String message;
    private final String messageHash;

    /** Creates a message with an auto-generated ID (used by the app). */
    public Message(int numMessagesSent, String recipient, String message) {
        this(generateMessageID(), numMessagesSent, recipient, message);
    }

    /** Creates a message with a supplied ID (used by the unit tests). */
    public Message(String messageID, int numMessagesSent,
            String recipient, String message) {

        this.messageID = messageID;
        this.numMessagesSent = numMessagesSent;
        this.recipient = recipient;
        this.message = message;
        this.messageHash = createMessageHash();
    }

    /** Generates a random ten-digit message ID. */
    private static String generateMessageID() {
        long number = ThreadLocalRandom.current().nextLong(10_000_000_000L);
        return String.format("%010d", number);
    }

    /** The message ID must be digits only and no more than ten characters. */
    public boolean checkMessageID() {
        return messageID != null && messageID.matches("[0-9]{1,10}");
    }

    /** Returns the message shown when the ID has been created. */
    public String messageIDGenerated() {
        return "Message ID generated: " + messageID;
    }

    /** Checks a cell number: international code (+) then no more than ten digits. */
    public static String validateRecipient(String number) {
        if (number != null && number.matches("^\\+\\d{1,3}\\d{1,10}$")) {
            return RECIPIENT_OK;
        }
        return RECIPIENT_BAD;
    }

    public String checkRecipientCell() {
        return validateRecipient(recipient);
    }

    /** Checks that message text is not empty and not longer than 250 characters. */
    public static String validateMessageLength(String text) {
        if (text == null || text.trim().isEmpty()) {
            return "Message cannot be empty.";
        }
        if (text.length() > MAX_LENGTH) {
            int over = text.length() - MAX_LENGTH;
            return "Message exceeds 250 characters by " + over
                    + "; please reduce the size.";
        }
        return LENGTH_OK;
    }

    public String checkMessageLength() {
        return validateMessageLength(message);
    }

    /**
     * Builds the hash: first two digits of the ID, the message number,
     * then the first and last word of the message, all in capitals.
     * Example: 00:0:HITONIGHT
     */
    public String createMessageHash() {
        String firstTwoNumbers = "";
        if (messageID != null && messageID.length() >= 2) {
            firstTwoNumbers = messageID.substring(0, 2);
        }

        String trimmed = (message == null) ? "" : message.trim();
        String[] words = trimmed.split("\\s+");

        String firstWord = words[0].replaceAll("[^A-Za-z0-9]", "");
        String lastWord = words[words.length - 1].replaceAll("[^A-Za-z0-9]", "");

        return (firstTwoNumbers + ":" + numMessagesSent + ":"
                + firstWord + lastWord).toUpperCase();
    }

    /** Asks the user to send, disregard or store the message. */
    public String SentMessage() {
        System.out.println("\nChoose what to do with this message");
        System.out.println("1. Send Message");
        System.out.println("2. Disregard Message");
        System.out.println("3. Store Message to send later");

        while (true) {
            System.out.println("Enter your choice:");
            String option = scanner.nextLine().trim();

            if (option.matches("[1-3]")) {
                return SentMessage(Integer.parseInt(option));
            }
            System.out.println("Please enter 1, 2 or 3.");
        }
    }

    /** Carries out the chosen option (1 = send, 2 = disregard, 3 = store). */
    public String SentMessage(int choice) {
        switch (choice) {
            case 1 -> {
                sentMessages.add(this);
                totalSent++;
                return MSG_SENT;
            }
            case 2 -> {
                return MSG_DISREGARD;
            }
            case 3 -> {
                storeMessage();
                return MSG_STORED;
            }
            default -> {
                return "Invalid option.";
            }
        }
    }

    
    public String printMessages() {
        StringBuilder allMessages = new StringBuilder();

        for (Message msg : sentMessages) {
            allMessages.append(msg.getDetails()).append(System.lineSeparator());
        }
        return allMessages.toString();
    }

    /** Returns the total number of messages sent. */
    public static int returnTotalMessages() {
        return totalSent;
    }

    /** Message details in the required order: ID, Hash, Recipient, Message. */
    public String getDetails() {
        return "Message ID: " + messageID + System.lineSeparator()
                + "Message Hash: " + messageHash + System.lineSeparator()
                + "Recipient: " + recipient + System.lineSeparator()
                + "Message: " + message + System.lineSeparator();
    }

    /**
     * Stores all stored messages in storedMessages.json.
     * TODO: add your reference for this JSON-writing approach here, e.g.
     * Author (Year) Title. Available at: URL (Accessed: date).
     */
    public void storeMessage() {
        if (!storedMessages.contains(this)) {
            storedMessages.add(this);
        }

        try (FileWriter writer = new FileWriter("storedMessages.json")) {
            writer.write("[\n");

            for (int i = 0; i < storedMessages.size(); i++) {
                Message msg = storedMessages.get(i);

                writer.write("  {\n");
                writer.write("    \"messageID\": \"" + escapeJSON(msg.messageID) + "\",\n");
                writer.write("    \"messageHash\": \"" + escapeJSON(msg.messageHash) + "\",\n");
                writer.write("    \"recipient\": \"" + escapeJSON(msg.recipient) + "\",\n");
                writer.write("    \"message\": \"" + escapeJSON(msg.message) + "\",\n");
                writer.write("    \"messageNumber\": " + msg.numMessagesSent + "\n");
                writer.write("  }");

                if (i < storedMessages.size() - 1) {
                    writer.write(",");
                }
                writer.write("\n");
            }
            writer.write("]");

        } catch (IOException e) {
            System.out.println("Could not store message: " + e.getMessage());
        }
    }

    /** Escapes characters that are not allowed raw inside a JSON string. */
    private static String escapeJSON(String text) {
        if (text == null) {
            return "";
        }
        return text.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\b", "\\b")
                .replace("\f", "\\f")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    /** Resets the static lists and counter (used between unit tests). */
    public static void clearAll() {
        sentMessages.clear();
        storedMessages.clear();
        totalSent = 0;
    }

    public String getMessageID() {
        return messageID;
    }

    public String getMessageHash() {
        return messageHash;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessage() {
        return message;
    }

    public int getNumMessagesSent() {
        return numMessagesSent;
    }
}
   
