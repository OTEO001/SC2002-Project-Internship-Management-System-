package controller;

import service.ICompanyRepService;
import service.IAuthenticationService;
import entity.*;
import java.util.List;
import java.util.Map;

public class CompanyRepController {
    private ICompanyRepService companyRepService;
    private IAuthenticationService authenticationService;

    public CompanyRepController(ICompanyRepService companyRepService, IAuthenticationService authenticationService) {
        this.companyRepService = companyRepService;
        this.authenticationService = authenticationService;
    }

    public void handleCreateOpportunity(Map<String, Object> details) {
        User currentUser = authenticationService.getCurrentUser();
        
        if (!(currentUser instanceof CompanyRepresentative)) {
            throw new RuntimeException("User is not a company representative");
        }

        CompanyRepresentative rep = (CompanyRepresentative) currentUser;
        
        try {
            companyRepService.createOpportunity(rep, details);
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to create opportunity: " + e.getMessage());
        }
    }

    public void handleEditOpportunity(String opportunityId, Map<String, Object> details) {
        User currentUser = authenticationService.getCurrentUser();
        
        if (!(currentUser instanceof CompanyRepresentative)) {
            throw new RuntimeException("User is not a company representative");
        }

        CompanyRepresentative rep = (CompanyRepresentative) currentUser;
        
        try {
            companyRepService.editOpportunity(rep, opportunityId, details);
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to edit opportunity: " + e.getMessage());
        }
    }

    public void handleDeleteOpportunity(String opportunityId) {
        User currentUser = authenticationService.getCurrentUser();
        
        if (!(currentUser instanceof CompanyRepresentative)) {
            throw new RuntimeException("User is not a company representative");
        }

        CompanyRepresentative rep = (CompanyRepresentative) currentUser;
        
        try {
            companyRepService.deleteOpportunity(rep, opportunityId);
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to delete opportunity: " + e.getMessage());
        }
    }

    public void handleToggleVisibility(String opportunityId) {
        User currentUser = authenticationService.getCurrentUser();
        
        if (!(currentUser instanceof CompanyRepresentative)) {
            throw new RuntimeException("User is not a company representative");
        }

        CompanyRepresentative rep = (CompanyRepresentative) currentUser;
        
        try {
            companyRepService.toggleVisibility(rep, opportunityId);
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to toggle visibility: " + e.getMessage());
        }
    }

    public List<InternshipOpportunity> handleViewMyOpportunities() {
        User currentUser = authenticationService.getCurrentUser();
        
        if (!(currentUser instanceof CompanyRepresentative)) {
            throw new RuntimeException("User is not a company representative");
        }

        CompanyRepresentative rep = (CompanyRepresentative) currentUser;
        
        try {
            return companyRepService.getMyOpportunities(rep);
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to retrieve opportunities: " + e.getMessage());
        }
    }

    public List<Application> handleViewApplications(String opportunityId) {
        User currentUser = authenticationService.getCurrentUser();
        
        if (!(currentUser instanceof CompanyRepresentative)) {
            throw new RuntimeException("User is not a company representative");
        }

        CompanyRepresentative rep = (CompanyRepresentative) currentUser;
        
        try {
            return companyRepService.viewApplications(rep, opportunityId);
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to retrieve applications: " + e.getMessage());
        }
    }

    public void handleProcessApplication(String applicationId, boolean approve) {
        User currentUser = authenticationService.getCurrentUser();
        
        if (!(currentUser instanceof CompanyRepresentative)) {
            throw new RuntimeException("User is not a company representative");
        }

        CompanyRepresentative rep = (CompanyRepresentative) currentUser;
        
        try {
            companyRepService.processApplication(rep, applicationId, approve);
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to process application: " + e.getMessage());
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
