package cn.anlper.train;

import cn.anlper.train.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class LoginMemberFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();

        // 排除不需要拦截的请求
        if (path.contains("/admin")
        || path.contains("/member/count")
        || path.contains("/member/login")
        || path.contains("/member/send-code")
        || path.contains("/nacos-test")
        || path.contains("/hello")) {
            log.info("不需要登录验证：{}", path);
            return chain.filter(exchange);
        } else {
            log.info("需要登录验证：{}", path);
        }

        String token = exchange.getRequest().getHeaders().getFirst("token");
        log.info("会员登录验证开始：token: {}", token);
        if (token == null || token.isEmpty()) {
            log.info("token为空，请求被拦截");
            exchange.getResponse().setRawStatusCode(HttpStatus.UNAUTHORIZED.value());
            return exchange.getResponse().setComplete();
        }

        JwtUtil.getJSONObject(token);
        boolean validate = JwtUtil.validate(token);
        if (validate) {
            log.info("token有效，放行该请求");
            return chain.filter(exchange);
        } else {
            log.info("token无效，请求被拦截");
            exchange.getResponse().setRawStatusCode(HttpStatus.UNAUTHORIZED.value());
            return exchange.getResponse().setComplete();
        }
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
