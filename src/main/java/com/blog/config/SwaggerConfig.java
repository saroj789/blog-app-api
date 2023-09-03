package com.blog.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfig {
	
	 @Bean
	    public OpenAPI usersMicroserviceOpenAPI() {
	        return new OpenAPI()
	                .info(apiInfo());
	    }
	 
	 
	 
	 private Info apiInfo(){
		 Contact c = new Contact().name("BLOG-APP-API Team").email("blogappapi@gmail.com").url("www.blogappapi.com");
		 License l = new License().name("License of API").url("API license URL");   
		// Another way of setting variable like above
		 
		 Map <String,Object> ext = new HashMap<>();
		 
		 Info info = new Info()
				 .title("BLOG-APP-API")
				 .description("Spring Boot BLOG-APP-API Documentation")
				 .version("v1")
				 .termsOfService("Terms of service")
				 .contact(c)
				 .license(l)
				 .extensions(ext);
				 
	     return info;
	   }
    
	 
}

