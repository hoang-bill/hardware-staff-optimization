package edu.iu.c212;

import edu.iu.c212.models.Item;
import edu.iu.c212.models.Staff;
import edu.iu.c212.programs.StaffScheduler;
import edu.iu.c212.utils.FileUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class Store {
    private List<Item> inventory;
    private List<Staff> staffList;
    private FileWriter outputWriter;

    public Store() {
        try {
            this.inventory = FileUtils.readInventoryFromFile();
            this.staffList = FileUtils.readStaffFromFile();
            this.outputWriter = new FileWriter("ProjectStarterCode/src/edu/iu/c212/resources/output.txt", true); // Specify the correct path and enable appending
        } catch (Exception e) {
            System.err.println("Initialization error: " + e.getMessage());
            System.exit(1);
        }
    }

    public void takeAction() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter command:");
            String command;

            // Continue reading and processing commands until "EXIT" is entered
            while (!(command = scanner.nextLine().trim()).equalsIgnoreCase("EXIT")) {
                System.out.println("Command received: " + command);
                executeCommand(command);
                outputWriter.flush(); // Flush after every command execution to ensure output is written
            }

            // Write the exit message to the output file when "EXIT" is entered
            outputWriter.write("Thank you for visiting High’s Hardware and Gardening!\n");
            outputWriter.flush(); // Ensure all data is written before closing

            // Inform the user on the console and wait for them to press Enter
            System.out.println("Thank you for visiting High’s Hardware and Gardening!");
            System.out.println("Press enter to continue...");
            scanner.nextLine(); // Wait for the user to press Enter

            outputWriter.close(); // Close the FileWriter
            System.out.println("Process completed. Check output.txt for results.");
        } catch (IOException e) {
            System.err.println("Error occurred while processing command: " + e.getMessage());
        }
    }


    private void executeCommand(String command) throws IOException {
        if (command.isEmpty()) return;
        System.out.println("Executing command: " + command);
        String[] parts = command.split("\\s+", 2);
        String action = parts[0];
        String arguments = parts.length > 1 ? parts[1] : "";

        switch (action.toUpperCase()) {
            case "ADD":
                handleAddCommand(arguments);
                break;
            case "FIND":
                handleFindCommand(arguments);
                break;
            case "COST":
                handleCostCommand(arguments);
                break;
            case "SELL":
                handleSellCommand(arguments);
                break;
            case "FIRE":
                handleFireCommand(arguments);
                break;
            case "HIRE":
                handleHireCommand(arguments);
                break;
            case "PROMOTE":
                handlePromoteCommand(arguments);
                break;
            case "SAW":
                handleSawCommand();
                break;
            case "SCHEDULE":
                handleScheduleCommand();
                break;
            case "QUANTITY":
                handleQuantityCommand(arguments);
                break;
            default:
                outputWriter.write("Unsupported command.\n");
        }
    }

    private void handleAddCommand(String arguments) throws IOException {
        // Split the arguments by spaces except those within single quotes
        String[] args = arguments.split("\\s+(?=(?:[^']*'[^']*')*[^']*$)");

        if (args.length < 4) {
            outputWriter.write("ERROR: Insufficient arguments for ADD command.\n");
            System.out.println("ERROR: Insufficient arguments for ADD command.");
            return;
        }

        try {
            String name = args[0].trim().replace("'", ""); // Remove single quotes around the item name.
            int price = Integer.parseInt(args[1].trim()); // Parsing price as integer.
            int quantity = Integer.parseInt(args[2].trim()); // Parsing quantity as integer.
            int aisle = Integer.parseInt(args[3].trim()); // Parsing aisle as integer.

            Item newItem = new Item(name, price, quantity, aisle);
            inventory.add(newItem);
            FileUtils.writeInventoryToFile(inventory);  // Update the inventory file with the new item list.

            outputWriter.write(name + " was added to inventory\n");
            System.out.println(name + " was added to inventory");  // Confirm addition on the console.
        } catch (NumberFormatException e) {
            outputWriter.write("ERROR: Invalid number format in ADD command.\n");
            System.out.println("ERROR: Invalid number format in ADD command.");
        }
    }


    private void handleFindCommand(String arguments) throws IOException {
        // Trim and remove single quotes from the input argument to get the item name.
        String itemName = arguments.trim().replace("'", "");
        System.out.println("Searching for item: " + itemName); // Debug print to confirm the item name being searched.

        boolean found = false; // Flag to track if the item is found.

        // Iterate over the inventory list to find the item.
        for (Item item : inventory) {
            System.out.println("Comparing with item: " + item.getName()); // Debug print each item's name during comparison.

            // Check if the current item's name matches the searched item name, ignoring case.
            if (item.getName().equalsIgnoreCase(itemName)) {
                found = true; // Set found to true as the item is found.
                // Prepare the result string with the item details.
                String result = item.getQuantity() + " " + item.getName() + " are available in aisle " + item.getAisle() + "\n";
                // Write the result to the output file.
                outputWriter.write(result);
                System.out.println("Found item, written to file: " + result); // Confirm what is written to the file.
                break; // Break the loop as the item is found.
            }
        }

        // Check if the item was not found after the loop completes.
        if (!found) {
            // Write an error message to the output file indicating the item could not be found.
            outputWriter.write("ERROR: " + itemName + " cannot be found\n");
            System.out.println("Item not found: " + itemName); // Debug print if the item is not found.
        }

        outputWriter.flush(); // Flush the writer to ensure all data is written to the file.
    }


    private void handleCostCommand(String arguments) throws IOException {
        // Trim the input to remove any leading or trailing spaces and remove single quotes around the item name
        String itemName = arguments.trim().replace("'", "");
        boolean found = false; // Flag to check if item is found

        // Loop through the inventory to find the item by name
        for (Item item : inventory) {
            // Use equalsIgnoreCase to make the search case-insensitive
            if (item.getName().equalsIgnoreCase(itemName)) {
                found = true; // Set found flag to true
                // Write the item's name and price to the output file, formatted as currency
                outputWriter.write(itemName + ": $" + item.getPrice() + "\n");
                outputWriter.flush(); // Ensure the written data is immediately output to the file
                System.out.println(itemName + ": $" + item.getPrice()); // Optionally print to console for immediate feedback
                break; // Exit the loop as the item has been found
            }
        }

        // If no item is found after checking the entire inventory, write an error message to the output file
        if (!found) {
            outputWriter.write("ERROR: " + itemName + " cannot be found\n");
            outputWriter.flush(); // Ensure the written data is immediately output to the file
            System.out.println("ERROR: " + itemName + " cannot be found"); // Optionally print to console for immediate feedback
        }
    }


    private void handleSellCommand(String arguments) throws IOException {
        // Split the arguments by spaces except those within single quotes
        String[] args = arguments.split("\\s+(?=(?:[^']*'[^']*')*[^']*$)");

        // Check if there are sufficient arguments
        if (args.length < 2) {
            outputWriter.write("ERROR: Insufficient arguments for SELL command.\n");
            outputWriter.flush();
            return;
        }

        // Extract the item name and quantity to sell from the arguments
        String itemName = args[0].replace("'", ""); // Remove surrounding single quotes from the item name
        int quantityToSell = Integer.parseInt(args[1]);

        // Search for the item in the inventory
        boolean found = false;
        for (Item item : inventory) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                found = true;
                // Check if there is sufficient quantity to sell
                if (item.getQuantity() >= quantityToSell) {
                    // Deduct the sold quantity from the inventory
                    item.setQuantity(item.getQuantity() - quantityToSell);
                    FileUtils.writeInventoryToFile(inventory); // Update the inventory file
                    // Write a success message to the output file
                    outputWriter.write(quantityToSell + " " + itemName + " was sold\n");
                    outputWriter.flush();
                    System.out.println(quantityToSell + " " + itemName + " was sold"); // Optionally print to console for immediate feedback
                } else {
                    // Write an error message if there is insufficient quantity
                    outputWriter.write("ERROR: Not enough " + itemName + " in stock to sell\n");
                    outputWriter.flush();
                    System.out.println("ERROR: Not enough " + itemName + " in stock to sell"); // Optionally print to console for immediate feedback
                }
                break;
            }
        }

        // If the item was not found in the inventory
        if (!found) {
            outputWriter.write("ERROR: " + itemName + " could not be sold\n");
            outputWriter.flush();
            System.out.println("ERROR: " + itemName + " could not be sold"); // Optionally print to console for immediate feedback
        }
    }


    private void handleFireCommand(String arguments) throws IOException {
        // Split the arguments based on commas to extract the staff name
        String[] args = arguments.split(",");
        String staffName = args[0].trim(); // Extract the first element as the staff name

        boolean found = false; // Flag to check if staff member is found

        // Use an iterator to safely remove the staff member while iterating
        Iterator<Staff> iterator = staffList.iterator();
        while (iterator.hasNext()) {
            Staff staff = iterator.next();
            if (staff.getName().equalsIgnoreCase(staffName)) {
                iterator.remove(); // Remove the staff member from the list
                found = true;
                break;
            }
        }

        // After checking/removing from the list, update the file and output file
        if (found) {
            FileUtils.writeStaffToFile(staffList); // Update the staff file
            outputWriter.write(staffName + " was fired\n"); // Confirm the firing in the output file
            System.out.println(staffName + " was fired"); // Optional: print confirmation to the console
        } else {
            outputWriter.write("ERROR: " + staffName + " cannot be found\n"); // Write an error message if not found
            System.out.println("ERROR: " + staffName + " cannot be found"); // Optional: print error to the console
        }
        outputWriter.flush(); // Flush the writer to ensure all data is written to the file
    }






    private void handleHireCommand(String arguments) throws IOException {
        System.out.println("Handling HIRE with raw arguments: " + arguments);

        // Split arguments by spaces and commas, ignoring commas within single quotes
        String[] args = arguments.split("\\s*,\\s*(?=([^']*'[^']*')*[^']*$)|\\s+(?![^']*'(?:(?:[^']*'){2})*[^']*$)");
        System.out.println("Split arguments: " + Arrays.toString(args));

        if (args.length < 4) {
            this.outputWriter.write("ERROR: Insufficient arguments for HIRE command.\n");
            this.outputWriter.flush();
        } else {
            try {
                String name = args[0].replace("'", "").trim(); // Remove single quotes and trim the name
                int age = Integer.parseInt(args[1].trim());
                String roleShort = args[2].trim();
                String availability = args[3].trim();
                String var10000;
                switch (roleShort) {
                    case "Manager":
                        var10000 = "Manager";
                        break;
                    case "Cashier":
                        var10000 = "Cashier";
                        break;
                    case "Gardening":
                        var10000 = "Gardening Expert";
                        break;
                    default:
                        var10000 = "Unknown Role";
                }

                String roleFullName = var10000;
                Staff newStaff = new Staff(name, age, roleFullName, availability);
                this.staffList.add(newStaff);
                FileUtils.writeStaffToFile(this.staffList);
                String confirmationMessage = name + " has been hired as a " + roleFullName + "\n"; // Update the confirmation message
                this.outputWriter.write(confirmationMessage);
                this.outputWriter.flush();
                System.out.println(confirmationMessage);
            } catch (NumberFormatException var10) {
                this.outputWriter.write("ERROR: Invalid age format. Age must be an integer.\n");
                this.outputWriter.flush();
            }
        }
    }








    private void handlePromoteCommand(String arguments) throws IOException {
        // Correctly split the arguments to handle quotes properly
        String[] args = arguments.split("\\s+(?=(?:[^']*'[^']*')*[^']*$)", 2);

        if (args.length < 2) {
            outputWriter.write("ERROR: Insufficient arguments for PROMOTE command.\n");
            outputWriter.flush();
            return;
        }

        String name = args[0].replace("'", ""); // Remove surrounding single quotes from the name
        String roleShort = args[1]; // Role is the second argument, already without quotes

        // Map role shorthand to full names
        String roleFullName = switch (roleShort.trim()) {
            case "M" -> "Manager";
            case "C" -> "Cashier";
            case "G" -> "Gardening Expert";
            default -> "Unknown Role"; // Default case to handle unexpected role inputs
        };

        boolean found = false;
        for (Staff staff : staffList) {
            if (staff.getName().equalsIgnoreCase(name)) {
                staff.setRole(roleFullName); // Update the role of the staff member
                found = true;
                break;
            }
        }

        if (found) {
            FileUtils.writeStaffToFile(staffList); // Update the staff file
            outputWriter.write(name + " was promoted to " + roleFullName + "\n");
            outputWriter.flush();
            System.out.println(name + " was promoted to " + roleFullName); // Optional: print confirmation to the console
        } else {
            outputWriter.write("ERROR: " + name + " cannot be found\n");
            outputWriter.flush();
            System.out.println("ERROR: " + name + " cannot be found"); // Optional: print error to the console
        }
    }


    private void handleSawCommand() throws IOException {
        System.out.println("Executing handleSawCommand...");

        // Read the inventory from the file
        List<Item> inventory = new ArrayList<>();
        try {
            inventory = FileUtils.readInventoryFromFile();
            System.out.println("Inventory read successfully: " + inventory.size() + " items");
        } catch (IOException e) {
            // Handle the case where reading the inventory file fails
            System.err.println("Error reading inventory file: " + e.getMessage());
            return;
        }

        // Perform the sawing operation (replace certain planks)
        // For example, let's say you want to saw down all planks with aisle number 3
        for (Item item : inventory) {
            if (item.getAisle() == 3) {
                // Perform sawing operation here
                // For demonstration, let's assume we reduce the quantity of planks by half
                int newQuantity = item.getQuantity() / 2;
                item.setQuantity(newQuantity);
            }
        }

        // Write the updated inventory back to the file
        try {
            FileUtils.writeInventoryToFile(inventory);
            System.out.println("Inventory updated and written back to file.");
        } catch (IOException e) {
            // Handle the case where writing to the inventory file fails
            System.err.println("Error writing inventory file: " + e.getMessage());
            return;
        }

        // Write the success message to the output file
        outputWriter.write("Planks sawed.\n");
        outputWriter.flush();

        System.out.println("handleSawCommand completed successfully.");
    }


    private void handleScheduleCommand() throws IOException {
        try {
            // Read staff availability from input file
            List<Staff> staffList = FileUtils.readStaffFromFile();

            // Read shift schedules from input file
            List<String> shiftSchedules = FileUtils.readShiftSchedulesFromFile();

            // Create a scheduler instance
            StaffScheduler scheduler = new StaffScheduler(staffList);

            // Generate the schedule based on shift schedules
            scheduler.scheduleStaff(shiftSchedules);

            // Write the schedule to the output file
            scheduler.writeScheduleToFile("store_schedule_OUT.txt");

            // Write confirmation message to the output file and console
            outputWriter.write("Schedule created.\n");
            outputWriter.flush();
            System.out.println("Schedule created.");
        } catch (IOException e) {
            // Handle any errors that occur during file reading or scheduling
            outputWriter.write("ERROR: Unable to create schedule. " + e.getMessage() + "\n");
            outputWriter.flush();
            System.err.println("ERROR: Unable to create schedule. " + e.getMessage());
        }
    }


    private void handleQuantityCommand(String arguments) throws IOException {
        // Trim and remove single quotes from the input argument to get the item name
        String itemName = arguments.trim().replace("'", "");
        boolean found = false; // Flag to track if item is found

        // Check if the inventory is empty
        if (inventory.isEmpty()) {
            outputWriter.write("Inventory is empty\n");
            outputWriter.flush(); // Flush the output to ensure it's immediately written
            return;
        }

        // Iterate through the inventory to find the item by name
        for (Item item : inventory) {
            // Use equalsIgnoreCase to make the search case-insensitive
            if (item.getName().equalsIgnoreCase(itemName)) {
                found = true; // Set found flag to true
                // Write the item's name and quantity to the output file
                outputWriter.write("Quantity of " + itemName + ": " + item.getQuantity() + "\n");
                outputWriter.flush(); // Flush the output to ensure it's immediately written
                System.out.println("Quantity of " + itemName + ": " + item.getQuantity()); // Print to console for immediate feedback
                break; // Exit the loop as the item has been found
            }
        }

        // If no item is found after checking the entire inventory, write an error message to the output file
        if (!found) {
            outputWriter.write("ERROR: " + itemName + " cannot be found\n");
            outputWriter.flush(); // Flush the output to ensure it's immediately written
            System.out.println("ERROR: " + itemName + " cannot be found"); // Print to console for immediate feedback
        }
    }
}