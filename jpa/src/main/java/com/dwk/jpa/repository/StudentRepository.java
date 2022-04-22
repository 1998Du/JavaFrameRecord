package com.dwk.jpa.repository;

import com.dwk.jpa.bean.StudentBean;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(rollbackFor = Exception.class)
public interface StudentRepository extends CrudRepository<StudentBean,String> {

    /**操作学生表*/

}
