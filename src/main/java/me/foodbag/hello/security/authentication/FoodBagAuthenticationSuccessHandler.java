package me.foodbag.hello.security.authentication;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import me.foodbag.hello.persistence.model.User;
import me.foodbag.hello.security.ActiveUserStore;
import me.foodbag.hello.security.LoggedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;

/**
 * Implement the interface and provide a custom implementation of the success handler. This
 * implementation is going to determine the URL to redirect the user to after login based on the
 * role of the user
 */
@Getter
@Setter
@Slf4j
@Component("FoodBagAuthenticationSuccessHandler")
public class FoodBagAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

  private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

  private static final int MAX_INTERVAL = 1800;
  private static final String READ_PRIVILEGE = "READ_PRIVILEGE";
  private static final String WRITE_PRIVILEGE = "WRITE_PRIVILEGE";

  @Autowired ActiveUserStore activeUserStorage;

  @Override
  public void onAuthenticationSuccess(
      HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse,
      Authentication authentication)
      throws IOException {
    handle(httpServletRequest, httpServletResponse, authentication);
    final HttpSession session = httpServletRequest.getSession(false);
    if (session != null) {
      session.setMaxInactiveInterval(MAX_INTERVAL);

      String username;
      if (authentication.getPrincipal() instanceof User) {
        username = ((User) authentication.getPrincipal()).getEmail();
      } else {
        username = authentication.getName();
      }
      LoggedUser loggedUser = new LoggedUser(username, activeUserStorage);
      session.setAttribute("user", loggedUser);
    }
    clearAuthAttributes(httpServletRequest);
  }

  private void handle(
      final HttpServletRequest request,
      final HttpServletResponse response,
      final Authentication auth)
      throws IOException {

    final String targetUrl = targetUrl(auth);

    if (response.isCommitted()) {
      log.debug("Response has already been commited. Unable to redirect into, {}", targetUrl);
      return;
    }
    redirectStrategy.sendRedirect(request, response, targetUrl);
  }

  /**
   * The core of the strategy – simply looks at the type of user (determined by the authority) and
   * picks the target URL based on this role. So, an admin user – determined by the ROLE_ADMIN
   * authority – will be redirected to the console page after login, while the standard user – as
   * determined by ROLE_USER – will be redirected to the homepage.
   *
   * @param auth
   * @return the target url based on user role
   */
  private String targetUrl(final Authentication auth) {
    boolean isUser = false;
    boolean isAdmin = false;
    final Collection<? extends GrantedAuthority> grantedAuthorities = auth.getAuthorities();
    for (final GrantedAuthority grantedAuthority : grantedAuthorities) {
      if (grantedAuthority.getAuthority().equals(READ_PRIVILEGE)) {
        isUser = true;
      } else if (grantedAuthority.getAuthority().equals(WRITE_PRIVILEGE)) {
        isAdmin = true;
        isUser = false;
        break;
      }
    }
    if (isUser) {
      String username;
      if (auth.getPrincipal() instanceof User) {
        username = ((User) auth.getPrincipal()).getEmail();
      } else {
        username = auth.getName();
      }

      return "/homepage.html?user=" + username;
    } else if (isAdmin) {
      return "/console.html";
    } else {
      throw new IllegalStateException("Unexpected exception has occured!");
    }
  }

  private void clearAuthAttributes(final HttpServletRequest request) {
    final HttpSession session = request.getSession(false);

    if (session == null) {
      return;
    }

    session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
  }
}
