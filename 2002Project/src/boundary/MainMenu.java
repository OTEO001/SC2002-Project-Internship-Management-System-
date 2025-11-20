package boundary;

import controller.*;
import entity.User;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;

public class MainMenu {
    private Scanner scanner;
    private AuthenticationController authController;
    private RegistrationController registrationController;

    public MainMenu(Scanner scanner, AuthenticationController authController, RegistrationController registrationController) {
        this.scanner = scanner;
        this.authController = authController;
        this.registrationController = registrationController;
    }

    public void display(StudentController studentController, CompanyRepController companyRepController, StaffController staffController) {
        while (true) {
            System.out.println("\n===== Internship Placement Management System =====");
            System.out.println("1. Login");
            System.out.println("2. Register as Company Representative");
            System.out.println("3. Exit");
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
                    handleLogin(studentController, companyRepController, staffController);
                    break;
                case 2:
                    handleRegistration();
                    break;
                case 3:
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void handleLogin(StudentController studentController, CompanyRepController companyRepController, StaffController staffController) {
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        try {
            User user = authController.handleLogin(userId, password);
            System.out.println("Login successful! Welcome, " + user.getName());

            // Route to appropriate menu
            if (user.getRole().equals("Student")) {
                StudentMenu studentMenu = new StudentMenu(scanner, studentController);
                studentMenu.display();
            } else if (user.getRole().equals("Company Representative")) {
                CompanyRepMenu companyRepMenu = new CompanyRepMenu(scanner, companyRepController);
                companyRepMenu.display();
            } else if (user.getRole().equals("Career Center Staff")) {
                StaffMenu staffMenu = new StaffMenu(scanner, staffController);
                staffMenu.display();
            }

            authController.handleLogout();
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void handleRegistration() {
        System.out.println("\n===== Company Representative Registration =====");
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        System.out.print("Enter Company Name: ");
        String company = scanner.nextLine();
        System.out.print("Enter Position: ");
        String position = scanner.nextLine();
        System.out.print("Enter Department: ");
        String department = scanner.nextLine();

        Map<String, String> details = new HashMap<>();
        details.put("name", name);
        details.put("email", email);
        details.put("password", password);
        details.put("company", company);
        details.put("position", position);
        details.put("department", department);

        try {
            registrationController.handleRegisterCompanyRep(details);
            System.out.println("Registration successful! Your account is pending approval by Career Center staff.");
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
