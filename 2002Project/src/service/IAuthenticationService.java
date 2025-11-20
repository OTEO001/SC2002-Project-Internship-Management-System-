package service;

import entity.User;

public interface IAuthenticationService {
    User login(String userId, String password);
    void logout();
    void changePassword(String oldPassword, String newPassword);
    boolean validateCredentials(String userId, String password);
    User getCurrentUser();
}
