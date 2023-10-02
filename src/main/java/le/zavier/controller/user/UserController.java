package le.zavier.controller.user;

import le.zavier.commons.LoginManager;
import le.zavier.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/le/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping(value = "/login")
    public String login(HttpSession session) {
        if (LoginManager.isLogin(session)) {
            return "/knowledge/list";
        }
        return "/user/login";
    }

    @GetMapping(value = "/registry")
    public void registry() {}
}
