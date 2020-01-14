package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

import java.util.List;

public interface UserDao {
    int insUser(User user);
    List<User> findByUsername(String username);
    int updStatusByCode(String code);
    User findUserByUsernameAndPwd(String username,String password);

}
