package com.kh.spring.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.FlashMapManager;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.kh.spring.member.model.vo.Member;

public class LoginInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//로그인검사
		HttpSession session = request.getSession();
		Member loginMember = (Member)session.getAttribute("loginMember");
		if(loginMember == null) {
			//RedirectAttributes 내부에서 사용하는 FlashMap에 사용자 피드백 저장
			FlashMap flashMap = new FlashMap();
			flashMap.put("msg", "로그인 후 이용하세요");
			//RequestContextUtils 이번요청을 담당하는 클래스 REQUEST전반에 관련 상태에 접근하기 위한 Util성 클래스
			FlashMapManager manager = RequestContextUtils.getFlashMapManager(request);
			manager.saveOutputFlashMap(flashMap, request, response);
			
			response.sendRedirect(request.getContextPath());
			return false;
		}
		return super.preHandle(request, response, handler);
	}

}
