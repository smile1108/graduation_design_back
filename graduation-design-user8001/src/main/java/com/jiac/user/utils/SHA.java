package com.jiac.user.utils;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * FileName: SHA
 * Author: Jiac
 * Date: 2022/4/27 13:40
 */
public class SHA {

    private static final String SHA = "SHA";

    public static String getResult(String inputStr){
        BigInteger bigInteger = null;
        try {
            MessageDigest sha = MessageDigest.getInstance(SHA);
            sha.update(inputStr.getBytes());
            bigInteger = new BigInteger(1, sha.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bigInteger.toString();
    }
}