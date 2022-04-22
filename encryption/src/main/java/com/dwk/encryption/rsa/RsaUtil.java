package com.dwk.encryption.rsa;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * RSA加解密
 */
public class RsaUtil {

    private static final String PUBLIC_KEY = "RSA_PUBLIC_KEY";
    private static final String PRIVATE_KEY = "RSA_PRIVATE_KEY";
    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    /**创建密钥对*/
    public static Map<String,Object> createKey(){
        Map<String,Object> returnMap = null;

        try {
            //密钥生成器
            KeyPairGenerator rsa = KeyPairGenerator.getInstance("RSA");
            rsa.initialize(1024);
            KeyPair keyPair = rsa.generateKeyPair();
            //公钥
            RSAPublicKey aPublic = (RSAPublicKey) keyPair.getPublic();
            //私钥
            RSAPrivateKey aPrivate = (RSAPrivateKey) keyPair.getPrivate();
            returnMap = new HashMap<>(2);
            returnMap.put(PUBLIC_KEY,aPublic);
            returnMap.put(PRIVATE_KEY,aPrivate);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return returnMap;
    }

    /**公钥加密*/
    public static String encryptByPublicKey(RSAPublicKey key,String metaDate){
        byte[] encryptedData = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            byte[] bytes = metaDate.getBytes("UTF-8");
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            int length = bytes.length;
            int offSet = 0;
            byte[] cache;
            int i = 0;
            //对数据分段加密
            while (length - offSet > 0){
                if (length - offSet > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(bytes, offSet, MAX_ENCRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(bytes, offSet, length - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_ENCRYPT_BLOCK;
            }
            encryptedData = out.toByteArray();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } finally {
            if (out != null){
                try {
                    out.close();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        }

        return  Base64.getEncoder().encodeToString(encryptedData);
    }

}
