package zara.zio.turn;


import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import zara.zio.turn.domain.MemberVO;
import zara.zio.turn.persistence.MemberService;

/**
 * Handles requests for the application home page.
 */

@Controller
public class RegController {
	
//	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
//	
//	@RequestMapping(value = "/", method = RequestMethod.GET)
//	public String home(Locale locale, Model model) {
//		logger.info("Welcome home! The client locale is {}.", locale);
//		
//		Date date = new Date();
//		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
//		
//		String formattedDate = dateFormat.format(date);
//		
//		model.addAttribute("serverTime", formattedDate );
//		
//		return "home";
//	}
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	
	@Inject
	private MemberService service;
	
	// 회원가입 폼이동 
	@RequestMapping(value="register", method = RequestMethod.GET)
	public String registerForm() {
		return "registerForm/register";
	}
	
	// 회원가입 정보입력 전송
	@RequestMapping(value="register", method=RequestMethod.POST)
	public String create(MemberVO mem) throws Exception {
		String yyyy = mem.getYyyy();
		String mm = mem.getMm();
		String dd = mem.getDd();
		String birth = yyyy + "-" + mm + "-" + dd;
		
		String default_img = "/default.png";
		
		mem.setUser_birth(birth);
		mem.setUser_profile(default_img); // 디폴트이미지 설정
		
		service.regist(mem);
		return "redirect:login";
		
	}
	
	// 등록을 하고 다시 새로고침하였을때 남아있는 주소정보를 초기화 
    //	@RequestMapping(value="success")
	//	public String regResult() {
	//		return "registerForm/success";
	//	}
	
	// 아이디 중복체크 (내부처리)
	@RequestMapping(value="confirm")
	@ResponseBody // 등록시 아이디중복확인 (내부처리)
	public String idConfirm(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		System.out.println(id);
		String text = "";
		try {
			text = service.confirm(id) + "";
			System.out.println(text);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return text;
	}
	
}
