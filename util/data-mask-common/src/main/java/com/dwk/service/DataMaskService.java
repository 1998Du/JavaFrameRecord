package com.dwk.service;

import com.dwk.dao.DataMaskDao;
import com.dwk.properties.DataMaskProperties;
import com.dwk.util.AesUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author duweikun
 * @date 2023/7/19  15:58
 * @description 存量数据加解密
 */

@Service
@Slf4j
public class DataMaskService {

    @Autowired
    private DataMaskProperties dataMaskProperties;

    @Autowired
    private DataMaskDao dataMaskDao;

    @Autowired
    private PlatformTransactionManager transactionManager;

    public String run() {
        int result = 0;
        String primaryKey = "";
        Map<String, List<String>> tableConfigMap = dataMaskProperties.getConfig();
        for (String table : tableConfigMap.keySet()) {
            List<Map<String, Object>> updateList = new ArrayList<>();
            primaryKey = tableConfigMap.get(table).get(0);
            List<Map<String, Object>> query = query(table, tableConfigMap.get(table));
            AtomicLong atomicLong = new AtomicLong(1);
            for (Map<String, Object> data : query) {
                encrypt(data, primaryKey, tableConfigMap.get(table));
                updateList.add(data);
                atomicLong.addAndGet(1L);
                if(atomicLong.get() % 100 == 0){
                    result += update(table, primaryKey, updateList);
                    updateList.clear();
                }
            }
            if (updateList != null && updateList.size() > 0) {
                result += update(table, primaryKey, updateList);
            }
        }
        return "成功加密" + result + "条数据!";
    }

    /**
     * 加密
     *
     * @param data       数据
     * @param primaryKey 主键
     * @param columns    字段列表
     * @return
     */
    public Map<String, Object> encrypt(Map<String, Object> data, String primaryKey, List<String> columns) {
        for (String column : columns) {
            if (!column.equals(primaryKey)) {
                String value = String.valueOf(data.get(column));
                boolean isNull = ("null").equals(value) || ("").equals(value) || StringUtils.isEmpty(value);
                if (!isNull){
                    data.put(column, AesUtil.encode(value));
                }
            }
        }
        return data;
    }

    /**
     * 动态查询配置中的表数据
     *
     * @param tableName
     * @param columns
     * @return
     */
    @Transactional
    public List<Map<String, Object>> query(String tableName, List<String> columns) {
        return dataMaskDao.query(tableName, columns);
    }

    /**
     * 手动提交事务更新
     *
     * @param tableName  表名
     * @param primaryKey 主键名
     */
    public int update(String tableName, String primaryKey, List<Map<String, Object>> columns) {
        int result = 0;
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            for(Map<String, Object> column : columns){
                Object primaryValue = column.get(primaryKey);
                result += dataMaskDao.update(tableName,primaryKey,primaryValue,column);
            }
//            log.info("加密{}中的数据{}", tableName, JSONUtil.toJsonStr(columns));
            transactionManager.commit(status);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            transactionManager.rollback(status);
            return result;
        }
    }

}