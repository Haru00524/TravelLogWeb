package zara.zio.turn.dao;

import java.util.List;

import zara.zio.turn.domain.PlaceVO;
import zara.zio.turn.domain.ReplyInfoVO;
import zara.zio.turn.domain.WishVO;

public interface PlaceCommandDAO {
	
	public PlaceVO placeRead(int no) throws Exception;
	public List<PlaceVO> placeReadimg(int no) throws Exception;
	public void placeView(int no) throws Exception;
	public List<PlaceVO> placeRecent() throws Exception;
	public List<PlaceVO> placePopular() throws Exception;
	
	public void wishLike(WishVO vo) throws Exception;
	public void wishUnLike(WishVO vo) throws Exception;
	public int wishCount(int no) throws Exception; 
	public int myWish(WishVO vo) throws Exception;
	public void replyPlaceWrite(ReplyInfoVO vo) throws Exception;
	public void replyPlaceModify(ReplyInfoVO vo) throws Exception;
	public void replyPlaceDelete(int no) throws Exception;
	public List<ReplyInfoVO> replyPlaceList(int replyno) throws Exception;
	
	
}
