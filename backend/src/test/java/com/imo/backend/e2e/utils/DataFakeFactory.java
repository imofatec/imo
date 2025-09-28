package com.imo.backend.e2e.utils;

import net.datafaker.Faker;

public class DataFakeFactory {

    private static Faker faker = new Faker();

    public static String generateValidUsername() {
        return faker.name().firstName() + faker.name().lastName();
    }

    public static String generateValidPassword() {
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
                50, false, false, true);
    }

    public static String generateValidTitle(){
        return faker.lorem().characters(10, 50);
    }

    public static String generateValidDescription(){
        return faker.educator().course();
    }

    public static String generateValidYoutubeLink(){
        String videoId = faker.regexify("[\\w-]{11}");
        return "https://www.youtube.com/watch?v=" + videoId;
    }

    public static String generateValidCourseName(){
        return faker.book().title();
    }

    public static String generateValidCourseCategory(){
        return faker.educator().course();
    }

    public static String generateValidCourseLevel(){
        return faker.options().option("Iniciante", "Intermediario", "Avancado");
    }

    public static String generateValidCourseDescription(){
        return faker.lorem().characters(10,300);
    }

}
