package cn.hy.core.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hy
 * @blame Development Group
 * createDate:2019/11/6/006 16:46
 * @since
 */
@RestController
@RequestMapping("/login")
public class LoginController {
    @RequestMapping("/getLoginName")
    public String getLoginName(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
