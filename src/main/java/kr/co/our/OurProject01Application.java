package kr.co.our;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@MapperScan(basePackages = "kr.co.our.mapper")
public class OurProject01Application {

	public static void main(String[] args) {
		SpringApplication.run(OurProject01Application.class, args);
	}

}
