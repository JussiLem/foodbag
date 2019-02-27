package me.foodbag.hello.security.custom;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

public class CustomWebAuthenticationDetails extends WebAuthenticationDetails {
  private static final long serialVersionUID = 1L;

  private final String verificationCode;

  CustomWebAuthenticationDetails(HttpServletRequest request) {
    super(request);
    verificationCode = request.getParameter("code");
  }

  public String getVerificationCode() {
    return verificationCode;
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    return super.equals(obj);
  }
}