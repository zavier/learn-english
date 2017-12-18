package le.zavier.service;

import com.github.pagehelper.PageInfo;
import java.util.List;
import le.zavier.pojo.Knowledge;
import le.zavier.util.CsvContent;

public interface IKnowledgeService {
    Knowledge addKnowledge(Knowledge knowledge);

    Knowledge updateKnowledge(Knowledge knowledge);

    boolean isExistKnowledgeId(long id);

    Knowledge getKnowledgeById(long id);

    int removeKnowledgeById(long id);

    int saveCsvContentTypeKnowledge(CsvContent csvContent);

    List<Knowledge> getRandomData(int size);

    void saveUserAnswers(String userId, List<Knowledge> answers);

    List<Knowledge> listUserAnswers(String userId);

    PageInfo<Knowledge> listKnowledge(int pageNum, int pageSize, String searchText);
}
