package le.zavier.controller.knowledge;

import com.github.pagehelper.PageInfo;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import le.zavier.commons.PageSearchParam;
import le.zavier.commons.ResultBean;
import le.zavier.pojo.Knowledge;
import le.zavier.pojo.User;
import le.zavier.service.KnowledgeService;
import le.zavier.util.LoginUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/knowledge")
public class KnowledgeRestController {

    private static final Logger logger = LoggerFactory.getLogger(KnowledgeRestController.class);

    @Autowired
    private KnowledgeService knowledgeService;

    /**
     * 获取资源列表
     * @param page 页数（从1开始）
     * @param size 每页条数
     * @param searchText 查询内容
     * @return
     */
    @PostMapping(value = "/list", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultBean list(@RequestParam(value = "page", defaultValue = "1") int page,
        @RequestParam(value = "size", defaultValue = "10") int size,
        @RequestParam(value = "search", required = false) String searchText) {
        logger.info("查看的关键词为:{}", searchText);
        PageSearchParam pageSearchParam = new PageSearchParam(page, size, searchText);
        PageInfo<Knowledge> pageResult = knowledgeService.listKnowledge(pageSearchParam);
        return ResultBean.createBySuccess(pageResult);
    }

    /**
     * 获取用户创建的资源列表
     * @param page 页数（从1开始）
     * @param size 每页条数
     * @param searchText 查询内容
     * @return
     */
    @PostMapping(value = "/list-user-create", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultBean listUserCreate(HttpSession session,
        @RequestParam(value = "page", defaultValue = "1") int page,
        @RequestParam(value = "size", defaultValue = "10") int size,
        @RequestParam(value = "search", required = false) String searchText) {
        logger.info("查看的关键词为:{}", searchText);
        PageSearchParam pageSearchParam = new PageSearchParam(page, size, searchText);
        User user = LoginUtil.getLoginUser(session);
        PageInfo<Knowledge> pageResult = knowledgeService.listUserCreateKnowledge(user.getId(), pageSearchParam);
        return ResultBean.createBySuccess(pageResult);
    }

    @PostMapping(value = "/save-knowledge", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultBean saveKnowledge(HttpSession session, @RequestBody @Valid Knowledge knowledge) {
        User loginUser = LoginUtil.getLoginUser(session);
        knowledge.setCreateUserId(loginUser.getId());
        knowledgeService.addKnowledge(knowledge);
        return ResultBean.createBySuccessMessage("保存成功");
    }

    /**
     * 更新资源
     * @param knowledge
     * @return
     */
    @PostMapping(value = "/update-knowledge", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultBean updateKnowledge(HttpSession session, @RequestBody @Valid Knowledge knowledge) {
        User loginUser = LoginUtil.getLoginUser(session);
        boolean exist = knowledgeService.isExistKnowledgeId(knowledge.getId());
        if (exist) {
            logger.info("要更新的资源存在, id:{}", knowledge.getId());
            knowledge.setUpdateUserId(loginUser.getId());
            Knowledge updateRes = knowledgeService.updateKnowledge(knowledge);
            return ResultBean.createBySuccess(updateRes);
        }
        logger.error("要更新的资源不存在,id:{}", knowledge.getId());
        return ResultBean.createByErrorMessage("要更新的资源不存在");
    }

    @GetMapping(value = "delete-knowledge/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultBean deleteKnowledgeById(HttpSession session, @PathVariable("id") int id) {
        User loginUser = LoginUtil.getLoginUser(session);
        Knowledge knowledge = knowledgeService.getKnowledgeById(id);
        if (knowledge != null) {
            logger.info("要删除的资源存在:{}", knowledge.toString());
            knowledgeService.removeKnowledgeById(id);
            logger.info("资源成功删除:{}, 删除人:{}", knowledge.toString(), loginUser.toString());
            return ResultBean.createBySuccessMessage("资源成功删除");
        }
        logger.error("要删除的资源不存在,id:{}", id);
        return ResultBean.createByErrorMessage("删除的资源不存在");
    }
}
