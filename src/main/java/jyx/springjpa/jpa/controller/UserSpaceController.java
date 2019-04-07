package jyx.springjpa.jpa.controller;

import jyx.springjpa.jpa.domain.Blog;
import jyx.springjpa.jpa.domain.Catalog;
import jyx.springjpa.jpa.domain.User;
import jyx.springjpa.jpa.domain.Vote;
import jyx.springjpa.jpa.service.BlogService;
import jyx.springjpa.jpa.service.CatalogService;
import jyx.springjpa.jpa.service.UserService;
import jyx.springjpa.jpa.util.ConstraintViolationExceptionHandler;
import jyx.springjpa.jpa.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

/**
 * @author jyx
 * @date 2019/3/20
 */
@Controller
@RequestMapping("/u")
public class UserSpaceController {

    @Autowired
    private UserService userService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private CatalogService catalogService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Value("http://localhost:8081/upload")
    private String fileServerUrl;


    /**
     * 获取个人设置页面
     *
     * @param username
     * @param model
     * @return
     */
    @GetMapping("/{username}/profile")
    @PreAuthorize("authentication.name.equals(#username)")
    public ModelAndView profile(@PathVariable("username") String username, Model model) {

        User user = (User) userDetailsService.loadUserByUsername(username);

        model.addAttribute("user", user);
        //文件服务器的地址返回给客户端
        model.addAttribute("fileServerUrl", fileServerUrl);

        return new ModelAndView("/userspace/profile", "userModel", model);
    }

    /**
     * 保存个人设置
     *
     * @param username
     * @param user
     * @return
     */
    @PostMapping("/{username}/profile")
    @PreAuthorize("authentication.name.equals(#username)")
    public String saveProfile(@PathVariable("username") String username, User user) {

        User originalUser = userService.getUserById(user.getId()).get();

        originalUser.setEmail(user.getEmail());
        originalUser.setName(user.getName());

        //判断密码是否做了更改
        String rawPassword = originalUser.getPassword();

        PasswordEncoder encoder = new BCryptPasswordEncoder();

        String encodePasswd = encoder.encode(user.getPassword());

        boolean isMatch = encoder.matches(rawPassword, encodePasswd);

        if (!isMatch) {
            originalUser.setEncodePassword(user.getPassword());
        }

        userService.saveOrUpdateUser(originalUser);

        return "redirect:/u/" + username + "/profile";
    }

    /**
     * 获取编辑头像界面
     *
     * @param username
     * @param model
     * @return
     */
    @GetMapping("/{username}/avatar")
    @PreAuthorize("authentication.name.equals(#username)")
    public ModelAndView avatar(@PathVariable("username") String username, Model model) {

        User user = (User) userDetailsService.loadUserByUsername(username);

        model.addAttribute("user", user);

        return new ModelAndView("/userspace/avatar", "userModel", model);

    }

    @PostMapping("/{username}/avatar")
    @PreAuthorize("authentication.name.equals(#username)")
    public ResponseEntity<Response> saveAvatar(@PathVariable("username") String username, @RequestBody User user) {

        String avatarUrl = user.getAvatar();

        User originalUser = userService.getUserById(user.getId()).get();

        originalUser.setAvatar(avatarUrl);

        userService.saveOrUpdateUser(originalUser);

        return ResponseEntity.ok().body(new Response(true, "处理成功", avatarUrl));
    }

    @GetMapping("/{username}")
    public String userSpace(@PathVariable("username") String username, Model model) {

        User user = (User) userDetailsService.loadUserByUsername(username);

        model.addAttribute("user", user);

        return "redirect:/u/" + username + "/blogs";
    }

    /**
     * 获取用户的博客列表
     *
     * @param username
     * @param order
     * @param catalogId
     * @param keyword
     * @param async
     * @param pageIndex
     * @param pageSize
     * @param model
     * @return
     */
    @GetMapping("/{username}/blogs")
    public String listBlogsByOrder(@PathVariable("username") String username,
                                   @RequestParam(value = "order", required = false, defaultValue = "new") String order,
                                   @RequestParam(value = "catalog", required = false) Long catalogId,
                                   @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
                                   @RequestParam(value = "async", required = false) boolean async,
                                   @RequestParam(value = "pageIndex", required = false, defaultValue = "0") int pageIndex,
                                   @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize, Model model) {
        String hot = "hot";
        String new1 = "new";
        User user = (User) userDetailsService.loadUserByUsername(username);

        Page<Blog> page = null;

        //分类查询
        if (catalogId != null && catalogId > 0) {
            /*  这部分是书上的代码
            Catalog catalog = catalogService.getCatalogById(catalogId);
            Pageable pageable = new PageRequest(pageIndex, pageSize);
            page = blogService.listBlogsByCatalog(catalog, pageable);
            order = "";
            */

            //下面的是作者源码
            Optional<Catalog> optionalCatalog = catalogService.getCatalogById(catalogId);

            Catalog catalog = null;

            if (optionalCatalog.isPresent()) {

                catalog = optionalCatalog.get();

                Pageable pageable = PageRequest.of(pageIndex, pageSize);

                page = blogService.listBlogsByCatalog(catalog, pageable);

                order = "";
            }

        }
        //最热查询
        else if (order.equals(hot)) {
            Sort sort = new Sort(Sort.Direction.DESC, "readSize", "commentSize", "voteSize");

            Pageable pageable = PageRequest.of(pageIndex, pageSize, sort);

            page = blogService.listBlogsByTitleVoteAndSort(user, keyword, pageable);

        }
        //最新查询
        else if (order.equals(new1)) {

            Pageable pageable = PageRequest.of(pageIndex, pageSize);

            page = blogService.listBlogsByTitleVote(user, keyword, pageable);
        }

        //当前所在页面的数据列表
        List<Blog> list = page.getContent();

        model.addAttribute("user", user);
        model.addAttribute("order", order);
        model.addAttribute("catalogId", catalogId);
        model.addAttribute("keyword", keyword);
        model.addAttribute("page", page);
        model.addAttribute("blogList", list);

        return (async == true ? "/userspace/u :: #mainContainerReplace" : "/userspace/u");
    }

