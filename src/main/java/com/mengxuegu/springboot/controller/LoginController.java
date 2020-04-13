package com.mengxuegu.springboot.controller;

import com.mengxuegu.springboot.entities.User;
import com.mengxuegu.springboot.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 用户登录的控制层
 * @Auther: 梦学谷
 */
@Controller
public class LoginController {

    @Autowired
    UserMapper userMapper;

    @PostMapping("/login")
    public String login (HttpSession session, String username, String password, Map<String, Object> map) {


        //判断用户名、密码是否为空
        if(!StringUtils.isEmpty(username)
                && !StringUtils.isEmpty(password)) {
            //根据用户名获取用户信息，返回一个用户对象
            User user = userMapper.getUserByUsername(username);
            //判断用户对象是否为空，该用户的密码是否匹配
            if(user != null && user.getPassword().equals(password)) {
                //登录成功，将用户添加到session中，浏览器关闭，session生命周期结束
                session.setAttribute("loginUser", user);
                //重定向 redirect：可以重定向到任意一个请求中（包括其他项目），地址栏改变
                return "redirect:/main.html";
            }

        }
        //登录失败
        map.put("msg", "用户名或密码错误");

        //转发，可以将错误的信息附带带前端显示
        return "main/login";

    }

    /**
     * 退出登录
     * @return
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        //1. 清空session中的用户信息
        session.removeAttribute("loginUser");
        //2. 再将session进行注销
        session.invalidate();
        //3. 返回登录页面，重定向
        return "redirect:/index.html";
    }

}
