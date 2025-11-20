package repository;

import entity.*;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class UserRepository implements IUserRepository {
    private Map<String, User> users;
    private String dataFilePath;

    public UserRepository(String filePath) {
        this.users = new HashMap<>();
        this.dataFilePath = filePath;
        loadFromCSV();
    }

    public void loadFromCSV() {
        // Load users from individual CSV files
        loadStudentsFromCSV("data/sample_student_list.csv");
        loadStaffFromCSV("data/sample_staff_list.csv");
        loadCompanyRepsFromCSV("data/sample_company_representative_list.csv");
        System.out.println("Loaded " + users.size() + " users from individual CSV files.");
    }

    private void loadStudentsFromCSV(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("Student data file not found: " + filePath);
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip header
                }
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    String userId = parts[0].trim();
                    String name = parts[1].trim();
                    String major = parts[2].trim();
                    int year = Integer.parseInt(parts[3].trim());
                    String email = parts[4].trim();
                    Student student = new Student(userId, name, "password123", email, major, year);
                    users.put(userId, student);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading student data: " + e.getMessage());
        }
    }

    private void loadStaffFromCSV(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("Staff data file not found: " + filePath);
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip header
                }
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    String userId = parts[0].trim();
                    String name = parts[1].trim();
                    String role = parts[2].trim();
                    String department = parts[3].trim();
                    String email = parts[4].trim();
                    CareerCenterStaff staff = new CareerCenterStaff(email, name, "password123", email, department);
                    users.put(email, staff);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading staff data: " + e.getMessage());
        }
    }

    private void loadCompanyRepsFromCSV(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("Company representative data file not found: " + filePath);
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip header
                }
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(",");
                if (parts.length >= 7) {
                    String userId = parts[0].trim();
                    String name = parts[1].trim();
                    String companyName = parts[2].trim();
                    String department = parts[3].trim();
                    String position = parts[4].trim();
                    String email = parts[5].trim();
                    String status = parts[6].trim();
                    CompanyRepresentative rep = new CompanyRepresentative(userId, name, "password123", email, companyName, position, department);
                    rep.setApproved(status.equalsIgnoreCase("Approved") || status.equalsIgnoreCase("approved") || status.equalsIgnoreCase("TRUE"));
                    users.put(userId, rep);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading company representative data: " + e.getMessage());
        }
    }

    public void saveToCSV() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(dataFilePath))) {
            pw.println("UserID,Name,Password,Email,Role,Extra1,Extra2,Extra3,Extra4");
            
            for (User user : users.values()) {
                if (user instanceof Student) {
                    Student s = (Student) user;
                    pw.printf("%s,%s,%s,%s,Student,%s,%d%n",
                            s.getUserId(), s.getName(), s.getPassword(), s.getEmail(),
                            s.getMajor(), s.getYearOfStudy());
                } else if (user instanceof CompanyRepresentative) {
                    CompanyRepresentative rep = (CompanyRepresentative) user;
                    pw.printf("%s,%s,%s,%s,CompanyRep,%s,%s,%s,%b%n",
                            rep.getUserId(), rep.getName(), rep.getPassword(), rep.getEmail(),
                            rep.getCompanyName(), rep.getPosition(), rep.getDepartment(), rep.isApproved());
                } else if (user instanceof CareerCenterStaff) {
                    CareerCenterStaff staff = (CareerCenterStaff) user;
                    pw.printf("%s,%s,%s,%s,Staff,%s%n",
                            staff.getUserId(), staff.getName(), staff.getPassword(), staff.getEmail(),
                            staff.getStaffDepartment());
                }
            }
        } catch (IOException e) {
            System.err.println("Error saving user data: " + e.getMessage());
        }
    }

    @Override
    public void save(User user) {
        users.put(user.getUserId(), user);
        saveToCSV();
    }

    @Override
    public void update(User user) {
        if (users.containsKey(user.getUserId())) {
            users.put(user.getUserId(), user);
            saveToCSV();
        }
    }

    @Override
    public void delete(String userId) {
        users.remove(userId);
        saveToCSV();
    }

    @Override
    public User findById(String userId) {
        return users.get(userId);
    }

    @Override
    public User findByEmail(String email) {
        return users.values().stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public List<Student> findAllStudents() {
        return users.values().stream()
                .filter(u -> u instanceof Student)
                .map(u -> (Student) u)
                .collect(Collectors.toList());
    }

    @Override
    public List<CompanyRepresentative> findAllCompanyReps() {
        return users.values().stream()
                .filter(u -> u instanceof CompanyRepresentative)
                .map(u -> (CompanyRepresentative) u)
                .collect(Collectors.toList());
    }

    @Override
    public List<CareerCenterStaff> findAllStaff() {
        return users.values().stream()
                .filter(u -> u instanceof CareerCenterStaff)
                .map(u -> (CareerCenterStaff) u)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsById(String userId) {
        return users.containsKey(userId);
    }
}
