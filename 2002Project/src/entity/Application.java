package entity;

import java.time.LocalDate;
import java.util.UUID;

public class Application {
    private String applicationId;
    private Student student;
    private InternshipOpportunity opportunity;
    private LocalDate applicationDate;
    private ApplicationStatus status;
    private boolean isAccepted;

    public Application(Student student, InternshipOpportunity opportunity) {
        this.applicationId = UUID.randomUUID().toString().substring(0, 8);
        this.student = student;
        this.opportunity = opportunity;
        this.applicationDate = LocalDate.now();
        this.status = ApplicationStatus.PENDING;
        this.isAccepted = false;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public Student getStudent() {
        return student;
    }

    public InternshipOpportunity getOpportunity() {
        return opportunity;
    }

    public LocalDate getApplicationDate() {
        return applicationDate;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        this.isAccepted = accepted;
    }
}