    /**
     * 获取博客展示界面
     *
     * @param username
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/{username}/blogs/{id}")
    public String getBlogById(@PathVariable("username") String username,
                              @PathVariable("id") Long id, Model model) {

        User principal = null;

        Optional<Blog> blog = blogService.getBlogById(id);

        //每次读取，简单的理解为阅读量+1
        blogService.readingIncrease(id);

        boolean isBlogOwner = false;

        if (SecurityContextHolder.getContext().getAuthentication() != null
                && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
                && !SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().equals("anonymousUser")) {

            principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if (principal != null && username.equals(principal.getUsername())) {

                isBlogOwner = true;
            }
        }

        // 判断操作用户的点赞情况
        List<Vote> votes = blog.get().getVotes();

        // 当前用户的点赞情况
        Vote currentVote = null;

        if (principal != null) {
            for (Vote vote : votes) {
                if (vote.getUser().getUsername().equals(principal.getUsername())) {
                    currentVote = vote;
                    break;
                }

            }
        }

        model.addAttribute("isBlogOwner", isBlogOwner);
        model.addAttribute("blogModel", blog.get());

        return "/userspace/blog";
    }

    /**
     * 获取新增博客的界面
     *
     * @param username
     * @param model
     * @return
     */
    @GetMapping("/{username}/blogs/edit")
    public ModelAndView createBlog(@PathVariable("username") String username, Model model) {

        //获取用户分类列表
        User user = (User) userDetailsService.loadUserByUsername(username);
        List<Catalog> catalogs = catalogService.listCatalogs(user);

        model.addAttribute("catalogs", catalogs);
        model.addAttribute("blog", new Blog(null, null, null));
        model.addAttribute("fileServerUrl", fileServerUrl);

        //文件服务器的地址返回给客户端
        return new ModelAndView("/userspace/blogedit", "blogModel", model);
    }

    /**
     * 获取编辑博客的界面
     *
     * @param username
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/{username}/blog/edit")
    public ModelAndView editBlog(@PathVariable("username") String username,
                                 @PathVariable("id") Long id, Model model) {

        //获取用户分类列表
        User user = (User) userDetailsService.loadUserByUsername(username);
        List<Catalog> catalogs = catalogService.listCatalogs(user);

        model.addAttribute("blog", blogService.getBlogById(id).get());
        model.addAttribute("fileServerUrl", fileServerUrl);
        model.addAttribute("catalogs", catalogs);

        return new ModelAndView("/userspace/blogedit", "blogModel", model);
    }

    /**
     * 保存博客
     *
     * @param username
     * @param blog
     * @return
     */
    @PostMapping("/{username}/blogs/edit")
    @PreAuthorize("authentication.name.equals(#username)")
    public ResponseEntity<Response> saveBlog(@PathVariable("username") String username, @RequestBody Blog blog) {

        //对Catalog进行空处理
        if (blog.getCatalog().getId() == null) {
            return ResponseEntity.ok().body(new Response(false, "未选择分类"));
        }
        try {
            //判断是修改还是新增
            if (blog.getId() != null) {

                Optional<Blog> optionalBlog = blogService.getBlogById(blog.getId());

                if (optionalBlog.isPresent()) {

                    Blog orignalBlog = optionalBlog.get();

                    orignalBlog.setTitle(blog.getTitle());
                    orignalBlog.setContent(blog.getContent());
                    orignalBlog.setSummary(blog.getSummary());
                    orignalBlog.setCatalog(blog.getCatalog());
                    orignalBlog.setTags(blog.getTags());

                    blogService.saveBlog(orignalBlog);
                }
            } else {

                User user = (User) userDetailsService.loadUserByUsername(username);

                blog.setUser(user);
                blogService.saveBlog(blog);
            }
        } catch (ConstraintViolationException e) {

            return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
        } catch (Exception e) {

            return ResponseEntity.ok().body(new Response(false, e.getMessage()));
        }

        String redirectUrl = "/u/" + username + "/blogs/" + blog.getId();

        return ResponseEntity.ok().body(new Response(true, "处理成功", redirectUrl));

    }

    /**
     * 删除博客
     *
     * @param username
     * @param id
     * @return
     */
    @DeleteMapping("/{username}/blogs/{id}")
    @PreAuthorize("authentication.name.equals(#username)")
    public ResponseEntity<Response> deleteBlog(@PathVariable("username") String username,
                                               @PathVariable("id") Long id) {

        try {
            blogService.removeBlog(id);
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Response(false, e.getMessage()));
        }

        String redirectUrl = "/u/" + username + "/blogs/";

        return ResponseEntity.ok().body(new Response(true, "处理成功", redirectUrl));
    }
}













