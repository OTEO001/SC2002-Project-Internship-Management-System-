package entity;

public enum InternshipLevel {
    BASIC,
    INTERMEDIATE,
    ADVANCED;

    @Override
    public String toString() {
        return name().substring(0, 1).toUpperCase() + name().substring(1).toLowerCase();
    }
}
