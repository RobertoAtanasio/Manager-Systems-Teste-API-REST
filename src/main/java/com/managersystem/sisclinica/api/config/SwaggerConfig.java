package com.managersystem.sisclinica.api.config;

import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
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
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()                 
                .apis(RequestHandlerSelectors.basePackage("com.managersystem.sisclinica.api.resource"))
                .paths(regex("/*.*"))
                .build()
                .apiInfo(apiInfo());
             
    }
	
	private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .contact(new Contact("Roberto Atanásio", "", "roberto.atanasio.pl@gmail.com"))
                .title("API REST Teste")
                .description("Documentação API REST Teste")
                .license("Apache Licence Version 2.0")
                .licenseUrl("https://apache.org")
                .version("1.0.0")
                .build();

    }

}
