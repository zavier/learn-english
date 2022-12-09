package le.zavier.controller.knowledge;

import le.zavier.commons.LoginManager;
import le.zavier.pojo.User;
import le.zavier.service.KnowledgeService;
import le.zavier.util.CsvContent;
import le.zavier.util.CsvUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("/knowledge")
public class KnowledgeController {
    private static final Logger logger = LoggerFactory.getLogger(KnowledgeController.class);

    @Autowired
    private KnowledgeService knowledgeService;

    @GetMapping(value = "/upload")
    public String upload() {
        return "/upload";
    }

    @GetMapping(value = "/list")
    public String list() {
        return "/knowledge/list";
    }



    @PostMapping(value = "/upload-csvfile")
    public String saveKnowledgeFromFile(@RequestParam("csvFile") MultipartFile file, Model model, HttpSession session)
        throws IOException {
        boolean isCsvFile = CsvUtil.isCsvFile(file.getOriginalFilename());
        if (!isCsvFile) {
            model.addAttribute("message", "错误的文件类型");
            return "/error";
        }

        User user = LoginManager.getLoginUser(session);
        CsvContent csvContent = CsvUtil.readCsvFile(file.getInputStream());
        int i = knowledgeService.saveCsvContentTypeKnowledge(csvContent, user);
        model.addAttribute("success", true);
        return "/upload";
    }
}
