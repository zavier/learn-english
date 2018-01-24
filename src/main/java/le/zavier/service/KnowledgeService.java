package le.zavier.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import le.zavier.commons.PageSearchParam;
import le.zavier.dao.KnowledgeMapper;
import le.zavier.exception.CheckException;
import le.zavier.pojo.Knowledge;
import le.zavier.util.CsvContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KnowledgeService {
    private static final Logger logger = LoggerFactory.getLogger(KnowledgeService.class);

    public static Cache<String, Knowledge> knowledgeCache = CacheBuilder.newBuilder()
        .expireAfterAccess(30, TimeUnit.MINUTES).maximumSize(100).build();

    public static Cache<Long, List<Knowledge>> answerCache = CacheBuilder.newBuilder()
        .expireAfterAccess(10, TimeUnit.MINUTES).maximumSize(100).build();

    @Autowired
    private KnowledgeMapper knowledgeMapper;

    /**
     * 添加资源
     * @param knowledge
     * @return
     */
    public Knowledge addKnowledge(Knowledge knowledge) {
        logger.info("执行添加资源：{}", knowledge.toString());
        int resultCount = knowledgeMapper.insert(knowledge);
        if (resultCount > 0) {
            logger.info("添加资源{}成功", knowledge.toString());
            return knowledge;
        }
        logger.error("添加资源{}失败", knowledge.toString());
        throw new CheckException("插入knowledge失败，" + knowledge.toString());
    }

    /**
     * 更新资源
     * @param knowledge
     * @return
     */
    public Knowledge updateKnowledge(Knowledge knowledge) {
        int resultCount = knowledgeMapper.updateByPrimaryKeySelective(knowledge);
        if (resultCount > 0) {
            logger.info("更新资源成功{}", knowledge.toString());
            return knowledge;
        }
        logger.error("更新资源失败{}", knowledge.toString());
        throw new CheckException("更新knowledge失败，" + knowledge.toString());
    }

    public boolean isExistKnowledgeId(long id) {
        int count = knowledgeMapper.countByPrimaryKey(id);
        if (count == 1) {
            return true;
        }
        return false;
    }

    /**
     * 通过id获取资源
     * @param id
     * @return
     */
    public Knowledge getKnowledgeById(long id) {
        Knowledge ifPresent = knowledgeCache.getIfPresent(String.valueOf(id));
        if (ifPresent != null) {
            return ifPresent;
        } else {
            return knowledgeMapper.selectByPrimaryKey(id);
        }
    }

    /**
     * 通过id删除资源
     * @param id
     * @return
     */
    public int removeKnowledgeById(long id) {
        return knowledgeMapper.deleteByPrimaryKey(id);
    }

    /**
     * 保存CSV文件中的资源
     * @param csvContent
     * @return
     */
    public int saveCsvContentTypeKnowledge(CsvContent csvContent) {
        List<Knowledge> knowledgeList = csvContentToKnowledge(csvContent);
        int i = knowledgeMapper.insertBatch(knowledgeList);
        return i;
    }

    /**
     * 将CSV转为资源列表
     * @param content
     * @return
     */
    private List<Knowledge> csvContentToKnowledge(CsvContent content) {
        List<Knowledge> knowledgeList = new ArrayList<>(content.getTotalRows());
        for (int i = 0; i < content.getTotalRows(); i++) {
            String[] rows = content.getRows(i);
            if (rows.length < 3) {
                // 忽略错误数据
                continue;
            }
            Knowledge knowledge = new Knowledge();
            knowledge.setChinese(rows[0]);
            knowledge.setEnglish(rows[1]);
            knowledge.setType(Short.parseShort(rows[2]));
            knowledgeList.add(knowledge);
        }
        return knowledgeList;
    }

    /**
     * 随机获取 size 条资源
     * @param size
     * @return
     */
    public List<Knowledge> getRandomData(int size) {
        List<Knowledge> knowledges = knowledgeMapper.selectRandom(size);
        knowledgeListToCache(knowledges);
        return knowledges;
    }

    /**
     * 将资源列表添加到缓存中
     * @param knowledgeList
     */
    private void knowledgeListToCache(List<Knowledge> knowledgeList) {
        for (Knowledge knowledge : knowledgeList) {
            knowledgeCache.put(knowledge.getId().toString(), knowledge);
        }
    }

    /**
     * 保存用户答案列表
     * @param userId 用户id
     * @param answers 答案列表
     */
    public void saveUserAnswers(long userId, List<Knowledge> answers) {
        answerCache.put(userId, answers);
        logger.info("已添加{}的答案{}", userId, answers.toString());
    }

    /**
     * 通过用户id获取用户之前填写的答案
     * @param userId 用户id
     * @return
     */
    public List<Knowledge> listUserAnswers(long userId) {
        List<Knowledge> answers = answerCache.getIfPresent(userId);
        if (answers == null) {
            logger.error("用户{}的答案不错在", userId);
            throw new CheckException("用户" + userId + "的答案不存在");
        }
        return answers;
    }

    /**
     * 分页获取资源列表
     * @param pageSearchParam 分页查询参数类，页数从1开始计数
     * @return
     */
    public PageInfo<Knowledge> listKnowledge(PageSearchParam pageSearchParam) {
        PageHelper.startPage(pageSearchParam.getPageNum(), pageSearchParam.getPageSize());
        List<Knowledge> knowledges = knowledgeMapper.selectList(pageSearchParam.getSearchText());
        PageInfo<Knowledge> pageResult = new PageInfo<>(knowledges);
        return pageResult;
    }

    /**
     * 分页获取用户创建的资源列表
     * @param userId 用户id
     * @param pageSearchParam 分页查询参数类
     * @return
     */
    public PageInfo<Knowledge> listUserCreateKnowledge(Long userId, PageSearchParam pageSearchParam) {
        PageHelper.startPage(pageSearchParam.getPageNum(), pageSearchParam.getPageSize());
        List<Knowledge> knowledges = knowledgeMapper.selectUserCreateList(userId, pageSearchParam.getSearchText());
        PageInfo<Knowledge> pageResult = new PageInfo<>(knowledges);
        return pageResult;
    }
}
