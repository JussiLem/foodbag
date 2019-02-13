package me.foodbag.hello.spring;

import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.Locale;

@Configuration
@ComponentScan(basePackages = "me.foodbag.hello.web")
@EnableWebMvc
public class MvcConf implements WebMvcConfigurer {

  @Autowired @Lazy
  private MessageSource messageSource;

    // Overrided methods

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/").setViewName("forward:/login");
    registry.addViewController("/login");
    registry.addViewController("/home.html");
    registry.addViewController("/registration.html");
    registry.addViewController("/console.html");
    registry.addViewController("/invalidSession.html");
  }



  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/resources/**").addResourceLocations("/", "/resources");
  }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        registry.addInterceptor(localeChangeInterceptor);
    }

    @Bean
    public LocaleResolver localeResolver() {
      final CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
      cookieLocaleResolver.setDefaultLocale(Locale.getDefault());
      return cookieLocaleResolver;
    }

   @Bean
  public MessageSource messageSource() {
  final ReloadableResourceBundleMessageSource messageSources = new ReloadableResourceBundleMessageSource();
     messageSources.setBasename("classpath:messages");
     messageSources.setUseCodeAsDefaultMessage(true);
     messageSources.setDefaultEncoding("UTF-8");
     messageSources.setCacheSeconds(0);
  return messageSources;
  }

  @Bean
  public EmailValidator usernameValidator() {
    return new EmailValidator();
  }

  @Bean
  @ConditionalOnMissingBean(RequestContextListener.class)
  public RequestContextListener requestContextListener() {
    return new RequestContextListener();
  }

  @Override
  public Validator getValidator() {
    LocalValidatorFactoryBean validatorFactoryBean = new LocalValidatorFactoryBean();
    validatorFactoryBean.setValidationMessageSource(messageSource);
    return validatorFactoryBean;
  }
}
