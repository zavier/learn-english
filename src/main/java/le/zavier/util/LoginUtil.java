package le.zavier.util;

import javax.servlet.http.HttpSession;
import le.zavier.commons.Const;
import le.zavier.exception.CheckException;
import le.zavier.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginUtil {
    private static final Logger logger = LoggerFactory.getLogger(LoginUtil.class);

    public static User getLoginUser(HttpSession session) {
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null) {
            throw new CheckException("当前用户未登录");
        }
        return currentUser;
    }

    public static void logout(HttpSession session) {
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null) {
            logger.warn("用户未登录，不需要退出");
        } else {
            session.removeAttribute(Const.CURRENT_USER);
            logger.info("{}已退出", currentUser.toString());
        }
    }
}
