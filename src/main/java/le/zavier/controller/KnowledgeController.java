package le.zavier.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import le.zavier.commons.ResultBean;
import le.zavier.pojo.Knowledge;
import le.zavier.service.IKnowledgeService;
import le.zavier.util.CsvContent;
import le.zavier.util.CsvUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class KnowledgeController {
    @Autowired
    private IKnowledgeService iknowledgeService;

    @GetMapping("/add-or-update-knowledge")
    @ResponseBody
    public ResultBean<Knowledge> addKnowledge(String chinese, String english, String type) {
        Knowledge knowledge = new Knowledge();
        knowledge.setChinese(chinese);
        knowledge.setEnglish(english);
        knowledge.setType(Short.parseShort(type));
        Knowledge result = iknowledgeService.addKnowledge(knowledge);
        return new ResultBean(result);
    }

    @GetMapping("/upload")
    public void upload() {
    }

    @PostMapping("/upload-csvfile")
    public String saveKnowledgeFromFile(@RequestParam("csvFile") MultipartFile file, Model model)
        throws IOException {
        boolean isCsvFile = iknowledgeService.isCsvFile(file.getOriginalFilename());
        if (!isCsvFile) {
            model.addAttribute("message", "错误的文件类型");
            return "/error";
        }

        CsvContent csvContent = CsvUtil.readCsvFile(file.getInputStream());
        int i = iknowledgeService.saveCsvContent(csvContent);
        model.addAttribute("success", true);
        return "/upload";
    }

    @GetMapping("/index")
    public String index(@RequestParam(value = "size", defaultValue = "10") Integer size, Model model) {
        List<Knowledge> randomData = iknowledgeService.getRandomData(size);
        model.addAttribute("knowledges", randomData);
        return "/index";
    }
}
