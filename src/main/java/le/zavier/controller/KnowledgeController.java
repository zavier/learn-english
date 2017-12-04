package le.zavier.controller;

import java.util.UUID;
import le.zavier.commons.ResultBean;
import le.zavier.pojo.Knowledge;
import le.zavier.service.IKnowledgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KnowledgeController {
    @Autowired
    private IKnowledgeService iknowledgeService;

    @GetMapping("/add-or-update-knowledge")
    public ResultBean<Knowledge> addKnowledge() {
        Knowledge knowledge = new Knowledge();
        knowledge.setChinese(UUID.randomUUID().toString());
        knowledge.setEnglish(UUID.randomUUID().toString());
        knowledge.setType((short)2);
        Knowledge result = iknowledgeService.addKnowledge(knowledge);
        return new ResultBean(result);
    }


}
