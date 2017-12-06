package de.hamburg;

import java.io.*;
import java.util.Scanner;

import static de.hamburg.CryptographyUtils.checkPassword;
import static de.hamburg.CryptographyUtils.generatePBKDF2Hash;
import static de.hamburg.FileUtils.savePasswordToFile;

public class Main {

    private static File file = new File("data.txt");
    private static OtpImpl otpImpl = new OtpImpl();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Menu select (press any key to exit menu)");
            System.out.println("1: register");
            System.out.println("2: login");
            String selection = scanner.next();
            switch (selection) {
                case "1":
                    register();
                    break;
                case "2":
                    login();
                    break;
                default:
                    return;
            }
        }
    }

    private static void login() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your unsername:");
        String username = scanner.next();
        System.out.println("Enter your password:");
        String password = scanner.next();
    }

    private static void register() {
        Scanner scanner1 = new Scanner(System.in);
        Scanner scanner2 = new Scanner(System.in);

        System.out.println("Enter a unsername:");
        String username = scanner1.next();
        String password;
        boolean isOtp = false;
        String securePass = null;

        do {
            System.out.println("Enter a password:");
            password = scanner2.nextLine();
        } while (!checkPassword(password));

        if (password.isEmpty()) {
            securePass = otpImpl.getOtpSecret();
            isOtp = true;
        } else {
            securePass = generatePBKDF2Hash(password);
            System.out.println(securePass);
        }

        savePasswordToFile(file, username, securePass, isOtp);
    }

/*static void setOTP(String username, int length) {
        String password = generatePassword(length);
        String securePass = generatePBKDF2Hash(password);
        System.out.println(securePass);
        savePasswordToFile(file, username, securePass, true);

        ToDo:   file ist jetzt static, ok?
                zusätzliches Flag in User/Password-Json um anzuzeigen ob es sich um ein OTP handelt oder nicht,
                demenstprechend muss bei Verwendung des Passwortes anders reagiert werden (neues Passwort muss angelegt werden).
                Der Rest müsste dann in Anwendung implementiert werden, was wir ja nicht machen brauchen.

} */

    /*static String generatePassword(int length) {
        SecureRandom random = new SecureRandom();
        String allowedChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789/*!@#$%^&*()\"{}_[]|\\?/<>,.";
        String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String upperCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String digits = "0123456789";
        String specialChars = "/*!@#$%^&*()\"{}_[]|\\?/<>,.";
        StringBuilder password = new StringBuilder(length);

        password.append(lowerCaseLetters.charAt(random.nextInt(lowerCaseLetters.length())));
        password.append(upperCaseLetters.charAt(random.nextInt(upperCaseLetters.length())));
        password.append(digits.charAt(random.nextInt(digits.length())));
        password.append(specialChars.charAt(random.nextInt(specialChars.length())));
        for (int i = 1; i <= length - 4; i++) {
            password.append(allowedChars.charAt(random.nextInt(allowedChars.length())));
        }
        StringBuilder passwordToReturn = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            passwordToReturn.append(password.charAt(random.nextInt(password.length())));
            Character charToIndex = passwordToReturn.charAt(i);
            int indexToDelete = password.indexOf(charToIndex.toString());
            password.deleteCharAt(indexToDelete);
        }
        String returnValue = passwordToReturn.toString();
        passwordToReturn.delete(0,passwordToReturn.length());
        password.delete(0,password.length());

        return returnValue;

    } */
}


