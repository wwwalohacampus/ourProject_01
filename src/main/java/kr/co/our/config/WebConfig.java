package kr.co.our.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
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
	
	@Bean
	public SpringResourceTemplateResolver templateResolver() {
		SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
	      templateResolver.setApplicationContext(applicationContext);
	      templateResolver.setPrefix("templates/");
	      templateResolver.setSuffix(".html");
		
		return templateResolver;
	}
	
	
	@Bean
   public SpringTemplateEngine templateEngine() {
      SpringTemplateEngine templateEngine = new SpringTemplateEngine();
      templateEngine.setTemplateResolver(classLoaderTemplateResolver());
      templateEngine.setEnableSpringELCompiler(true);
      return templateEngine;
   }


	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		ThymeleafViewResolver resolver = new ThymeleafViewResolver();
		resolver.setTemplateEngine(templateEngine());
		registry.viewResolver(resolver);
	}
	
	
	
	
}








