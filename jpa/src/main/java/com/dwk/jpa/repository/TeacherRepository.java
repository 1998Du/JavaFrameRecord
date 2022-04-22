package com.dwk.jpa.repository;

import com.dwk.jpa.bean.TeacherBean;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(rollbackFor = Exception.class)
public interface TeacherRepository extends CrudRepository<TeacherBean,String> {

    /**操作老师表*/

}
