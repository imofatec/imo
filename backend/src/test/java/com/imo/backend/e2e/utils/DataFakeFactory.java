package com.imo.backend.e2e.utils;

import net.datafaker.Faker;

public class DataFakeFactory {

    private static Faker faker = new Faker(); 

    public static String generateValidUsername(){
        return faker.name().firstName() + faker.name().lastName();
    }

    public static String generateValidPassword(){
        return faker.internet().password(8, 50, true, true, true);
    }

    public static String generateInValidPassword(){
        return faker.name().firstName() + faker.name().lastName();
    }

    public static String generateValidEmail(){
        return faker.internet().emailAddress();
    }

    public static String generateInvalidEmail(){
        return faker.internet().password(8,
        50,false,false,true);
    }

}
