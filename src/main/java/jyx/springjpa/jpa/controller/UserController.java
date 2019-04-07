package jyx.springjpa.jpa.controller;

import jyx.springjpa.jpa.domain.Authority;
import jyx.springjpa.jpa.domain.User;
import jyx.springjpa.jpa.service.AuthorityService;
import jyx.springjpa.jpa.service.UserService;
import jyx.springjpa.jpa.util.ConstraintViolationExceptionHandler;
import jyx.springjpa.jpa.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author jyx
 * @date 2019/3/20
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthorityService authorityService;



    /**
     * 查询所用用户
     *
     * @param async
     * @param pageIndex
     * @param pageSize
     * @param name
     * @param model
     * @return
     */
    @GetMapping
    public ModelAndView list(@RequestParam(value = "async", required = false) boolean async,
                             @RequestParam(value = "pageIndex", required = false, defaultValue = "0") int pageIndex,
                             @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                             @RequestParam(value = "name", required = false, defaultValue = "") String name,
                             Model model) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);

        Page<User> page = userService.listUsersByNameLike(name, pageable);
        //当前所在页面数据列表
        List<User> list = page.getContent();

        model.addAttribute("page", page);

        model.addAttribute("userList", list);

        return new ModelAndView(async == true ? "users/list :: #mainContainerRepleace" : "users/list", "userModel", model);
    }

    /**
     * 获取创建表单页面
     *
     * @param model
     * @return
     */
    @GetMapping("/add")
    public ModelAndView createForm(Model model) {
        model.addAttribute("user", new User(null, null, null, null));
        return new ModelAndView("users/add", "userModel", model);
    }

    /**
     * 保存用户或修改用户
     *
     * @param user
     * @return
     */
    @PostMapping
    public ResponseEntity<Response> saveOrUpateUser(User user,Long authorityId) {

        List<Authority> authorities = new ArrayList<>();
                                                                    //这里有个 .get() 书上是没有的，但是在作者的源码中有写，不写会报错
        authorities.add(authorityService.getAuthorityById(authorityId).get());
        user.setAuthorities(authorities);

        try {
            userService.saveOrUpdateUser(user);
        } catch (ConstraintViolationException e) {
            return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
        }
        //重定向到list页面
        return ResponseEntity.ok().body(new Response(true, "处理成功"));
    }

    /**
     * 删除用户
     *
     * @param id
     * @param model
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Response> delete(@PathVariable("id") Long id, Model model) {
        try {
            userService.removeUser(id);
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Response(false, e.getMessage()));
        }
        //重定向到list页面
        return ResponseEntity.ok().body(new Response(true, "处理成功"));
    }

    /**
     * 获取修改用户的界面
     *
     * @param id
     * @param model
     * @return
     */
    @GetMapping(value = "edit/{id}")
    public ModelAndView modifyForm(@PathVariable("id") Long id, Model model) {
        Optional<User> user = userService.getUserById(id);

        model.addAttribute("user", user.get());

        return new ModelAndView("users/edit", "userModel", model);
    }


}












