package le.zavier.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import le.zavier.dao.KnowledgeMapper;
import le.zavier.exception.CheckException;
import le.zavier.pojo.Knowledge;
import le.zavier.service.IKnowledgeService;
import le.zavier.util.CsvContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("iKnowledgeService")
public class KnowledgeServiceImpl implements IKnowledgeService {
    private static final Logger logger = LoggerFactory.getLogger(KnowledgeServiceImpl.class);

    public static Cache<String, Knowledge> knowledgeCache = CacheBuilder.newBuilder()
        .expireAfterAccess(30, TimeUnit.MINUTES).maximumSize(100).build();

    public static Cache<String, List<Knowledge>> answerCache = CacheBuilder.newBuilder()
        .expireAfterAccess(10, TimeUnit.MINUTES).maximumSize(100).build();

    @Autowired
    private KnowledgeMapper knowledgeMapper;

    /**
     * 添加资源
     * @param knowledge
     * @return
     */
    @Override
    public Knowledge addKnowledge(Knowledge knowledge) {
        int resultCount = knowledgeMapper.insert(knowledge);
        if (resultCount > 0) {
            return knowledge;
        }
        throw new CheckException("插入knowledge失败，" + knowledge.toString());
    }

    /**
     * 更新资源
     * @param knowledge
     * @return
     */
    @Override
    public Knowledge updateKnowledge(Knowledge knowledge) {
        int resultCount = knowledgeMapper.updateByPrimaryKeySelective(knowledge);
        if (resultCount > 0) {
            return knowledge;
        }
        throw new CheckException("更新knowledge失败，" + knowledge.toString());
    }

    @Override
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
    @Override
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
    @Override
    public int removeKnowledgeById(long id) {
        return knowledgeMapper.deleteByPrimaryKey(id);
    }

    /**
     * 保存CSV文件中的资源
     * @param csvContent
     * @return
     */
    @Override
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
    @Override
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
    @Override
    public void saveUserAnswers(String userId, List<Knowledge> answers) {
        answerCache.put(userId, answers);
        logger.info("已添加{}的答案{}", userId, answers.toString());
    }

    /**
     * 通过用户id获取用户之前填写的答案
     * @param userId
     * @return
     */
    @Override
    public List<Knowledge> listUserAnswers(String userId) {
        List<Knowledge> answers = answerCache.getIfPresent(userId);
        if (answers == null) {
            logger.error("用户{}的答案不错在", userId);
            throw new CheckException("用户" + userId + "的答案不存在");
        }
        return answers;
    }

    /**
     * 分页获取资源列表
     * @param pageNum 页码（从1开始）
     * @param pageSize 每页条数
     * @param searchText 查询内容
     * @return
     */
    @Override
    public PageInfo<Knowledge> listKnowledge(int pageNum, int pageSize, String searchText) {
        PageHelper.startPage(pageNum, pageSize);
        List<Knowledge> knowledges = knowledgeMapper.selectList(searchText);
        PageInfo<Knowledge> pageResult = new PageInfo<>(knowledges);
        return pageResult;
    }
}
