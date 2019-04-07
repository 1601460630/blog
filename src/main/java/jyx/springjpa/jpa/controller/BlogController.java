package jyx.springjpa.jpa.controller;

import jyx.springjpa.jpa.domain.User;
import jyx.springjpa.jpa.domain.es.EsBlog;
import jyx.springjpa.jpa.service.EsBlogService;
import jyx.springjpa.jpa.vo.TagVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author jyx
 * @date 2019/3/20
 */
@Controller
@RequestMapping("/blogs")
public class BlogController {

    @Autowired
    private EsBlogService esBlogService;

    @GetMapping
    public String listBlogs(@RequestParam(value = "order", required = false, defaultValue = "new") String order,
                            @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
                            @RequestParam(value = "async", required = false) boolean async,
                            @RequestParam(value = "keyword", required = false, defaultValue = "0") int pageIndex,
                            @RequestParam(value = "keyword", required = false, defaultValue = "10") int pageSize, Model model) {

        Page<EsBlog> page = null;
        List<EsBlog> list = null;

        //系统初始化时，没有博客数据
        boolean isEmpty = true;

        try {
            if (order.equals("hot")) {
                Sort sort = new Sort(Sort.Direction.DESC, "readSize", "commentSize", "voteSize", "createTime");

                Pageable pageable = PageRequest.of(pageIndex, pageSize, sort);

                page = esBlogService.listHotestEsBlogs(keyword, pageable);
            } else if (order.equals("new")) {
                Sort sort = new Sort(Sort.Direction.DESC, "createTime");
                Pageable pageable = PageRequest.of(pageIndex, pageSize, sort);
                page = esBlogService.listNewestEsBlogs(keyword, pageable);
            }

            isEmpty = false;
        } catch (Exception e) {

            Pageable pageable = PageRequest.of(pageIndex, pageSize);
            page = esBlogService.listEsBlogs(pageable);
        }

        //当前所在页面数据列表
        list = page.getContent();

        model.addAttribute("order", order);
        model.addAttribute("keyword", keyword);
        model.addAttribute("page", page);
        model.addAttribute("blogList", list);

        if (!async && !isEmpty) {

            List<EsBlog> newest = esBlogService.listTop5NewestEsBlog();
            model.addAttribute("newest", newest);

            List<EsBlog> hotest = esBlogService.listTop5HotestEsBlog();
            model.addAttribute("hotest", hotest);

            List<TagVO> tags = esBlogService.listTop30Tags();
            model.addAttribute("tags", tags);

            List<User> users = esBlogService.listTop12Users();
            model.addAttribute("users", users);
        }

        return (async == true ? "/index ::  #mainContainerReplace" : "index");
    }
}
