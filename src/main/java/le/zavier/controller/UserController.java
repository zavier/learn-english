package le.zavier.controller;

import javax.jws.soap.SOAPBinding.Use;
import javax.validation.Valid;
import le.zavier.commons.ResultBean;
import le.zavier.pojo.User;
import le.zavier.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserService iUserService;

    @GetMapping(value = "/login")
    public String login() {
        return "/login";
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResultBean login(@RequestBody @Valid User user) {
        User login = iUserService.login(user);
        if (login == null) {
            return ResultBean.createByErrorMessage("用户不存在或密码错误");
        } else {
            return ResultBean.createBySuccess(login);
        }
    }

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResultBean register(@RequestBody @Valid User user) {
        iUserService.register(user);
        return ResultBean.createBySuccessMessage("用户注册成功");
    }
}
