package le.zavier.service;

import le.zavier.pojo.Knowledge;

public interface IKnowledgeService {
    Knowledge addKnowledge(Knowledge knowledge);

    Knowledge updateKnowledge(Knowledge knowledge);

    Knowledge getKnowledgeById(long id);

    int removeKnowledgeById(long id);
}
