package cn.itcast.travel.service;

import cn.itcast.travel.domain.User;

import java.util.List;

public interface UserService {
     String LOGIN_LOCK="LOCK"; //锁定状态,需要激活
     String LOGIN_SUCCESS="SUCCESS"; //登陆成功
     String LOGIN_FAIL="FAIL"; //登录失败,账号密码错误

    Integer insert(User user);
    List<User> selByUsername(String username);
    boolean activeStatus(String code);
    String login(String username,String password);
    User selByUsernameAndPwd(String username,String password);
}
