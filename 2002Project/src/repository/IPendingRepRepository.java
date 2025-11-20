package repository;

import entity.CompanyRepresentative;
import java.util.List;

public interface IPendingRepRepository {
    void add(CompanyRepresentative rep);
    void remove(CompanyRepresentative rep);
    List<CompanyRepresentative> findAll();
    boolean exists(CompanyRepresentative rep);
}
