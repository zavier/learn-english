package le.zavier.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExamineResultVo {
    private Long id;
    private String correctChinese;
    private String correctEnglish;
    private String userAnswerChinese;
    private String userAnswerEnglish;

}
