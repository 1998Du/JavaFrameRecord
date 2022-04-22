package com.dwk.jpa.service;

import com.dwk.jpa.bean.UserBean;
import com.dwk.jpa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void test(){
        Iterable<UserBean> all = userRepository.findAll();
        all.forEach(System.out::print);
    }

}
