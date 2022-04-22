package com.dwk.jpa.repository;

import com.dwk.jpa.bean.MajorBean;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(rollbackFor = Exception.class)
public interface MajorRepository extends CrudRepository<MajorBean,String> {

    /**操作专业表*/

}
