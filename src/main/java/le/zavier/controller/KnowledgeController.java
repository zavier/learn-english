package le.zavier.controller;

import com.github.pagehelper.PageInfo;
import java.io.IOException;
import java.util.Objects;
import javax.validation.Valid;
import le.zavier.commons.ResultBean;
import le.zavier.pojo.Knowledge;
import le.zavier.service.IKnowledgeService;
import le.zavier.util.CsvContent;
import le.zavier.util.CsvUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class KnowledgeController {
    private static final Logger logger = LoggerFactory.getLogger(KnowledgeController.class);

    @Autowired
    private IKnowledgeService iknowledgeService;

    @GetMapping(value = "/upload")
    public void upload() {
    }

    @GetMapping(value = "/list")
    public void list() {
    }

    /**
     * 获取资源列表
     * @param page 页数（从1开始）
     * @param size 每页条数
     * @param searchText 查询内容
     * @return
     */
    @PostMapping(value = "/list", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResultBean list(@RequestParam(value = "page", defaultValue = "1") int page,
                            @RequestParam(value = "size", defaultValue = "10") int size,
                            @RequestParam(value = "search", required = false) String searchText) {
        logger.info("查看的关键词为:{}", searchText);
        PageInfo<Knowledge> pageResult = iknowledgeService.listKnowledge(page, size, searchText);
        return ResultBean.createBySuccess(pageResult);
    }

    @PostMapping(value = "/save-knowledge", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResultBean saveKnowledge(@RequestBody @Valid Knowledge knowledge) {
        Objects.requireNonNull(knowledge, "参数不能为空");
        iknowledgeService.addKnowledge(knowledge);
        return ResultBean.createBySuccessMessage("保存成功");
    }

    /**
     * 更新资源
     * @param knowledge
     * @return
     */
    @PostMapping(value = "/update-knowledge", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResultBean updateKnowledge(@RequestBody Knowledge knowledge) {
        Objects.requireNonNull(knowledge, "参数不能为空");
        boolean exist = iknowledgeService.isExistKnowledgeId(knowledge.getId());
        if (exist) {
            logger.info("要更新的资源存在, id:{}", knowledge.getId());
            Knowledge updateRes = iknowledgeService.updateKnowledge(knowledge);
            return ResultBean.createBySuccess(updateRes);
        }
        logger.error("要更新的资源不存在,id:{}", knowledge.getId());
        return ResultBean.createByErrorMessage("要更新的资源不存在");
    }

    @GetMapping(value = "delete-knowledge/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResultBean deleteKnowledgeById(@PathVariable("id") int id) {
        boolean exist = iknowledgeService.isExistKnowledgeId(id);
        if (exist) {
            logger.info("要删除的资源存在, id:{}", id);
            iknowledgeService.removeKnowledgeById(id);
            logger.info("资源成功删除, id:{}", id);
            return ResultBean.createBySuccessMessage("资源成功删除");
        }
        logger.error("要删除的资源不存在,id:{}", id);
        return ResultBean.createByErrorMessage("删除的资源不存在");
    }

    @PostMapping(value = "/upload-csvfile")
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
