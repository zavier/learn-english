package le.zavier.pojo;

/**
 * 百度翻译API，返回结果对应的实体类
 */
public class TransResult {
    // 翻译源语言
    private String from;
    // 翻译目标语言
    private String to;

    // 原文
    private String src;
    // 译文
    private String dst;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getDst() {
        return dst;
    }

    public void setDst(String dst) {
        this.dst = dst;
    }
}
