package zara.zio.turn.dao;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository
public class LayersDAOImpl implements LayersDAO{

	@Inject
	private SqlSession sqlSession;
	
	private static final String NAMESPACE = "zara.zio.layers";
	
	
	@Override
	public List<String> gif_image_list(String user_id) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(NAMESPACE + ".gif_image_list", user_id);
	}

}
