package com.homura.common.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.UUID;

public class CryptoUtils {
    public static final String UUID_REG = "^\\{?([a-fA-F0-9]{8})-?([a-fA-F0-9]{4})-?([a-fA-F0-9]{4})-?([a-fA-F0-9]{4})-?([a-fA-F0-9]{12})\\}?$";
    public static final String CIPHER_ARGS = "AES/CBC/PKCS5Padding";
    public static final String CHARSETNAME = "utf-8";

    public static String aesEncrypt(String content) {
        UUID uuid = UUID.randomUUID();
        String key = uuid.toString().replaceAll("^\\{?([a-fA-F0-9]{8})-?([a-fA-F0-9]{4})-?([a-fA-F0-9]{4})-?([a-fA-F0-9]{4})-?([a-fA-F0-9]{12})\\}?$", "$1$2$3$4$5");
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            int size = cipher.getBlockSize();
            byte[] keyBytes = key.getBytes();
            byte[] buf = new byte[size];
            for (int i = 0; i < keyBytes.length && i < buf.length; i++) {
                buf[i] = keyBytes[i];
            }
            IvParameterSpec ivSpec = new IvParameterSpec(buf);
            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
            cipher.init(1, keySpec, ivSpec);
            byte[] bytes = cipher.doFinal(content.getBytes("utf-8"));

            ByteBuffer buffer = ByteBuffer.allocate(16 + bytes.length);
            buffer.putLong(uuid.getMostSignificantBits());
            buffer.putLong(uuid.getLeastSignificantBits());
            buffer.put(bytes);
            return base64Encode(buffer.array());
        } catch (Exception ex) {
            //logger.warn("aesEncrypt Exception Message {}", ex.getMessage());
            //logger.debug("aesEncrypt error: {}", ex);

            return null;
        }

    }

    public static String aesEncrypt(String content, String key) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            int size = cipher.getBlockSize();
            byte[] keyBytes = key.getBytes();
            byte[] buf = new byte[size];
            for (int i = 0; i < keyBytes.length && i < buf.length; i++) {
                buf[i] = keyBytes[i];
            }
            IvParameterSpec ivSpec = new IvParameterSpec(buf);
            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
            cipher.init(1, keySpec, ivSpec);
            byte[] bytes = cipher.doFinal(content.getBytes("utf-8"));
            return base64Encode(bytes);
        } catch (Exception ex) {
            //logger.warn("aesEncrypt Exception Message {}", ex.getMessage());
            //logger.debug("aesEncrypt error: {}", ex);

            return null;
        }
    }

    public static String aesDecrypt(String content) {
        try {
            byte[] bytes = base64Decode(content);
            ByteBuffer buffer = ByteBuffer.wrap(bytes);
            long mostSigBits = buffer.getLong();
            long leastSigBits = buffer.getLong();
            UUID uuid = new UUID(mostSigBits, leastSigBits);
            String key = uuid.toString().replaceAll("^\\{?([a-fA-F0-9]{8})-?([a-fA-F0-9]{4})-?([a-fA-F0-9]{4})-?([a-fA-F0-9]{4})-?([a-fA-F0-9]{12})\\}?$", "$1$2$3$4$5");
            byte[] encryptBytes = new byte[bytes.length - 16];
            buffer.get(encryptBytes, 0, encryptBytes.length);

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            int size = cipher.getBlockSize();
            byte[] keyBytes = key.getBytes();
            byte[] buf = new byte[size];
            for (int i = 0; i < keyBytes.length && i < buf.length; i++) {
                buf[i] = keyBytes[i];
            }
            IvParameterSpec ivSpec = new IvParameterSpec(buf);
            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
            cipher.init(2, keySpec, ivSpec);
            return new String(cipher.doFinal(encryptBytes));
        } catch (Exception ex) {
            //logger.warn("aesDecrypt Exception Message {}", ex.getMessage());
            //logger.debug("aesDecrypt error: ", ex);

            return null;
        }
    }

    public static String aesDecrypt(String content, String key) {
        try {
            byte[] bytes = base64Decode(content);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            int size = cipher.getBlockSize();
            byte[] keyBytes = key.getBytes();
            byte[] buf = new byte[size];
            for (int i = 0; i < keyBytes.length && i < buf.length; i++) {
                buf[i] = keyBytes[i];
            }
            IvParameterSpec ivSpec = new IvParameterSpec(buf);
            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
            cipher.init(2, keySpec, ivSpec);
            return new String(cipher.doFinal(bytes));
        } catch (Exception ex) {
            //logger.warn("aesDecrypt Exception Message {}", ex.getMessage());
            //logger.debug("aesEncrypt error: ", ex);

            return null;
        }
    }

    public static String encrypt(String content, String password) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, new SecureRandom(password.getBytes()));
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(1, key);
            byte[] bytes = cipher.doFinal(byteContent);
            return base64Encode(bytes);
        } catch (Exception ex) {
            //logger.warn("encrypt Exception Message {}", ex.getMessage());
            //logger.debug("encrypt error: ", ex);

            return null;
        }
    }

    public static String decrypt(String content, String password) {
        try {
            byte[] bytes = base64Decode(content);
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, new SecureRandom(password.getBytes()));
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(2, key);
            return new String(cipher.doFinal(bytes));
        } catch (Exception ex) {
            //logger.warn("decrypt Exception Message {}", ex.getMessage());
            //logger.debug("decrypt error: ", ex);

            return null;
        }
    }

    public static String base64Encode(byte[] bytes) { return Base64.getEncoder().withoutPadding().encodeToString(bytes); }

    public static byte[] base64Decode(String str) { return Base64.getDecoder().decode(str); }

    public static void main(String[] args) {
        System.out.println(aesDecrypt("DWCC5y30QgKqSzDIzYjWzcG91M1R7AwaL6LEd6yf7V0"));
    }

}
