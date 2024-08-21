package com.dwk.util;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.dwk.config.DataConfig;
import com.dwk.config.RsaConfig;
import org.springframework.util.StringUtils;

import java.security.KeyPair;

/**
 * @author duweikun
 * @date 2023/7/18  14:45
 * @description Rsa加解密工具类
 */
public class RsaUtil {


    /**
     * 公钥加密
     *
     * @param data
     * @return  返回结果加上前缀防止重复加密
     */
    public static String encode(String data) {
        try {
            if(StringUtils.isEmpty(data)){
                return data;
            }
            RSA rsa = new RSA(null, RsaConfig.PUBLIC_KEY);
            if (!data.startsWith(DataConfig.KEY_PREFIX)){
                return DataConfig.KEY_PREFIX + rsa.encryptBase64(data, KeyType.PublicKey);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * 私钥解密
     *
     * @param data
     * @return
     */
    public static String decode(String data) {
        try {
            RSA rsa = new RSA(RsaConfig.PRIVATE_KEY, null);
            if (data.startsWith(DataConfig.KEY_PREFIX)){
                data = data.substring(DataConfig.KEY_PREFIX.length());
                return rsa.decryptStr(data, KeyType.PrivateKey);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * 刷新密钥对
     */
    public static void refreshKey() {
        KeyPair pair = SecureUtil.generateKeyPair("RSA");
        RsaConfig.PRIVATE_KEY = Base64.encode(pair.getPrivate().getEncoded());
        ;
        RsaConfig.PUBLIC_KEY = Base64.encode(pair.getPublic().getEncoded());
        ;
    }

}
