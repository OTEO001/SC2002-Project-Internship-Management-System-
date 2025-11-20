package entity;

public class CareerCenterStaff extends User {
    private String staffDepartment;

    public CareerCenterStaff(String userId, String name, String password, String email, String department) {
        super(userId, name, password, email);
        this.staffDepartment = department;
    }

    public String getStaffDepartment() {
        return staffDepartment;
    }

    @Override
    public String getRole() {
        return "Career Center Staff";
    }

    @Override
    public boolean validateUserId() {
        // Staff ID is NTU account format
        return userId != null && userId.contains("@ntu.edu.sg");
    }
}
