package service;

import entity.*;
import repository.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Comparator;

public class StaffService implements IStaffService {
    private IUserRepository userRepository;
    private IInternshipRepository internshipRepository;
    private IApplicationRepository applicationRepository;
    private IPendingRepRepository pendingRepRepository;

    public StaffService(IUserRepository userRepository,
                       IInternshipRepository internshipRepository,
                       IApplicationRepository applicationRepository,
                       IPendingRepRepository pendingRepRepository) {
        this.userRepository = userRepository;
        this.internshipRepository = internshipRepository;
        this.applicationRepository = applicationRepository;
        this.pendingRepRepository = pendingRepRepository;
    }

    @Override
    public void approveCompanyRep(CareerCenterStaff staff, String repId) {
        List<CompanyRepresentative> pendingReps = pendingRepRepository.findAll();
        CompanyRepresentative rep = pendingReps.stream()
                .filter(r -> r.getUserId().equals(repId))
                .findFirst()
                .orElse(null);

        if (rep == null) {
            throw new RuntimeException("Company representative not found in pending list");
        }

        rep.setApproved(true);
        userRepository.save(rep);
        pendingRepRepository.remove(rep);
    }

    @Override
    public void rejectCompanyRep(CareerCenterStaff staff, String repId) {
        List<CompanyRepresentative> pendingReps = pendingRepRepository.findAll();
        CompanyRepresentative rep = pendingReps.stream()
                .filter(r -> r.getUserId().equals(repId))
                .findFirst()
                .orElse(null);

        if (rep == null) {
            throw new RuntimeException("Company representative not found in pending list");
        }

        pendingRepRepository.remove(rep);
    }

    @Override
    public List<CompanyRepresentative> getPendingReps(CareerCenterStaff staff) {
        return pendingRepRepository.findAll();
    }

    @Override
    public void approveOpportunity(CareerCenterStaff staff, String opportunityId) {
        InternshipOpportunity opportunity = internshipRepository.findById(opportunityId);
        
        if (opportunity == null) {
            throw new RuntimeException("Internship opportunity not found");
        }

        if (opportunity.getStatus() != OpportunityStatus.PENDING) {
            throw new RuntimeException("Opportunity is not pending approval");
        }

        opportunity.setStatus(OpportunityStatus.APPROVED);
        internshipRepository.update(opportunity);
    }

    @Override
    public void rejectOpportunity(CareerCenterStaff staff, String opportunityId) {
        InternshipOpportunity opportunity = internshipRepository.findById(opportunityId);
        
        if (opportunity == null) {
            throw new RuntimeException("Internship opportunity not found");
        }

        if (opportunity.getStatus() != OpportunityStatus.PENDING) {
            throw new RuntimeException("Opportunity is not pending approval");
        }

        opportunity.setStatus(OpportunityStatus.REJECTED);
        internshipRepository.update(opportunity);
    }

    @Override
    public List<InternshipOpportunity> getPendingOpportunities(CareerCenterStaff staff) {
        return internshipRepository.findByStatus(OpportunityStatus.PENDING);
    }

    @Override
    public List<InternshipOpportunity> generateReport(CareerCenterStaff staff, Map<String, String> filters) {
        List<InternshipOpportunity> opportunities = internshipRepository.findAll();
        
        // Apply filters
        opportunities = applyFilters(opportunities, filters);
        
        // Sort alphabetically by title
        opportunities = sortAlphabetically(opportunities);
        
        return opportunities;
    }

    private List<InternshipOpportunity> applyFilters(List<InternshipOpportunity> opportunities, Map<String, String> filters) {
        if (filters == null || filters.isEmpty()) {
            return opportunities;
        }

        // Filter by company
        if (filters.containsKey("company")) {
            String company = filters.get("company");
            opportunities = opportunities.stream()
                    .filter(opp -> opp.getCompanyName().equalsIgnoreCase(company))
                    .collect(Collectors.toList());
        }

        // Filter by level
        if (filters.containsKey("level")) {
            String levelStr = filters.get("level");
            InternshipLevel level = InternshipLevel.valueOf(levelStr.toUpperCase());
            opportunities = opportunities.stream()
                    .filter(opp -> opp.getLevel() == level)
                    .collect(Collectors.toList());
        }

        // Filter by status
        if (filters.containsKey("status")) {
            String statusStr = filters.get("status");
            OpportunityStatus status = OpportunityStatus.valueOf(statusStr.toUpperCase());
            opportunities = opportunities.stream()
                    .filter(opp -> opp.getStatus() == status)
                    .collect(Collectors.toList());
        }

        return opportunities;
    }

    private List<InternshipOpportunity> sortAlphabetically(List<InternshipOpportunity> opportunities) {
        return opportunities.stream()
                .sorted(Comparator.comparing(InternshipOpportunity::getTitle))
                .collect(Collectors.toList());
    }
}
