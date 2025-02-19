package interfaces;

import models.User;

public interface IServiceAuth {
    String login(String cin, String password);
}
