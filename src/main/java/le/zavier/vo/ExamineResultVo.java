package le.zavier.vo;

import lombok.Data;

@Data
public class ExamineResultVo {
    private Long id;
    private String correctChinese;
    private String correctEnglish;
    private String userAnswerChinese;
    private String userAnswerEnglish;

}
