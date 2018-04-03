/**
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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


    public static int[] createHashes(byte[] data, int hashNumber) {
        int[] result = new int[hashNumber];

        int k = 0;
        byte salt = 0;
        while (k < hashNumber) {
            byte[] digest;
            synchronized (messageDigest) {
                messageDigest.update(salt);
                salt++;
                digest = messageDigest.digest(data);
            }

            for (int i = 0; i < digest.length / 4 && k < hashNumber; i++) {
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
