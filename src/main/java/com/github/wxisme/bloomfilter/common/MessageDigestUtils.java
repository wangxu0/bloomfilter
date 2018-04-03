package com.github.wxisme.bloomfilter.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MessageDigestUtils {

    private static final String MESSAGE_DIGEST_ALGORITHM_NAME = "MD5";//SHA1,SHA256


    private static final MessageDigest messageDigest;

    static {
        MessageDigest tempMessageDigest = null;
        try {
            tempMessageDigest = MessageDigest.getInstance(MESSAGE_DIGEST_ALGORITHM_NAME);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        messageDigest = tempMessageDigest;
    }

    public static int createHash(byte[] data) {
        return createHashes(data, 1)[0];
    }


    public static int[] createHashes(byte[] data, int hashes) {
        int[] result = new int[hashes];

        int k = 0;
        byte salt = 0;
        while (k < hashes) {
            byte[] digest;
            synchronized (messageDigest) {
                messageDigest.update(salt);
                salt++;
                digest = messageDigest.digest(data);
            }

            for (int i = 0; i < digest.length / 4 && k < hashes; i++) {
                int h = 0;
                for (int j = (i * 4); j < (i * 4) + 4; j++) {
                    h <<= 8;
                    h |= ((int) digest[j]) & 0xFF;
                }
                result[k] = h;
                k++;
            }
        }
        return result;
    }

}
