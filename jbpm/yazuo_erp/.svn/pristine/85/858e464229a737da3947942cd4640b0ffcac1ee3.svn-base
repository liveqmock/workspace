package com.yazuo.erp.base;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.*;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.io.IOException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;

/**
 * 加解密方法
 * Encrypt.getInstance().encrypt(plainText)
 */
public  class Encrypt {
    protected static Log log = LogFactory.getLog(Encrypt.class);
    private static Encrypt instance;
    private static final String KEY = "3F71916D46F94F5EE51BCEE817AD8FAE";
    private static String algorithmName = "DESede";
    private static Object lock = new Object();

    private DESedeKeySpec spec;
    private SecretKeyFactory keyFactory;
    private SecretKey theKey;
    private IvParameterSpec ivParameters;
    private Cipher cipher;

    private Encrypt() {
        try {
            cipher = Cipher.getInstance(algorithmName);
        } catch (Exception e) {
            log.info("安装SunJCE provider");
            Provider sunjce = new com.sun.crypto.provider.SunJCE();
            Security.addProvider(sunjce);
            e.printStackTrace();
        }

        try {
            spec = new DESedeKeySpec(KEY.getBytes());
            keyFactory = SecretKeyFactory.getInstance(algorithmName);
            theKey = keyFactory.generateSecret(spec);
            cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
            // 为 CBC 模式创建一个用于初始化的 vector 对象
            ivParameters = new IvParameterSpec(new byte[]{12, 34, 56, 78, 90, 87, 65, 43});
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
    }


    public static Encrypt getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new Encrypt();
                }
            }
        }
        return instance;
    }

    /**
     * 解密方法
     *
     * @param ciphertext
     * @return
     */
    public String decrypt(String ciphertext) {
        String plaintext = null;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            cipher.init(Cipher.DECRYPT_MODE, theKey, ivParameters);
            byte[] plaintextBytes = cipher.doFinal(decoder.decodeBuffer(ciphertext));
            plaintext = new String(plaintextBytes);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return plaintext;
    }

    /**
     * 加密方法
     *
     * @param plaintext
     * @return
     */
    public String encrypt(String plaintext) {
        String ciphertext = null;
        try {
            cipher.init(Cipher.ENCRYPT_MODE, theKey, ivParameters);
            byte[] ciphertestBytes = cipher.doFinal(plaintext.getBytes());
            ciphertext = new BASE64Encoder().encode(ciphertestBytes);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return ciphertext;
    }
}