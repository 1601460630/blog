package jyx.springjpa.jpa.domain;


import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 评论
 *
 * @Author jyxlalala
 * @Date 2019/3/28 21:25
 * @Version 1.0
 */
@Entity
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户的唯一标识
     *
     * @Id 主键
     * @GeneratedValue (strategy = GenerationType.IDENTITY) // 自增长策略
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 评论内容不能为空
     *
     * @NotEmpty 不能为空
     * @Size 长度限制
     * @Column 映射为字段，值不能为空
     */
    @NotEmpty(message = "评论内容不能为空")
    @Size(min = 2, max = 500)
    @Column(nullable = false)
    private String content;

    /**
     * @OneToOne //建立一对一关系
     * @JoinColumn (name = " user_id ")
     */
    @OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * @Column(nullable = false)
     * @CreationTimestamp //由数据库自动创建时间
     */
    @Column(nullable = false)
    @CreationTimestamp
    private Timestamp creatteime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    protected Comment() {
    }

    public Comment(User user, String content) {
        this.content = content;
        this.user = user;
    }

    public Timestamp getCreatteime() {
        return creatteime;
    }
}
