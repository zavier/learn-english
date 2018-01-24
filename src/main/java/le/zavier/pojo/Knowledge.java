package le.zavier.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
public class Knowledge implements Serializable{
    private static final long serialVersionUID = 1L;

    private Long id;

    @Length(min = 1, max = 200)
    private String chinese;

    @Length(min = 1, max = 200)
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

}