package le.zavier.service.impl;

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

    @Override
    public Knowledge addKnowledge(Knowledge knowledge) {
        int resultCount = knowledgeMapper.insert(knowledge);
        if (resultCount > 0) {
            return knowledge;
        }
        throw new CheckException("插入knowledge失败，" + knowledge.toString());
    }

    @Override
    public Knowledge updateKnowledge(Knowledge knowledge) {
        int resultCount = knowledgeMapper.updateByPrimaryKeySelective(knowledge);
        if (resultCount > 0) {
            return knowledge;
        }
        throw new CheckException("更新knowledge失败，" + knowledge.toString());
    }

    @Override
    public Knowledge getKnowledgeById(long id) {
        Knowledge ifPresent = knowledgeCache.getIfPresent(String.valueOf(id));
        if (ifPresent != null) {
            return ifPresent;
        } else {
            return knowledgeMapper.selectByPrimaryKey(id);
        }
    }

    @Override
    public int removeKnowledgeById(long id) {
        return knowledgeMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int saveCsvContentTypeKnowledge(CsvContent csvContent) {
        List<Knowledge> knowledgeList = csvContentToKnowledge(csvContent);
        int i = knowledgeMapper.insertBatch(knowledgeList);
        return i;
    }

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

    @Override
    public List<Knowledge> getRandomData(int size) {
        List<Knowledge> knowledges = knowledgeMapper.selectRandom(size);
        knowledgeListToCache(knowledges);
        return knowledges;
    }

    private void knowledgeListToCache(List<Knowledge> knowledgeList) {
        for (Knowledge knowledge : knowledgeList) {
            knowledgeCache.put(knowledge.getId().toString(), knowledge);
        }
    }

    @Override
    public void saveUserAnswers(String userId, List<Knowledge> answers) {
        answerCache.put(userId, answers);
        logger.info("已添加{}的答案{}", userId, answers.toString());
    }

    @Override
    public List<Knowledge> listUserAnswers(String userId) {
        List<Knowledge> answers = answerCache.getIfPresent(userId);
        if (answers == null) {
            logger.error("用户{}的答案不错在", userId);
            throw new CheckException("用户" + userId + "的答案不存在");
        }
        return answers;
    }
}
