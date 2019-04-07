package jyx.springjpa.jpa.domain;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @Author jyxlalala
 * @Date 2019/3/30 14:42
 * @Version 1.0
 */
@Entity
public class Vote implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户的唯一标识
     *
     * @Id 主键
     * @GeneratedValue(strategy = GenerationType.IDENTITY) // 自增长策略
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade =  CascadeType.DETACH,fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * @Column 映射为字段，值不能为空
     * @CreationTimestamp 由数据库自动创建时间
     */
    @Column(nullable = false)
    @CreationTimestamp
    private Timestamp createTime;

    protected Vote(){}

    public Vote(User user) {
        this.user = user;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
