package com.wsda.project.filter;

import com.wsda.project.dao.SystemUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 对所有的控制器的请求处理方法执行日志记录，包括对请求参数的获取以及记录
 */
@Slf4j
@Configuration
public class LogInterceptor extends HandlerInterceptorAdapter {
    private static Logger logger = LoggerFactory.getLogger(LogInterceptor.class);

    @Resource
    private SystemUserMapper userMapper;
//暂时不需要验证登录
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
//            throws Exception {
//        try {
//            //得到session
//            HttpSession session = request.getSession(true);
//            //得到对象
//            SystemUser admin = (SystemUser) session.getAttribute("user");
//            // 获取request里面的cookie cookie里面存值方式也是 键值对的方式  cookie
//            String token=null;
//            Cookie[] cookie = request.getCookies();
//            if(cookie!=null){
//                for (int i = 0; i < cookie.length; i++) {
//                    Cookie cook = cookie[i];
//                    if(cook.getName().equalsIgnoreCase("token")){ //获取token键 
//                        token=cook.getValue().toString();
//                    }
//                }
//            }
//            //判断对象是否存在
//            if (token != null && admin != null) {//
//                String tokenUser = userMapper.getToken(admin.getUserCode());//获取数据库的token
//                if (tokenUser != null && token.equals(tokenUser)) {
//                    return true;
//                }
//                for (int i = 0; i < cookie.length; i++) {
//                    Cookie cook = cookie[i];
//                    cook.setMaxAge(0);
//                }
//                session.invalidate();
//                response.setStatus(401);
//                return false;
//            } else {
//                if ("/loginCheck".equals(request.getServletPath()) || "/loginOut".equals(request.getServletPath())) {
//                    System.err.println("正常访问");
//                    return true;
//                }
//                String requestUrl = String.valueOf(request.getRequestURL());
//                logger.info("拦截未登录访问拦截IP：{}", requestUrl);
//                response.setStatus(401);
//                return false;
//            }
//        } catch (Exception e) {
//            logger.info("日志拦截出现异常：{}", e.getMessage());
//        }
//        return true;
//    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        //controller方法处理完毕后，调用此方法
        System.out.println("在后端控制器执行后调用 ");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        //页面渲染完毕后调用此方法
        System.out.println("整个请求执行完成后调用 ");
    }

    /**
     * 展示请求参数
     *
     * @param request
     * @return
     */
    public static Map<String, Object> showRequestParams(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        Enumeration paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();

            String[] paramValues = request.getParameterValues(paramName);
            if (paramValues.length > 0) {
                String paramValue = paramValues[0];
                if (paramValue.length() != 0) {
                    map.put(paramName, paramValue);
                }
            }
        }

        Set<Map.Entry<String, Object>> set = map.entrySet();
        logger.debug("=============================请求的参数列表=================================");
        for (Map.Entry entry : set) {
            logger.debug(entry.getKey() + ":" + entry.getValue());
        }
        logger.debug("==========================end===================================");
        return map;
    }
}