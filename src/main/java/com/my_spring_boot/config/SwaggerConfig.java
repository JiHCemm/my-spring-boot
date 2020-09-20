package com.my_spring_boot.config;

import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2配置类
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer {

    @Bean
    public Docket createRestApi() {

        // 为swagger添加header参数可供输入
        ParameterBuilder userTokenHeader = new ParameterBuilder();
        userTokenHeader.name("token").description("令牌")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(false).build();
        List<Parameter> pars = new ArrayList<Parameter>();
        pars.add(userTokenHeader.build());
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                // 加了ApiOperation注解的类，才会生成接口文档
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                // 指定包下的类，才生成接口文档
                //.apis(RequestHandlerSelectors.basePackage("com.my_springboot.user.controller"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(pars);
    }

    private ApiInfo apiInfo() {
        //访问地址：http://localhost:8080/swagger-ui.html#!
        return new ApiInfoBuilder()
                .title("my_springboot后端api接口文档")// 设置页面标题
                .description("欢迎访问my_springboot接口文档，本文档描述了my_springboot后端相关接口的定义")// 描述
                .termsOfServiceUrl("https://服务条款.com/")// 服务条款
                .contact(new Contact("联系人", null, null))// 设置联系人
                .version("1.0.0")// 定义版本号
                .build();
    }

}
