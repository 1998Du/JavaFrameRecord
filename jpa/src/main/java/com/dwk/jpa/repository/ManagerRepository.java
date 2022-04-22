package com.dwk.jpa.repository;

import com.dwk.jpa.bean.ManagerBean;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(rollbackFor = Exception.class)
public interface ManagerRepository extends CrudRepository<ManagerBean,String> {

    /**操作管理员表*/

}
