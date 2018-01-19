package le.zavier.controller.examination;

import java.util.List;
import javax.servlet.http.HttpSession;
import le.zavier.commons.ResultBean;
import le.zavier.pojo.Knowledge;
import le.zavier.service.KnowledgeService;
import le.zavier.util.LoginUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/examination")
public class ExaminationRestController {
    private static final Logger logger = LoggerFactory.getLogger(ExaminationRestController.class);

    @Autowired
    private KnowledgeService knowledgeService;

    @PostMapping(value = "upload-answer", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultBean<String> test(HttpSession session, @RequestBody List<Knowledge> knowledges) {
        long userId = LoginUtil.getLoginUser(session).getId();
        knowledgeService.saveUserAnswers(userId, knowledges);
        return ResultBean.createBySuccessMessage("保存" + userId + "的答案成功");
    }
}
