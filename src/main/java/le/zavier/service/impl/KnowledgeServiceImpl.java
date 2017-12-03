package le.zavier.service.impl;

import le.zavier.dao.KnowledgeMapper;
import le.zavier.exception.CheckException;
import le.zavier.pojo.Knowledge;
import le.zavier.service.IKnowledgeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("iKnowledgeService")
public class KnowledgeServiceImpl implements IKnowledgeService {

    private static final Logger logger = LoggerFactory.getLogger(KnowledgeServiceImpl.class);

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
        return knowledgeMapper.selectByPrimaryKey(id);
    }

    @Override
    public int removeKnowledgeById(long id) {
        return knowledgeMapper.deleteByPrimaryKey(id);
    }
}
