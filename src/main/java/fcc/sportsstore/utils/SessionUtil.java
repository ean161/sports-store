package fcc.sportsstore.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class SessionUtil {

    private HttpServletRequest request = null;

    private HttpServletResponse response = null;

    public SessionUtil(HttpServletRequest request,
                       HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    public SessionUtil(HttpServletRequest request) {
        this.request = request;
    }

    /**
     * Get session value by key
     * @param key Session key
     * @return Session value
     */
    public Object getSession(String key) {
        return request.getSession().getAttribute(key);
    }

    /**
     * Set session
     * @param key Session key
     * @param value Session value
     */
    public void setSession(String key,
                           Object value) {
        request.getSession(true).setAttribute(key, value);
    }

    /**
     * delete cookie
     * @param key Session key
     */
    public void deleteSession(String key){
        HttpSession session = request.getSession();
        session.removeAttribute(key);
    }
}
