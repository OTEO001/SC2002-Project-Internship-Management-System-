package repository;

import entity.User;
import entity.Student;
import entity.CompanyRepresentative;
import entity.CareerCenterStaff;
import java.util.List;

public interface IUserRepository {
    void save(User user);
    void update(User user);
    void delete(String userId);
    User findById(String userId);
    User findByEmail(String email);
    List<User> findAll();
    List<Student> findAllStudents();
    List<CompanyRepresentative> findAllCompanyReps();
    List<CareerCenterStaff> findAllStaff();
    boolean existsById(String userId);
}
