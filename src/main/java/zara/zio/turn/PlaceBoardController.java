package zara.zio.turn;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import zara.zio.turn.domain.PlaceInfoListVO;
import zara.zio.turn.domain.PlaceVO;
import zara.zio.turn.domain.ReplyInfoVO;
import zara.zio.turn.domain.WishVO;
import zara.zio.turn.persistence.PlaceCommandService;
import zara.zio.turn.persistence.PlaceService;

@Controller
public class PlaceBoardController {
	
	@Inject
	private PlaceService service;
	@Inject
	private PlaceCommandService serviceE;
	
	@RequestMapping(value="/placeInfo", method = RequestMethod.GET)
	public String placeInfo() {
		
		return "placeBorad/placeInfo";
	}
	
	// 게시글보기 
	@RequestMapping(value="/placeRead", method = RequestMethod.GET)
	public String placeInformation(@RequestParam(value="post", defaultValue="0") int post, Model model, HttpSession session) throws Exception {
		
		String user = (String)session.getAttribute("mem");
		Map<String, Object> map = serviceE.PlaceCommand(user, post);
		model.addAttribute("map", map);
		
		return "placeBorad/placeRead";
	}
	
	
	@ResponseBody
	@RequestMapping(value="/listPaging", method = RequestMethod.GET)
	public String listPaging(PlaceInfoListVO InfoCount) throws Exception {
		
		int totalCount = service.getInfoCount(InfoCount);
		System.out.println("totalCount[" + totalCount + "]");
		
		return totalCount+"";
	}
	
	@ResponseBody
	@RequestMapping(value="/placeInfoList", method = RequestMethod.GET)
	public List<PlaceVO> placeInfoList(PlaceInfoListVO InfoList, HttpSession session) throws Exception {
		
		String user = (String)session.getAttribute("mem");
		InfoList.setUsers(user); // 유저 발자국정보
		List<PlaceVO> list = service.placeInfoList(InfoList);
		
		return list;
	}
	
	@ResponseBody
	@RequestMapping(value="/wishlike", method = RequestMethod.GET)
	public int wishlike(int state, int place, HttpSession session) throws Exception {
		
		String user = (String)session.getAttribute("mem");
		WishVO vo = new WishVO();
		vo.setPlace_code(place);
		vo.setUser_id(user);
		
		int value = serviceE.wishLike(vo, state);
		
		return value;
	}
	
	@ResponseBody
	@RequestMapping(value="/placeReplyCommend", method = RequestMethod.POST)
	public List<ReplyInfoVO> placeReplyCommend(int code, int score, int type, int replyno, String text, HttpSession session) throws Exception {
		
		String user = (String)session.getAttribute("mem");
		
		List<ReplyInfoVO> list = serviceE.placeReplyCommend(code, score, type, replyno, text, user);
		
		return list;
	}
	
	
	
	
	
	
}
