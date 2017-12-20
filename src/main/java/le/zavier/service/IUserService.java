package le.zavier.service;

import le.zavier.pojo.User;

public interface IUserService {

    void register(User user);

    User login(User user);
}
