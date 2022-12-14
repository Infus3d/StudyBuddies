package org.springframework.studybuddies;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;



/**
 * Iowa State University COM S 309 Project
 * Study Buddies Spring Project
 * 
 * @author Ryan Sand and Brady Heath
 */

@SpringBootApplication
@EnableSwagger2
public class StudyBuddiesApplication {
	
    public static void main(String[] args) throws Exception {
        SpringApplication.run(StudyBuddiesApplication.class, args);
    }

    
    @Configuration
    @EnableSwagger2WebMvc
    @Import(SpringDataRestConfiguration.class)
    public class SpringFoxConfig {                                    
        @Bean
        public Docket api() { 
            return new Docket(DocumentationType.SWAGGER_2)  
              .select()                                  
              .apis(RequestHandlerSelectors.any())              
              .paths(PathSelectors.any())                          
              .build();                                           
        }
    }
    
    
}
