package boundary;

import controller.CompanyRepController;
import entity.*;
import java.util.List;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class CompanyRepMenu {
    private Scanner scanner;
    private CompanyRepController companyRepController;

    public CompanyRepMenu(Scanner scanner, CompanyRepController companyRepController) {
        this.scanner = scanner;
        this.companyRepController = companyRepController;
    }

    public void display() {
        while (true) {
            System.out.println("\n===== Company Representative Menu =====");
            System.out.println("1. Create Internship Opportunity");
            System.out.println("2. Edit Internship Opportunity");
            System.out.println("3. Delete Internship Opportunity");
            System.out.println("4. Toggle Opportunity Visibility");
            System.out.println("5. View My Opportunities");
            System.out.println("6. View Applications for Opportunity");
            System.out.println("7. Process Application");
            System.out.println("8. Change Password");
            System.out.println("9. Logout");
            System.out.print("Enter choice: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    createOpportunity();
                    break;
                case 2:
                    editOpportunity();
                    break;
                case 3:
                    deleteOpportunity();
                    break;
                case 4:
                    toggleVisibility();
                    break;
                case 5:
                    viewMyOpportunities();
                    break;
                case 6:
                    viewApplications();
                    break;
                case 7:
                    processApplication();
                    break;
                case 8:
                    changePassword();
                    break;
                case 9:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void createOpportunity() {
        System.out.println("\n===== Create Internship Opportunity =====");
        System.out.print("Enter Title: ");
        String title = scanner.nextLine();
        System.out.print("Enter Description: ");
        String description = scanner.nextLine();
        System.out.print("Enter Level (BASIC/INTERMEDIATE/ADVANCED): ");
        String levelStr = scanner.nextLine();
        System.out.print("Enter Preferred Major (or leave blank for any): ");
        String preferredMajor = scanner.nextLine();
        System.out.print("Enter Opening Date (YYYY-MM-DD): ");
        String openingDateStr = scanner.nextLine();
        System.out.print("Enter Closing Date (YYYY-MM-DD): ");
        String closingDateStr = scanner.nextLine();
        System.out.print("Enter Total Slots: ");
        String slotsStr = scanner.nextLine();

        try {
            InternshipLevel level = InternshipLevel.valueOf(levelStr.toUpperCase());
            LocalDate openingDate = LocalDate.parse(openingDateStr);
            LocalDate closingDate = LocalDate.parse(closingDateStr);
            int totalSlots = Integer.parseInt(slotsStr);

            Map<String, Object> details = new HashMap<>();
            details.put("title", title);
            details.put("description", description);
            details.put("level", level);
            details.put("preferredMajor", preferredMajor.isEmpty() ? null : preferredMajor);
            details.put("openingDate", openingDate);
            details.put("closingDate", closingDate);
            details.put("totalSlots", totalSlots);

            companyRepController.handleCreateOpportunity(details);
            System.out.println("Opportunity created successfully! It is pending approval by Career Center staff.");
        } catch (IllegalArgumentException | DateTimeParseException e) {
            System.out.println("Error: Invalid input format.");
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void editOpportunity() {
        System.out.print("Enter Opportunity ID: ");
        String opportunityId = scanner.nextLine();
        
        System.out.println("\nLeave fields blank to keep current values");
        System.out.print("Enter New Title: ");
        String title = scanner.nextLine();
        System.out.print("Enter New Description: ");
        String description = scanner.nextLine();
        System.out.print("Enter New Level (BASIC/INTERMEDIATE/ADVANCED): ");
        String levelStr = scanner.nextLine();
        System.out.print("Enter New Preferred Major: ");
        String preferredMajor = scanner.nextLine();
        System.out.print("Enter New Total Slots: ");
        String slotsStr = scanner.nextLine();

        try {
            Map<String, Object> details = new HashMap<>();
            if (!title.isEmpty()) details.put("title", title);
            if (!description.isEmpty()) details.put("description", description);
            if (!levelStr.isEmpty()) details.put("level", InternshipLevel.valueOf(levelStr.toUpperCase()));
            if (!preferredMajor.isEmpty()) details.put("preferredMajor", preferredMajor);
            if (!slotsStr.isEmpty()) details.put("totalSlots", Integer.parseInt(slotsStr));

            companyRepController.handleEditOpportunity(opportunityId, details);
            System.out.println("Opportunity updated successfully!");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: Invalid input format.");
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void deleteOpportunity() {
        System.out.print("Enter Opportunity ID: ");
        String opportunityId = scanner.nextLine();

        try {
            companyRepController.handleDeleteOpportunity(opportunityId);
            System.out.println("Opportunity deleted successfully!");
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void toggleVisibility() {
        System.out.print("Enter Opportunity ID: ");
        String opportunityId = scanner.nextLine();

        try {
            companyRepController.handleToggleVisibility(opportunityId);
            System.out.println("Visibility toggled successfully!");
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewMyOpportunities() {
        try {
            List<InternshipOpportunity> opportunities = companyRepController.handleViewMyOpportunities();
            
            if (opportunities.isEmpty()) {
                System.out.println("You have no internship opportunities.");
                return;
            }

            System.out.println("\n===== My Internship Opportunities =====");
            for (InternshipOpportunity opp : opportunities) {
                System.out.println("\nOpportunity ID: " + opp.getOpportunityId());
                System.out.println("Title: " + opp.getTitle());
                System.out.println("Description: " + opp.getDescription());
                System.out.println("Level: " + opp.getLevel());
                System.out.println("Status: " + opp.getStatus());
                System.out.println("Visible: " + (opp.isVisible() ? "Yes" : "No"));
                System.out.println("Slots: " + opp.getFilledSlots() + " / " + opp.getTotalSlots());
                System.out.println("-----------------------------------");
            }
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewApplications() {
        System.out.print("Enter Opportunity ID: ");
        String opportunityId = scanner.nextLine();

        try {
            List<Application> applications = companyRepController.handleViewApplications(opportunityId);
            
            if (applications.isEmpty()) {
                System.out.println("No applications for this opportunity.");
                return;
            }

            System.out.println("\n===== Applications =====");
            for (Application app : applications) {
                System.out.println("\nApplication ID: " + app.getApplicationId());
                System.out.println("Student: " + app.getStudent().getName());
                System.out.println("Student ID: " + app.getStudent().getUserId());
                System.out.println("Major: " + app.getStudent().getMajor());
                System.out.println("Year of Study: " + app.getStudent().getYearOfStudy());
                System.out.println("Application Date: " + app.getApplicationDate());
                System.out.println("Status: " + app.getStatus().getDisplayName());
                System.out.println("-----------------------------------");
            }
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void processApplication() {
        System.out.print("Enter Application ID: ");
        String applicationId = scanner.nextLine();
        System.out.print("Approve? (yes/no): ");
        String approveStr = scanner.nextLine();

        boolean approve = approveStr.equalsIgnoreCase("yes");

        try {
            companyRepController.handleProcessApplication(applicationId, approve);
            System.out.println("Application " + (approve ? "approved" : "rejected") + " successfully!");
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void changePassword() {
        System.out.print("Enter Old Password: ");
        String oldPassword = scanner.nextLine();
        System.out.print("Enter New Password: ");
        String newPassword = scanner.nextLine();

        try {
            companyRepController.handleChangePassword(oldPassword, newPassword);
            System.out.println("Password changed successfully!");
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
