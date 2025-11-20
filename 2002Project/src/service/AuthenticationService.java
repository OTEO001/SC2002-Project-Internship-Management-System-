package service;

import entity.User;
import entity.CompanyRepresentative;
import repository.IUserRepository;

public class AuthenticationService implements IAuthenticationService {
    private IUserRepository userRepository;
    private User currentUser;

    public AuthenticationService(IUserRepository userRepository) {
        this.userRepository = userRepository;
        this.currentUser = null;
    }

    @Override
    public User login(String userId, String password) {
        User user = userRepository.findById(userId);
        
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        // Check if company rep is approved
        if (user instanceof CompanyRepresentative) {
            CompanyRepresentative rep = (CompanyRepresentative) user;
            if (!rep.isApproved()) {
                throw new RuntimeException("Your account is pending approval from Career Center Staff");
            }
        }

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Incorrect password");
        }

        this.currentUser = user;
        return user;
    }

    @Override
    public void logout() {
        this.currentUser = null;
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        if (currentUser == null) {
            throw new RuntimeException("No user logged in");
        }

        if (!currentUser.getPassword().equals(oldPassword)) {
            throw new RuntimeException("Incorrect old password");
        }

        validatePasswordStrength(newPassword);

        currentUser.setPassword(newPassword);
        userRepository.update(currentUser);
    }

    @Override
    public boolean validateCredentials(String userId, String password) {
        User user = userRepository.findById(userId);
        return user != null && user.getPassword().equals(password);
    }

    public User getCurrentUser() {
        return currentUser;
    }

    private boolean validateUserIdFormat(String userId) {
        return userId != null && !userId.trim().isEmpty();
    }

    private boolean validatePasswordStrength(String password) {
        return password != null && password.length() >= 6;
    }
}
