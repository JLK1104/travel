package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RouteDaoImpl implements RouteDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public List<Route> splitPageByCid(Integer cid, PageBean pageBean) {
//        String sql = "select * from tab_route where cid=? limit ?,?";
        StringBuilder sql = new StringBuilder("select * from tab_route where 1=1 ");
        ArrayList<Object> params = new ArrayList<>();
        sql.append(" and cid=? ");
        params.add(cid);
        if (pageBean.getKeyword()!=null && pageBean.getKeyword().length()!=0 && !"null".equals(pageBean.getKeyword())){
            sql.append(" and " + pageBean.getColumn() + " like ? ");
            params.add("%"+pageBean.getKeyword()+"%");
        }
        sql.append(" limit ?,? ");
        System.out.println(sql);
        int currentPage = pageBean.getCurrentPage();
        int lineSize = pageBean.getLineSize();
        int start = (currentPage - 1) * lineSize;
        params.add(start);
        params.add(lineSize);
        List<Route> splitList = jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper<>(Route.class),params.toArray());
        return splitList;
    }

    @Override
    public int splitPageCount(Integer cid,PageBean pageBean) {
        StringBuilder sql = new StringBuilder("select count(*) from tab_route where 1=1 ");
        ArrayList<Object> params = new ArrayList<>();
        sql.append(" and cid=? ");
        params.add(cid);
        if (pageBean.getKeyword()!=null && pageBean.getKeyword().length()!=0 && !"null".equals(pageBean.getKeyword())){
            sql.append(" and " + pageBean.getColumn() + " like ? ");
            params.add("%"+pageBean.getKeyword()+"%");
        }
        Integer integer = jdbcTemplate.queryForObject(sql.toString(), Integer.class,params.toArray());
        return integer;
    }

    @Override
    public Route findOne(int rid) {
        String sql = "select * from tab_route where rid=? ";
        Route route;
        try {
            route = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Route.class), rid);
            return route;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
