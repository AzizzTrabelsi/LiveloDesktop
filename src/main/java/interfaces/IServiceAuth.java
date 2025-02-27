package interfaces;

import models.User;

public interface IServiceAuth {
    String login(String cin, String password);
    boolean forgotPassword(String email);


    boolean resetPassword(String email, String code, String newPassword);
}
