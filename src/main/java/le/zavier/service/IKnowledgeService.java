package le.zavier.service;

import le.zavier.pojo.Knowledge;
import le.zavier.util.CsvContent;

public interface IKnowledgeService {
    Knowledge addKnowledge(Knowledge knowledge);

    Knowledge updateKnowledge(Knowledge knowledge);

    Knowledge getKnowledgeById(long id);

    int removeKnowledgeById(long id);

    boolean isCsvFile(String fileName);

    int saveCsvContent(CsvContent csvContent);
}
