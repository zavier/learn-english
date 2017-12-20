package le.zavier.dao;

import le.zavier.pojo.User;

public interface UserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    User selectByAccountOrEmailAndPassword(User user);

    int countByAccountOrEmail(User user);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}