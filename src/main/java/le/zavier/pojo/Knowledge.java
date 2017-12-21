package le.zavier.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;

@ToString
public class Knowledge implements Serializable{
    private static final long serialVersionUID = 1L;

    private Long id;

    @NotEmpty
    private String chinese;

    @NotEmpty
    private String english;

    /**
     * 0-单词 1-短语 2-句子
     */
    private Short type;

    private String knowledge;

    private String tag;

    private Long createUserId;

    private Long updateUserId;

    private Date createTime;

    private Date updateTime;

    public Knowledge(Long id, String chinese, String english, Short type, String knowledge,
        String tag, Long createUserId, Long updateUserId, Date createTime, Date updateTime) {
        this.id = id;
        this.chinese = chinese;
        this.english = english;
        this.type = type;
        this.knowledge = knowledge;
        this.tag = tag;
        this.createUserId = createUserId;
        this.updateUserId = updateUserId;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Knowledge() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChinese() {
        return chinese;
    }

    public void setChinese(String chinese) {
        this.chinese = chinese == null ? null : chinese.trim();
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english == null ? null : english.trim();
    }

    /**
     * 0-单词 1-短语 2-句子
     */
    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public String getKnowledge() {
        return knowledge;
    }

    public void setKnowledge(String knowledge) {
        this.knowledge = knowledge == null ? null : knowledge.trim();
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag == null ? null : tag.trim();
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Long getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Long updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}