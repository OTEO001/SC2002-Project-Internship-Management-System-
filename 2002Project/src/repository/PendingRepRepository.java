package repository;

import entity.CompanyRepresentative;
import java.util.*;

public class PendingRepRepository implements IPendingRepRepository {
    private List<CompanyRepresentative> pendingReps;

    public PendingRepRepository() {
        this.pendingReps = new ArrayList<>();
    }

    @Override
    public void add(CompanyRepresentative rep) {
        if (!exists(rep)) {
            pendingReps.add(rep);
        }
    }

    @Override
    public void remove(CompanyRepresentative rep) {
        pendingReps.removeIf(r -> r.getUserId().equals(rep.getUserId()));
    }

    @Override
    public List<CompanyRepresentative> findAll() {
        return new ArrayList<>(pendingReps);
    }

    @Override
    public boolean exists(CompanyRepresentative rep) {
        return pendingReps.stream()
                .anyMatch(r -> r.getUserId().equals(rep.getUserId()));
    }
}
