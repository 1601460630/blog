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
     * 用户的唯一标识
     *
     * @Id 主键
     * @GeneratedValue(strategy = GenerationType.IDENTITY) // 自增长策略
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 标题不能为空
     *
     * @NotEmpty 不能为空
     * @Size 长度限制
     * @Column 映射为字段，值不能为空
     */
    @NotEmpty(message = "标题不能为空")
    @Size(min = 2, max = 50)
    @Column(nullable = false, length = 50)
    private String title;

    /**
     * 摘要不能为空
     *
     * @NotEmpty 不能为空
     * @Size 长度限制
     * @Column 映射为字段，值不能为空
     */
    @NotEmpty(message = "摘要不能为空")
    @Size(min = 2, max = 300)
    @Column(nullable = false, length = 50)
    private String summary;

    /**
     * @Lob 大对象，映射MySQL的LONG Text类型
     * @Basic 懒加载
     * @NotEmpty
     * @Size
     * @Column 映射为字段，值不能为空
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @NotEmpty(message = "内容不能为空")
    @Size(min = 2)
    @Column(nullable = false)
    private String content;

    /**
     * 将 md 转为 html
     *
     * @Lob 大对象，映射MySQL的LONG Text类型
     * @Basic 懒加载
     * @NotEmpty
     * @Size
     * @Column 映射为字段，值不能为空
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @NotEmpty(message = "内容不能为空")
    @Size(min = 2)
    @Column(nullable = false)
    private String htmlContent;

    /**
     * @Column 映射为字段，值不能为空
     * @CreationTimestamp 由数据库自动创建时间
     */
    @Column(nullable = false)
    @CreationTimestamp
    private Timestamp createTime;


    /**
     * 访问量、阅读量
     */
    @Column(name = "readSize")
    private Integer readSize = 0;

    /**
     * 评论量
     */
    @Column(name = "commentSize")
    private Integer commentSize = 0;

    /**
     * 点赞量
     */
    @Column(name = "voteSize")
    private Integer voteSize = 0;

    /**
     * 标签
     */
    @Column(name = "tags", length = 100)
    private String tags;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * 创建一对多关系，hibernate的相关知识
     *
     * @OneToMany (cascade = CascadeType.ALL, fetch = FetchType.LAZY) //在书中用的是FetchType.EAGER，但若如此做程序会报错，
     * 这是hibernate相关的问题，在一对多和多对多的情况中应该使用Set数组，而不是List数组，我有尝试改成Set，但是失败了，就采用FetchType.LAZY这种取巧的方法
     * 用这种方法可能程序会有隐患，但现在还未知
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
        // 将Markdown 内容转为 HTML 格式.
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
     * 添加评论
     *
     * @param comment
     */
    public void addComment(Comment comment) {

        this.comments.add(comment);
        this.commentSize = this.comments.size();
    }

    /**
     * 删除评论
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
     * 点赞
     *
     * @param vote
     * @return
     */
    public boolean addVote(Vote vote) {

        boolean isExist = false;
        //判断重复
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
     * 取消点赞
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












