package jyx.springjpa.jpa.domain;


import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * ����
 *
 * @Author jyxlalala
 * @Date 2019/3/28 21:25
 * @Version 1.0
 */
@Entity
public class Comment implements Serializable {

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
     * �������ݲ���Ϊ��
     *
     * @NotEmpty ����Ϊ��
     * @Size ��������
     * @Column ӳ��Ϊ�ֶΣ�ֵ����Ϊ��
     */
    @NotEmpty(message = "�������ݲ���Ϊ��")
    @Size(min = 2, max = 500)
    @Column(nullable = false)
    private String content;

    /**
     * @OneToOne //����һ��һ��ϵ
     * @JoinColumn (name = " user_id ")
     */
    @OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * @Column(nullable = false)
     * @CreationTimestamp //�����ݿ��Զ�����ʱ��
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
