package kr.co.our.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import nz.net.ultraq.thymeleaf.LayoutDialect;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	/*
    @Bean
    public ClassLoaderTemplateResolver templateResolver() {
        ClassLoaderTemplateResolver configurer = new ClassLoaderTemplateResolver();
        configurer.setPrefix("templates/");
        configurer.setCacheable(false);
        configurer.setSuffix(".html");
        configurer.setTemplateMode("HTML5");
        configurer.setCharacterEncoding("UTF-8");
        return configurer;
    }
    
    */
	
	@Bean
	public SpringResourceTemplateResolver templateResolver() {
		SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
		templateResolver.setPrefix("classpath:templates/");
		templateResolver.setCharacterEncoding("UTF-8");
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode("LEGACYHTML5");
		
		return templateResolver;
	}
	
    
    
    @Bean
    public SpringTemplateEngine templateEngine() {
    	
    	SpringTemplateEngine templateEngine = new SpringTemplateEngine();
    	templateEngine.setTemplateResolver(templateResolver());
    	templateEngine.setEnableSpringELCompiler(true);
    	templateEngine.addDialect(layoutDialect());
    	templateEngine.addDialect(securityDialect());
    	
    	return templateEngine;
    }
    
    @Bean
    public LayoutDialect layoutDialect() {
        return new LayoutDialect();
    }
    @Bean
	public SpringSecurityDialect securityDialect() {
		return new SpringSecurityDialect();
	}

    
    @Bean
    public ViewResolver viewResolver() {
    	
    	ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
    	viewResolver.setTemplateEngine(templateEngine());
    	viewResolver.setCharacterEncoding("UTF-8");
    	viewResolver.setOrder(0);
    	
    	return viewResolver;
    }


    
    
}
























