package zara.zio.turn.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import zara.zio.turn.domain.Pagination;
import zara.zio.turn.domain.PlaceInfoListVO;
import zara.zio.turn.domain.PlaceVO;



@Repository
public class PlaceDAOImpl implements PlaceDAO {
	
	private static final String NAMESPACE = "zara.zio.placeMapper"; 
	
	@Inject
	private SqlSession sqlSession;

	@Override
	public void place_insert(PlaceVO vo) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.insert(NAMESPACE + ".place_insert", vo);
	}
	
	@Override
	public void img_insert(List<PlaceVO> list) throws Exception {
		// TODO Auto-generated method stub
		
		for(int i=0; i<list.size(); i++) {
			sqlSession.insert(NAMESPACE + ".img_insert", list.get(i));
		}
	}

	@Override
	public int place_max() throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(NAMESPACE + ".place_max");
	}

	@Override
	public List<PlaceVO> listPage(Pagination pagination) throws Exception {
		// TODO Auto-generated method stub
		
		return sqlSession.selectList(NAMESPACE + ".place_list", pagination);
	}

	@Override
	public int getTotalCount() throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(NAMESPACE + ".totalCount");
	}

	@Override
	public PlaceVO read(int no) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(NAMESPACE + ".read", no);
	}

	@Override
	public List<PlaceVO> readimg(int no) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(NAMESPACE + ".read_img", no);
	}

	@Override
	public void placeAll_delete(int no) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.delete(NAMESPACE + ".placeAll_delete", no);
	}

	@Override
	public void pimg_delete(int no) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.delete(NAMESPACE + ".pimg_delete", no);
	}

	@Override
	public void place_update(PlaceVO vo, int no) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("v", vo);
		paramMap.put("no", no);
		sqlSession.update(NAMESPACE + ".place_update", paramMap);
	}

	@Override
	public List<PlaceVO> readLocal(String local) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(NAMESPACE + ".read_local", local);
	}

	// 장소정보
	@Override
	public List<PlaceVO> placeInfoList(PlaceInfoListVO vo) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(NAMESPACE + ".place_info_list", vo);
	}

	@Override
	public int getInfoCount(PlaceInfoListVO vo) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(NAMESPACE + ".place_info_count", vo);
	}

	
	
	@Override
	public List<PlaceVO> placeFilter(PlaceVO vo) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(NAMESPACE + ".placeFilter", vo);
	}

	@Override
	public List<PlaceVO> searchAllFilter(PlaceVO vo) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(NAMESPACE + ".searchAllFilter", vo);
	}

	@Override
	public List<PlaceVO> searchLocalFilter(PlaceVO vo) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(NAMESPACE + ".searchLocalFilter", vo);
	}

	@Override
	public PlaceVO placeDetail(int place_code) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(NAMESPACE + ".placeDetail", place_code);
	}

	
	
}
