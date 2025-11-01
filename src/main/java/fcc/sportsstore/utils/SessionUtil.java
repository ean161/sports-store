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

    public Object getSession(String key) {
        return request.getSession().getAttribute(key);
    }

    public void setSession(String key,
                           Object value) {
        request.getSession(true).setAttribute(key, value);
    }

    public void deleteSession(String key){
        HttpSession session = request.getSession();
        session.removeAttribute(key);
    }
}
