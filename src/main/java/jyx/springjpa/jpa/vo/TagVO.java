package jyx.springjpa.jpa.vo;

/**
 * @Author jyxlalala
 * @Date 2019/3/31 15:07
 * @Version 1.0
 */
public class TagVO {
    private String name;
    private Long count;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public TagVO(String name, Long count) {
        this.name = name;
        this.count = count;
    }
}
