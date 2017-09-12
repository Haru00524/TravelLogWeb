package zara.zio.turn.dao;

import java.util.List;
import java.util.Map;

import zara.zio.turn.domain.Pagination;
import zara.zio.turn.domain.PlaceInfoListVO;
import zara.zio.turn.domain.PlaceVO;

public interface PlaceDAO {
	public void place_insert(PlaceVO vo) throws Exception;
	public void img_insert(List<PlaceVO> list) throws Exception;
	public int place_max() throws Exception;
	
	public List<PlaceVO> listPage(Pagination pagination) throws Exception;
	public int getTotalCount() throws Exception;
	public PlaceVO read(int no) throws Exception;
	public List<PlaceVO> readimg(int no) throws Exception;
	
	public void placeAll_delete(int no) throws Exception;
	public void pimg_delete(int no) throws Exception;
	public void place_update(PlaceVO vo, int no) throws Exception;
	
	public List<PlaceVO> readLocal(String local) throws Exception;
	
	// 장소정보 
	public List<PlaceVO> placeInfoList(PlaceInfoListVO vo) throws Exception;
	public int getInfoCount(PlaceInfoListVO vo) throws Exception;
	
	// 여행일정만들기 필터
	public List<PlaceVO> placeFilter(PlaceVO vo) throws Exception;
	public List<PlaceVO> searchAllFilter(PlaceVO vo) throws Exception;
	public List<PlaceVO> searchLocalFilter(PlaceVO vo) throws Exception;
	
	// 여행일정만들기에서 장소 상세정보
	public PlaceVO placeDetail(int place_code) throws Exception ;

}
