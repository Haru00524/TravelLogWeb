package zara.zio.turn.persistence;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import zara.zio.turn.dao.PlaceDAO;
import zara.zio.turn.domain.Pagination;
import zara.zio.turn.domain.PlaceInfoListVO;
import zara.zio.turn.domain.PlaceVO;

@Service
public class PlaceServiceImpl implements PlaceService {
	
	@Inject
	private PlaceDAO dao;
	
	@Override
	public void place_insert(PlaceVO vo) throws Exception {
		// TODO Auto-generated method stub
		dao.place_insert(vo);
	}

	@Override
	public void img_insert(List<PlaceVO> list) throws Exception {
		// TODO Auto-generated method stub
		dao.img_insert(list);
	}

	@Override
	public int place_max() throws Exception {
		// TODO Auto-generated method stub
		return dao.place_max();
	}

	@Override
	public List<PlaceVO> listPage(Pagination pagination) throws Exception {
		// TODO Auto-generated method stub
		return dao.listPage(pagination);
	}

	@Override
	public int getTotalCount() throws Exception {
		// TODO Auto-generated method stub
		return dao.getTotalCount();
	}

	@Override
	public PlaceVO read(int no) throws Exception {
		// TODO Auto-generated method stub
		return dao.read(no);
	}

	@Override
	public List<PlaceVO> readimg(int no) throws Exception {
		// TODO Auto-generated method stub
		return dao.readimg(no);
	}

	@Override
	public void placeAll_delete(int no) throws Exception {
		// TODO Auto-generated method stub
		dao.placeAll_delete(no);
	}

	@Override
	public void pimg_delete(int no) throws Exception {
		// TODO Auto-generated method stub
		dao.pimg_delete(no);
	}

	@Override
	public void place_update(PlaceVO vo, int no) throws Exception {
		// TODO Auto-generated method stub
		dao.place_update(vo, no);
	}

	@Override
	public List<PlaceVO> readLocal(String local) throws Exception {
		// TODO Auto-generated method stub
		return dao.readLocal(local);
	}

	// 장소정보
	@Override
	public List<PlaceVO> placeInfoList(PlaceInfoListVO vo) throws Exception {
		// TODO Auto-generated method stub
		return dao.placeInfoList(vo);
	}

	@Override
	public int getInfoCount(PlaceInfoListVO vo) throws Exception {
		// TODO Auto-generated method stub
		return dao.getInfoCount(vo);
	}

	@Override
	public List<PlaceVO> placeFilter(PlaceVO vo) throws Exception {
		// TODO Auto-generated method stub
		return dao.placeFilter(vo);
	}

	@Override
	public List<PlaceVO> searchAllFilter(PlaceVO vo) throws Exception {
		// TODO Auto-generated method stub
		return dao.searchAllFilter(vo);
	}

	@Override
	public List<PlaceVO> searchLocalFilter(PlaceVO vo) throws Exception {
		// TODO Auto-generated method stub
		return dao.searchLocalFilter(vo);
	}

	@Override
	public PlaceVO placeDetail(int place_code) throws Exception {
		// TODO Auto-generated method stub
		return dao.placeDetail(place_code);
	}

}
