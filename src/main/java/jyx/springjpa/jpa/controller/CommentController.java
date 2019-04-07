package jyx.springjpa.jpa.controller;

import jyx.springjpa.jpa.domain.Blog;
import jyx.springjpa.jpa.domain.Comment;
import jyx.springjpa.jpa.domain.User;
import jyx.springjpa.jpa.service.BlogService;
import jyx.springjpa.jpa.service.CommentService;
import jyx.springjpa.jpa.util.ConstraintViolationExceptionHandler;
import jyx.springjpa.jpa.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

/**
 * @Author jyxlalala
 * @Date 2019/3/28 22:06
 * @Version 1.0
 */
@Controller
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private CommentService commentService;

    /**
     * ��ȡ�����б�
     *
     * @param blogId
     * @param model
     * @return
     */
    @GetMapping()
    public String listComments(@RequestParam(value = "blogId", required = true) Long blogId, Model model) {
        Optional<Blog> optionalBlog = blogService.getBlogById(blogId);
        List<Comment> comments = null;

        if (optionalBlog.isPresent()) {
            comments = optionalBlog.get().getComments();
        }

        //�жϲ����û��Ƿ�Ϊ���۵�������
        String commentOwner = "";

        if (SecurityContextHolder.getContext().getAuthentication() != null
                && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
                && !SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().equals("anonymousUser")) {

            User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if (principal != null) {
                commentOwner = principal.getUsername();
            }

        }

        model.addAttribute("commentOwner", commentOwner);
        model.addAttribute("comments", comments);

        return "/userspace/blog :: #mainContainerReplace";
    }

    /**
     * ��������
     *
     * @param blogId
     * @param commentContent
     * @return
     * @PreAuthorize (" hasAnyAuthority ( ' ROLE_ADMIN ', ' ROLE_USER ')") //ָ����ɫȨ�޲��ܲ�������
     */
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<Response> createComment(Long blogId, String commentContent) {

        try {
            blogService.createComment(blogId, commentContent);
        } catch (ConstraintViolationException e) {
            return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
        } catch (Exception e) {
            return ResponseEntity.ok().body(new Response(false, e.getMessage()));
        }

        return ResponseEntity.ok().body(new Response(true, "����ɹ�", null));

    }

    /**
     * @param id
     * @param blogId
     * @return
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<Response> delete(@PathVariable("id") Long id, Long blogId) {

        boolean isOwner = false;

        Optional<Comment> optionalComment = commentService.getCommentById(id);

        User user = null;

        if (optionalComment.isPresent()) {
            user = optionalComment.get().getUser();
        } else {
            return ResponseEntity.ok().body(new Response(false, "�����ڸ�����!"));
        }

        //�жϲ����û��Ƿ�Ϊ���۵�������
        if (SecurityContextHolder.getContext().getAuthentication() != null
                && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
                && !SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().equals("anonymousUser")) {

            User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if (principal != null && user.getPassword().equals(principal.getUsername())) {

                isOwner = true;
            }

            if (!isOwner) {
                return ResponseEntity.ok().body(new Response(false, "û�в���Ȩ��!"));
            }

            try {
                blogService.removeComment(blogId, id);
                commentService.removeComment(id);
            } catch (ConstraintViolationException e) {
                return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
            } catch (Exception e) {
                return ResponseEntity.ok().body(new Response(false, e.getMessage()));
            }
        }

        return ResponseEntity.ok().body(new Response(true, "����ɹ�", null));
    }
}









