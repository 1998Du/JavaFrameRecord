package com.dwk.util;

import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import com.dwk.config.AESConfig;
import com.dwk.config.DataConfig;
import org.springframework.util.StringUtils;

/**
 * @author duweikun
 * @date 2023/8/4  15:57
 * @description aes加解密工具类
 */
public class AesUtil {

    private static SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, AESConfig.KEY);

    /**
     * 加密
     * @param data
     * @return
     */
    public static String encode(String data) {
        try {
            if(StringUtils.isEmpty(data)){
                return data;
            }
            if (!data.startsWith(DataConfig.KEY_PREFIX)){
                return DataConfig.KEY_PREFIX + aes.encryptHex(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * 解密
     *
     * @param data
     * @return
     */
    public static String decode(String data) {
        try {
            if (data.startsWith(DataConfig.KEY_PREFIX)){
                data = data.substring(DataConfig.KEY_PREFIX.length());
                String decryptStr = aes.decryptStr(data);
                return ("null").equals(decryptStr) ? "" : decryptStr;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }


}
