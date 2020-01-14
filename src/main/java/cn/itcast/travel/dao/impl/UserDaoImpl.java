package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

public class UserDaoImpl implements UserDao {

    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public int insUser(User user) {
        String sql = "insert into tab_user(uid,username,password,name,birthday,sex,telephone,email,status,code) values(default,?,?,?,?,?,?,?,?,?)";
        int update = jdbcTemplate.update(sql, user.getUsername(),
                user.getPassword(),
                user.getName(),
                user.getBirthday(),
                user.getSex(),
                user.getTelephone(),
                user.getEmail(),
                user.getStatus(),
                user.getCode());
        return update;
    }

    @Override
    public List<User> findByUsername(String username) {
        String sql = "select * from tab_user where username=? ";
        List<User> userList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class), username);
        return userList;
    }

    @Override
    public int updStatusByCode(String code) {
        String sql = "update tab_user set status=? where code=?";
        int update = jdbcTemplate.update(sql, "Y", code);
        return update;
    }

    @Override
    public User findUserByUsernameAndPwd(String username, String password) {
        String sql = "select * from tab_user where username=? and password=?";
        User user = null;
        try {
            user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), username, password);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return user;
        }
    }
}
