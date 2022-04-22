package com.dwk.jpa.repository;

import com.dwk.jpa.bean.UserBean;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(rollbackFor = Exception.class)
@Component
public interface UserRepository extends CrudRepository<UserBean,String> {

    /**操作用户表*/

}
