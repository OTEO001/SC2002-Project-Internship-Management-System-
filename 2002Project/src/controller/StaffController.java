package controller;

import service.IStaffService;
import service.IAuthenticationService;
import entity.*;
import java.util.List;
import java.util.Map;

public class StaffController {
    private IStaffService staffService;
    private IAuthenticationService authenticationService;

    public StaffController(IStaffService staffService, IAuthenticationService authenticationService) {
        this.staffService = staffService;
        this.authenticationService = authenticationService;
    }

    public List<CompanyRepresentative> handleViewPendingReps() {
        User currentUser = authenticationService.getCurrentUser();
        
        if (!(currentUser instanceof CareerCenterStaff)) {
            throw new RuntimeException("User is not a staff member");
        }

        CareerCenterStaff staff = (CareerCenterStaff) currentUser;
        
        try {
            return staffService.getPendingReps(staff);
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to retrieve pending representatives: " + e.getMessage());
        }
    }

    public void handleApproveRep(String repId) {
        User currentUser = authenticationService.getCurrentUser();
        
        if (!(currentUser instanceof CareerCenterStaff)) {
            throw new RuntimeException("User is not a staff member");
        }

        CareerCenterStaff staff = (CareerCenterStaff) currentUser;
        
        try {
            staffService.approveCompanyRep(staff, repId);
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to approve representative: " + e.getMessage());
        }
    }

    public void handleRejectRep(String repId) {
        User currentUser = authenticationService.getCurrentUser();
        
        if (!(currentUser instanceof CareerCenterStaff)) {
            throw new RuntimeException("User is not a staff member");
        }

        CareerCenterStaff staff = (CareerCenterStaff) currentUser;
        
        try {
            staffService.rejectCompanyRep(staff, repId);
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to reject representative: " + e.getMessage());
        }
    }

    public List<InternshipOpportunity> handleViewPendingOpportunities() {
        User currentUser = authenticationService.getCurrentUser();
        
        if (!(currentUser instanceof CareerCenterStaff)) {
            throw new RuntimeException("User is not a staff member");
        }

        CareerCenterStaff staff = (CareerCenterStaff) currentUser;
        
        try {
            return staffService.getPendingOpportunities(staff);
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to retrieve pending opportunities: " + e.getMessage());
        }
    }

    public void handleApproveOpportunity(String opportunityId) {
        User currentUser = authenticationService.getCurrentUser();
        
        if (!(currentUser instanceof CareerCenterStaff)) {
            throw new RuntimeException("User is not a staff member");
        }

        CareerCenterStaff staff = (CareerCenterStaff) currentUser;
        
        try {
            staffService.approveOpportunity(staff, opportunityId);
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to approve opportunity: " + e.getMessage());
        }
    }

    public void handleRejectOpportunity(String opportunityId) {
        User currentUser = authenticationService.getCurrentUser();
        
        if (!(currentUser instanceof CareerCenterStaff)) {
            throw new RuntimeException("User is not a staff member");
        }

        CareerCenterStaff staff = (CareerCenterStaff) currentUser;
        
        try {
            staffService.rejectOpportunity(staff, opportunityId);
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to reject opportunity: " + e.getMessage());
        }
    }

    public List<InternshipOpportunity> handleGenerateReport(Map<String, String> filters) {
        User currentUser = authenticationService.getCurrentUser();
        
        if (!(currentUser instanceof CareerCenterStaff)) {
            throw new RuntimeException("User is not a staff member");
        }

        CareerCenterStaff staff = (CareerCenterStaff) currentUser;
        
        try {
            return staffService.generateReport(staff, filters);
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to generate report: " + e.getMessage());
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
