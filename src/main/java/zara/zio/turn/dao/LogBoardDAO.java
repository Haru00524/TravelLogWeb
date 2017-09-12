package zara.zio.turn.dao;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import zara.zio.turn.domain.ComunityVO;
import zara.zio.turn.domain.FileAndHashVO;
import zara.zio.turn.domain.LikesVO;
import zara.zio.turn.domain.LogBoardVO;
import zara.zio.turn.domain.PaginationE;
import zara.zio.turn.domain.ReplyInfoVO;
import zara.zio.turn.domain.StepLogVO;
import zara.zio.turn.domain.TravelListVO;

public interface LogBoardDAO {
	
	public void logInfoCreate(LogBoardVO vo) throws Exception;
	public void logHashCreate(String hash, int cnt) throws Exception;
	public void logImageFileCreate(String image, int cnt, int type) throws Exception;
	public Map<String, Object> maxCode() throws Exception;
	
	public List<LogBoardVO> logInfoRead(int type, int start, int timeNum) throws Exception;
	public List<FileAndHashVO> logHashRead(int type, int start, int timeNum) throws Exception;
	public List<FileAndHashVO> logImageFileRead(int type, int start, int timeNum) throws Exception;
	public List<LikesVO> likeCounts() throws Exception;
	public List<LikesVO> myLikes(String id) throws Exception;
	
	public void likeUp (LikesVO vo) throws Exception;
	public void likeDown (LikesVO vo) throws Exception;
	public LikesVO likeState (int no) throws Exception;
	
	public void viewCount(int no) throws Exception;
	public int viewSearch(int no) throws Exception;
	public List<LogBoardVO> replyList(int no) throws Exception;
	public void replyWrite(LogBoardVO vo) throws Exception;
	public void replyModify(LogBoardVO vo) throws Exception;
	public void replyDelete(int replyno) throws Exception;
	
	public List<StepLogVO> stepLogs(int no) throws Exception;
	public List<FileAndHashVO> stepLogs2(int no) throws Exception;
	
	public List<ComunityVO> comunityInfoList(PaginationE pagenation) throws Exception;
	public int comuTotalCount(PaginationE pagenation) throws Exception;
	public ComunityVO comunityInfoRead(int page) throws Exception;
	public List<Map<String, Object>> comunityFileRead(int page) throws Exception;
	public void comunityView(int no) throws Exception;
	public int comunityAllCount() throws Exception;
	public List<ComunityVO> comunityRecent() throws Exception;
	
	public void comunityFileDel (int target) throws Exception;
	public void comunityFileAdd (String file_name, int type, int page) throws Exception;
	public void comunityUpdate (LogBoardVO vo, int page) throws Exception;
	
	public void boardAllDel (int page) throws Exception;
	public void boardfileAllDel (int page) throws Exception;
	public void boardhashAllDel (int page) throws Exception;
	
	public void replyComuWrite(ReplyInfoVO vo) throws Exception;
	public void replyComuModify(ReplyInfoVO vo) throws Exception;
	public void replyComuDelete(int no) throws Exception;
	public void replyComuLike(ReplyInfoVO vo) throws Exception;
	public List<ReplyInfoVO> replyComuList(int replyno, String user) throws Exception;
	public List<ReplyInfoVO> replyComuRank(int replyno, String user) throws Exception;
	
	// travel-log
	public LogBoardVO travelMain(int no, int group) throws Exception;
	public List<TravelListVO> travelPlace(int group, String selectDate) throws Exception;
	public List<LogBoardVO> travelPost(int group, String start, String end) throws Exception;
	public List<FileAndHashVO> travelImg(int group, String start, String end) throws Exception;
	public List<FileAndHashVO> travelHash(int group, String start, String end) throws Exception;
	
	public List<LogBoardVO> replyTravelList(int no, int page, int recordNum) throws Exception;
	public int replyTravelTotalCount(int post) throws Exception;
	
}
