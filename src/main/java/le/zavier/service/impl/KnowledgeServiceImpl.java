package le.zavier.service.impl;

import java.util.ArrayList;
import java.util.List;
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

    @Override
    public boolean isCsvFile(String fileName) {
        if (fileName.endsWith(".csv")) {
            return true;
        }
        return false;
    }

    @Override
    public int saveCsvContent(CsvContent csvContent) {
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

}
