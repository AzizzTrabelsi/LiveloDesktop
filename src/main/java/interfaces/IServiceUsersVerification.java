package interfaces;

import models.User;
import java.util.List;

public interface IServiceUsersVerification {

    List<User> getUnverifiedUsers();

    void acceptUser(int userId);

    void refuseUser(int id);

    List<User> search(String criteria);

}