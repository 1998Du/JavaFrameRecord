package com.dwk.jpa.repository;

import com.dwk.jpa.bean.DepartmentBean;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(rollbackFor = Exception.class)
public interface DepartmentRepository extends CrudRepository<DepartmentBean,String> {

    /**操作院系表*/

}
