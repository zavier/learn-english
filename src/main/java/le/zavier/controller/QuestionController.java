package le.zavier.controller;

import java.util.List;
import java.util.stream.Collectors;
import le.zavier.commons.ResultBean;
import le.zavier.pojo.Knowledge;
import le.zavier.service.IKnowledgeService;
import le.zavier.vo.ExamineResultVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/examination")
public class QuestionController {
    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    private IKnowledgeService iknowledgeService;

    @GetMapping("/index")
    public String index(@RequestParam(value = "size", defaultValue = "10") Integer size, Model model) {
        List<Knowledge> randomData = iknowledgeService.getRandomData(size);
        model.addAttribute("knowledges", randomData);
        return "/index";
    }

    private String getUserId() {
        return "USERID";
    }

    @PostMapping(value = "upload-answer", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResultBean<String> test(@RequestBody List<Knowledge> knowledges) {
        String userId = getUserId();
        iknowledgeService.saveUserAnswers(userId, knowledges);
        return ResultBean.createBySuccessMessage("保存" + userId + "的答案成功");
    }

    @GetMapping("show-result")
    public String showResult(Model model) {
        String userId = getUserId();
        List<Knowledge> knowledges = iknowledgeService.listUserAnswers(userId);
        List<ExamineResultVo> examineResults = knowledges.stream().map(knowledge -> {
            Knowledge correctAnswer = iknowledgeService.getKnowledgeById(knowledge.getId());
            String correctEnglish = correctAnswer.getEnglish();
            ExamineResultVo examineResultVo = new ExamineResultVo();
            examineResultVo.setId(correctAnswer.getId());
            examineResultVo.setCorrectChinese(correctAnswer.getChinese());
            examineResultVo.setUserAnswerEnglish(knowledge.getEnglish());
            examineResultVo.setCorrectEnglish(correctAnswer.getEnglish());
            return examineResultVo;
        }).collect(Collectors.toList());
        model.addAttribute("examineResults", examineResults);
        return "/examineResult";
    }
}
