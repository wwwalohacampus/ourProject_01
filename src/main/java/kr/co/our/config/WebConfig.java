package kr.co.our.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "templates")
public class WebConfig implements WebMvcConfigurer {
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Bean
	public ClassLoaderTemplateResolver classLoaderTemplateResolver() {
		
		ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
		
		templateResolver.setPrefix("templates/");
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode(TemplateMode.HTML);
		templateResolver.setCharacterEncoding("UTF-8");
		templateResolver.setOrder(0);
		templateResolver.setCacheable(false);
		templateResolver.setCheckExistence(true);
		
		return templateResolver;
	}
	
	
	
}








