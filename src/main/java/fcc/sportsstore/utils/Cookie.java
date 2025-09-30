package fcc.sportsstore.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Cookie {

    /**
     * Get cookie by key
     * @param request Http servlet request
     * @param key Cookie key to get
     * @return Cookie value
     */
    public String getCookie(HttpServletRequest request, String key) {
        jakarta.servlet.http.Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (jakarta.servlet.http.Cookie cookie : cookies) {
                if (cookie.getName().equals(key)) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }

    /**
     * Set cookie
     * @param response Http servlet response
     * @param key Cookie key
     * @param value Cookie value
     * @param expired Expired time (seconds)
     */
    public void setCookie(HttpServletResponse response,
              String key,
              String value,
              int expired) {
        jakarta.servlet.http.Cookie cookie = new jakarta.servlet.http.Cookie(key, value);

        cookie.setPath("/");
        cookie.setMaxAge(expired);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);

        response.addCookie(cookie);
    }
}
