package de.hamburg;


import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

public class CryptographyUtils {

    //Password-Based Key Derivation Function 2
    public static String generatePBKDF2Hash(String password) {

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
        if (paddingLength > 0) {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        } else {
            return hex;
        }
    }

    public static boolean checkPassword(String password) {
        if (password.isEmpty()) {
            return true;
        }

        boolean hasLetter = false;
        boolean hasDigit = false;
        boolean hasUppercase = false;
        boolean hasLowerCase = false;
        boolean hasSpecialChar = false;
        String specialChars = "/*!@#$%^&*()\"{}_[]|\\?/<>,.";

        if (password.length() >= 8) {
            for (int i = 0; i < password.length(); i++) {
                char x = password.charAt(i);
                if (Character.isLetter(x)) {

                    hasLetter = true;

                    if (Character.isUpperCase(x)) {
                        hasUppercase = true;
                    } else if (Character.isLowerCase(x)) {
                        hasLowerCase = true;
                    }
                }
                //  !Uu11111
                else if (Character.isDigit(x)) {

                    hasDigit = true;
                } else if (specialChars.contains("" + x + "")) {

                    hasSpecialChar = true;
                }

                if (hasLetter && hasDigit && hasUppercase && hasLowerCase && hasSpecialChar) {

                    return true;
                }

            }
        }
        System.out.println("Password is not strong, try again. \n Password must contain at least 1 uppercase letter, 1 lowercase letter, 1 digit and 1 special char and passwords minimum length must be at least 8.");
        return false;
    }
}
