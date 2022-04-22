package com.dwk;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface Dao {

  int findById(String id);

}
