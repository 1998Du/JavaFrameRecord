package com.dwk.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author duweikun
 * @date 2023/7/19  16:39
 * @description
 */
@Mapper
public interface DataMaskDao {

    List<Map<String,Object>> query(@Param("tableName") String tableName,@Param("columns") List<String> columns);

    int update(@Param("tableName")String tableName,@Param("primaryKey") String primaryKey,@Param("primaryValue") Object primaryValue,@Param("columnMap")Map<String, Object>  columnMap);
}
