package br.com.cupuama;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.com.cupuama.util.LoggingInterceptor;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
public class CupuamaApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
    	System.setProperty("server.servlet.context-path", "/cupuama-app");
		SpringApplication.run(CupuamaApplication.class, args);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoggingInterceptor()).addPathPatterns("/**");
    }

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//    	registry.addMapping("/cupuama-app/v1/fruits").allowedOrigins("*");
//    }
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
          .addResourceLocations("classpath:/META-INF/resources/");
     
        registry.addResourceHandler("/webjars/**")
          .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
    
    @Bean
    public Docket docket() {
		return new Docket(DocumentationType.SWAGGER_2)
			.select()
			.apis(RequestHandlerSelectors.basePackage(getClass()
				.getPackage()
				.getName()))
			.paths(PathSelectors
				.any())
			.build()
			.apiInfo(generateApiInfo());
    }
    
    private ApiInfo generateApiInfo() {
		return new ApiInfoBuilder()
					.title("Cupuama SpringBoot Services")
					.description("Cupuama Application Services")
					.version("2.0.0")
					.license("Apache")
      				.termsOfServiceUrl("http://www.apache.org/licenses/LICENSE-2.0")
					.build();
    }

}
