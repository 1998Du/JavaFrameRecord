package com.dwk.jpa.repository;

import com.dwk.jpa.bean.ActivityBean;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(rollbackFor = Exception.class)
public interface ActivityRepository extends CrudRepository<ActivityBean,String> {

    /**操作活动表*/
    @Modifying
    @Query("update user t set t.name =: name where t.id =: id")
    int update(@Param("id") String id,@Param("name") String name);

}
