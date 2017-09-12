package zara.zio.turn.persistence;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import zara.zio.turn.domain.ComunityVO;
import zara.zio.turn.domain.LikesVO;
import zara.zio.turn.domain.LogBoardVO;
import zara.zio.turn.domain.PaginationE;
import zara.zio.turn.domain.ReplyInfoVO;

public interface LogBoardService {
	
	public void logBoardCreate (LogBoardVO vo, int cnt) throws Exception;
	public Map<String, Object> maxCode() throws Exception;
	
	public List<LogBoardVO> logInfoRead(int type, int start, int timeNum, String my) throws Exception;
	public int LikeUpDown(LikesVO vo, int states) throws Exception;
	public Map<String, Object> commandTwo(int state, int no, int type, String id) throws Exception;
	public List<LogBoardVO> replyCommand(int type, int no, int replyno, String text, String id) throws Exception;
	
	public Map<String,Object> comunityInfoList(PaginationE pagenation) throws Exception;
	public int comuTotalCount(PaginationE pagenation) throws Exception;
	public Map<String, Object> comunityInfoRead(int page, String user) throws Exception;
	public ComunityVO comunityInfoRead2(int page) throws Exception;
	public List<Map<String, Object>> comunityFileRead(int page) throws Exception;
	
	public void comunityFileDel (int target) throws Exception;
	public void comunityFileAdd (String file_name, int type, int page) throws Exception;
	public void comunityUpdate (LogBoardVO vo, int page) throws Exception;
	
	public void boardAllDel (int page) throws Exception;
	
	// Ä¿¹Â´ñ±Û Ä¿¸Çµå.
	public Map<String, Object> comuReplyCommand(int code, int replyno, int type, String text, String user) throws Exception;
	
	// travel-log Ä¿¸Çµå
	public Map<String, Object> travelLogCommand(int post, int group, String selectDate, String id) throws Exception;
	public Map<String, Object> changeNalCommand(int group, String selectDate, String id) throws Exception;
	public Map<String, Object> replyTravelCommand(int no, int replytype, int replyno, String text, String user) throws Exception;
	public Map<String, Object> replyTravelList(int post, int page) throws Exception;
	
}
