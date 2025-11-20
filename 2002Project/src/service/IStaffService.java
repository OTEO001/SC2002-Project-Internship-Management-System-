package service;

import entity.CareerCenterStaff;
import entity.CompanyRepresentative;
import entity.InternshipOpportunity;
import java.util.List;
import java.util.Map;

public interface IStaffService {
    void approveCompanyRep(CareerCenterStaff staff, String repId);
    void rejectCompanyRep(CareerCenterStaff staff, String repId);
    List<CompanyRepresentative> getPendingReps(CareerCenterStaff staff);
    void approveOpportunity(CareerCenterStaff staff, String opportunityId);
    void rejectOpportunity(CareerCenterStaff staff, String opportunityId);
    List<InternshipOpportunity> getPendingOpportunities(CareerCenterStaff staff);
    List<InternshipOpportunity> generateReport(CareerCenterStaff staff, Map<String, String> filters);
}
