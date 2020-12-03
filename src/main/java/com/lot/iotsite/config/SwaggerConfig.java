package com.lot.iotsite.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    //配置Swagger的Docket实例
    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .enable(true)
                .select()
                //只扫描controller包
                //.apis(RequestHandlerSelectors.basePackage("com.Iot.iotsite.controller"))
                .build();
    }

    //配置Swagger信息
    public ApiInfo apiInfo(){
        //作者信息
         Contact concat=new Contact("打工人","http://39.106.66.219","972991342@qq.com");
                return new ApiInfo(
                        "智慧工地检查系统的SwaggerAPI文档",
                        "干就完了！",
                        "v1.0",
                        "http://39.106.66.219",
                        concat,
                        "Apache 2.0",
                        "http://www.apache.org/licenses/LICENSE-2.0",
                        new ArrayList<>()
                        );
    }
}
