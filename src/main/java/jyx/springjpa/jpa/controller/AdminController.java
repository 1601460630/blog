package jyx.springjpa.jpa.controller;

import jyx.springjpa.jpa.vo.Menu;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jyx
 * @date 2019/3/20
 */
@RestController
@RequestMapping("/admins")
public class AdminController {



    /**
     * ��ȡ��̨������ҳ��
     *
     * @param model
     * @return
     */
    @GetMapping
    public ModelAndView listUsers(Model model) {
        List<Menu> list = new ArrayList<>();
        list.add(new Menu("�û�����", "/users"));
        model.addAttribute("list", list);
        return new ModelAndView("/admins/index", "model", model);
    }
}
