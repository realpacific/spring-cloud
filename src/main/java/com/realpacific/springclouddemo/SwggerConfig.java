package com.realpacific.springclouddemo;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableSwagger2
public class SwggerConfig {

    private static ApiInfo Default_API_INFO = new ApiInfo("Prashant's api", "Dociumentation for Spring Cloud demo app",
            "v2.11", "Whatever",
            new Contact("Prashant", "wwww", "prashant@spring.com"), "MIT", "www");
    private static final Set<String> DEFAULT_PRODUCE_AND_CONSUME = new HashSet<>(Arrays.asList("application/json", "application/xml"));

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(Default_API_INFO)
                .produces(DEFAULT_PRODUCE_AND_CONSUME)
                .consumes(DEFAULT_PRODUCE_AND_CONSUME);
    }

}
