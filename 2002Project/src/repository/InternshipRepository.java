package repository;

import entity.*;
import java.util.*;
import java.util.stream.Collectors;

public class InternshipRepository implements IInternshipRepository {
    private Map<String, InternshipOpportunity> opportunities;

    public InternshipRepository() {
        this.opportunities = new HashMap<>();
    }

    @Override
    public void save(InternshipOpportunity opportunity) {
        opportunities.put(opportunity.getOpportunityId(), opportunity);
    }

    @Override
    public void update(InternshipOpportunity opportunity) {
        opportunities.put(opportunity.getOpportunityId(), opportunity);
    }

    @Override
    public void delete(String opportunityId) {
        opportunities.remove(opportunityId);
    }

    @Override
    public InternshipOpportunity findById(String opportunityId) {
        return opportunities.get(opportunityId);
    }

    @Override
    public List<InternshipOpportunity> findAll() {
        return new ArrayList<>(opportunities.values());
    }

    @Override
    public List<InternshipOpportunity> findByCompanyRep(CompanyRepresentative rep) {
        return opportunities.values().stream()
                .filter(opp -> opp.getCompanyRep().getUserId().equals(rep.getUserId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<InternshipOpportunity> findByStatus(OpportunityStatus status) {
        return opportunities.values().stream()
                .filter(opp -> opp.getStatus() == status)
                .collect(Collectors.toList());
    }

    @Override
    public List<InternshipOpportunity> findVisibleOpportunities() {
        return opportunities.values().stream()
                .filter(InternshipOpportunity::isVisible)
                .filter(opp -> opp.getStatus() == OpportunityStatus.APPROVED)
                .collect(Collectors.toList());
    }

    @Override
    public List<InternshipOpportunity> findByMajor(String major) {
        return opportunities.values().stream()
                .filter(opp -> opp.getPreferredMajor() == null || 
                              opp.getPreferredMajor().isEmpty() || 
                              opp.getPreferredMajor().equalsIgnoreCase(major))
                .collect(Collectors.toList());
    }

    @Override
    public List<InternshipOpportunity> findByLevel(InternshipLevel level) {
        return opportunities.values().stream()
                .filter(opp -> opp.getLevel() == level)
                .collect(Collectors.toList());
    }
}
