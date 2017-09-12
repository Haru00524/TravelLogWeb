package zara.zio.turn.dao;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import zara.zio.turn.domain.PlaceVO;
import zara.zio.turn.domain.ReplyInfoVO;
import zara.zio.turn.domain.WishVO;

@Repository
public class PlaceCommandDAOImpl implements PlaceCommandDAO {
	
	private static final String NAMESPACE = "zara.zio.placeCommandMapper";
	
	@Inject
	private SqlSession sqlSession;

	@Override
	public void wishLike(WishVO vo) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.insert(NAMESPACE + ".wishLike", vo);
	}

	@Override
	public void wishUnLike(WishVO vo) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.delete(NAMESPACE + ".wishUnLike", vo);
	}

	@Override
	public int wishCount(int no) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(NAMESPACE + ".wishCount", no);
	}

	@Override
	public int myWish(WishVO vo) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(NAMESPACE + ".myWish", vo);
	}

	@Override
	public void replyPlaceWrite(ReplyInfoVO vo) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.insert(NAMESPACE + ".replyPlaceWrite", vo);
	}
	
	@Override
	public void replyPlaceModify(ReplyInfoVO vo) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.update(NAMESPACE + ".replyPlaceModify", vo);
	}

	@Override
	public void replyPlaceDelete(int no) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.delete(NAMESPACE + ".replyPlaceDelete", no);
	}

	@Override
	public List<ReplyInfoVO> replyPlaceList(int replyno) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(NAMESPACE + ".replyPlaceList", replyno);
	}

	@Override
	public PlaceVO placeRead(int no) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(NAMESPACE + ".placeRead", no);
	}

	@Override
	public List<PlaceVO> placeReadimg(int no) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(NAMESPACE + ".placeRead_img", no);
	}

	@Override
	public void placeView(int no) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.update(NAMESPACE + ".placeView", no);
	}

	@Override
	public List<PlaceVO> placeRecent() throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(NAMESPACE + ".placeRecent");
	}

	@Override
	public List<PlaceVO> placePopular() throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(NAMESPACE + ".placePopular");
	}
	
	
	
}
