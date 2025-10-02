package fcc.sportsstore.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CookieUtil {

    private HttpServletRequest request = null;

    private HttpServletResponse response = null;

    /**
     * Constructor
     * @param request HTTP servlet request
     * @param response HTTP servlet response
     */
    public CookieUtil(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    /**
     * Constructor with req
     * @param request HTTP servlet request
     */
    public CookieUtil(HttpServletRequest request) {
        this.request = request;
    }

    /**
     * Constructor with res
     * @param response HTTP servlet response
     */
    public CookieUtil(HttpServletResponse response) {
        this.response = response;
    }

    /**
     * Get cookie by key
     * @param key Cookie key to get
     * @return Cookie value
     */
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

    /**
     * Set cookie
     * @param key Cookie key
     * @param value Cookie value
     * @param expired Expired time (seconds)
     */
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
}
