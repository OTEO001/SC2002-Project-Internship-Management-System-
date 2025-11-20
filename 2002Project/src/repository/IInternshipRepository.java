package repository;

import entity.InternshipOpportunity;
import entity.CompanyRepresentative;
import entity.OpportunityStatus;
import entity.InternshipLevel;
import java.util.List;

public interface IInternshipRepository {
    void save(InternshipOpportunity opportunity);
    void update(InternshipOpportunity opportunity);
    void delete(String opportunityId);
    InternshipOpportunity findById(String opportunityId);
    List<InternshipOpportunity> findAll();
    List<InternshipOpportunity> findByCompanyRep(CompanyRepresentative rep);
    List<InternshipOpportunity> findByStatus(OpportunityStatus status);
    List<InternshipOpportunity> findVisibleOpportunities();
    List<InternshipOpportunity> findByMajor(String major);
    List<InternshipOpportunity> findByLevel(InternshipLevel level);
}
