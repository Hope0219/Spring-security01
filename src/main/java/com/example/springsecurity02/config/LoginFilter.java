package com.example.springsecurity02.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

//自定义前后端分离认证

public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //1.判断是否是post请求
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not support " + request.getMethod());
        }
        //   2.判断是否是json格式请求类型
        if (request.getContentType().equalsIgnoreCase(MediaType.APPLICATION_JSON_VALUE)) {
        //3.从json数据中读取username和password进行认证
        System.out.println(request.getAttribute(getUsernameParameter()));
        System.out.println(request.getContentType());
        System.out.println(request);
        System.out.println(request.getAttribute(getUsernameParameter()));
        System.out.println(request.getAttribute(getPasswordParameter()));

        try {

            Map<String, String> userInfo = new ObjectMapper().readValue(request.getInputStream(), Map.class);
            String username = userInfo.get(getUsernameParameter());
            String password = userInfo.get(getPasswordParameter());
//            String username = (String) request.getAttribute(getUsernameParameter());
//            String password = (String) request.getAttribute(getPasswordParameter());


            Enumeration er = request.getHeaderNames();//获取请求头的所有name值
            while(er.hasMoreElements()) {
                String name = (String) er.nextElement();
                String value = request.getHeader(name);
                System.out.println(name + "=" + value);
            }

            System.out.println();
            System.out.println(getUsernameParameter()+"::"+username + "====" +getPasswordParameter()+"::"+ password);
            //当在页面中输入用户名和密码之后首先会进入到UsernamePasswordAuthenticationToken验证(Authentication)，
            //然后生成的Authentication会被交由AuthenticationManager来进行管理
            UsernamePasswordAuthenticationToken   authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            setDetails(request, authenticationToken);
            System.out.println(authenticationToken);
            //
            return this.getAuthenticationManager().authenticate(authenticationToken);


//
        } catch (IOException e) {
            e.printStackTrace();
        }

        }return super.attemptAuthentication(request, response);
    }
}