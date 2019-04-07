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
     * �û���Ψһ��ʶ
     *
     * @Id ����
     * @GeneratedValue(strategy = GenerationType.IDENTITY) // ����������
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade =  CascadeType.DETACH,fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * @Column ӳ��Ϊ�ֶΣ�ֵ����Ϊ��
     * @CreationTimestamp �����ݿ��Զ�����ʱ��
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
