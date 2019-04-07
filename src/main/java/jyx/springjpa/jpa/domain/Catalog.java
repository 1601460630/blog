package jyx.springjpa.jpa.domain;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @Author jyxlalala
 * @Date 2019/3/30 23:09
 * @Version 1.0
 */
@Entity
public class Catalog implements Serializable {

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
     * @NotEmpty 不能为空
     * @Size 长度限制
     * @Column 映射为字段，值不能为空
     */
    @NotEmpty(message = "名称不能为空")
    @Size(min = 2, max = 30)
    @Column(nullable = false)
    private String name;

    @OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    protected Catalog() {
    }

    public Catalog(String name, User user) {
        this.name = name;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
