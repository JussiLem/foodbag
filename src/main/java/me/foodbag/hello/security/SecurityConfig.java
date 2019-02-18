package me.foodbag.hello.security;

import me.foodbag.hello.security.authentication.FoodBagAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;


@Configuration
@ComponentScan("me.foodbag.hello.security")
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired private UserDetailsService userDetailsService;

  @Autowired private AuthenticationSuccessHandler myAuthenticationSuccessHandler;

  @Autowired
  private AuthenticationFailureHandler authenticationFailureHandler;

  @Autowired
  private LogoutSuccessHandler foodbagLogoutSuccessHandler;


  @Override
  protected void configure(final HttpSecurity http) throws Exception {
    http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/login*","/login*", "/logout*", "/signin/**", "/signup/**", "/customLogin",
                    "/user/registration*", "/registrationConfirm*", "/expiredAccount*", "/registration*",
                    "/badUser*", "/user/resendRegistrationToken*" ,"/forgetPassword*", "/user/resetPassword*",
                    "/user/changePassword*", "/emailError*", "/resources/**","/old/user/registration*","/successRegister*","/qrcode*").permitAll()
            .antMatchers("/invalidSession*").anonymous()
            .antMatchers("/user/updatePassword*","/user/savePassword*","/updatePassword*").hasAuthority("CHANGE_PASSWORD_PRIVILEGE")
            .anyRequest().hasAuthority("READ_PRIVILEGE")
            .and()
            .formLogin()
            .loginPage("/login")
            .defaultSuccessUrl("/homepage.html")
            .failureUrl("/login?error=true")
            .successHandler(myAuthenticationSuccessHandler)
            .failureHandler(authenticationFailureHandler)
            .permitAll()
            .and()
            .sessionManagement()
            .invalidSessionUrl("/invalidSession.html")
            .maximumSessions(1).sessionRegistry(sessionRegistry()).and()
            .sessionFixation().none()
            .and()
            .logout()
            .logoutSuccessHandler(foodbagLogoutSuccessHandler)
            .invalidateHttpSession(false)
            .logoutSuccessUrl("/logout.html?logSucc=true")
            .deleteCookies("JSESSIONID")
            .permitAll();
  }

  @Bean
  public DaoAuthenticationProvider authProvider() {
    final FoodBagAuthenticationProvider authProvider = new FoodBagAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService);
    authProvider.setPasswordEncoder(passwordEncoder());
    return authProvider;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(11);
  }

  @Bean
  SessionRegistry sessionRegistry() {
    return new SessionRegistryImpl();
  }
}
