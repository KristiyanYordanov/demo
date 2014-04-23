package com.krissoft.saa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@ComponentScan(basePackages = "com.krissoft.saa")
@EnableWebMvc
@EnableMongoRepositories(basePackages = "com.krissoft.saa")
public class ApplicationContext extends WebMvcConfigurerAdapter {

	@Autowired
	private Environment env;

	public ApplicationContext() {
		super();
	}

	// @Override
	// public void addResourceHandlers(ResourceHandlerRegistry registry) {
	// registry.addResourceHandler("/resources/**")
	// .addResourceLocations("classpath:/META-INF/resources/webjars/")
	// .setCachePeriod(31556926);
	// registry.addResourceHandler("/css/**").addResourceLocations("/css/")
	// .setCachePeriod(31556926);
	// registry.addResourceHandler("/img/**").addResourceLocations("/img/")
	// .setCachePeriod(31556926);
	// registry.addResourceHandler("/js/**").addResourceLocations("/js/")
	// .setCachePeriod(31556926);
	// }

	@Override
	public void configureDefaultServletHandling(
			DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Bean
	public InternalResourceViewResolver getInternalResourceViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}
}