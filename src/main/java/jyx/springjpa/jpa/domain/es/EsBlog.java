package jyx.springjpa.jpa.domain.es;

import jyx.springjpa.jpa.domain.Blog;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @Author jyxlalala
 * @Date 2019/3/31 14:11
 * @Version 1.0
 */
@Document(indexName = "blog", type = "blog")
public class EsBlog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Id
    private String id;

    /**
     * Blog实体的id
     */
    @Field(type = FieldType.Long)
    private Long blogId;

    @Field(type = FieldType.Text)
    private String title;

    @Field(type = FieldType.Text)
    private String summary;

    @Field(type = FieldType.Text)
    private String content;

    /**
     * index =false 不做全文检索的字段
     * index的默认值为true
     */
    @Field(type = FieldType.Keyword, index = false)
    private String username;

    @Field(type = FieldType.Text, index = false)
    private String avatar;

    @Field(type = FieldType.Date, index = false)
    private Timestamp createTime;

    @Field(type = FieldType.Integer, index = false)
    private Integer readSize = 0;

    @Field(type = FieldType.Integer, index = false)
    private Integer commentSize = 0;

    @Field(type = FieldType.Integer, index = false)
    private Integer voteSize = 0;

    @Field(type = FieldType.Text, fielddata = true, searchAnalyzer = "ik_smart", analyzer = "ik_smart")
    private String tags;

    protected EsBlog() {
    }

    public EsBlog(Long blogId, String title, String summary, String content, String username, String avatar,
                  Timestamp createTime, Integer readSize, Integer commentSize, Integer voteSize, String tags) {
        this.blogId = blogId;
        this.title = title;
        this.summary = summary;
        this.content = content;
        this.username = username;
        this.avatar = avatar;
        this.createTime = createTime;
        this.readSize = readSize;
        this.commentSize = commentSize;
        this.voteSize = voteSize;
        this.tags = tags;
    }

    public EsBlog(Blog blog) {
        this.blogId = blogId;
        this.title = title;
        this.summary = summary;
        this.content = content;
        this.username = username;
        this.avatar = avatar;
        this.createTime = createTime;
        this.readSize = readSize;
        this.commentSize = commentSize;
        this.voteSize = voteSize;
        this.tags = tags;
    }

    public void update(Blog blog) {
        this.blogId = blogId;
        this.title = title;
        this.summary = summary;
        this.content = content;
        this.username = username;
        this.avatar = avatar;
        this.createTime = createTime;
        this.readSize = readSize;
        this.commentSize = commentSize;
        this.voteSize = voteSize;
        this.tags = tags;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getblogId() {
        return blogId;
    }

    public void setblogId(Long blogId) {
        this.blogId = blogId;
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
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
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

    @Override
    public String toString() {
        return String.format(
                "EsBlog[blogId=%d, title='%s', summary='%s']",
                blogId, title, summary);
    }

}
