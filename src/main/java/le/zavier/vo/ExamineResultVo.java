package le.zavier.vo;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExamineResultVo {
    private static final Logger logger = LoggerFactory.getLogger(ExamineResultVo.class);

    private Long id;
    private String correctChinese;
    private String correctEnglish;
    private String userAnswerChinese;
    private String userAnswerEnglish;
    private Boolean correct;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCorrectChinese() {
        return correctChinese;
    }

    public void setCorrectChinese(String correctChinese) {
        this.correctChinese = correctChinese;
    }

    public String getCorrectEnglish() {
        return correctEnglish;
    }

    public void setCorrectEnglish(String correctEnglish) {
        this.correctEnglish = correctEnglish;
    }

    public String getUserAnswerChinese() {
        return userAnswerChinese;
    }

    public void setUserAnswerChinese(String userAnswerChinese) {
        this.userAnswerChinese = userAnswerChinese;
    }

    public String getUserAnswerEnglish() {
        return userAnswerEnglish;
    }

    public void setUserAnswerEnglish(String userAnswerEnglish) {
        this.userAnswerEnglish = userAnswerEnglish;
    }

    public Boolean getCorrect() {
        return correct;
    }

    public void setCorrect(Boolean correct) {
        this.correct = correct;
    }

    public void setAnswerResult() {
        if (StringUtils.isNotBlank(userAnswerEnglish) && StringUtils.isNotBlank(correctEnglish)) {
            char[] correntEnglishChar = correctEnglish.trim().toCharArray();
            char[] answerEnglishChar = userAnswerEnglish.trim().toCharArray();
            correct = isAlphabetSame(correntEnglishChar, answerEnglishChar);
        } else {
            correct = false;
        }
    }

    private boolean isAlphabetSame(char[] c1, char[] c2) {
        String s1 = getAlphabetString(c1);
        String s2 = getAlphabetString(c2);
        return s1.equalsIgnoreCase(s2);
    }

    private String getAlphabetString(char[] c1) {
        StringBuilder string1 = new StringBuilder(c1.length);
        for (int i = 0; i < c1.length ; i++) {
            if (isAlphabet(c1[i])) {
                string1.append(c1[i]);
            }
        }
        return string1.toString();
    }

    private boolean isAlphabet(char c) {
        String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        if (alphabet.indexOf(c) != -1) {
            return true;
        }
        return false;
    }
}
