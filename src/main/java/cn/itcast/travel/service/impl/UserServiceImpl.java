package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.JDBCUtils;

import java.util.List;

public class UserServiceImpl implements UserService {
    UserDao userDao = new UserDaoImpl();

    @Override
    public Integer insert(User user) {
        if (userDao.findByUsername(user.getUsername()).size() == 0) {
            int i = userDao.insUser(user);
            return i;
        }
        return null;
    }

    @Override
    public List<User> selByUsername(String username) {
        List<User> list = userDao.findByUsername(username);
        return list;
    }

    @Override
    public boolean activeStatus(String code) {
        int i = userDao.updStatusByCode(code);
        if (i == 1) {
            return true;
        }
        return false;
    }

    @Override
    public String login(String username, String password) {
        User user = userDao.findUserByUsernameAndPwd(username, password);
        if (user != null) {
            if (user.getStatus().equals("Y")) {
                return LOGIN_SUCCESS;
            } else {
                return LOGIN_LOCK;//未激活状态
            }
        } else {
            return LOGIN_FAIL;
        }
    }

    @Override
    public User selByUsernameAndPwd(String username, String password) {
        User user = userDao.findUserByUsernameAndPwd(username, password);
        return user;
    }


}
