package jyx.springjpa.jpa.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;

/**
 * 权限.
 *
 * @author jyx
 * @date 2019/3/24
 */
@Entity
public class Authority implements GrantedAuthority {

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

    /**
     * @Column(nullable = false) 映射为字段，值不能为空
     */
    @Column(nullable = false)
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getAuthority() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
