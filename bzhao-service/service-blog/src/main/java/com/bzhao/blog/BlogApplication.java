package com.bzhao.blog;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestController;


@SpringBootConfiguration
@SpringBootApplication
@RestController
@ComponentScan(
        basePackages = {"com.bzhao.blog.*","com.bzhao.common.component","com.bzhao.common.handler","springfox.documentation.schema"}
)
@MapperScan({"com.bzhao.**.mapper"})
@EnableDiscoveryClient
public class BlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }

}
