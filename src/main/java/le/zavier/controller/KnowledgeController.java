package le.zavier.controller;

import java.io.IOException;
import le.zavier.service.IKnowledgeService;
import le.zavier.util.CsvContent;
import le.zavier.util.CsvUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class KnowledgeController {
    @Autowired
    private IKnowledgeService iknowledgeService;

    @GetMapping("/upload")
    public void upload() {
    }

    @PostMapping("/upload-csvfile")
    public String saveKnowledgeFromFile(@RequestParam("csvFile") MultipartFile file, Model model)
        throws IOException {
        boolean isCsvFile = CsvUtil.isCsvFile(file.getOriginalFilename());
        if (!isCsvFile) {
            model.addAttribute("message", "错误的文件类型");
            return "/error";
        }

        CsvContent csvContent = CsvUtil.readCsvFile(file.getInputStream());
        int i = iknowledgeService.saveCsvContentTypeKnowledge(csvContent);
        model.addAttribute("success", true);
        return "/upload";
    }
}
