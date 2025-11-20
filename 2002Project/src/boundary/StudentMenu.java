package boundary;

import controller.StudentController;
import entity.*;
import java.util.List;
import java.util.Scanner;

public class StudentMenu {
    private Scanner scanner;
    private StudentController studentController;

    public StudentMenu(Scanner scanner, StudentController studentController) {
        this.scanner = scanner;
        this.studentController = studentController;
    }

    public void display() {
        while (true) {
            System.out.println("\n===== Student Menu =====");
            System.out.println("1. Browse Internship Opportunities");
            System.out.println("2. Apply for Internship");
            System.out.println("3. View My Applications");
            System.out.println("4. Accept Placement");
            System.out.println("5. Withdraw Application");
            System.out.println("6. Change Password");
            System.out.println("7. Logout");
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
                    browseOpportunities();
                    break;
                case 2:
                    applyForInternship();
                    break;
                case 3:
                    viewApplications();
                    break;
                case 4:
                    acceptPlacement();
                    break;
                case 5:
                    withdrawApplication();
                    break;
                case 6:
                    changePassword();
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void browseOpportunities() {
        try {
            List<InternshipOpportunity> opportunities = studentController.handleViewOpportunities();
            
            if (opportunities.isEmpty()) {
                System.out.println("No eligible internship opportunities available.");
                return;
            }

            System.out.println("\n===== Eligible Internship Opportunities =====");
            for (InternshipOpportunity opp : opportunities) {
                System.out.println("\nOpportunity ID: " + opp.getOpportunityId());
                System.out.println("Title: " + opp.getTitle());
                System.out.println("Company: " + opp.getCompanyName());
                System.out.println("Description: " + opp.getDescription());
                System.out.println("Level: " + opp.getLevel());
                System.out.println("Preferred Major: " + (opp.getPreferredMajor() == null || opp.getPreferredMajor().isEmpty() ? "Any" : opp.getPreferredMajor()));
                System.out.println("Opening Date: " + opp.getOpeningDate());
                System.out.println("Closing Date: " + opp.getClosingDate());
                System.out.println("Available Slots: " + (opp.getTotalSlots() - opp.getFilledSlots()) + " / " + opp.getTotalSlots());
                System.out.println("-----------------------------------");
            }
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void applyForInternship() {
        System.out.print("Enter Opportunity ID: ");
        String opportunityId = scanner.nextLine();

        try {
            studentController.handleApplyForInternship(opportunityId);
            System.out.println("Application submitted successfully!");
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewApplications() {
        try {
            List<Application> applications = studentController.handleViewApplications();
            
            if (applications.isEmpty()) {
                System.out.println("You have no applications.");
                return;
            }

            System.out.println("\n===== My Applications =====");
            for (Application app : applications) {
                System.out.println("\nApplication ID: " + app.getApplicationId());
                System.out.println("Opportunity: " + app.getOpportunity().getTitle());
                System.out.println("Company: " + app.getOpportunity().getCompanyName());
                System.out.println("Application Date: " + app.getApplicationDate());
                System.out.println("Status: " + app.getStatus().getDisplayName());
                System.out.println("Accepted: " + (app.isAccepted() ? "Yes" : "No"));
                System.out.println("-----------------------------------");
            }
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void acceptPlacement() {
        System.out.print("Enter Application ID: ");
        String applicationId = scanner.nextLine();

        try {
            studentController.handleAcceptPlacement(applicationId);
            System.out.println("Placement accepted successfully! Your other applications have been withdrawn.");
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void withdrawApplication() {
        System.out.print("Enter Application ID: ");
        String applicationId = scanner.nextLine();

        try {
            studentController.handleWithdrawApplication(applicationId);
            System.out.println("Application withdrawn successfully!");
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
            studentController.handleChangePassword(oldPassword, newPassword);
            System.out.println("Password changed successfully!");
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
