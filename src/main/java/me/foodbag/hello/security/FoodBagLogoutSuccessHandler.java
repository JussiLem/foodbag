package me.foodbag.hello.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component("foodBagLogoutSuccessHandler")
public class FoodBagLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        final HttpSession session = httpServletRequest.getSession();
        if (session != null) {
            session.removeAttribute("user");
        }

        httpServletResponse.sendRedirect("/logout.html?logSucc=true");
    }
}
