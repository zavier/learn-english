package le.zavier.commons;

import le.zavier.commons.ResultBean.ResultCode;
import le.zavier.exception.CheckException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 处理和包装异常
 *
 */
@Aspect
@Component
public class ControllerAOP {
    private static final Logger logger = LoggerFactory.getLogger(ControllerAOP.class);

    @Around("execution(public le.zavier.commons.ResultBean *(..))")
    public Object handlerControllerMethod(ProceedingJoinPoint pjp) {
        long startTime = System.currentTimeMillis();

        ResultBean<?> result;

        try {
            result = (ResultBean<?>) pjp.proceed();
            logger.info(pjp.getSignature() + " use time: " + (System.currentTimeMillis() - startTime) + "ms");
        } catch (Throwable e) {
            result = handlerException(pjp, e);
        }

        return result;
    }

    private ResultBean<?> handlerException(ProceedingJoinPoint pjp, Throwable e) {
        ResultBean<?> result = new ResultBean();

        // 已知异常
        if (e instanceof CheckException) {
            result.setMsg(e.getLocalizedMessage());
            result.setCode(ResultCode.ERROR);
        } else {
            logger.error(pjp.getSignature() + " error ", e);
            //TODO 未知的异常，应该格外注意，可以发送邮件通知等
            result.setMsg(e.toString());
            result.setCode(ResultCode.ERROR);
        }

        return result;
    }
}
