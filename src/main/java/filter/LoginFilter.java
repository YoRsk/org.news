package filter;

import entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

/**
 * @author yangxin
 * @time 2019/1/2  13:46
 */
@WebFilter
public class LoginFilter implements Filter {
    Logger log = LoggerFactory.getLogger(LoginFilter.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        log.info("进入过滤器处理");
        HttpServletRequest httpRequest =(HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        String uri = httpRequest.getRequestURI();
        if(uri.contains("login")||uri.contains("register")||uri.contains("AdminLogin")){
            filterChain.doFilter(servletRequest, servletResponse);
        }else{
            User user =(User) httpRequest.getSession().getAttribute("user");//获取当前session的user
            ServletContext application = httpRequest.getSession().getServletContext();//获取上下文
            @SuppressWarnings("unchecked")
            Map<Integer, Object> loginMap = (Map<Integer, Object>) application.getAttribute("loginMap");

            if(null==user){
                httpResponse.sendRedirect("login.jsp");
            }else if(loginMap.get((int)user.getUserId()) == null){//如果loginMap没有该user 则
                httpRequest.getSession().removeAttribute("user");
                httpResponse.sendRedirect("login.jsp");
            }else{
                filterChain.doFilter(servletRequest, servletResponse);
            }
            assert user != null;
            log.info("############pengliuyi专用日志###########  loginMap.get((int)user.getUserId())：" + loginMap.get((int)user.getUserId()));
        }
    }

    @Override
    public void destroy() {

    }
}
