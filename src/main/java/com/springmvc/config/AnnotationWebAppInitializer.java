package com.springmvc.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class AnnotationWebAppInitializer  implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext container) throws ServletException {
    	AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();
        dispatcherContext.register(WebConfig.class);

        DispatcherServlet dispacherServlet = new DispatcherServlet(dispatcherContext);

        ServletRegistration.Dynamic myDispacherServlet = container.addServlet("myDispatcherServlet", dispacherServlet);
        myDispacherServlet.setLoadOnStartup(1);
        myDispacherServlet.addMapping("/");
    }
}