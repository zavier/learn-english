package le.zavier.sentence.model;

import java.io.Serializable;
import lombok.Data;

/**
 * @author 
 */
@Data
public class Sentence implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 中文
     */
    private String chinese;

    /**
     * 英文
     */
    private String english;

    /**
     * 备注
     */
    private String remark;

    public Sentence() {
    }

    public Sentence(String chinese, String english) {
        this.chinese = chinese;
        this.english = english;
    }
}