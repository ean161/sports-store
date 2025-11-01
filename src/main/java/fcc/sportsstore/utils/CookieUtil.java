package fcc.sportsstore.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CookieUtil {

    private HttpServletRequest request = null;

    private HttpServletResponse response = null;

    public CookieUtil(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    public CookieUtil(HttpServletRequest request) {
        this.request = request;
    }

    public CookieUtil(HttpServletResponse response) {
        this.response = response;
    }

    public String getCookie(String key) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(key)) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }

    public void setCookie(String key,
              String value,
              int expired) {
        Cookie cookie = new Cookie(key, value);

        cookie.setPath("/");
        cookie.setMaxAge(expired);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);

        response.addCookie(cookie);
    }

    public void deleteCookie(String key) {
        Cookie cookie = new Cookie(key, null);

        cookie.setPath("/");
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);

        response.addCookie(cookie);
    }

}
