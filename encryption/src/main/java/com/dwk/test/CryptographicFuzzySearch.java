package com.dwk.test;

import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import cn.hutool.json.JSONUtil;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author dwk
 * @date 2024/2/22  15:18
 * @description  模糊加密搜索
 */
public class CryptographicFuzzySearch {

    // 代数据库
    private static Map<String,String> db = new HashMap<>(64);
    // 数据加密保存字段
    private static final String COLUMN_DATA_ENCRYPT = "column_data_encrypt";
    // 数据分词组合加密保存字段
    private static final String COLUMN_CUT_DATA_ENCRYPT = "column_cut_data_encrypt";


    public static void main(String[] args) {
        String sourceData = "hello,world";
        String searchData = "hello";
        int cutLength = 4;
        // 原数据分词加密组合
        List<String> sourceDataCutArray = strCut(cutLength, sourceData);
        String sourceDataCutEncryptStr = sourceDataCutArray.stream().map(item -> AesUtil.encode(item)).collect(Collectors.toList()).stream().collect(Collectors.joining());
        // 原数据加密
        String sourceDataEncrypt = AesUtil.encode(sourceData);
        // 保存
        db.put(COLUMN_DATA_ENCRYPT,sourceDataEncrypt);
        db.put(COLUMN_CUT_DATA_ENCRYPT,sourceDataCutEncryptStr);
        // 打印数据
        System.out.println(JSONUtil.toJsonStr(db));
        // 查询条件分词加密组合
        List<String> searchDataCutArray = strCut(cutLength, searchData);
        String searchDataCutEncryptStr = searchDataCutArray.stream().map(item -> AesUtil.encode(item)).collect(Collectors.toList()).stream().collect(Collectors.joining());
        // 查询
        if (db.get(COLUMN_CUT_DATA_ENCRYPT).contains(searchDataCutEncryptStr)){
            System.out.println("模糊查询匹配成功,查询条件原文：" + searchData + "   分词加密组合后：" + searchDataCutEncryptStr);
            System.out.println("原数据分词加密组合：" + db.get(COLUMN_DATA_ENCRYPT));
            System.out.println("原数据解密：" + AesUtil.decode(db.get(COLUMN_DATA_ENCRYPT)));
            System.out.println("分段加密数据解密：" + AesUtil.decode(searchDataCutEncryptStr));
        }else{
            System.out.println("模糊查询未匹配!!!");
        }
    }

    /**
     * 分词
     * @param length 分词长度
     * @param text  明文数据
     * @return
     */
    public static List<String> strCut(int length , String text){
        List<String> result = new LinkedList<>();
        for(int i = 0; i < text.length()-length+1; i++){
            String substring = text.substring(i, i + length);
            result.add(substring);
        }
        return result;
    }


}

/**
 * aes加密工具类
 */
class AesUtil {
    public final static byte[] KEY = "codeKey!2024AeS@".getBytes();

    private static SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, KEY);

    /**
     * 加密
     *
     * @param data
     * @return
     */
    public static String encode(String data) {
        try {
            if (StringUtils.isEmpty(data)) {
                return data;
            }
            return aes.encryptHex(data);
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
            String decryptStr = aes.decryptStr(data);
            return ("null").equals(decryptStr) ? "" : decryptStr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}
