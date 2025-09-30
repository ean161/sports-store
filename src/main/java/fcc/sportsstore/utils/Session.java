package fcc.sportsstore.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Session {

    /**
     * Get session value by key
     * @param request Http servlet request
     * @param key Session key
     * @return Session value
     */
    public Object getSession(HttpServletRequest request, String key) {
        return request.getSession().getAttribute(key);
    }

    /**
     * Set session
     * @param request Http servlet request
     * @param key Session key
     * @param value Session value
     */
    public void setSession(HttpServletRequest request,
           String key,
           Object value) {
        request.getSession(true).setAttribute(key, value);
    }
}
