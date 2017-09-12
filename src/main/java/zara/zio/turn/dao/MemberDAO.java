package zara.zio.turn.dao;

import java.util.List;
import java.util.Map;

import zara.zio.turn.domain.MemberVO;
import zara.zio.turn.domain.Pagination;

public interface MemberDAO {
	// CRUD
	public void user_create(MemberVO vo) throws Exception;
	public MemberVO user_read(String user_id) throws Exception;
	public void user_update(MemberVO vo, String user) throws Exception;
	public void user_delete(String user_id) throws Exception;
	public List<MemberVO> user_listAll(Pagination pagination) throws Exception;
	
	public int id_confirm(String user_id) throws Exception;
	public String user_confirm(String user_id) throws Exception;
	public int getTotalAll() throws Exception;
	
	public List<Map<String, Object>> friends(String search) throws Exception;
	public List<Map<String, Object>> friendAll(String id) throws Exception;
	public List<Map<String, Object>> friendList(String id) throws Exception;
	   
	public void friendReq (String my_id, String user_id) throws Exception;
	public void friendDelCancel (String my_id, String user_id) throws Exception;
	public void friendAccept (String my_id, String user_id) throws Exception;
	
}
