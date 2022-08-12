package com.bzhao.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.addOriginalRequestUrl;

@Component
@Slf4j
public class RequestGlobalFilter implements GlobalFilter, Ordered {


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        addOriginalRequestUrl(exchange, request.getURI());
        String finPath="";
        String rawPath = request.getURI().getRawPath();
        int i = rawPath.indexOf("/", 0);
        int ii = rawPath.indexOf("/", 1);
        String serviceName = rawPath.substring(i, ii);
        String path = rawPath.substring( ii);
        if(!path.startsWith("/api")){
            finPath="/api"+path;
        }else {
            finPath=path;
        }
        List<String>regPath=new ArrayList<>();
//        regPath.add("/test");
        regPath.add("/oauth");
        regPath.add("/v2/api-docs");
        regPath.add("/repository/deployments");
        for (String s : regPath) {
            if(path.startsWith(s)){
                rawPath.replace("/api","");
                finPath=path;
                break;
            }
        }
        finPath=serviceName+finPath;
        ServerHttpRequest newRequest = request.mutate()
                .path(finPath)
                .build();
        log.info(newRequest.getURI().toString());
        exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, newRequest.getURI());

        return chain.filter(exchange.mutate()
                .request(newRequest).build());

    }
    @Override
    public int getOrder() {
        return 1;
    }


}
