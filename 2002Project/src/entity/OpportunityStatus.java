package entity;

public enum OpportunityStatus {
    PENDING,
    APPROVED,
    REJECTED,
    FILLED;

    @Override
    public String toString() {
        return name().substring(0, 1).toUpperCase() + name().substring(1).toLowerCase();
    }
}
