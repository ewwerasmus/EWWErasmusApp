package com.tuxdave.erasmusapp.ws_login.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.tuxdave.erasmusapp.ws_login.controller"))
                .paths(PathSelectors.regex("\\/.*"))
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("ErasmusApp Web Service")
                .description("Spring Boot REST Api per la gestione dei Login.")
                .contact(new Contact("TuxDave", "https://github.com/TuxDave", "godinodavide@gmail.com"))
                .version("1.0.0-SNAPSHOT")
                .build();
    }
}
