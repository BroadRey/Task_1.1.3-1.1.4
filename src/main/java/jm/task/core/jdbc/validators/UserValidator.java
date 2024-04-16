package jm.task.core.jdbc.validators;

public class UserValidator {
    public static boolean isValidUser(String name, String lastName, byte age) {
        return isValidName(name) && isValidName(lastName) && isValidAge(age);
    }

    private static boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty();
    }

    private static boolean isValidAge(byte age) {
        return age >= 1 && age <= 120;
    }
}

