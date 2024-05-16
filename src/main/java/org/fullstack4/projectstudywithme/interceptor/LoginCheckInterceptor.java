package org.fullstack4.projectstudywithme.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.fullstack4.projectstudywithme.Common.CommonUtil;
import org.fullstack4.projectstudywithme.Common.CookieUtil;
import org.fullstack4.projectstudywithme.dto.MemberDTO;
import org.fullstack4.projectstudywithme.service.MemberServiceIf;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Log4j2
@Component
@RequiredArgsConstructor
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        String sessionId = "";
        String admin_YN = "";
        if (session != null) {
            MemberDTO sessionMemberDTO = null;
            sessionMemberDTO = (session.getAttribute("memberDTO") != null) ? (MemberDTO) session.getAttribute("memberDTO") : null;
            sessionId = (sessionMemberDTO != null) ? sessionMemberDTO.getMemberId() : "";
        }
        if (sessionId.equals("")) {
            response.sendRedirect("/login/login");
            return false;
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
