package le.zavier.service;

import java.util.Objects;
import le.zavier.dao.UserMapper;
import le.zavier.exception.CheckException;
import le.zavier.pojo.User;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserMapper userMapper;

    public void register(User user) {
        logger.info("注册用户{}", user.toString());
        hasRegisted(user);
        int i = userMapper.insert(user);
        if (i == 0) {
            logger.error("添加用户{}失败", user.toString());
            throw new CheckException("添加用户:" + user.toString() + "失败");
        }
        logger.info("添加用户{}成功", user.toString());
    }

    /**
     * 检查用户的账号和邮箱是否已经注册过，如果注册过则抛出异常，否则什么也不做
     * @param user
     */
    private void hasRegisted(User user) {
        int count = userMapper.countByAccountOrEmail(user);
        if (count > 0) {
            logger.error("账号或邮箱已注册{}", user.toString());
            throw new CheckException("账号或邮箱已注册");
        }
        logger.info("账号或邮箱未注册，可以注册{}", user.toString());
    }

    public User login(User user) {
        checkLoginUserInfo(user);
        User resultUser = userMapper.selectByAccountOrEmailAndPassword(user);
        if (resultUser != null) {
            logger.info("{}登录成功", user.toString());
            return resultUser;
        } else {
            logger.info("{}登录失败，用户不存在或密码错误", user.toString());
            return null;
        }
    }

    /**
     * 检查登录时的用户信息是否完整
     * @param user
     */
    private void checkLoginUserInfo(User user) {
        Objects.requireNonNull(user, "登录用户信息为空");
        Objects.requireNonNull(user.getPassword(), "密码不能为空");
        if (StringUtils.isBlank(user.getAccount()) && StringUtils.isBlank(user.getEmail())) {
            throw new CheckException("参数不正确，登录时账户和Email不能同时为空");
        }
        if (StringUtils.isNotBlank(user.getAccount()) && StringUtils.isNotBlank(user.getEmail())) {
            throw new CheckException("参数不正确，登录时账户和Email不能同时存在");
        }
    }
}
