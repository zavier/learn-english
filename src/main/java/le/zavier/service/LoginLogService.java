package le.zavier.service;

import java.util.Date;
import le.zavier.dao.LoginLogMapper;
import le.zavier.pojo.LoginLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 登录日志Service
 */
@Service
public class LoginLogService {
    private static final Logger logger = LoggerFactory.getLogger(LoginLogService.class);

    @Autowired
    private LoginLogMapper loginLogMapper;

    public void saveLoginLog(LoginLog loginLog) {
        loginLogMapper.insert(loginLog);
    }

    public LoginLog generateLoginLog(long userId, String ip) {
        return new LoginLog(userId, ip, new Date());
    }
}
