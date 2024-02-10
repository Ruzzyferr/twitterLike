package com.ruzzyfer.twitterlike.util;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.stereotype.Component;

@Component
public class Encryptor {

    private static Encryptor encryptor;

    StandardPBEStringEncryptor stringEncryptor = new StandardPBEStringEncryptor();

    private Encryptor() {
        stringEncryptor.setPassword("twitter-like");
    }

    public static Encryptor getInstance() {
        if (encryptor == null) {
            encryptor = new Encryptor();
        }
        return encryptor;
    }

    public String generateSecurePassword(String password) {
        return stringEncryptor.encrypt(password);
    }

    public boolean verifyPersonnelPassword(String providedPassword, String securedPassword) {
        return stringEncryptor.decrypt(securedPassword).equals(providedPassword);
    }

    public String getDecryptedPassword(String securedPassword) {
        return stringEncryptor.decrypt(securedPassword);
    }


}
