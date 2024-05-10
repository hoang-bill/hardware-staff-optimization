package edu.iu.c212.programs;

import edu.iu.c212.models.Staff;
import edu.iu.c212.utils.FileUtils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StaffScheduler {
    private List<Staff> staffList;
    private Map<String, List<String>> staffSchedule;  // Day to staff names

    public StaffScheduler(List<Staff> staffList) {
        this.staffList = staffList;
        this.staffSchedule = new HashMap<>();
    }

    public void scheduleStaff(List<String> shiftSchedules) {
        // Initialize staff availability in the schedule
        for (String day : Arrays.asList("M", "T", "W", "TR", "F", "SAT", "SUN")) {
            staffSchedule.put(day, new ArrayList<>());
        }

        // Distribute shifts based on the provided shift schedules
        for (String schedule : shiftSchedules) {
            String[] parts = schedule.split("\\s+");
            String day = parts[0];
            List<String> staffNames = Arrays.asList(parts).subList(1, parts.length);

            for (String staffName : staffNames) {
                staffSchedule.get(day).add(staffName);
            }
        }
    }

    public void writeScheduleToFile(String outputFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            // Write the schedule header
            writer.write("Created on 4/12/2022 at 1727\n");

            // Write the schedule for each day
            for (Map.Entry<String, List<String>> entry : staffSchedule.entrySet()) {
                String day = entry.getKey();
                List<String> staffNames = entry.getValue();

                writer.write(day + " ");
                for (String name : staffNames) {
                    writer.write("(" + name + ") ");
                }
                writer.write("\n");
            }

            System.out.println("Schedule created. Check " + outputFile + " for the schedule.");
        } catch (IOException e) {
            System.err.println("Error writing schedule to file: " + e.getMessage());
            e.printStackTrace(); // Print stack trace for debugging
        }
    }

    public static void main(String[] args) {
        try {
            // Read staff availability from input file
            List<Staff> staffList = FileUtils.readStaffFromFile();

            // Read shift schedules from input file
            List<String> shiftSchedules = FileUtils.readShiftSchedulesFromFile();

            // Create a StaffScheduler instance and schedule staff
            StaffScheduler scheduler = new StaffScheduler(staffList);
            scheduler.scheduleStaff(shiftSchedules);

            // Write the schedule to an output file
            scheduler.writeScheduleToFile("store_schedule_OUT.txt");
        } catch (IOException e) {
            System.err.println("Error reading input files or scheduling staff: " + e.getMessage());
            e.printStackTrace(); // Print stack trace for debugging
        }
    }
}
