package com.oauth2.example.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oauth2.example.model.UserModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author lvhaibao
 * @description
 * @date 2018/12/25 0025 14:51
 */
@Mapper
public interface UserDao extends BaseMapper<UserModel> {

    /**
     * 通过手机号获取用户
     *
     * @param mobile 手机号
     * @return UserModel
     */
    UserModel findByMobile(String mobile);

    /**
     * 通过账号获取用户
     *
     * @param username 账号
     * @return UserModel
     */
    UserModel findByUsername(String username);


}
