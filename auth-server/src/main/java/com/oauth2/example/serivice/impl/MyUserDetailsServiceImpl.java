package com.oauth2.example.serivice.impl;

import com.oauth2.example.dao.UserDao;
import com.oauth2.example.model.UserModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author lvhaibao
 * @description
 * @date 2018/11/21 0021 20:52
 */
@Service
@Slf4j
public class MyUserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserDao userDao;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String s) {
        log.info("用户登录传参=={}",s);

        UserModel userModel = this.userDao.findByUsername(s);
        if (userModel == null) {
            userModel = this.userDao.findByMobile(s);
        }
        if (userModel == null) {
            log.error("帐号未找到={}",s);
            throw new UsernameNotFoundException("帐号未找到=" + s);
        }

        //数据库取到的密码，后面返回的是用户用户哪些权限
        String password = passwordEncoder.encode(userModel.getPassword());

        return new UserModel(userModel.getUin(), userModel.getUsername(), password, userModel.getMobile());

    }


}
