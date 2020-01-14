package cn.itcast.travel.dao;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;

import java.util.List;

public interface RouteDao {
    List<Route> splitPageByCid(Integer cid, PageBean pageBean);
    int splitPageCount(Integer cid,PageBean pageBean);
    Route findOne(int rid);

}
