package me.foodbag.hello.security.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

/** Customizes the exception messages coming from MyUserDetailsService */
@Component("authenticationFailureHandler")
public class FoodBagAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

  @Autowired MessageSource messageSource;

  @Autowired LocaleResolver localeResolver;

  @Override
  public void onAuthenticationFailure(
      HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
      throws IOException, ServletException {
    super.onAuthenticationFailure(request, response, exception);

    final Locale locale = localeResolver.resolveLocale(request);

    String errorMessage = messageSource.getMessage("message.badCredentials", null, locale);

    if (exception.getMessage().equalsIgnoreCase("User is disabled")) {
      errorMessage = messageSource.getMessage("auth.message.disabled", null, locale);
    } else if (exception.getMessage().equalsIgnoreCase("User account has expired")) {
      errorMessage = messageSource.getMessage("auth.message.expired", null, locale);
    } else if (exception.getMessage().equalsIgnoreCase("blocked")) {
      errorMessage = messageSource.getMessage("auth.message.blocked", null, locale);
    }

    request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, errorMessage);
  }
}
