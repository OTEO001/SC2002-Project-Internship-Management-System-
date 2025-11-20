package entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InternshipOpportunity {
    private String opportunityId;
    private String title;
    private String description;
    private InternshipLevel level;
    private String preferredMajor;
    private LocalDate openingDate;
    private LocalDate closingDate;
    private OpportunityStatus status;
    private String companyName;
    private CompanyRepresentative companyRep;
    private int totalSlots;
    private int filledSlots;
    private boolean isVisible;
    private List<Application> applications;

    public InternshipOpportunity(String title, String description, InternshipLevel level, String preferredMajor,
                                  LocalDate openingDate, LocalDate closingDate, String companyName,
                                  CompanyRepresentative companyRep, int totalSlots) {
        this.opportunityId = UUID.randomUUID().toString().substring(0, 8);
        this.title = title;
        this.description = description;
        this.level = level;
        this.preferredMajor = preferredMajor;
        this.openingDate = openingDate;
        this.closingDate = closingDate;
        this.status = OpportunityStatus.PENDING;
        this.companyName = companyName;
        this.companyRep = companyRep;
        this.totalSlots = totalSlots;
        this.filledSlots = 0;
        this.isVisible = true;
        this.applications = new ArrayList<>();
    }

    public String getOpportunityId() {
        return opportunityId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public InternshipLevel getLevel() {
        return level;
    }

    public void setLevel(InternshipLevel level) {
        this.level = level;
    }

    public String getPreferredMajor() {
        return preferredMajor;
    }

    public void setPreferredMajor(String preferredMajor) {
        this.preferredMajor = preferredMajor;
    }

    public OpportunityStatus getStatus() {
        return status;
    }

    public void setStatus(OpportunityStatus status) {
        this.status = status;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        this.isVisible = visible;
    }

    public int getTotalSlots() {
        return totalSlots;
    }

    public void setTotalSlots(int totalSlots) {
        this.totalSlots = totalSlots;
    }

    public int getFilledSlots() {
        return filledSlots;
    }

    public void incrementFilledSlots() {
        this.filledSlots++;
        if (this.filledSlots >= this.totalSlots) {
            this.status = OpportunityStatus.FILLED;
        }
    }

    public void decrementFilledSlots() {
        if (this.filledSlots > 0) {
            this.filledSlots--;
        }
        if (this.status == OpportunityStatus.FILLED && this.filledSlots < this.totalSlots) {
            this.status = OpportunityStatus.APPROVED;
        }
    }

    public CompanyRepresentative getCompanyRep() {
        return companyRep;
    }

    public String getCompanyName() {
        return companyName;
    }

    public LocalDate getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(LocalDate openingDate) {
        this.openingDate = openingDate;
    }

    public LocalDate getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(LocalDate closingDate) {
        this.closingDate = closingDate;
    }

    public List<Application> getApplications() {
        return applications;
    }

    public void addApplication(Application application) {
        this.applications.add(application);
    }
}
