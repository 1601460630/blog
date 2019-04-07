package jyx.springjpa.jpa.domain;

import com.github.rjeschke.txtmark.Processor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * @Author jyxlalala
 * @Date 2019/3/26 16:08
 * @Version 1.0
 */
@Entity
public class Blog implements Serializable {

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

    /**
     * ���ⲻ��Ϊ��
     *
     * @NotEmpty ����Ϊ��
     * @Size ��������
     * @Column ӳ��Ϊ�ֶΣ�ֵ����Ϊ��
     */
    @NotEmpty(message = "���ⲻ��Ϊ��")
    @Size(min = 2, max = 50)
    @Column(nullable = false, length = 50)
    private String title;

    /**
     * ժҪ����Ϊ��
     *
     * @NotEmpty ����Ϊ��
     * @Size ��������
     * @Column ӳ��Ϊ�ֶΣ�ֵ����Ϊ��
     */
    @NotEmpty(message = "ժҪ����Ϊ��")
    @Size(min = 2, max = 300)
    @Column(nullable = false, length = 50)
    private String summary;

    /**
     * @Lob �����ӳ��MySQL��LONG Text����
     * @Basic ������
     * @NotEmpty
     * @Size
     * @Column ӳ��Ϊ�ֶΣ�ֵ����Ϊ��
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @NotEmpty(message = "���ݲ���Ϊ��")
    @Size(min = 2)
    @Column(nullable = false)
    private String content;

    /**
     * �� md תΪ html
     *
     * @Lob �����ӳ��MySQL��LONG Text����
     * @Basic ������
     * @NotEmpty
     * @Size
     * @Column ӳ��Ϊ�ֶΣ�ֵ����Ϊ��
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @NotEmpty(message = "���ݲ���Ϊ��")
    @Size(min = 2)
    @Column(nullable = false)
    private String htmlContent;

    /**
     * @Column ӳ��Ϊ�ֶΣ�ֵ����Ϊ��
     * @CreationTimestamp �����ݿ��Զ�����ʱ��
     */
    @Column(nullable = false)
    @CreationTimestamp
    private Timestamp createTime;


    /**
     * ���������Ķ���
     */
    @Column(name = "readSize")
    private Integer readSize = 0;

    /**
     * ������
     */
    @Column(name = "commentSize")
    private Integer commentSize = 0;

    /**
     * ������
     */
    @Column(name = "voteSize")
    private Integer voteSize = 0;

    /**
     * ��ǩ
     */
    @Column(name = "tags", length = 100)
    private String tags;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * ����һ�Զ��ϵ��hibernate�����֪ʶ
     *
     * @OneToMany (cascade = CascadeType.ALL, fetch = FetchType.LAZY) //�������õ���FetchType.EAGER���������������ᱨ��
     * ����hibernate��ص����⣬��һ�Զ�Ͷ�Զ�������Ӧ��ʹ��Set���飬������List���飬���г��Ըĳ�Set������ʧ���ˣ��Ͳ���FetchType.LAZY����ȡ�ɵķ���
     * �����ַ������ܳ�����������������ڻ�δ֪
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "blog_comment", joinColumns = @JoinColumn(name = "blog_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "comment_id", referencedColumnName = "id"))
    private List<Comment> comments;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "blog_vote", joinColumns = @JoinColumn(name = "blog_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "comment_id", referencedColumnName = "id"))
    private List<Vote> votes;

    @OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "catalog_id")
    private Catalog catalog;

    protected Blog() {
    }

    public Blog(String title, String summary, String content) {
        this.title = title;
        this.summary = summary;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        // ��Markdown ����תΪ HTML ��ʽ.
        this.htmlContent = Processor.process(content);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public Integer getReadSize() {
        return readSize;
    }

    public void setReadSize(Integer readSize) {
        this.readSize = readSize;
    }

    public Integer getCommentSize() {
        return commentSize;
    }

    public void setCommentSize(Integer commentSize) {
        this.commentSize = commentSize;
    }

    public Integer getVoteSize() {
        return voteSize;
    }

    public void setVoteSize(Integer voteSize) {
        this.voteSize = voteSize;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
        this.commentSize = this.comments.size();
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    /**
     * �������
     *
     * @param comment
     */
    public void addComment(Comment comment) {

        this.comments.add(comment);
        this.commentSize = this.comments.size();
    }

    /**
     * ɾ������
     *
     * @param commentId
     */
    public void removeComment(Long commentId) {

        for (int index = 0; index < this.comments.size(); index++) {
            if (comments.get(index).getId() == commentId) {
                this.comments.remove(index);
                break;
            }

        }

        /*for (Comment comment : comments) {
            if (comment.getId() == commentId) {
                this.comments.remove(comment);
                break;
            }
        }*/
        this.commentSize = this.comments.size();
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    /**
     * ����
     *
     * @param vote
     * @return
     */
    public boolean addVote(Vote vote) {

        boolean isExist = false;
        //�ж��ظ�
        for (int index = 0; index < this.votes.size(); index++) {

            if (this.votes.get(index).getUser().getId() == vote.getUser().getId()) {

                isExist = true;
                break;
            }

            if (!isExist) {
                this.votes.add(vote);
                this.voteSize = this.votes.size();
            }

        }

        return isExist;
    }

    /**
     * ȡ������
     * @param voteId
     */
    public void removeVote(Long voteId) {
        for (int index = 0; index < this.votes.size(); index++) {
            if (this.votes.get(index).getId() == voteId) {
                this.votes.remove(index);
                break;
            }
        }

        this.voteSize = this.votes.size();
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", content='" + content + '\'' +
                ", htmlContent='" + htmlContent + '\'' +
                ", user=" + user +
                ", createTime=" + createTime +
                ", readSize=" + readSize +
                ", commentSize=" + commentSize +
                ", voteSize=" + voteSize +
                ", tags='" + tags + '\'' +
                '}';
    }
}












