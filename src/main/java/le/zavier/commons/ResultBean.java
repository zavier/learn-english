package le.zavier.commons;

import java.io.Serializable;
import lombok.Data;

@Data
public class ResultBean<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    public interface ResultCode {
        int SUCCESS = 0;
        int ERROR = 1;
    }

    private int code = ResultCode.SUCCESS;
    private String msg = "SUCCESS";
    private T data;

    public ResultBean() {
    }

    public ResultBean(int code) {
        this.code = code;
    }

    public ResultBean(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultBean(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public ResultBean(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResultBean(T data) {
        this.data = data;
    }

    public ResultBean(Throwable e) {
        super();
        this.msg = e.toString();
        this.code = ResultCode.ERROR;
    }

    public static <T> ResultBean<T> createBySuccess() {
        return new ResultBean<T>(ResultCode.SUCCESS);
    }

    public static <T> ResultBean<T> createBySuccessMessage(String msg) {
        return new ResultBean<T>(ResultCode.SUCCESS, msg);
    }

    public static <T> ResultBean<T> createBySuccess(T data) {
        return new ResultBean<T>(ResultCode.SUCCESS, data);
    }

    public static <T> ResultBean<T> createBySuccess(String msg, T data) {
        return new ResultBean<T>(ResultCode.SUCCESS, msg, data);
    }

    public static <T> ResultBean<T> createByError(){
        return new ResultBean<T>(ResultCode.ERROR);
    }

    public static <T> ResultBean<T> createByErrorMessage(String errorMessage){
        return new ResultBean<T>(ResultCode.ERROR,errorMessage);
    }
}
