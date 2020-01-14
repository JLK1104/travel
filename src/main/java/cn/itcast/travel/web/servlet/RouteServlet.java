package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.FavoriteServiceImpl;
import cn.itcast.travel.service.impl.RouteServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.Encoder;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
    private RouteService routeService = new RouteServiceImpl();
    private FavoriteService favoriteService = new FavoriteServiceImpl();
    // route/splitPage
    public void splitPage(HttpServletRequest request,HttpServletResponse response) throws IOException {
        Integer currentPage = 1;
        try {
            currentPage = Integer.parseInt(request.getParameter("currentPage"));
        }catch (Exception e){
            e.printStackTrace();
            currentPage = 1;
        }
        Integer lineSize = 10;
//        try {
//            lineSize = Integer.parseInt(request.getParameter("lineSize"));
//        }catch (Exception e){
//            e.printStackTrace();
//            lineSize = 10;
//        }
        String column = "rname";
        String keyword = request.getParameter("rname");
        String keyword_str = URLDecoder.decode(keyword, "utf-8");
        PageBean pageBean = new PageBean(currentPage,lineSize,keyword_str,column,0,0);
        Integer cid = Integer.parseInt(request.getParameter("cid"));
        HashMap<String, Object> map = new HashMap<>();
        List<Route> routes = routeService.selSplitByCid(cid, pageBean);
        int allRecorders = routeService.splitPageCount(cid,pageBean);
        int pageSize = allRecorders % lineSize == 0 ? allRecorders / lineSize : (allRecorders / lineSize) + 1;
        pageBean = new PageBean(currentPage,lineSize,keyword_str,column,allRecorders,pageSize);
        System.out.println(pageBean);
        map.put("routes",routes);
        map.put("pageBean",pageBean);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(map);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }
    // route/findOne
    public void findOne(HttpServletRequest request,HttpServletResponse response) throws IOException{
        Integer rid = Integer.parseInt(request.getParameter("rid"));
        Route one = routeService.findOne(rid);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(one);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }
    /**
     * 判断当前登录用户是否收藏过该线路
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1. 获取线路id
        String rid = request.getParameter("rid");

        //2. 获取当前登录的用户 user
        User user = (User) request.getSession().getAttribute("user");
        int uid;//用户id
        if(user == null){
            //用户尚未登录
            uid = 0;
        }else{
            //用户已经登录
            uid = user.getUid();
        }

        //3. 调用FavoriteService查询是否收藏
        boolean flag = favoriteService.isFavorite(rid, uid);

        //4. 写回客户端
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(flag);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }

    /**
     * 添加收藏
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1. 获取线路rid
        String rid = request.getParameter("rid");
        //2. 获取当前登录的用户
        User user = (User) request.getSession().getAttribute("user");
        int uid;//用户id
        if(user == null){
            //用户尚未登录
            return ;
        }else{
            //用户已经登录
            uid = user.getUid();
        }


        //3. 调用service添加
        favoriteService.add(rid,uid);

    }
}
