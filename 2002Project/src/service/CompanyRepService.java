package service;

import entity.*;
import repository.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class CompanyRepService implements ICompanyRepService {
    private IInternshipRepository internshipRepository;
    private IApplicationRepository applicationRepository;
    private IUserRepository userRepository;

    public CompanyRepService(IInternshipRepository internshipRepository,
                            IApplicationRepository applicationRepository,
                            IUserRepository userRepository) {
        this.internshipRepository = internshipRepository;
        this.applicationRepository = applicationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public InternshipOpportunity createOpportunity(CompanyRepresentative rep, Map<String, Object> details) {
        // Check max opportunities (5)
        checkMaxOpportunities(rep);

        String title = (String) details.get("title");
        String description = (String) details.get("description");
        InternshipLevel level = (InternshipLevel) details.get("level");
        String preferredMajor = (String) details.get("preferredMajor");
        LocalDate openingDate = (LocalDate) details.get("openingDate");
        LocalDate closingDate = (LocalDate) details.get("closingDate");
        int totalSlots = (int) details.get("totalSlots");

        InternshipOpportunity opportunity = new InternshipOpportunity(
                title, description, level, preferredMajor, openingDate, closingDate,
                rep.getCompanyName(), rep, totalSlots
        );

        internshipRepository.save(opportunity);
        rep.addOpportunity(opportunity);
        
        return opportunity;
    }

    @Override
    public void editOpportunity(CompanyRepresentative rep, String opportunityId, Map<String, Object> details) {
        InternshipOpportunity opportunity = internshipRepository.findById(opportunityId);
        
        if (opportunity == null) {
            throw new RuntimeException("Internship opportunity not found");
        }

        validateOpportunityOwnership(rep, opportunity);
        checkCanEdit(opportunity);

        // Update fields
        if (details.containsKey("title")) {
            opportunity.setTitle((String) details.get("title"));
        }
        if (details.containsKey("description")) {
            opportunity.setDescription((String) details.get("description"));
        }
        if (details.containsKey("level")) {
            opportunity.setLevel((InternshipLevel) details.get("level"));
        }
        if (details.containsKey("preferredMajor")) {
            opportunity.setPreferredMajor((String) details.get("preferredMajor"));
        }
        if (details.containsKey("totalSlots")) {
            opportunity.setTotalSlots((int) details.get("totalSlots"));
        }

        internshipRepository.update(opportunity);
    }

    @Override
    public void deleteOpportunity(CompanyRepresentative rep, String opportunityId) {
        InternshipOpportunity opportunity = internshipRepository.findById(opportunityId);
        
        if (opportunity == null) {
            throw new RuntimeException("Internship opportunity not found");
        }

        validateOpportunityOwnership(rep, opportunity);
        checkCanEdit(opportunity);

        internshipRepository.delete(opportunityId);
    }

    @Override
    public void toggleVisibility(CompanyRepresentative rep, String opportunityId) {
        InternshipOpportunity opportunity = internshipRepository.findById(opportunityId);
        
        if (opportunity == null) {
            throw new RuntimeException("Internship opportunity not found");
        }

        validateOpportunityOwnership(rep, opportunity);

        opportunity.setVisible(!opportunity.isVisible());
        internshipRepository.update(opportunity);
    }

    @Override
    public List<InternshipOpportunity> getMyOpportunities(CompanyRepresentative rep) {
        return internshipRepository.findByCompanyRep(rep);
    }

    @Override
    public List<Application> viewApplications(CompanyRepresentative rep, String opportunityId) {
        InternshipOpportunity opportunity = internshipRepository.findById(opportunityId);
        
        if (opportunity == null) {
            throw new RuntimeException("Internship opportunity not found");
        }

        validateOpportunityOwnership(rep, opportunity);

        return applicationRepository.findByOpportunity(opportunity);
    }

    @Override
    public void processApplication(CompanyRepresentative rep, String applicationId, boolean approve) {
        Application application = applicationRepository.findById(applicationId);
        
        if (application == null) {
            throw new RuntimeException("Application not found");
        }

        validateOpportunityOwnership(rep, application.getOpportunity());

        if (application.getStatus() != ApplicationStatus.PENDING) {
            throw new RuntimeException("Application has already been processed");
        }

        if (approve) {
            // Check if slots are available
            InternshipOpportunity opp = application.getOpportunity();
            if (opp.getFilledSlots() >= opp.getTotalSlots()) {
                throw new RuntimeException("No slots available");
            }
            application.setStatus(ApplicationStatus.APPROVED);
        } else {
            application.setStatus(ApplicationStatus.REJECTED);
        }

        applicationRepository.update(application);
        updateOpportunityStatus(application.getOpportunity());
    }

    private void validateOpportunityOwnership(CompanyRepresentative rep, InternshipOpportunity opportunity) {
        if (!opportunity.getCompanyRep().getUserId().equals(rep.getUserId())) {
            throw new RuntimeException("You do not own this internship opportunity");
        }
    }

    private void checkMaxOpportunities(CompanyRepresentative rep) {
        List<InternshipOpportunity> opportunities = internshipRepository.findByCompanyRep(rep);
        if (opportunities.size() >= 5) {
            throw new RuntimeException("You have reached the maximum of 5 internship opportunities");
        }
    }

    private void checkCanEdit(InternshipOpportunity opportunity) {
        if (opportunity.getStatus() != OpportunityStatus.PENDING) {
            throw new RuntimeException("You can only edit pending internship opportunities");
        }
    }

    private void updateOpportunityStatus(InternshipOpportunity opportunity) {
        if (opportunity.getFilledSlots() >= opportunity.getTotalSlots()) {
            opportunity.setStatus(OpportunityStatus.FILLED);
            internshipRepository.update(opportunity);
        }
    }
}
