package zara.zio.turn.persistence;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;


import zara.zio.turn.dao.LayersDAO;

@Service
public class LayersServiceImpl implements LayersService {

	@Inject 
	private LayersDAO dao; 
	
	@Override
	public List<String> gif_image_list(String user_id) throws Exception {
		// TODO Auto-generated method stub
		return dao.gif_image_list(user_id);
	}

}
