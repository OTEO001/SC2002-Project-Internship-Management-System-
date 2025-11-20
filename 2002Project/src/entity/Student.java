package entity;

import java.util.ArrayList;
import java.util.List;

public class Student extends User {
    private String major;
    private int yearOfStudy;
    private List<Application> applications;
    private InternshipOpportunity acceptedInternship;

    public Student(String userId, String name, String password, String email, String major, int yearOfStudy) {
        super(userId, name, password, email);
        this.major = major;
        this.yearOfStudy = yearOfStudy;
        this.applications = new ArrayList<>();
        this.acceptedInternship = null;
    }

    public String getMajor() {
        return major;
    }

    public int getYearOfStudy() {
        return yearOfStudy;
    }

    public List<Application> getApplications() {
        return applications;
    }

    public void addApplication(Application application) {
        this.applications.add(application);
    }

    public InternshipOpportunity getAcceptedInternship() {
        return acceptedInternship;
    }

    public void setAcceptedInternship(InternshipOpportunity internship) {
        this.acceptedInternship = internship;
    }

    @Override
    public String getRole() {
        return "Student";
    }

    @Override
    public boolean validateUserId() {
        // Student ID format: U1234567A
        return userId != null && userId.matches("U\\d{7}[A-Z]");
    }
}
