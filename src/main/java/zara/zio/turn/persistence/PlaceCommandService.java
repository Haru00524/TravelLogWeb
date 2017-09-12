package zara.zio.turn.persistence;

import java.util.List;
import java.util.Map;

import zara.zio.turn.domain.PlaceVO;
import zara.zio.turn.domain.ReplyInfoVO;
import zara.zio.turn.domain.WishVO;

public interface PlaceCommandService {
	
	public Map<String, Object> PlaceCommand(String id, int no) throws Exception; 
	public int wishLike(WishVO vo, int state) throws Exception;
	public List<ReplyInfoVO> placeReplyCommend(int code, int score, int type, int replyno, String text, String id) throws Exception;
}
