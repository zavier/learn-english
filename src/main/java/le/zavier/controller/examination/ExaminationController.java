package le.zavier.controller.examination;

import le.zavier.commons.LoginManager;
import le.zavier.pojo.Knowledge;
import le.zavier.service.KnowledgeService;
import le.zavier.vo.ExamineResultVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/le/examination")
public class ExaminationController {
    private static final Logger logger = LoggerFactory.getLogger(ExaminationController.class);

    @Autowired
    private KnowledgeService knowledgeService;

    @GetMapping("/index")
    public String index(HttpSession session,
        @RequestParam(value = "size", defaultValue = "10") Integer size, Model model) {
        Long createUserId = LoginManager.getLoginUser(session).getId();
        List<Knowledge> randomData = knowledgeService.getRandomData(createUserId, size);
        model.addAttribute("knowledges", randomData);
        return "/examination/index";
    }

    @GetMapping("show-result")
    public String showResult(HttpSession session, Model model) {
        long userId = LoginManager.getLoginUser(session).getId();
        List<Knowledge> knowledges = knowledgeService.listUserAnswers(userId);
        List<ExamineResultVo> examineResults = knowledges.stream().map(knowledge -> {
            Knowledge correctAnswer = knowledgeService.getKnowledgeById(knowledge.getId());
            String correctEnglish = correctAnswer.getEnglish();
            ExamineResultVo examineResultVo = new ExamineResultVo();
            examineResultVo.setId(correctAnswer.getId());
            examineResultVo.setCorrectChinese(correctAnswer.getChinese());
            examineResultVo.setUserAnswerEnglish(knowledge.getEnglish());
            examineResultVo.setCorrectEnglish(correctAnswer.getEnglish());
            // 设置结果
            examineResultVo.setAnswerResult();
            return examineResultVo;
        }).collect(Collectors.toList());
        model.addAttribute("examineResults", examineResults);
        return "/examination/examineResult";
    }
}
