package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

import java.util.List;

public interface RouteService {
    List<Route> selSplitByCid(Integer cid, PageBean pageBean);
    int splitPageCount(Integer cid,PageBean pageBean);
    Route findOne(int rid);
}
