package controller;

import service.IAuthenticationService;
import entity.User;

public class AuthenticationController {
    private IAuthenticationService authenticationService;

    public AuthenticationController(IAuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    public User handleLogin(String userId, String password) {
        try {
            return authenticationService.login(userId, password);
        } catch (RuntimeException e) {
            throw new RuntimeException("Login failed: " + e.getMessage());
        }
    }

    public void handleLogout() {
        authenticationService.logout();
    }

    public void handleChangePassword(String oldPassword, String newPassword) {
        try {
            authenticationService.changePassword(oldPassword, newPassword);
        } catch (RuntimeException e) {
            throw new RuntimeException("Password change failed: " + e.getMessage());
        }
    }
}
