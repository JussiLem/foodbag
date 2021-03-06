package me.foodbag.hello.security;

import me.foodbag.hello.security.authentication.FoodBagAuthenticationProvider;
import me.foodbag.hello.security.custom.CustomAuthenticationProvider;
import me.foodbag.hello.security.custom.CustomWebAuthenticationDetailsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
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

  @Autowired private AuthenticationFailureHandler authenticationFailureHandler;

  @Autowired private LogoutSuccessHandler foodbagLogoutSuccessHandler;

  @Autowired private CustomWebAuthenticationDetailsSource authenticationDetailsSource;

  public SecurityConfig() {
    super();
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  /**
   * Reference to authProvider()
   *
   * @param auth
   * @throws Exception
   */
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(authProvider());
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers("/resources/**");
  }

  @Override
  protected void configure(final HttpSecurity http) throws Exception {
    http.csrf()
        .disable()
        .authorizeRequests()
        .antMatchers(
            "/login*",
            "/login*",
            "/logout*",
            "/signin/**",
            "/signup/**",
            "/customLogin",
            "/user/registration*",
            "/registrationConfirm*",
            "/expiredAccount*",
            "/registration*",
            "/badUser*",
            "/user/resendRegistrationToken*",
            "/forgetPassword*",
            "/user/resetPassword*",
            "/user/changePassword*",
            "/emailError*",
            "/resources/**",
            "/old/user/registration*",
            "/successRegister*",
            "/webjars/**",
            "/qrcode*")
        .permitAll()
        .antMatchers("/invalidSession*")
        .anonymous()
        .antMatchers("/user/updatePassword*", "/user/savePassword*", "/updatePassword*")
        .hasAuthority("CHANGE_PASSWORD_PRIVILEGE")
        .anyRequest()
        .hasAuthority("READ_PRIVILEGE")
        .and()
        .formLogin()
        .loginPage("/login")
        .defaultSuccessUrl("/homepage.html")
        .failureUrl("/login?error=true")
        .successHandler(myAuthenticationSuccessHandler)
        .failureHandler(authenticationFailureHandler)
        .authenticationDetailsSource(authenticationDetailsSource)
        .permitAll()
        .and()
        .sessionManagement()
        .invalidSessionUrl("/invalidSession.html")
        .maximumSessions(1)
        .sessionRegistry(sessionRegistry())
        .and()
        .sessionFixation()
        .none()
        .and()
        .logout()
        .logoutSuccessHandler(foodbagLogoutSuccessHandler)
        .invalidateHttpSession(false)
        .logoutSuccessUrl("/logout.html?logSucc=true")
        .deleteCookies("JSESSIONID")
        .permitAll();
  }

  /**
   * Will encode the password when the user authenticates.
   *
   * @return
   */
  @Bean
  public DaoAuthenticationProvider authProvider() {
    final CustomAuthenticationProvider authProvider = new CustomAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService);
    authProvider.setPasswordEncoder(passwordEncoder());
    return authProvider;
  }

  /**
   * BCrypt will internally generate a random salt, meaning that each call will have a different
   * result, so there is only need to encode the password once.
   *
   * @return String of length 60, make sure that the password will be stored in a column that can
   *     accommodate it. Otherwise multiple <i>Invalid Username or Password</i> errors will ensue.
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(11);
  }

  @Bean
  SessionRegistry sessionRegistry() {
    return new SessionRegistryImpl();
  }
}
