package de.hamburg;

import org.json.JSONException;
import org.json.JSONObject;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.*;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        File file = new File("data.txt");
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter a unsername:");
            String username = scanner.next();
            String password;
            do {
                System.out.println("Enter a password:");
                password = scanner.next();
            } while (!checkPassword(password));
            String securePass = generatePBKDF2Hash(password);
            System.out.println(securePass);
            savePasswordToFile(file, username, securePass);
        }
    }

    private static void savePasswordToFile(File file, String username, String password) {

        String data = generateJson(username, password);

        FileWriter fooWriter = null; // true to append
        try {
            fooWriter = new FileWriter(file, true);
            // false to overwrite.
            fooWriter.append(data);
            fooWriter.append(System.getProperty( "line.separator" ));
            fooWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static String generateJson(String username, String password) {
        JSONObject json = new JSONObject();
        try {
            json.put("username", username);
            json.put("password", password);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json.toString();
    }

    private static String generatePBKDF2Hash(String password) {

        int iterations = 1000;
        char[] passChars = password.toCharArray();
        byte[] salt = getSalt();

        int keyLength = 64 * 8; // 512
        PBEKeySpec spec = new PBEKeySpec(passChars, salt, iterations, keyLength);
        SecretKeyFactory skf = null;
        byte[] hash = new byte[0];
        try {
            skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            hash = skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException e) {
            System.err.println(e.getMessage());
        } catch (InvalidKeySpecException e) {
            System.err.println(e.getMessage());
        }
        return iterations + ":" + toHex(salt) + ":" + toHex(hash);
    }

    private static byte[] getSalt() {
        SecureRandom sr = null;
        byte[] salt = new byte[0];
        try {
            sr = SecureRandom.getInstance("SHA1PRNG");
            salt = new byte[16];
            sr.nextBytes(salt);
        } catch (NoSuchAlgorithmException e) {
            System.err.println(e.getMessage());
        }
        return salt;
    }

    private static String toHex(byte[] array) {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if(paddingLength > 0)
        {
            return String.format("%0"  +paddingLength + "d", 0) + hex;
        }else{
            return hex;
        }
    }

    private static boolean checkPassword(String password) {

        boolean hasLetter = false;
        boolean hasDigit = false;
        boolean hasUppercase = false;
        boolean hasSpecialChar = false;
        String specialChars = "/*!@#$%^&*()\"{}_[]|\\?/<>,.";

        if (password.length() >= 8) {
            for (int i = 0; i < password.length(); i++) {
                char x = password.charAt(i);
                if (Character.isLetter(x)) {

                    hasLetter = true;

                    if (Character.isUpperCase(x)) {

                        hasUppercase = true;
                    }
                }

                else if (Character.isDigit(x)) {

                    hasDigit = true;
                }


                else if (specialChars.contains(""+x+"")) {

                    hasSpecialChar = true;
                }

                if(hasLetter && hasDigit && hasUppercase && hasSpecialChar){

                    return true;
                }

            }
        }
        System.out.println("Password is not strong, try again.");
        return false;
    }
}
