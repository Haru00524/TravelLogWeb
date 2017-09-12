package zara.zio.turn.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import zara.zio.turn.dao.PlaceCommandDAO;
import zara.zio.turn.domain.PlaceVO;
import zara.zio.turn.domain.ReplyInfoVO;
import zara.zio.turn.domain.WishVO;

@Service
public class PlaceCommandServiceImpl implements PlaceCommandService {
	
	@Inject
	private PlaceCommandDAO dao;
	
	@Override
	public int wishLike(WishVO vo, int state) throws Exception {
		// TODO Auto-generated method stub
		int value = 0;
		
		if(state == 1) {
			dao.wishLike(vo);
		}
		
		if(state == 0) {
			dao.wishUnLike(vo);
		}
		
		value = dao.wishCount(vo.getPlace_code());
		
		return value;
	}

	@Transactional
	@Override
	public List<ReplyInfoVO> placeReplyCommend(int code, int score, int type, int replyno, String text, String id) throws Exception {
		
		List<ReplyInfoVO> list = new ArrayList<>();
		
		if(type == 1) {
			ReplyInfoVO vo = new ReplyInfoVO();
			vo.setPlace_reply_code(code);
			vo.setPlace_score(score);
			vo.setBoard_content(text);
			vo.setUser_id(id);
			dao.replyPlaceWrite(vo);
		}
		if(type == 2) {
			ReplyInfoVO vo = new ReplyInfoVO();
			vo.setBoard_content(text);
			vo.setPlace_score(score);
			vo.setBoard_code(replyno);
			dao.replyPlaceModify(vo);
		}
		if(type == 3) {
			dao.replyPlaceDelete(replyno);
		}

		list = dao.replyPlaceList(code);
		
		return list;
	}

	
	@Transactional
	@Override
	public Map<String, Object> PlaceCommand(String id, int no) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		
		dao.placeView(no);
		
		WishVO wish = new WishVO();
		wish.setUser_id(id);
		wish.setPlace_code(no);
		int myWish = dao.myWish(wish); 
		int Count = dao.wishCount(no);
		wish.setMyWish(myWish);
		wish.setWishCount(Count);
		
		PlaceVO place = dao.placeRead(no);
		List<PlaceVO> list = dao.placeReadimg(no);
		List<ReplyInfoVO> replylist = dao.replyPlaceList(no);
		List<PlaceVO> recent = dao.placeRecent();
		List<PlaceVO> popular = dao.placePopular();
		
		map.put("place", place);
		map.put("list", list);
		map.put("wish", wish);
		map.put("replylist", replylist);
		map.put("recent", recent);
		map.put("popular", popular);
		
		return map;
	}
	
	

}
