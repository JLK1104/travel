package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class SellerDaoImpl implements SellerDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public Seller findBySid(int sid) {
        Seller seller;
        String sql = "select * from tab_seller where sid=? ";
        try {
            seller = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Seller.class), sid);
            return seller;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
