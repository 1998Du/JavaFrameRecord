package com.dwk.jpa.repository;

import com.dwk.jpa.bean.StoreBean;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(rollbackFor = Exception.class)
public interface StoreRepository extends CrudRepository<StoreBean,String> {

    /**操作商铺表*/

}
