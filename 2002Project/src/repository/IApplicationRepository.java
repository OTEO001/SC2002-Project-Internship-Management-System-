package repository;

import entity.Application;
import entity.Student;
import entity.InternshipOpportunity;
import entity.ApplicationStatus;
import java.util.List;

public interface IApplicationRepository {
    void save(Application application);
    void update(Application application);
    void delete(String applicationId);
    Application findById(String applicationId);
    List<Application> findAll();
    List<Application> findByStudent(Student student);
    List<Application> findByOpportunity(InternshipOpportunity opportunity);
    List<Application> findByStatus(ApplicationStatus status);
    int countByStudent(Student student);
}
