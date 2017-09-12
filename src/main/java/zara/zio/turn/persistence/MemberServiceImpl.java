package zara.zio.turn.persistence;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import zara.zio.turn.dao.MemberDAO;
import zara.zio.turn.domain.MemberVO;
import zara.zio.turn.domain.Pagination;

@Service
public class MemberServiceImpl implements MemberService {
	
	@Inject
	private MemberDAO dao;
	
	@Override
	public void regist(MemberVO vo) throws Exception {
		// TODO Auto-generated method stub'
		System.out.println("service옴");
		dao.user_create(vo);
	}

	@Override
	public MemberVO read(String id) throws Exception {
		// TODO Auto-generated method stub
		return dao.user_read(id);
	}

	@Override
	public void modify(MemberVO vo, String user) throws Exception {
		// TODO Auto-generated method stub
		dao.user_update(vo, user);
	}

	@Override
	public void remove(String id) throws Exception {
		// TODO Auto-generated method stub
		dao.user_delete(id);
	}

	@Override
	public List<MemberVO> listAll(Pagination pagination) throws Exception {
		// TODO Auto-generated method stub
		return dao.user_listAll(pagination);
	}

	@Override
	public int confirm(String id) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Service드감"+id);
		return dao.id_confirm(id);
	}

	@Override
	public String userin(String id) throws Exception {
		// TODO Auto-generated method stub
		return dao.user_confirm(id);
	}

	@Override
	public int getTotalAll() throws Exception {
		// TODO Auto-generated method stub
		return dao.getTotalAll();
	}

	@Override
	public List<Map<String, Object>> friends(String search) throws Exception {
		// TODO Auto-generated method stub
		return dao.friends(search);
	}

	@Override
	public List<Map<String, Object>> friendAll(String id) throws Exception {
		// TODO Auto-generated method stub
		return dao.friendAll(id);
	}

	@Override
	public List<Map<String, Object>> friendList(String id) throws Exception {
		// TODO Auto-generated method stub
		return dao.friendList(id);
	}

	@Override
	public void friendReq(String my_id, String user_id) throws Exception {
		// TODO Auto-generated method stub
		dao.friendReq(my_id, user_id);
	}

	@Override
	public void friendDelCancel(String my_id, String user_id) throws Exception {
		// TODO Auto-generated method stub
		dao.friendDelCancel(my_id, user_id);
	}

	@Override
	public void friendAccept(String my_id, String user_id) throws Exception {
		// TODO Auto-generated method stub
		dao.friendAccept(my_id, user_id);
	}

	
	



}
