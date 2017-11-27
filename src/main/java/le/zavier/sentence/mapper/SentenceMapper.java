package le.zavier.sentence.mapper;

import java.util.List;
import le.zavier.sentence.model.Sentence;
import le.zavier.sentence.model.SentenceExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SentenceMapper {
    long countByExample(SentenceExample example);

    int deleteByExample(SentenceExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Sentence record);

    int insertSelective(Sentence record);

    List<Sentence> selectByExample(SentenceExample example);

    Sentence selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Sentence record, @Param("example") SentenceExample example);

    int updateByExample(@Param("record") Sentence record, @Param("example") SentenceExample example);

    int updateByPrimaryKeySelective(Sentence record);

    int updateByPrimaryKey(Sentence record);
}