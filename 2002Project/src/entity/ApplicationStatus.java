package entity;

public enum ApplicationStatus {
    PENDING,
    APPROVED,
    REJECTED,
    WITHDRAWN;

    public String getDisplayName() {
        switch (this) {
            case APPROVED:
                return "Successful";
            case REJECTED:
                return "Unsuccessful";
            case PENDING:
                return "Pending";
            case WITHDRAWN:
                return "Withdrawn";
            default:
                return name();
        }
    }

    @Override
    public String toString() {
        return getDisplayName();
    }
}
