package me.foodbag.hello.spring;

import me.foodbag.hello.validation.password.PasswordMatchesValidator;
import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.Locale;

/** Kustomointi konfiguraatioita */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "me.foodbag.hello.web")
public class MvcConf implements WebMvcConfigurer {

  public MvcConf() {
    super();
  }

  @Autowired private MessageSource messageSource;

  // Overrided methods

  /**
   * Map URLs to views so there is no need to create actual controller class. Need to pre-define
   * URLs and their mapping views
   *
   * @param registry
   */
  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/").setViewName("forward:/login");
    registry.addViewController("/login");
    registry.addViewController("/home.html");
    registry.addViewController("/registration.html");
    registry.addViewController("/console.html");
    registry.addViewController("/invalidSession.html");
    registry.addViewController("/homepage.html");
    registry.addViewController("/successRegister.html");
    registry.addViewController("/users.html");
    registry.addViewController("/admin.html");
    registry.addViewController("/logout.html");
    registry.addViewController("/emailError.html");
    registry.addViewController("/users.html");
    registry.addViewController("/forgetPassword.html");
    registry.addViewController("/updatePassword.html");
    registry.addViewController("/changePassword.html");
    registry.addViewController("/badUser.html");
    registry.addViewController("/registrationCaptcha.html");
  }

  @Override
  public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
    configurer.enable();
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/resources/**").addResourceLocations("/", "/resources");
  }

  /**
   * Needs to be configured as a Spring bean somewhere (XML, Java Config or using annotations)
   *
   * @param registry
   */
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
    localeChangeInterceptor.setParamName("lang");
    registry.addInterceptor(localeChangeInterceptor);
  }

  /**
   * works in conjunction with its LocaleChangeInterceptor API to make possible the display of
   * messages in different languages. By default, the locale resolver will obtain the locale code
   * from the HTTP header, so it needs to be forced by setting it on the LocalResolver().
   * CookieLocaleResolver is being used, which means that it stores the locale information in a
   * client side cookie; it will remember the user’s locale every time they log in, and during the entire visit.
   *
   * @return local language
   */
  @Bean
  public LocaleResolver localeResolver() {
    final CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
    cookieLocaleResolver.setDefaultLocale(Locale.getDefault());
    return cookieLocaleResolver;
  }
  /*
    @Bean
    public MessageSource messageSource() {
      final ReloadableResourceBundleMessageSource messageSources =
          new ReloadableResourceBundleMessageSource();
      messageSources.setBasename("classpath:messages");
      messageSources.setUseCodeAsDefaultMessage(true);
      messageSources.setDefaultEncoding("UTF-8");
      messageSources.setCacheSeconds(0);
      return messageSources;
    }
  */
  @Bean
  public EmailValidator usernameValidator() {
    return new EmailValidator();
  }

  @Bean
  PasswordMatchesValidator passwordMatchesValidator() {
    return new PasswordMatchesValidator();
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
