package le.zavier.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import le.zavier.commons.ResultBean;
import le.zavier.pojo.User;
import le.zavier.service.UserService;
import le.zavier.util.LoginUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserRestController {
    private static final Logger logger = LoggerFactory.getLogger(UserRestController.class);

    @Autowired
    private LoginUtil loginUtil;
    @Autowired
    private UserService userService;

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultBean login(HttpServletRequest request, @RequestBody @Valid User user) {
        User loginUser = userService.login(user);
        if (loginUser == null) {
            return ResultBean.createByErrorMessage("用户不存在或密码错误");
        } else {
            loginUtil.saveLoginStatus(request, loginUser);
            return ResultBean.createBySuccess(loginUser);
        }
    }

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultBean register(@RequestBody @Valid User user) {
        userService.register(user);
        return ResultBean.createBySuccessMessage("用户注册成功");
    }

    @GetMapping(value = "/logout", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultBean logout(HttpSession session) {
        LoginUtil.logout(session);
        return ResultBean.createBySuccessMessage("退出成功");
    }
}
