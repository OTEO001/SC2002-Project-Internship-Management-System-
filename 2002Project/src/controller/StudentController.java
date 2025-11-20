package controller;

import service.IStudentService;
import service.IAuthenticationService;
import entity.*;
import java.util.List;

public class StudentController {
    private IStudentService studentService;
    private IAuthenticationService authenticationService;

    public StudentController(IStudentService studentService, IAuthenticationService authenticationService) {
        this.studentService = studentService;
        this.authenticationService = authenticationService;
    }

    public List<InternshipOpportunity> handleViewOpportunities() {
        User currentUser = authenticationService.getCurrentUser();
        
        if (!(currentUser instanceof Student)) {
            throw new RuntimeException("User is not a student");
        }

        Student student = (Student) currentUser;
        
        try {
            return studentService.getEligibleOpportunities(student);
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to retrieve opportunities: " + e.getMessage());
        }
    }

    public void handleApplyForInternship(String opportunityId) {
        User currentUser = authenticationService.getCurrentUser();
        
        if (!(currentUser instanceof Student)) {
            throw new RuntimeException("User is not a student");
        }

        Student student = (Student) currentUser;
        
        try {
            studentService.applyForInternship(student, opportunityId);
        } catch (RuntimeException e) {
            throw new RuntimeException("Application failed: " + e.getMessage());
        }
    }

    public List<Application> handleViewApplications() {
        User currentUser = authenticationService.getCurrentUser();
        
        if (!(currentUser instanceof Student)) {
            throw new RuntimeException("User is not a student");
        }

        Student student = (Student) currentUser;
        
        try {
            return studentService.viewMyApplications(student);
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to retrieve applications: " + e.getMessage());
        }
    }

    public void handleAcceptPlacement(String applicationId) {
        User currentUser = authenticationService.getCurrentUser();
        
        if (!(currentUser instanceof Student)) {
            throw new RuntimeException("User is not a student");
        }

        Student student = (Student) currentUser;
        
        try {
            studentService.acceptPlacement(student, applicationId);
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to accept placement: " + e.getMessage());
        }
    }

    public void handleWithdrawApplication(String applicationId) {
        User currentUser = authenticationService.getCurrentUser();
        
        if (!(currentUser instanceof Student)) {
            throw new RuntimeException("User is not a student");
        }

        Student student = (Student) currentUser;
        
        try {
            studentService.withdrawApplication(student, applicationId);
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to withdraw application: " + e.getMessage());
        }
    }

    public void handleChangePassword(String oldPassword, String newPassword) {
        try {
            authenticationService.changePassword(oldPassword, newPassword);
        } catch (RuntimeException e) {
            throw new RuntimeException("Password change failed: " + e.getMessage());
        }
    }
}
