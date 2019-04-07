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
     * �û���Ψһ��ʶ
     *
     * @Id ����
     * @GeneratedValue (strategy = GenerationType.IDENTITY) // ����������
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * @NotEmpty ����Ϊ��
     * @Size ��������
     * @Column ӳ��Ϊ�ֶΣ�ֵ����Ϊ��
     */
    @NotEmpty(message = "���Ʋ���Ϊ��")
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
