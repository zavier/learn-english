package le.zavier.dao;

import java.util.List;
import le.zavier.pojo.Knowledge;
import org.apache.ibatis.annotations.Param;

public interface KnowledgeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Knowledge record);

    int insertBatch(@Param("knowledgeList") List<Knowledge> knowledgeList);

    int insertSelective(Knowledge record);

    Knowledge selectByPrimaryKey(Long id);

    int countByPrimaryKey(Long id);

    List<Knowledge> selectRandom(int size);

    List<Knowledge> selectList(@Param("searchText") String searchText);

    List<Knowledge> selectUserCreateList(@Param("createUserId") Long createUserId,
        @Param("searchText") String searchText);

    int updateByPrimaryKeySelective(Knowledge record);

    int countByChineseAndCreateUser(@Param("createUserId") Long createUserId, @Param("chinese") String chinese);

    int updateByPrimaryKey(Knowledge record);
}