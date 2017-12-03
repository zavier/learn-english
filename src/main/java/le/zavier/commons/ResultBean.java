package le.zavier.commons;

import java.io.Serializable;
import lombok.Data;

@Data
public class ResultBean<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    public interface Code {
        int SUCCESS = 0;
        int FAIL = 1;
    }

    private int code = Code.SUCCESS;
    private String msg;
    private T data;

    public ResultBean() {
        super();
    }

    public ResultBean(T data) {
        super();
        this.data = data;
    }

    public ResultBean(Throwable e) {
        super();
        this.msg = e.toString();
        this.code = Code.FAIL;
    }
}
