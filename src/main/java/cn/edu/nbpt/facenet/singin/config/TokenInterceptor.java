package cn.edu.nbpt.facenet.singin.config;

import cn.edu.nbpt.facenet.singin.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getMethod().toUpperCase().equals("OPTIONS")){
            System.out.println("放行预检请求");
            return true;
        }
        String token = request.getHeader("token");
        Claims claims = JwtUtil.checkTOKEN(token);
        if (claims==null){
            System.out.println("token不合法，拦截");
            request.getRequestDispatcher("/api/v1/users/result").forward(request,response);
            return false;
        }
        return true;
    }
}
