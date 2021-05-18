package com.demo.intercept;

import com.demo.util.StringUtil;
import com.demo.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class LoginTokenFilter extends HandlerInterceptorAdapter {

    /**
     * 请求进去controller进行请求拦截
     *
     * @param request  请求
     * @param response 响应
     * @param handler  处理
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取头部header 信息
        String token = request.getHeader("Authorization");
        //如果为空，表示未登录，禁止使用
        if (StringUtil.isEmpty(token)) {
            log.error("您没有登录！");
            return false;
        } else {
            //不为空，但是redis 中不存在，请重新登录
            //StringUtil.isNotEmpty(TokenUtil.getUserIdByToken(token))
            if (TokenUtil.getUserByToken(token) != null) {
                log.info("token 验证通过...");
                return true;
            } else {
                log.error("登录过期，请重新登录！");
                return false;
            }
        }
    }

    /**
     * 错误信息
     *
     * @param response
     * @param msg
     */
    /*private void error(HttpServletResponse response, String msg) {
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Type", "application/json");
        try {
            PrintWriter writer = response.getWriter();
            //将返回的错误提示压入流中
            writer.write(new ObjectMapper().writeValueAsString(R.failed(msg)));
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }*/
}

