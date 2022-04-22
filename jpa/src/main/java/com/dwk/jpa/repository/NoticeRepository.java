package com.dwk.jpa.repository;

import com.dwk.jpa.bean.NoticeBean;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(rollbackFor = Exception.class)
public interface NoticeRepository extends CrudRepository<NoticeBean,String> {

    /**操作公告表*/

}
