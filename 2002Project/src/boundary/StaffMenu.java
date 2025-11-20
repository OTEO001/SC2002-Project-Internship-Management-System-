package boundary;

import controller.StaffController;
import entity.*;
import java.util.List;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;

public class StaffMenu {
    private Scanner scanner;
    private StaffController staffController;

    public StaffMenu(Scanner scanner, StaffController staffController) {
        this.scanner = scanner;
        this.staffController = staffController;
    }

    public void display() {
        while (true) {
            System.out.println("\n===== Career Center Staff Menu =====");
            System.out.println("1. View Pending Company Representatives");
            System.out.println("2. Approve/Reject Company Representative");
            System.out.println("3. View Pending Internship Opportunities");
            System.out.println("4. Approve/Reject Internship Opportunity");
            System.out.println("5. Generate Report");
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
                    viewPendingReps();
                    break;
                case 2:
                    processRep();
                    break;
                case 3:
                    viewPendingOpportunities();
                    break;
                case 4:
                    processOpportunity();
                    break;
                case 5:
                    generateReport();
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

    private void viewPendingReps() {
        try {
            List<CompanyRepresentative> reps = staffController.handleViewPendingReps();
            
            if (reps.isEmpty()) {
                System.out.println("No pending company representatives.");
                return;
            }

            System.out.println("\n===== Pending Company Representatives =====");
            for (CompanyRepresentative rep : reps) {
                System.out.println("\nUser ID: " + rep.getUserId());
                System.out.println("Name: " + rep.getName());
                System.out.println("Email: " + rep.getEmail());
                System.out.println("Company: " + rep.getCompanyName());
                System.out.println("Position: " + rep.getPosition());
                System.out.println("Department: " + rep.getDepartment());
                System.out.println("-----------------------------------");
            }
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void processRep() {
        System.out.print("Enter Representative User ID: ");
        String repId = scanner.nextLine();
        System.out.print("Approve? (yes/no): ");
        String approveStr = scanner.nextLine();

        boolean approve = approveStr.equalsIgnoreCase("yes");

        try {
            if (approve) {
                staffController.handleApproveRep(repId);
                System.out.println("Representative approved successfully!");
            } else {
                staffController.handleRejectRep(repId);
                System.out.println("Representative rejected successfully!");
            }
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewPendingOpportunities() {
        try {
            List<InternshipOpportunity> opportunities = staffController.handleViewPendingOpportunities();
            
            if (opportunities.isEmpty()) {
                System.out.println("No pending internship opportunities.");
                return;
            }

            System.out.println("\n===== Pending Internship Opportunities =====");
            for (InternshipOpportunity opp : opportunities) {
                System.out.println("\nOpportunity ID: " + opp.getOpportunityId());
                System.out.println("Title: " + opp.getTitle());
                System.out.println("Company: " + opp.getCompanyName());
                System.out.println("Description: " + opp.getDescription());
                System.out.println("Level: " + opp.getLevel());
                System.out.println("Total Slots: " + opp.getTotalSlots());
                System.out.println("Opening Date: " + opp.getOpeningDate());
                System.out.println("Closing Date: " + opp.getClosingDate());
                System.out.println("-----------------------------------");
            }
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void processOpportunity() {
        System.out.print("Enter Opportunity ID: ");
        String opportunityId = scanner.nextLine();
        System.out.print("Approve? (yes/no): ");
        String approveStr = scanner.nextLine();

        boolean approve = approveStr.equalsIgnoreCase("yes");

        try {
            if (approve) {
                staffController.handleApproveOpportunity(opportunityId);
                System.out.println("Opportunity approved successfully!");
            } else {
                staffController.handleRejectOpportunity(opportunityId);
                System.out.println("Opportunity rejected successfully!");
            }
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void generateReport() {
        System.out.println("\n===== Generate Report =====");
        System.out.println("Apply filters (leave blank to skip):");
        System.out.print("Filter by Company: ");
        String company = scanner.nextLine();
        System.out.print("Filter by Level (BASIC/INTERMEDIATE/ADVANCED): ");
        String levelStr = scanner.nextLine();
        System.out.print("Filter by Status (PENDING/APPROVED/REJECTED/FILLED): ");
        String statusStr = scanner.nextLine();

        try {
            Map<String, String> filters = new HashMap<>();
            if (!company.isEmpty()) filters.put("company", company);
            if (!levelStr.isEmpty()) filters.put("level", levelStr);
            if (!statusStr.isEmpty()) filters.put("status", statusStr);

            List<InternshipOpportunity> opportunities = staffController.handleGenerateReport(filters);
            
            if (opportunities.isEmpty()) {
                System.out.println("No opportunities match the specified filters.");
                return;
            }

            System.out.println("\n===== Report Results =====");
            System.out.println("Total Opportunities: " + opportunities.size());
            System.out.println("\n");
            for (InternshipOpportunity opp : opportunities) {
                System.out.println("Opportunity ID: " + opp.getOpportunityId());
                System.out.println("Title: " + opp.getTitle());
                System.out.println("Company: " + opp.getCompanyName());
                System.out.println("Level: " + opp.getLevel());
                System.out.println("Status: " + opp.getStatus());
                System.out.println("Slots: " + opp.getFilledSlots() + " / " + opp.getTotalSlots());
                System.out.println("-----------------------------------");
            }
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
            staffController.handleChangePassword(oldPassword, newPassword);
            System.out.println("Password changed successfully!");
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
