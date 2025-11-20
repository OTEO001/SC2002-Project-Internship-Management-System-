import repository.*;
import service.*;
import controller.*;
import boundary.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Initialize Repositories
        // Note: Update this path to match your project location or use relative path
        String dataFilePath = "data/sample_staff_list.csv";  // Relative path from project root
        IUserRepository userRepository = new UserRepository(dataFilePath);
        IInternshipRepository internshipRepository = new InternshipRepository();
        IApplicationRepository applicationRepository = new ApplicationRepository();
        IPendingRepRepository pendingRepRepository = new PendingRepRepository();

        // Initialize Services (Dependency Injection)
        IAuthenticationService authenticationService = new AuthenticationService(userRepository);
        IStudentService studentService = new StudentService(internshipRepository, applicationRepository, userRepository);
        ICompanyRepService companyRepService = new CompanyRepService(internshipRepository, applicationRepository, userRepository);
        IStaffService staffService = new StaffService(userRepository, internshipRepository, applicationRepository, pendingRepRepository);
        IRegistrationService registrationService = new RegistrationService(userRepository, pendingRepRepository);

        // Initialize Controllers (Dependency Injection)
        AuthenticationController authController = new AuthenticationController(authenticationService);
        StudentController studentController = new StudentController(studentService, authenticationService);
        CompanyRepController companyRepController = new CompanyRepController(companyRepService, authenticationService);
        StaffController staffController = new StaffController(staffService, authenticationService);
        RegistrationController registrationController = new RegistrationController(registrationService);

        // Initialize UI
        Scanner scanner = new Scanner(System.in);
        MainMenu mainMenu = new MainMenu(scanner, authController, registrationController);

        // Start Application
        System.out.println("===== Welcome to SC2002 Internship Placement Management System =====");
        mainMenu.display(studentController, companyRepController, staffController);
        
        scanner.close();
    }
}
