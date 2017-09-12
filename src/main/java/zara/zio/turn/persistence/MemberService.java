package zara.zio.turn.persistence;

import java.util.List;
import java.util.Map;

import zara.zio.turn.domain.MemberVO;
import zara.zio.turn.domain.Pagination;

public interface MemberService {
	
	public void regist(MemberVO vo) throws Exception;
	public MemberVO read(String id) throws Exception;
	public void modify(MemberVO vo, String user) throws Exception;
	public void remove(String id) throws Exception;
	public List<MemberVO> listAll(Pagination pagination) throws Exception;
	
	public int confirm(String id) throws Exception;
	public String userin(String id) throws Exception;
	public int getTotalAll() throws Exception;
	
    public List<Map<String, Object>> friends(String search) throws Exception;
    public List<Map<String, Object>> friendAll(String id) throws Exception;
    public List<Map<String, Object>> friendList(String id) throws Exception;
   
    public void friendReq (String my_id, String user_id) throws Exception;
    public void friendDelCancel (String my_id, String user_id) throws Exception;
    public void friendAccept (String my_id, String user_id) throws Exception;
}
