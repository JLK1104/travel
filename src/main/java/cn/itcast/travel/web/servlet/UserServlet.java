package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    //regist
    public void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String check = request.getParameter("check");
        String checkcode_server = (String) request.getSession().getAttribute("CHECKCODE_SERVER");
        request.getSession().removeAttribute("CHECKCODE_SERVER");
        HashMap<String, Object> maps = new HashMap<>();
        //验证码校验
        if (checkcode_server != null && checkcode_server.equalsIgnoreCase(check)) {
            //验证码通过
            UserService userService = new UserServiceImpl();
            User user = new User();
            Map<String, String[]> map = request.getParameterMap();
            try {
                BeanUtils.populate(user, map);
            } catch (Exception e) {
                e.printStackTrace();
            }
            user.setStatus("N"); //未激活
            user.setCode(UuidUtil.getUuid()); //随机码
            Integer insert = userService.insert(user);
            if (insert == 1) {
                maps.put("flag",true);
                //发送激活邮件
                String content="<a href='http://localhost:8081/travel/active?code="+user.getCode()+"'>点击激活【黑马旅游网】</a>";
                MailUtils.sendMail(user.getEmail(),content,"激活邮件");
            } else {
                maps.put("flag",false);
                maps.put("msg", "添加失败");
            }
        } else {
            maps.put("flag",false);
            maps.put("msg", "验证码有误");
        }
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(maps);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }
    //find
    public void checkUserExist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        UserService userService = new UserServiceImpl();
        List<User> users = userService.selByUsername(username);
        Map<String, Object> map = new HashMap<>();
        if (users.size() >= 1) {
            map.put("msg",false);
        } else {
            map.put("msg",true);
        }
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(map);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }
    //login
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String check = request.getParameter("check");
        String checkcode_server = (String) request.getSession().getAttribute("CHECKCODE_SERVER");
//        Enumeration<String> attributeNames = request.getSession().getAttributeNames();
//        while (attributeNames.hasMoreElements()){
//            String s = attributeNames.nextElement();
//            System.out.println(s);
//        }
        request.getSession().removeAttribute("CHECKCODE_SERVER");
        HashMap<String, Object> maps = new HashMap<>();
        //验证码校验
        if (checkcode_server != null && checkcode_server.equalsIgnoreCase(check)) {
            //验证码通过
            UserService userService = new UserServiceImpl();
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            User user = userService.selByUsernameAndPwd(username, password);
            String login_str = userService.login(username, password);
            if (login_str.equals(UserService.LOGIN_SUCCESS)) {
                maps.put("flag", true);
                request.getSession().setAttribute("user",user);
            } else if (login_str.equals(UserService.LOGIN_LOCK)) {
                maps.put("flag", false);
                maps.put("msg", "账号未激活");
            } else if (login_str.equals(UserService.LOGIN_FAIL)) {
                maps.put("flag", false);
                maps.put("msg", "账号密码错误");
            }
        } else {
            maps.put("flag", false);
            maps.put("msg", "验证码有误");
        }
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(maps);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }
    public void findUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        Map<String,Object> map = new HashMap<>();
        map.put("user",user);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(map);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }
    //注销
    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        response.sendRedirect(request.getContextPath()+"/login.html");
    }
    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        UserService userService = new UserServiceImpl();
        String code = request.getParameter("code");
        boolean b = userService.activeStatus(code);
        if (b) {
            System.out.println("激活成功");
        } else {
            System.out.println("激活失败");
        }

    }
}
