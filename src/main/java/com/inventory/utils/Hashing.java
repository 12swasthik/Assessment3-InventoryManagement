package com.inventory.utils;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

public class Hashing {

    private static Hashing instance;

    private Hashing(){}
    public static synchronized Hashing getInstance(){
        if(instance == null)
            instance = new Hashing();
        return instance;
    }

    public String getHash(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        return DatatypeConverter.printHexBinary(hash);
    }

    public boolean comparePassword(String password,String hashValue) throws NoSuchAlgorithmException {
        String hashedPassword = getHash(password);
        return Objects.equals(hashedPassword,hashValue);
    }


}
