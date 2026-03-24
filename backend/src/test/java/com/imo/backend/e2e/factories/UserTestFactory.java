package com.imo.backend.e2e.factories;

import com.imo.backend.contexts.identity.user.commands.CreateUserCommand;
import com.imo.backend.e2e.utils.DataFakeFactory;

public class UserTestFactory {

  public static CreateUserCommand mountValidUser() {
    String name = DataFakeFactory.generateValidUsername();
    String email = DataFakeFactory.generateValidEmail();
    String password = DataFakeFactory.generateValidPassword();

    return new CreateUserCommand(name, email, password, password);
  }

  public static CreateUserCommand mountInvalidUser() {
    String password = DataFakeFactory.generateInValidPassword();

    return new CreateUserCommand(
        DataFakeFactory.generateValidUsername(),
        DataFakeFactory.generateValidEmail(),
        password,
        password
    );
  }
}
