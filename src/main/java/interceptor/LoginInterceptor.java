package interceptor;

import dto.NewsResult;
import entity.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        //控制层中存入session的数据，这里取出
        User user = (User)session.getAttribute("user");

        ServletContext application = session.getServletContext();//获取上下文
        @SuppressWarnings("unchecked")
        Map<Integer, Object> loginMap = (Map<Integer, Object>) application.getAttribute("loginMap");

        if(user != null ){//session中有当前请求用户对应的数据，已登录
            //检验是否被管理员强制下线
            if(loginMap.get((int)user.getUserId()) != null){
                return true;
            }
            NewsResult<User> result = new NewsResult<User>(false, "你已被管理员强制下线");
            //request.setAttribute("result", result);
            session.removeAttribute("user");
        }else{
            //未登录或者被踢下线  重定向到登录页面
            //小细节：把当前工程了ContextPath加上
            NewsResult<User> result = new NewsResult<User>(false, "请先登录");
            //request.setAttribute("result", result);
        }
        response.sendRedirect(request.getContextPath()+"/user/login");
        return false;
    }
}
