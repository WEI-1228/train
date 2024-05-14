package cn.anlper.train.interceptor;

import cn.anlper.train.context.LoginMemberContext;
import cn.anlper.train.resp.MemberLoginResp;
import cn.anlper.train.utils.JwtUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class MemberInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if (StrUtil.isNotBlank(token)) {
            log.info("获取会员登陆token: {}", token);
            JSONObject jsonObject = JwtUtil.getJSONObject(token);
            log.info("当前登陆会员：{}", jsonObject);
            LoginMemberContext.setMember(JSONUtil.toBean(jsonObject, MemberLoginResp.class));
        }
        return true;
    }
}
