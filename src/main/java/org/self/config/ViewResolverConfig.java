package org.self.config;

import org.springframework.context.annotation.Bean;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

public class ViewResolverConfig {
	@Bean
	public ClassLoaderTemplateResolver classPathTemplateResolver() {
	    ClassLoaderTemplateResolver classPathTemplateResolver = new ClassLoaderTemplateResolver();
	    classPathTemplateResolver.setPrefix("templates/");
	    classPathTemplateResolver.setSuffix(".html");
	    classPathTemplateResolver.setTemplateMode(TemplateMode.HTML);
	    classPathTemplateResolver.setCharacterEncoding("UTF-8");
	    classPathTemplateResolver.setOrder(1);
	    classPathTemplateResolver.setCheckExistence(true);
	    classPathTemplateResolver.setCacheable(false);
	        
	    return classPathTemplateResolver;
	}
}
