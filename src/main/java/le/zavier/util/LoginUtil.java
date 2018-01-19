package le.zavier.util;

import javax.servlet.http.HttpSession;
import le.zavier.exception.CheckException;
import le.zavier.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 登录工具类
 *
 * @author zhengwei
 */
public class LoginUtil {
    private static final Logger logger = LoggerFactory.getLogger(LoginUtil.class);
    
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

    public static void saveLoginStatus(HttpSession session, User user) {
        session.setAttribute(CURRENT_USER, user);
        logger.info("用户{}登录成功", user.getAccount());
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
}
