package zara.zio.turn.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import zara.zio.turn.domain.ComunityVO;
import zara.zio.turn.domain.FileAndHashVO;
import zara.zio.turn.domain.LikesVO;
import zara.zio.turn.domain.LogBoardVO;
import zara.zio.turn.domain.PaginationE;
import zara.zio.turn.domain.ReplyInfoVO;
import zara.zio.turn.domain.StepLogVO;
import zara.zio.turn.domain.TravelListVO;

@Repository
public class LogBoardDAOImpl implements LogBoardDAO {
	
	@Inject
	private SqlSession sqlSession;
	
	private static final String NAMESPACE = "zara.zio.LogBoardMapper";
	
	
	@Override
	public void logInfoCreate(LogBoardVO vo) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.insert(NAMESPACE +".Loginfo", vo);
	}

	@Override
	public void logHashCreate(String hash, int cnt) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> hashtagMap = new HashMap<String, Object>();
		hashtagMap.put("hash", hash);
		hashtagMap.put("cnt", cnt);
		sqlSession.insert(NAMESPACE +".Loghash", hashtagMap);
	}

	@Override
	public void logImageFileCreate(String image, int cnt, int type) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> imageMap = new HashMap<String, Object>();
		
		imageMap.put("type", type);
		imageMap.put("image", image);
		imageMap.put("cnt", cnt);
		
		sqlSession.insert(NAMESPACE +".Logimgfile", imageMap);
	}

	@Override
	public Map<String, Object> maxCode() throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(NAMESPACE + ".maxcode");
	}

	
	// 리스트 읽기 전용
	@Override
	public List<LogBoardVO> logInfoRead(int type, int start, int timeNum) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Integer> typeMap = new HashMap<String, Integer>();
		typeMap.put("type", type);
		typeMap.put("startRecord", start);
		typeMap.put("timeLineNum", timeNum);
		return sqlSession.selectList(NAMESPACE + ".infoRead", typeMap);
	}
	
	@Override
	public List<FileAndHashVO> logHashRead(int type, int start, int timeNum) throws Exception {
		// TODO Auto-generated method stub
		
		Map<String, Integer> typeMap = new HashMap<String, Integer>();
		typeMap.put("type", type);
		typeMap.put("startRecord", start);
		typeMap.put("timeLineNum", timeNum);
		
		return sqlSession.selectList(NAMESPACE + ".hashRead", typeMap);
		
	}

	@Override
	public List<FileAndHashVO> logImageFileRead(int type, int start, int timeNum) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Integer> typeMap = new HashMap<String, Integer>();
		typeMap.put("type", type);
		typeMap.put("startRecord", start);
		typeMap.put("timeLineNum", timeNum);
		
		return sqlSession.selectList(NAMESPACE + ".imgfileRead", typeMap);
	}
	
	@Override
	public List<LikesVO> likeCounts() throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(NAMESPACE + ".likeCounts");
	}
	
	@Override
	public List<LikesVO> myLikes(String id) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(NAMESPACE + ".likeMy", id);
	}
	
	
	@Override
	public void likeUp(LikesVO vo) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.insert(NAMESPACE + ".likeUp", vo);
	}

	@Override
	public void likeDown(LikesVO vo) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.delete(NAMESPACE + ".likeDown", vo);
	}
	
	@Override
	public LikesVO likeState(int no) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(NAMESPACE + ".likeState", no);
	}
	
	@Override
	public void viewCount(int no) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.update(NAMESPACE + ".viewCount", no);
	}
	
	@Override
	public int viewSearch(int no) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(NAMESPACE + ".viewSearch", no);
	}
	
	@Override
	public List<LogBoardVO> replyList(int no) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(NAMESPACE + ".replyList", no);
	}
	
	@Override
	public void replyWrite(LogBoardVO vo) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.insert(NAMESPACE + ".replyWrite", vo);
	}
	
	@Override
	public void replyModify(LogBoardVO vo) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.update(NAMESPACE + ".replyModify", vo);
	}
	
	@Override
	public void replyDelete(int replyno) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.delete(NAMESPACE + ".replyDelete", replyno);
	}
	
	@Override
	public List<StepLogVO> stepLogs(int no) throws Exception {
		// TODO Auto-generated method stub
		
		return sqlSession.selectList(NAMESPACE + ".stepLogs", no);
		
	}
	@Override
	public List<FileAndHashVO> stepLogs2(int no) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(NAMESPACE + ".stepLogs2", no);
	}
	
	
	@Override
	public List<ComunityVO> comunityInfoList(PaginationE pagenation) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(NAMESPACE + ".comunityList", pagenation);
	}
	
	@Override
	public int comuTotalCount(PaginationE pagenation) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(NAMESPACE + ".comuTotalCount", pagenation);
	}

	@Override
	public ComunityVO comunityInfoRead(int page) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(NAMESPACE + ".comunityRead",page);
	}

	@Override
	public List<Map<String, Object>> comunityFileRead(int page) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(NAMESPACE + ".comunityfileRead", page);
	}
	
	
	@Override
	public void comunityView(int no) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.update(NAMESPACE + ".comunityView", no);
	}
	
	
	@Override
	public int comunityAllCount() throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(NAMESPACE + ".comunityAllCount");
	}
	
	@Override
	public List<ComunityVO> comunityRecent() throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(NAMESPACE + ".comunityRecent");
	}
	
	@Override
	public void comunityFileDel(int target) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.delete(NAMESPACE + ".comunityfileDel", target);
	}
	@Override
	public void comunityFileAdd(String file_name, int type, int page) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> fileMap = new HashMap<String, Object>();
		fileMap.put("file_name", file_name);
		fileMap.put("type", type);
		fileMap.put("page", page);
		sqlSession.insert(NAMESPACE + ".comunityfileAdd", fileMap);
	}

	@Override
	public void comunityUpdate(LogBoardVO vo, int page) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> Updateinfo = new HashMap<String, Object>();
		Updateinfo.put("vo", vo);
		Updateinfo.put("page", page);
		sqlSession.update(NAMESPACE + ".conmunityUpdate", Updateinfo); 
	}

	
	@Override
	public void boardAllDel(int page) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.delete(NAMESPACE + ".boardAllDel", page);
	}

	@Override
	public void boardfileAllDel(int page) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.delete(NAMESPACE + ".boardfileAllDel", page);
	}

	@Override
	public void boardhashAllDel(int page) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.delete(NAMESPACE + ".boardhashAllDel", page);
	}

	
	
	@Override
	public void replyComuWrite(ReplyInfoVO vo) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.insert(NAMESPACE + ".replyComuWrite", vo);
	}

	@Override
	public void replyComuModify(ReplyInfoVO vo) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.update(NAMESPACE + ".replyComuModify", vo);
	}

	@Override
	public void replyComuDelete(int no) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.delete(NAMESPACE + ".replyComuDelete", no);
	}
	
	@Override
	public void replyComuLike(ReplyInfoVO vo) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.insert(NAMESPACE + ".replyComuLike", vo);
	}

	@Override
	public List<ReplyInfoVO> replyComuList(int replyno, String user) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<>();
		map.put("replyno", replyno);
		map.put("user", user);
		return sqlSession.selectList(NAMESPACE + ".replyComuList", map);
	}

	@Override
	public List<ReplyInfoVO> replyComuRank(int replyno, String user) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<>();
		map.put("replyno", replyno);
		map.put("user", user);
		return sqlSession.selectList(NAMESPACE + ".replyComuRank", map);
	}

	@Override
	public LogBoardVO travelMain(int no, int group) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Integer> map = new HashMap<>();
		map.put("no", no);
		map.put("group", group);
		
		return sqlSession.selectOne(NAMESPACE + ".travel_log", map);
	}

	@Override
	public List<TravelListVO> travelPlace(int group, String selectDate) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<>();
		map.put("group", group);
		map.put("selectDate", selectDate);
		
		return sqlSession.selectList(NAMESPACE + ".travel_day_place", map);
	}

	@Override
	public List<LogBoardVO> travelPost(int group, String start, String end) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<>();
		map.put("group", group);
		map.put("start", start);
		map.put("end", end);
		
		return sqlSession.selectList(NAMESPACE + ".travel_log_post", map);
	}

	@Override
	public List<FileAndHashVO> travelImg(int group, String start, String end) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<>();
		map.put("group", group);
		map.put("start", start);
		map.put("end", end);
		return sqlSession.selectList(NAMESPACE + ".travel_log_file", map);
	}

	@Override
	public List<FileAndHashVO> travelHash(int group, String start, String end) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<>();
		map.put("group", group);
		map.put("start", start);
		map.put("end", end);
		return sqlSession.selectList(NAMESPACE + ".travel_log_hash", map);
	}

	@Override
	public List<LogBoardVO> replyTravelList(int no, int page, int recordNum) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Integer> map = new HashMap<>();
		map.put("no", no);
		map.put("page", page);
		map.put("recordNum", recordNum);
		return sqlSession.selectList(NAMESPACE + ".replyTravelList", map);
	}

	@Override
	public int replyTravelTotalCount(int post) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(NAMESPACE + ".replyTravelTotalCount", post);
	}

}
