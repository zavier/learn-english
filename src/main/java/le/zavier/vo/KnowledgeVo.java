package le.zavier.vo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

@Data
@NoArgsConstructor
public class KnowledgeVo implements Serializable{
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
}
