package com.kh.spring.demo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.kh.spring.demo.model.service.DemoService;
import com.kh.spring.demo.model.vo.Dev;

import lombok.extern.slf4j.Slf4j;

/*
* HttpServletRequest
* HttpServletResponse
* HttpSession
* java.util.Locale : 요청에 대한 Locale
* InputStream/Reader : 요청에 대한 입력스트림
* OutputStream/Writer : 응답에 대한 출력스트림. ServletOutputStream, PrintWriter
컨트롤러클래스 메소드가 가질수 있는 파라미터
* @PathVariable: 요청url중 일부를 매개변수로 취할 수 있다.
* @RequestParam
* @RequestHeader
* @CookieValue
* @RequestBody
뷰에 전달할 모델 데이터 설정
* ModelAndView
* Model
* ModelMap 
* @ModelAttribute : model속성에 대한 getter
* @SessionAttribute : session속성에 대한 getter
* SessionStatus: @SessionAttribute로 등록된 속성에 대하여 사용완료(complete)처리
* Command객체 : http요청 파라미터를 커맨드객체에 저장한 VO객체
* Error, BindingResult : Command객체에 저장결과, Command객체 바로 다음위치시킬것.
기타
* MultipartFile : 업로드파일 처리 인터페이스. CommonsMultipartFile
* RedirectAttributes : DML처리후 요청주소 변경을 위한 redirect를 지원
*/

@Slf4j
@Controller
@RequestMapping("/demo")
public class DemoController {
	
	//@Slf4j에 의해 생성되는 코드
	//Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private DemoService demoService;
	
	/**
	 * 사용자 요청 url에 따라 호출되는 메소드
	 * 지금까지는 요청 하나당 그에 상응하는 서블릿이 필요했으나, 이제는 그에 상응하는 메소드만 준비해두면 됨
	 * @return
	 */
	@RequestMapping("/devForm.do")
	public String devForm() {
		return "demo/devForm";
	}
	
//	@RequestMapping("/demo/dev1.do") //GET POST 처리
	@RequestMapping(value = "/dev1.do", method = RequestMethod.POST) //GET 처리
	public String dev1(HttpServletRequest request, HttpServletResponse response, Model model) {
		String name = request.getParameter("name");
		int career = Integer.valueOf(request.getParameter("career"));
		String email = request.getParameter("email");
		String gender = request.getParameter("gender");
		String[] lang = request.getParameterValues("lang");
		
		Dev dev = new Dev(0, name, career, email, gender, lang);
//		System.out.println(dev);
		log.info("dev = {}", dev);
		
		//request.setAttribute("dev", dev);
		model.addAttribute("dev", dev); // model객체를 통해 jsp에 객체,값등을 전달
		
		
		return "demo/devResult";
	}
	
	@RequestMapping(value = "/dev2.do", method = RequestMethod.POST)
	public String dev2(
				@RequestParam(value="name") String name,
				@RequestParam(value="career") int career,
				@RequestParam(value="email") String email,
				@RequestParam(value="gender", defaultValue = "M") String gender,
				@RequestParam(value="lang", required = false) String[] lang,
				//자바랑 전송하는 name값이 다를떄 이렇게씀
				//같다면 @RequestParam String name, 생략가능
				Model model
			
			) {
		Dev dev = new Dev(0, name, career, email, gender, lang);
		log.info("dev = {}", dev);
		
		model.addAttribute("dev", dev);
		
		return "demo/devResult";
	}
	
	/*
	 * 커맨드객체 : 사용자입력값과 일치하는 필드에 값대임
	 * 커맨드객체는 자동으로 model속성으로 지정
	 * */
	
//	@RequestMapping(value = "/dev3.do", method = RequestMethod.POST)
	@PostMapping("/dev3.do")
	public String dev3(Dev dev) {
		
		return "demo/devResult";
	}
	
	@PostMapping("/insertDev.do")
	public String insertDev(Dev dev) {
		log.info("{}", dev);
		return "demo/devResult";
	}
}


