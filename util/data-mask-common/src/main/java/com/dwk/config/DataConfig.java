package com.dwk.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author duweikun
 * @date 2023/7/18  14:39
 * @description 特定表及字段配置
 */
public class DataConfig {

    /**密文前缀*/
    public static String KEY_PREFIX = "N0e0w0b0D0a0t0a0M0a0s0k_";

    private static HashMap<String, List<String>> DataConfig = new HashMap<>();

    static {
        String[] ac_user_column = {"name"};
        // key:value  ->  表名:字段集合
        DataConfig.put("table_name", Arrays.asList(ac_user_column));
    }

}
