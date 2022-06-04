package com.zakharenko.lab03.tool;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * A utility class that encrypts or decrypts a file.
 * @author www.codejava.net
 *
 */
public class CryptoUtils {
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";
    private static final String KEY = "1234567890123456";

    public static byte[] encrypt(byte[] bytes) {
        return doCrypto(Cipher.ENCRYPT_MODE, KEY, bytes);
    }

    public static byte[] decrypt(byte[] bytes) {
        return doCrypto(Cipher.DECRYPT_MODE, KEY, bytes);
    }

    private static byte[] doCrypto(int cipherMode, String key, byte[] bytes) {
        try {
            Key secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(cipherMode, secretKey);

//            FileInputStream inputStream = new FileInputStream(inputFile);
//            byte[] inputBytes = new byte[(int) inputFile.length()];
//            inputStream.read(inputBytes);

            return cipher.doFinal(bytes);

//            FileOutputStream outputStream = new FileOutputStream(outputFile);
//            outputStream.write(outputBytes);
//
//            inputStream.close();
//            outputStream.close();

        } catch (NoSuchPaddingException | NoSuchAlgorithmException
                | InvalidKeyException | BadPaddingException
                | IllegalBlockSizeException ex) {
//            throw new CryptoException(ex.getMessage());
            return null;
        }
    }
}
