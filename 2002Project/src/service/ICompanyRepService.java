package service;

import entity.CompanyRepresentative;
import entity.InternshipOpportunity;
import entity.Application;
import java.util.List;
import java.util.Map;

public interface ICompanyRepService {
    InternshipOpportunity createOpportunity(CompanyRepresentative rep, Map<String, Object> details);
    void editOpportunity(CompanyRepresentative rep, String opportunityId, Map<String, Object> details);
    void deleteOpportunity(CompanyRepresentative rep, String opportunityId);
    void toggleVisibility(CompanyRepresentative rep, String opportunityId);
    List<InternshipOpportunity> getMyOpportunities(CompanyRepresentative rep);
    List<Application> viewApplications(CompanyRepresentative rep, String opportunityId);
    void processApplication(CompanyRepresentative rep, String applicationId, boolean approve);
}
