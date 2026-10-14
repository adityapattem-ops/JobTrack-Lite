import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * JobTrack Lite
 * A simple console-based job application tracker.
 *
 * Features:
 * - Add applications
 * - View all applications
 * - Search by company
 * - Update application status
 * - Save/load applications from jobs.txt
 */
class JobApplication {
    private final int id;
    private final String company;
    private final String role;
    private String status;

    JobApplication(int id, String company, String role, String status) {
        this.id = id;
        this.company = company;
        this.role = role;
        this.status = status;
    }

    int getId() {
        return id;
    }

    String getCompany() {
        return company;
    }

    String getRole() {
        return role;
    }

    String getStatus() {
        return status;
    }

    void setStatus(String status) {
        this.status = status;
    }

    void display() {
        System.out.println("\nID: " + id);
        System.out.println("Company: " + company);
        System.out.println("Role: " + role);
        System.out.println("Status: " + status);
        System.out.println("------------------------------");
    }

    String toFileLine() {
        // The pipe character is used as a separator, so input values replace it.
        return id + "|" + company + "|" + role + "|" + status;
    }
}

public class Main {
    private static final ArrayList<JobApplication> jobs = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);
    private static final Path DATA_FILE = Paths.get("jobs.txt");
    private static int nextId = 1;

    public static void main(String[] args) {
        loadApplications();

        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    addApplication();
                    break;
                case "2":
                    viewApplications();
                    break;
                case "3":
                    searchApplications();
                    break;
                case "4":
                    updateStatus();
                    break;
                case "5":
                    saveApplications();
                    System.out.println("Goodbye! Your applications have been saved if storage is available.");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number from 1 to 5.");
            }
        }

        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n========== JobTrack Lite ==========");
        System.out.println("1. Add job application");
        System.out.println("2. View all applications");
        System.out.println("3. Search by company");
        System.out.println("4. Update application status");
        System.out.println("5. Save and exit");
        System.out.print("Choose an option (1-5): ");
    }

    private static String readRequiredText(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim().replace("|", "-");
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("This field cannot be empty. Please try again.");
        }
    }

    private static void addApplication() {
        String company = readRequiredText("Company name: ");
        String role = readRequiredText("Job role: ");

        JobApplication application =
                new JobApplication(nextId++, company, role, "Applied");
        jobs.add(application);

        System.out.println("Application added successfully.");
        saveApplications();
    }

    private static void viewApplications() {
        if (jobs.isEmpty()) {
            System.out.println("No applications found. Add one using option 1.");
            return;
        }

        for (JobApplication job : jobs) {
            job.display();
        }
        System.out.println("Total applications: " + jobs.size());
    }

    private static void searchApplications() {
        if (jobs.isEmpty()) {
            System.out.println("There are no applications to search.");
            return;
        }

        System.out.print("Enter company name (or part of it): ");
        String query = scanner.nextLine().trim();

        if (query.isEmpty()) {
            System.out.println("Search text cannot be empty.");
            return;
        }

        boolean found = false;
        for (JobApplication job : jobs) {
            if (job.getCompany().toLowerCase().contains(query.toLowerCase())) {
                job.display();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No matching applications found.");
        }
    }

    private static void updateStatus() {
        if (jobs.isEmpty()) {
            System.out.println("There are no applications to update.");
            return;
        }

        System.out.print("Enter application ID: ");
        String idText = scanner.nextLine().trim();
        int id;

        try {
            id = Integer.parseInt(idText);
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID. Please enter a number.");
            return;
        }

        JobApplication selected = null;
        for (JobApplication job : jobs) {
            if (job.getId() == id) {
                selected = job;
                break;
            }
        }

        if (selected == null) {
            System.out.println("No application found with ID " + id + ".");
            return;
        }

        System.out.println("Choose new status:");
        System.out.println("1. Applied");
        System.out.println("2. Interview");
        System.out.println("3. Selected");
        System.out.println("4. Rejected");
        System.out.print("Status (1-4): ");

        String choice = scanner.nextLine().trim();
        String newStatus;

        switch (choice) {
            case "1":
                newStatus = "Applied";
                break;
            case "2":
                newStatus = "Interview";
                break;
            case "3":
                newStatus = "Selected";
                break;
            case "4":
                newStatus = "Rejected";
                break;
            default:
                System.out.println("Invalid status choice. No changes made.");
                return;
        }

        selected.setStatus(newStatus);
        System.out.println("Application status updated to: " + newStatus);
        saveApplications();
    }

    private static void loadApplications() {
        if (!Files.exists(DATA_FILE)) {
            System.out.println("No saved data found. Starting a new tracker.");
            return;
        }

        try (BufferedReader reader =
                     Files.newBufferedReader(DATA_FILE, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|", -1);
                if (parts.length != 4) {
                    System.out.println("Skipping one invalid line in jobs.txt.");
                    continue;
                }

                try {
                    int id = Integer.parseInt(parts[0]);
                    JobApplication job =
                            new JobApplication(id, parts[1], parts[2], parts[3]);
                    jobs.add(job);
                    nextId = Math.max(nextId, id + 1);
                } catch (NumberFormatException e) {
                    System.out.println("Skipping a saved record with an invalid ID.");
                }
            }
            System.out.println("Loaded " + jobs.size() + " saved application(s).");
        } catch (IOException e) {
            System.out.println("Could not load saved applications: " + e.getMessage());
        }
    }

    private static void saveApplications() {
        try (BufferedWriter writer =
                     Files.newBufferedWriter(DATA_FILE, StandardCharsets.UTF_8)) {
            for (JobApplication job : jobs) {
                writer.write(job.toFileLine());
                writer.newLine();
            }
            System.out.println("Data saved to " + DATA_FILE.toAbsolutePath());
        } catch (IOException e) {
            System.out.println("Warning: applications could not be saved: " + e.getMessage());
            System.out.println("Your data will be lost when this run ends if it was not saved.");
        }
    }
}
