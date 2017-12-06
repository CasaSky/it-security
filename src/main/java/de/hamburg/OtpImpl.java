package de.hamburg;

import org.jboss.aerogear.security.otp.Totp;
import org.jboss.aerogear.security.otp.api.Base32;

public class OtpImpl {

    private String otpSecret;
    private Totp totp;

    public OtpImpl() {

        String secret = Base32.random();
        totp = new Totp(secret);
        startOtp();
    }

    private Totp startOtp(){

        otpSecret = totp.now();
        System.out.println("New otp key generated: "+otpSecret);

        try {
            Thread.sleep(40);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return totp;
    }

    public boolean isValid(String otpSecret) {
        return totp.verify(otpSecret);
    }

    public String getOtpSecret() {
        return otpSecret;
    }
}
