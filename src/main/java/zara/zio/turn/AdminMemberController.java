package zara.zio.turn;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import zara.zio.turn.domain.MemberVO;
import zara.zio.turn.domain.Pagination;
import zara.zio.turn.persistence.MemberService;

@Controller
public class AdminMemberController {
	
	@Inject
	private MemberService service;
	
	@RequestMapping(value="/memberList", method = RequestMethod.GET)
	public String memberList(Model model, Pagination pagination) throws Exception{
		
		System.out.println(pagination);
		
		List<MemberVO> member = service.listAll(pagination);
		
		model.addAttribute("member", member);
		model.addAttribute("pagination", pagination);
		
		int totalCount = service.getTotalAll(); 
		
		pagination.setTotalCount(totalCount); // pagination 계산
		
		return "adminPage/memberList";
	}
	
	@RequestMapping(value="/memberRead", method=RequestMethod.GET)
	public String memberRead(@RequestParam(value="user_id", defaultValue="-1") String user_id, @ModelAttribute Pagination pagination, Model model) throws Exception {
		// (read?bno=?? 라는 주소로 접근한다.) 
		MemberVO member = service.read(user_id); // 자바빈 객체를 반환
		model.addAttribute("member", member); // 해당정보반환 
		
		return "adminPage/memberRead";
	}
	
	@RequestMapping(value="/memberDel", method=RequestMethod.GET)
	public String uploadDel(String check) throws Exception{

		service.remove(check);
		
		return "redirect:memberList";
	}
	
	@RequestMapping(value="/memberSet", method=RequestMethod.GET)
	public String memberSet(String check, @ModelAttribute Pagination pagination, Model model) throws Exception {
		
		MemberVO member = service.read(check); // 자바빈 객체를 반환
		
		String yyyy = member.getUser_birth().substring(0,4);
		String mm = member.getUser_birth().substring(5,7);
		String dd = member.getUser_birth().substring(8);
		
		member.setYyyy(yyyy);
		member.setDd(dd);
		member.setMm(mm);
		
		model.addAttribute("member", member);
		
		return "adminPage/memberSet";
	}
	
	@RequestMapping(value="/memberSet", method=RequestMethod.POST)
	public String memberSet(String check, MemberVO mem, RedirectAttributes rttr, int page, int recordPage) throws Exception {
		
		String yyyy = mem.getYyyy();
		String mm = mem.getMm();
		String dd = mem.getDd();
		String birth = yyyy + "-" + mm + "-" + dd;
		mem.setUser_birth(birth);
		
		service.modify(mem, check);
		
		rttr.addAttribute("page", page);
		rttr.addAttribute("recordPage", recordPage);
		rttr.addAttribute("user_id", mem.getUser_id());
		
		return "redirect:memberRead";
		
	}
	
}
