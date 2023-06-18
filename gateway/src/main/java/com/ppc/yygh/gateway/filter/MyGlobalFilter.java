package com.ppc.yygh.gateway.filter;

import com.google.common.net.HttpHeaders;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class MyGlobalFilter implements GlobalFilter, Ordered {
    private final AntPathMatcher antPathMatcher=new AntPathMatcher();
    //执行过滤功能
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        //对于登录接口的请求就不拦截
        if(antPathMatcher.match("/admin/user/**",path)){
            return chain.filter(exchange);
        }else {//对于非登录接口必须验证
            List<String> token = request.getHeaders().get("X-Token");
            if(token==null){
                //拦截
                ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                //路由跳转
                response.getHeaders().set(HttpHeaders.LOCATION,"http://localhost:9528");
                return response.setComplete();
            }else {
                //放行
                return chain.filter(exchange);
            }
        }

    }

    //影响全局过滤器执行顺序，值越小，优先级越高
    @Override
    public int getOrder() {
        return 0;
    }
}
