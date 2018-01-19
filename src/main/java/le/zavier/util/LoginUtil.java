package le.zavier.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import le.zavier.exception.CheckException;
import le.zavier.pojo.LoginLog;
import le.zavier.pojo.User;
import le.zavier.service.LoginLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 登录工具类
 *
 * @author zhengwei
 */
@Component
public class LoginUtil {
    private static final Logger logger = LoggerFactory.getLogger(LoginUtil.class);

    @Autowired
    private LoginLogService loginLogService;
    
    private static final String CURRENT_USER = "CURRENT_USER";

    public static User getLoginUser(HttpSession session) {
        User currentUser = (User) session.getAttribute(CURRENT_USER);
        if (currentUser == null) {
            throw new CheckException("当前用户未登录");
        }
        return currentUser;
    }
    
    public static boolean isLogin(HttpSession session) {
        Object attribute = session.getAttribute(CURRENT_USER);
        return attribute == null ? false : true;
    }

    public static void logout(HttpSession session) {
        User currentUser = (User) session.getAttribute(CURRENT_USER);
        if (currentUser == null) {
            logger.warn("用户未登录，不需要退出");
        } else {
            session.removeAttribute(CURRENT_USER);
            logger.info("{}已退出", currentUser.getAccount());
        }
    }

    /**
     * 记录登录状态及日志
     * @param request
     * @param user
     */
    public void saveLoginStatus(HttpServletRequest request, User user) {
        HttpSession session = request.getSession();
        session.setAttribute(CURRENT_USER, user);
        String ip = request.getRemoteAddr();
        long userId = user.getId();
        loginLogService.saveLoginLog(generateLoginLog(userId, ip));
        logger.info("用户{}登录成功", user.getAccount());
    }

    private LoginLog generateLoginLog(long userId, String ip) {
        return loginLogService.generateLoginLog(userId, ip);
    }
}
