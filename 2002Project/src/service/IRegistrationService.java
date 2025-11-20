package service;

import entity.CompanyRepresentative;
import java.util.Map;

public interface IRegistrationService {
    CompanyRepresentative registerCompanyRep(Map<String, String> details);
    boolean isPendingApproval(String repId);
}
