package br.com.ftech.users.conf;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class ServletSpringMVC extends AbstractAnnotationConfigDispatcherServletInitializer {
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[]{SecurityConfiguration.class,AppWebConfiguration.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return null;

	}

	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"};
	}

}
