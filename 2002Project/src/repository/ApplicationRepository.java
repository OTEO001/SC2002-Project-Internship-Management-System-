package repository;

import entity.*;
import java.util.*;
import java.util.stream.Collectors;

public class ApplicationRepository implements IApplicationRepository {
    private Map<String, Application> applications;

    public ApplicationRepository() {
        this.applications = new HashMap<>();
    }

    @Override
    public void save(Application application) {
        applications.put(application.getApplicationId(), application);
    }

    @Override
    public void update(Application application) {
        applications.put(application.getApplicationId(), application);
    }

    @Override
    public void delete(String applicationId) {
        applications.remove(applicationId);
    }

    @Override
    public Application findById(String applicationId) {
        return applications.get(applicationId);
    }

    @Override
    public List<Application> findAll() {
        return new ArrayList<>(applications.values());
    }

    @Override
    public List<Application> findByStudent(Student student) {
        return applications.values().stream()
                .filter(app -> app.getStudent().getUserId().equals(student.getUserId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Application> findByOpportunity(InternshipOpportunity opportunity) {
        return applications.values().stream()
                .filter(app -> app.getOpportunity().getOpportunityId().equals(opportunity.getOpportunityId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Application> findByStatus(ApplicationStatus status) {
        return applications.values().stream()
                .filter(app -> app.getStatus() == status)
                .collect(Collectors.toList());
    }

    @Override
    public int countByStudent(Student student) {
        return (int) applications.values().stream()
                .filter(app -> app.getStudent().getUserId().equals(student.getUserId()))
                .filter(app -> app.getStatus() == ApplicationStatus.PENDING || 
                              app.getStatus() == ApplicationStatus.APPROVED)
                .count();
    }
}
