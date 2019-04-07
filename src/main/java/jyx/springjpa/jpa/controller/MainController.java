package jyx.springjpa.jpa.controller;

import jyx.springjpa.jpa.domain.Authority;
import jyx.springjpa.jpa.domain.User;
import jyx.springjpa.jpa.service.AuthorityService;
import jyx.springjpa.jpa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jyx
 * @date 2019/3/20
 */
@Controller
public class MainController {

    /**
     * 用户权限（博主）
     */
    private static final Long ROLE_USER_AUTHORITY_ID = 2L;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthorityService authorityService;

    @GetMapping("/")
    public String root() {
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String index() {
        return "redirect:/blogs";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        model.addAttribute("errorMsg", "登录失败，用户名或秘密错误");

        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    /**
     * 注册用户
     *
     * @param user
     * @return
     */
    @PostMapping("/register")
    public String registerUser(User user) {

        System.out.println(user.toString());

        List<Authority> authorities = new ArrayList<>();

        authorities.add(authorityService.getAuthorityById(ROLE_USER_AUTHORITY_ID).get());

        user.setAuthorities(authorities);

        userService.registerUser(user);

        return "redirect:/login";
    }
}
