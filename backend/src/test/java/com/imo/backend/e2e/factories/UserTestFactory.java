package com.imo.backend.e2e.factories;

import com.imo.backend.e2e.utils.DataFakeFactory;
import com.imo.backend.modules.user.actions.inputs.CreateUserInput;

public class UserTestFactory {

    public static CreateUserInput mountValidUser() {
        String name = DataFakeFactory.generateValidUsername();
        String email = DataFakeFactory.generateValidEmail();
        String password = DataFakeFactory.generateValidPassword();

        return new CreateUserInput(name, email, password, password);
    }

    public static CreateUserInput mountInvalidUser() {
        String password = DataFakeFactory.generateInValidPassword();

        return new CreateUserInput(
                DataFakeFactory.generateValidUsername(),
                DataFakeFactory.generateValidEmail(),
                password,
                password);
    }
}
