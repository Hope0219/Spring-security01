//package com.example.springsecurity02.service;
//
//import com.example.springsecurity02.entity.User;
//import com.example.springsecurity02.mapper.UserMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import org.springframework.util.ObjectUtils;
//
//@Service
//public class MyUserDetailService implements UserDetailsService {
//
//
//    private UserMapper userMapper;
////
//
//    public MyUserDetailService(UserMapper userMapper) {
//        this.userMapper = userMapper;
//    }
////
//
//    public MyUserDetailService() {
//    }
//
//
////    public void setUserMapper(UserMapper userMapper) {
////        this.userMapper = userMapper;
////    }
//
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userMapper.loadUserByUsername(username);
//        if(ObjectUtils.isEmpty(user)){
//            throw new UsernameNotFoundException("用户名不存在！！");
//        }
//        user.setRoles(userMapper.getRolesByUid(user.getId()));
////        User user=new User();
////        user.setUsername(username);
////        user.setPassword("111");
////        user.setAccountNonLocked(true);
////        user.setEnabled(true);
////        user.setAccountNonExpired(true);
////        user.setCredentialsNonExpired(true);
//        System.out.println(user);
//        return user;
//
//
//    }
//}
