package service;

import entity.CompanyRepresentative;
import repository.IUserRepository;
import repository.IPendingRepRepository;
import java.util.Map;
import java.util.List;

public class RegistrationService implements IRegistrationService {
    private IUserRepository userRepository;
    private IPendingRepRepository pendingRepRepository;

    public RegistrationService(IUserRepository userRepository, IPendingRepRepository pendingRepRepository) {
        this.userRepository = userRepository;
        this.pendingRepRepository = pendingRepRepository;
    }

    @Override
    public CompanyRepresentative registerCompanyRep(Map<String, String> details) {
        String email = details.get("email");
        String name = details.get("name");
        String password = details.get("password");
        String company = details.get("company");
        String position = details.get("position");
        String department = details.get("department");

        // Validate email
        if (!validateEmail(email)) {
            throw new RuntimeException("Invalid email format");
        }

        // Check duplicate email
        checkDuplicateEmail(email);

        // Create company representative (not approved)
        CompanyRepresentative rep = new CompanyRepresentative(
                email, name, password, email, company, position, department
        );

        // Add to pending repository
        pendingRepRepository.add(rep);

        return rep;
    }

    @Override
    public boolean isPendingApproval(String repId) {
        List<CompanyRepresentative> pendingReps = pendingRepRepository.findAll();
        return pendingReps.stream()
                .anyMatch(rep -> rep.getUserId().equals(repId));
    }

    private boolean validateEmail(String email) {
        return email != null && email.contains("@") && email.contains(".");
    }

    private void checkDuplicateEmail(String email) {
        if (userRepository.findByEmail(email) != null) {
            throw new RuntimeException("Email already registered");
        }
    }
}
