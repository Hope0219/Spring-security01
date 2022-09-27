package com.example.springsecurity02.mapper;

import com.example.springsecurity02.entity.Role;
import com.example.springsecurity02.entity.User;



import java.util.List;


public interface UserMapper {
        /**
         * 根据用户名查找用户
         *
         * @param username
         * @return
         */
        User loadUserByUsername(String username);

        /**
         * 根据用户id查询一个角色，注意一个用户可能不止一种角色
         *
         * @param uid
         * @return
         */
        List<Role> getRolesByUid(Integer uid);
    }
