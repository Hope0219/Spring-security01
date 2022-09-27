package com.example.springsecurity02.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {
//    @Autowired
//    private MyUserDetailService myUserDetailService;

//    有自定义userDetailService 默认AuthenticationManager检测当前工厂中是否有userDetailService，有则自动配置了
    @Bean
    public UserDetailsService userDetailsService(){
        InMemoryUserDetailsManager userDetailsManager=new InMemoryUserDetailsManager();
        userDetailsManager.createUser(User.withUsername("www").password("{noop}111").roles("admin").build());
        return userDetailsManager;
    }

//    //springboot对security默认配置中 在工厂中默认创建 AuthenticationManager
//    //默认找当前项目中是否存在自定义UserDetailService实例
////    @Autowired
////    public void initialize(AuthenticationManagerBuilder builder) throws Exception {
////        System.out.println("springboot 默认配置"+ builder);
////        //   貌似可有可无
//////        builder.userDetailsService(userDetailsService());
////    }
//
//
//
//
    //自定义配置AuthenticationManager 推荐 并没有在工厂中暴露出来
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        System.out.println("自定义配置authenticationManager"+auth);
        //自定义的不会自动去工厂中寻找userDetailService 需要手动寻找
        auth.userDetailsService(userDetailsService());
    }

//
    @Bean
    public MyAuthenticationSuccess authenticationSuccess(){
        return new MyAuthenticationSuccess();
    }

    @Bean
    public MyAuthenticationFailure authenticationFailure(){
        return new MyAuthenticationFailure();
    }


    //将AuthenticationManager在工厂中暴露出来，可以在认可位置注入
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
//    //自定义filter交给工厂管理
    @Bean
    public LoginFilter loginFilter() throws Exception {
        LoginFilter loginFilter=new LoginFilter();
        loginFilter.setUsernameParameter("uname");
        loginFilter.setPasswordParameter("pwd");
        loginFilter.setAuthenticationSuccessHandler(authenticationSuccess());
        loginFilter.setAuthenticationFailureHandler(authenticationFailure());
        loginFilter.setAuthenticationManager(authenticationManagerBean());
        return loginFilter;
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .inMemoryAuthentication()
//                .withUser("user")
//                .password("aaa")
//                .roles("admin")
//        ;
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
//                .mvcMatchers("/Login.html").permitAll()   //给自定义登陆的界面放行
//                .mvcMatchers("/index").permitAll()   //允许通过
                .anyRequest().authenticated()    //其余任何路径都拦截
                .and()
                .formLogin()//表单登录
//                .loginPage("/Login.html")   //自定义登陆界面
//                .loginProcessingUrl("/dologin")     //默认登陆界面
//                .usernameParameter("username")  //对照登陆页面input的username值
//                .passwordParameter("password")//对照password值
                .successHandler(new MyAuthenticationSuccess())
                .failureHandler(new MyAuthenticationFailure())
                .and()
                .csrf().disable();  //禁止csrf 跨站请求保护
              //.defaultSuccessUrl("/hello/1");    //认证成功 redirect 重定向后跳转  默认 如果之前有请求路径，优先跳转到之前的路径
            //.successForwardUrl("/index/1");   //认证成功 forward 转发跳转   不跳转到之前的请求路径

        //at 替换过滤链的某个filter  after 放在过滤链之后 before 放在过滤链之前
        //loginfilter代替默认的Usernamepasswordfilter
        http.addFilterAt(loginFilter(), UsernamePasswordAuthenticationFilter.class);

    }




}
