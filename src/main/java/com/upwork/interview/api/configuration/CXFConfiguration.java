package com.upwork.interview.api.configuration;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.upwork.interview.api.ConnectionResource;
import org.apache.cxf.Bus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.validation.JAXRSBeanValidationFeature;
import org.apache.cxf.jaxrs.validation.JAXRSBeanValidationInvoker;
import org.apache.cxf.jaxrs.validation.JAXRSParameterNameProvider;
import org.apache.cxf.validation.BeanValidationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.ws.rs.ext.ExceptionMapper;
import java.util.Arrays;
import java.util.List;

@Configuration
public class CXFConfiguration {

    private final ConnectionResource connectionResource;

    public CXFConfiguration(ConnectionResource connectionResource) {
        this.connectionResource = connectionResource;
    }

    @Bean
    @Autowired
    public Server rsServer(Bus bus, List<ExceptionMapper<?>> exceptionMappers) {
        JAXRSServerFactoryBean endpoint = new JAXRSServerFactoryBean();
        endpoint.setBus(bus);
        endpoint.setAddress("/");
        endpoint.setFeatures(Arrays.asList(validationFeature()));
        endpoint.setInvoker(validationInvoker());
        setServiceBeans(endpoint);
        setProviders(endpoint, exceptionMappers);
        return endpoint.create();
    }

    @Bean
    public JacksonJsonProvider jacksonJsonProvider() {
        return new JacksonJsonProvider();
    }

    private void setServiceBeans(JAXRSServerFactoryBean endpoint) {
        endpoint.setServiceBean(connectionResource);
    }

    private void setProviders(JAXRSServerFactoryBean endpoint, List<ExceptionMapper<?>> exceptionMappers) {
        endpoint.setProvider(jacksonJsonProvider());
        endpoint.setProviders(exceptionMappers);
    }

    @Bean
    public JAXRSParameterNameProvider parameterNameProvider() {
        return new JAXRSParameterNameProvider();
    }

    @Bean
    public BeanValidationProvider validationProvider() {
        return new BeanValidationProvider(parameterNameProvider());
    }

    @Bean
    public JAXRSBeanValidationInvoker validationInvoker() {
        final JAXRSBeanValidationInvoker validationInvoker = new JAXRSBeanValidationInvoker();
        validationInvoker.setProvider(validationProvider());
        return validationInvoker;
    }

    @Bean
    public JAXRSBeanValidationFeature validationFeature() {
        final JAXRSBeanValidationFeature feature = new JAXRSBeanValidationFeature();
        feature.setProvider(validationProvider());
        return feature;
    }

}
