package org.fullstack4.projectstudywithme.Common;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CookieUtil {
    public static void setCookies(HttpServletResponse resp, int expire, String name, String val) {
        Cookie cookie = new Cookie(name,val);
        cookie.setPath("/");
        cookie.setMaxAge(expire);
        resp.addCookie(cookie);
    }

    public static String getCookieValue(HttpServletRequest req, String name) {
        String rtnCookie="";
        Cookie[] cookies = req.getCookies();
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
                    rtnCookie = cookie.getValue();
                    break;
                }
            }
        }
        return rtnCookie;
    }

    public static void setDeleteCookie(HttpServletResponse resp,String name) {
        setCookies(resp,  0, name, "");
    }
}
