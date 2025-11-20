package service;

import entity.Student;
import entity.InternshipOpportunity;
import entity.Application;
import java.util.List;

public interface IStudentService {
    List<InternshipOpportunity> getEligibleOpportunities(Student student);
    Application applyForInternship(Student student, String opportunityId);
    List<Application> viewMyApplications(Student student);
    void acceptPlacement(Student student, String applicationId);
    void withdrawApplication(Student student, String applicationId);
}
