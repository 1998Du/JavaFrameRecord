package com.dwk.jpa.service;

import cn.hutool.core.util.StrUtil;
import com.dwk.jpa.bean.UserBean;
import com.dwk.jpa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.List;

/**
 *
 * Description:
 *
 * @author: mushi
 * @Date: 2021/3/16 15:38
 */
@Service
public class TestService {

    @Autowired
    private UserRepository userRepository;
    @PersistenceContext
    private EntityManager entityManager;

    public void test(){
        List<HashMap> resultList = entityManager.createQuery(StrUtil.format("select * from activity"), HashMap.class).getResultList();
        Iterable<UserBean> all = userRepository.findAll();
        all.forEach(System.out::print);
    }

}
