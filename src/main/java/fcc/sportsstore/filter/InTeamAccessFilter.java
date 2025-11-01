package fcc.sportsstore.filter;

import fcc.sportsstore.utils.CookieUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class InTeamAccessFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String path = req.getRequestURI();

        if (path.startsWith("/access/")) {
            chain.doFilter(request, response);
            return;
        }

        CookieUtil cookie = new CookieUtil(req, res);
        if (cookie.getCookie("is-access") == null || !cookie.getCookie("is-access").equals("true")) {
            res.sendRedirect("/access/denied");
            return;
        }

        chain.doFilter(request, response);
    }
}
