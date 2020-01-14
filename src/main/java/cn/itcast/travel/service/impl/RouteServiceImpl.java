package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.RouteImgDaoImpl;
import cn.itcast.travel.dao.impl.SellerDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {
    private RouteDao routeDao = new RouteDaoImpl();
    private RouteImgDao routeImgDao = new RouteImgDaoImpl();
    private SellerDao sellerDao = new SellerDaoImpl();
    @Override
    public List<Route> selSplitByCid(Integer cid, PageBean pageBean) {
        List<Route> routes = routeDao.splitPageByCid(cid, pageBean);
        return routes;
    }

    @Override
    public int splitPageCount(Integer cid,PageBean pageBean) {
        int i = routeDao.splitPageCount(cid,pageBean);
        return i;
    }

    @Override
    public Route findOne(int rid) {
        Route one = routeDao.findOne(rid);
        List<RouteImg> imgByRid = routeImgDao.findImgByRid(rid);
        one.setRouteImgList(imgByRid);
        Seller bySid = sellerDao.findBySid(one.getSid());
        one.setSeller(bySid);
        return one;
    }
}
