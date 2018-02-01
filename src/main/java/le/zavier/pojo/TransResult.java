package le.zavier.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 百度翻译API，返回结果对应的实体类
 */
@Data
@NoArgsConstructor
public class TransResult {
    // 翻译源语言
    private String from;
    // 翻译目标语言
    private String to;

    // 原文
    private String src;
    // 译文
    private String dst;
}
