package entity;

import java.util.ArrayList;
import java.util.List;

public class CompanyRepresentative extends User {
    private String companyName;
    private String position;
    private String department;
    private boolean isApproved;
    private List<InternshipOpportunity> opportunities;

    public CompanyRepresentative(String userId, String name, String password, String email,
                                  String companyName, String position, String department) {
        super(userId, name, password, email);
        this.companyName = companyName;
        this.position = position;
        this.department = department;
        this.isApproved = false;
        this.opportunities = new ArrayList<>();
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getPosition() {
        return position;
    }

    public String getDepartment() {
        return department;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        this.isApproved = approved;
    }

    public List<InternshipOpportunity> getOpportunities() {
        return opportunities;
    }

    public void addOpportunity(InternshipOpportunity opportunity) {
        this.opportunities.add(opportunity);
    }

    @Override
    public String getRole() {
        return "Company Representative";
    }

    @Override
    public boolean validateUserId() {
        // Company Rep ID is email format
        return userId != null && userId.contains("@") && userId.contains(".");
    }
}
