package cn.itcast.travel.web.servlet.demo;

import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/registerUserServlet")
public class RegisterUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
                String content="<a href='http://localhost:8081/travel/activeUserServlet?code="+user.getCode()+"'>点击激活【黑马旅游网】</a>";
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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
