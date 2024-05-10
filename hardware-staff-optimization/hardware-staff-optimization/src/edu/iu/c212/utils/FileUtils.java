package edu.iu.c212.utils;

import edu.iu.c212.models.Item;
import edu.iu.c212.models.Staff;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class FileUtils {
    private static File inputFile = new File("ProjectStarterCode/src/edu/iu/c212/resources/input.txt");
    private static File outputFile = new File("ProjectStarterCode/src/edu/iu/c212/resources/output.txt");
    private static File inventoryFile = new File("ProjectStarterCode/src/edu/iu/c212/resources/inventory.txt");
    private static File staffFile = new File("ProjectStarterCode/src/edu/iu/c212/resources/staff.txt");
    private static File staffAvailabilityFile = new File("ProjectStarterCode/src/edu/iu/c212/resources/staff_availability_IN.txt");
    private static File shiftSchedulesFile = new File("ProjectStarterCode/src/edu/iu/c212/resources/shift_schedules_IN.txt");
    private static File storeScheduleFile = new File("ProjectStarterCode/src/edu/iu/c212/resources/store_schedule_OUT.txt");
    public static List<String> readShiftSchedulesFromFile() throws IOException {
        List<String> schedules = new ArrayList<>();
        Path path = Paths.get(shiftSchedulesFile.getPath());
        List<String> lines = Files.readAllLines(path);
        for (String line : lines) {
            schedules.add(line.trim());  // Assuming each line represents a shift schedule
        }
        return schedules;
    }
    public static List<Item> readInventoryFromFile() throws IOException {
        List<Item> items = new ArrayList<>();
        Path path = Paths.get(inventoryFile.getPath());
        try (Stream<String> lines = Files.lines(path)) {
            lines.forEach(line -> {
                System.out.println("Reading line: " + line); // Debug print each line
                // Adjust regex to correctly match the line format with flexible floating point representation
                if (!line.startsWith("//") && line.matches("'.+',\\d+\\.\\d{1,6},\\d+,\\d+")) {
                    try {
                        String[] parts = line.split(",");
                        String name = parts[0].trim().replace("'", ""); // Remove single quotes around item name
                        double cost = Double.parseDouble(parts[1].trim()); // Convert cost to double
                        int quantity = Integer.parseInt(parts[2].trim()); // Convert quantity to integer
                        int aisle = Integer.parseInt(parts[3].trim()); // Convert aisle number to integer
                        items.add(new Item(name, cost, quantity, aisle));
                    } catch (NumberFormatException e) {
                        System.err.println("Skipping line due to formatting issue: " + line);
                    }
                }
            });
        }
        return items;
    }



    public static List<Staff> readStaffFromFile() throws IOException {
        List<Staff> staff = new ArrayList<>();
        Path path = Paths.get(staffAvailabilityFile.getPath());
        List<String> lines = Files.readAllLines(path);
        for (String line : lines) {
            String[] parts = line.split(",");  // Split by commas
            if (parts.length == 4) { // Ensure all attributes are present
                String name = parts[0].trim();
                int age = Integer.parseInt(parts[1].trim());
                String role = parts[2].trim();
                String availability = parts[3].trim();
                staff.add(new Staff(name, age, role, availability));
            } else {
                System.err.println("Skipping line due to incorrect format: " + line);
            }
        }
        return staff;
    }



    public static void writeInventoryToFile(List<Item> items) throws IOException {
        List<String> lines = new ArrayList<>();
        for (Item item : items) {
            lines.add(String.format("'%s',%f,%d,%d", item.getName(), item.getPrice(), item.getQuantity(), item.getAisle()));
        }
        Files.write(Paths.get(inventoryFile.getPath()), lines);
    }

    public static void writeStaffToFile(List<Staff> staff) throws IOException {
        List<String> lines = new ArrayList<>();
        for (Staff s : staff) {
            lines.add(String.format("%s,%d,%s,%s", s.getName(), s.getAge(), s.getRole(), s.getAvailability()));
        }
        Files.write(Paths.get(staffAvailabilityFile.getPath()), lines);
    }

    public static List<String> readCommandsFromFile() throws IOException {
        return Files.readAllLines(Paths.get(inputFile.getPath()));
    }
}
