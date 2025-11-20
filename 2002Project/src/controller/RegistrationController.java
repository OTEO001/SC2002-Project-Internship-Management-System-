package controller;

import service.IRegistrationService;
import entity.CompanyRepresentative;
import java.util.Map;

public class RegistrationController {
    private IRegistrationService registrationService;

    public RegistrationController(IRegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    public CompanyRepresentative handleRegisterCompanyRep(Map<String, String> details) {
        try {
            return registrationService.registerCompanyRep(details);
        } catch (RuntimeException e) {
            throw new RuntimeException("Registration failed: " + e.getMessage());
        }
    }

    public boolean handleCheckPendingStatus(String repId) {
        return registrationService.isPendingApproval(repId);
    }
}
