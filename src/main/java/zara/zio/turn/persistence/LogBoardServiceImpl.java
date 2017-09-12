package zara.zio.turn.persistence;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import zara.zio.turn.dao.LogBoardDAO;
import zara.zio.turn.domain.ComunityVO;
import zara.zio.turn.domain.FileAndHashVO;
import zara.zio.turn.domain.LikesVO;
import zara.zio.turn.domain.LogBoardVO;
import zara.zio.turn.domain.PaginationE;
import zara.zio.turn.domain.ReplyInfoVO;
import zara.zio.turn.domain.StepLogVO;
import zara.zio.turn.domain.TravelListVO;
import zara.zio.turn.util.DateParserBoxUtils;
import zara.zio.turn.util.GsonParserUtils;
import zara.zio.turn.util.KmlParsingUtils;

@Service
public class LogBoardServiceImpl implements LogBoardService {

	@Inject 
	private LogBoardDAO dao;
	
	@Resource(name="stepPath")
	private String stepPath;
	
	@Transactional
	@Override
	public void logBoardCreate(LogBoardVO vo, int cnt) throws Exception {
		// TODO Auto-generated method stub
		
		dao.logInfoCreate(vo);
		
		boolean test1 = true;
		boolean test2 = true;

		String [] imagefile = vo.getFile_content();
		String[] hash = vo.getHash_tag_content();
		
		if(imagefile == null) {
			test1 = false;
		}
		if(hash == null) {
			test2 = false;
		}
		
		if(test1) {
			for(int i=0; i<imagefile.length; i++) {
				int type = 1;
				if(imagefile[i].contains(".youtube")) {
					type = 4;
				}
				dao.logImageFileCreate(imagefile[i], cnt, type);
			}
		} 
		if(test2) {
			for(int i=0; i<hash.length; i++) {
				dao.logHashCreate(hash[i], cnt);
			}
		}
		
	}

	@Override
	public Map<String, Object> maxCode() throws Exception {
		// TODO Auto-generated method stub
		return dao.maxCode();
	}

	@Transactional
	@Override
	public List<LogBoardVO> logInfoRead(int type, int start, int timeNum, String my) throws Exception {
		// TODO Auto-generated method stub
		
		List<LogBoardVO> list = new ArrayList<LogBoardVO>();
		list = dao.logInfoRead(type, start, timeNum);
		List<FileAndHashVO> LogHash = dao.logHashRead(type, start, timeNum); // 해시태그
		List<FileAndHashVO> LogImage = dao.logImageFileRead(type, start, timeNum); // 이미지 
		List<LikesVO> likes = dao.likeCounts(); // 좋아요 갯수 리스트
		List<LikesVO> myLike = dao.myLikes(my); // 나의좋아요 my
		
		Double lat, lng;
		String AddressData; 
		
		// 좋아요 갯수 추가
		// 해당게시글의 내좋아요 엑티브 추가
		
		for(int i=0; i<list.size(); i++) {
			String hash = "";
			String image = "";
			
			int listNum = list.get(i).getBoard_code();

			for(int a=0; a<LogHash.size(); a++) {
				int hashNum = LogHash.get(a).getBoard_code();
				if(listNum == hashNum) {
					hash += LogHash.get(a).getHash_tag_content() + "◆";
				}
			}
			
			for(int b=0; b<LogImage.size(); b++) {
				int imageNum = LogImage.get(b).getBoard_code();
				String resultfile = LogImage.get(b).getFile_content();
				if(listNum == imageNum) {
					if(resultfile.contains(".kml")) {
						
						image += KmlParsingUtils.kmlParse(stepPath, resultfile) + "◆";
						
					} else {
						image += resultfile + "◆";
					}
				}
			}
			
			for(int c=0; c<likes.size(); c++) {
				
				int likeNum = likes.get(c).getBoard_code();
				if(likeNum == listNum) {
					list.get(i).setLike_count(likes.get(c).getCnt());
				}
				
			}
			
			for(int d=0; d<myLike.size(); d++) {
				int myLikeNum = myLike.get(d).getBoard_code();
				if(listNum == myLikeNum) {
					list.get(i).setMy_like(1);
				}
			}
			
			String [] itemA = hash.split("◆");
			list.get(i).setHash_tag_content(itemA);

			String [] itemB = image.split("◆");
			list.get(i).setFile_content(itemB);
			
			if(list.get(i).getLog_latitude() != 0.000000) {
				lat = list.get(i).getLog_latitude();
				lng = list.get(i).getLog_longtitude();
				AddressData = GsonParserUtils.parser(lng, lat);
				list.get(i).setOnAddress(AddressData);
			} 

		}
		
		
		return list;
		
	}
	
	@Transactional
	@Override
	public int LikeUpDown(LikesVO vo, int states) throws Exception {
		// TODO Auto-generated method stub
		
		int value = 0;
		
		if(states == 1) {
			dao.likeUp(vo);
		} 
		if(states == 0) {
			dao.likeDown(vo);
		}
		
		LikesVO no = dao.likeState(vo.getBoard_code());
		if(no != null) {
			value = no.getCnt();
		}
		
		return value;
	}
	
	@Transactional
	@Override
	public Map<String, Object> commandTwo(int state, int no, int type, String id) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<>(); // 맵데이터
		List<LogBoardVO> list = new ArrayList<LogBoardVO>();
		list = dao.replyList(no);
		List<StepLogVO> step = new ArrayList<StepLogVO>();
		
		int value = 0;
		
		if(state == 0) {
			dao.viewCount(no);
		} 
		value = dao.viewSearch(no);
		
		
		for(int i=0; i<list.size(); i++) {
			String replyid = list.get(i).getUser_id();
			if(replyid.equals(id)) {
				list.get(i).setReply_state(1);
			}
		}
		
		// 스텝로그일때 실행  
		if(type == 3) {
			
			step = dao.stepLogs(no);
			List<FileAndHashVO> list2 = dao.stepLogs2(no);
			List<LikesVO> myLike = dao.myLikes(id);
			
			int lico = 0;
			int lico2 = 0;
			Double lat, lng;
			String AddressData; 
			
			
			for(int z=0; z<step.size(); z++) {
				
				String hash = "";
				lico = step.get(z).getBoard_code();
				
				for(int j=0; j<list2.size(); j++) {
					lico2 = list2.get(j).getBoard_code();
					if(lico == lico2) {
						hash += list2.get(z).getHash_tag_content() + "◆";
					}
				}
				
				for(int x=0; x<myLike.size(); x++) {
					int mynum = myLike.get(x).getBoard_code();
					if(lico == mynum) {
						step.get(z).setMylike(1);
					}
				}
				
				if(hash != "") {
					String [] itemA = hash.split("◆");
					step.get(z).setHash_tag_content(itemA);
				} 
				
				if(step.get(z).getLog_latitude() != 0.000000) {
					lat = step.get(z).getLog_latitude();
					lng = step.get(z).getLog_longtitude();
					AddressData = GsonParserUtils.parser(lng, lat);
					step.get(z).setOnAddress(AddressData);
				} 
				
				
			}
			
		}
		
		map.put("reply", list); // 댓글정보 
		map.put("view", value); // 조회수 정보 
		map.put("step", step); // 스텝로그 정보
		
		return map;
		
	}
	
	@Transactional
	@Override
	public List<LogBoardVO> replyCommand(int type, int no, int replyno, String text, String id) throws Exception {
		// TODO Auto-generated method stub

		if(type != 3) {
			
			LogBoardVO vo = new LogBoardVO();
			vo.setBoard_content(text);
			vo.setBoard_type_code(6);
			vo.setUser_id(id);
			vo.setReply_code(no);
			vo.setWrite_type(0);
			
			if(type == 1) {
				// wirte
				dao.replyWrite(vo);
			}
			
			if(type == 2) {
				
				// modify
				vo.setReply_code(replyno);
				dao.replyModify(vo);
				
			}
		} 
		
		if(type == 3) {
			
			// delete
			dao.replyDelete(replyno);
			
		}
		
		List<LogBoardVO> list = new ArrayList<LogBoardVO>();
		list = dao.replyList(no);
		
		for(int i=0; i<list.size(); i++) {
			String replyid = list.get(i).getUser_id();
			if(replyid.equals(id)) {
				list.get(i).setReply_state(1);
			}
		}
		
		
		return list;
	}
	
	@Transactional
	@Override
	public Map<String, Object> comunityInfoList(PaginationE pagenation) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<>();
		
		List<ComunityVO> list = dao.comunityInfoList(pagenation);
		int all = dao.comunityAllCount();
		List<ComunityVO> recent = dao.comunityRecent();
		map.put("list", list);
		map.put("all", all);
		map.put("recent", recent);
		
		return map;
	}
	
	@Override
	public int comuTotalCount(PaginationE pagenation) throws Exception {
		// TODO Auto-generated method stub
		return dao.comuTotalCount(pagenation);
	}

	@Transactional
	@Override
	public Map<String, Object> comunityInfoRead(int page, String user) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<>();
		dao.comunityView(page);
		ComunityVO vo = dao.comunityInfoRead(page); 
		List<ReplyInfoVO> replylist = dao.replyComuList(page, user);
		List<ReplyInfoVO> rank = dao.replyComuRank(page, user);
		int all = dao.comunityAllCount();
		List<ComunityVO> recent = dao.comunityRecent();
		
		map.put("vo", vo);
		map.put("replylist", replylist);
		map.put("rank", rank);
		map.put("all", all);
		map.put("recent", recent);
		
		return map;
	}
	
	@Override
	public ComunityVO comunityInfoRead2(int page) throws Exception {
		// TODO Auto-generated method stub
		return dao.comunityInfoRead(page);
	}

	@Override
	public List<Map<String, Object>> comunityFileRead(int page) throws Exception {
		// TODO Auto-generated method stub
		return dao.comunityFileRead(page);
	}
	
	@Override
	public void comunityFileDel(int target) throws Exception {
		// TODO Auto-generated method stub
		dao.comunityFileDel(target);
	}
	
	@Override
	public void comunityFileAdd(String file_name, int type, int page) throws Exception {
		// TODO Auto-generated method stub
		dao.comunityFileAdd(file_name, type, page);
	}

	@Override
	public void comunityUpdate(LogBoardVO vo, int page) throws Exception {
		// TODO Auto-generated method stub
		dao.comunityUpdate(vo, page);
	}

	@Transactional
	@Override
	public void boardAllDel(int page) throws Exception {
		// TODO Auto-generated method stub
		dao.boardAllDel(page);
		dao.boardfileAllDel(page);
		dao.boardhashAllDel(page);
	}
	
	@Transactional
	@Override
	public Map<String, Object> comuReplyCommand(int code, int replyno, int type, String text, String user) throws Exception {
		// TODO Auto-generated method stub
		
		Map<String, Object> map = new HashMap<>();
		
		if(type == 1) { // 리플쓰기 
			ReplyInfoVO vo = new ReplyInfoVO();
			vo.setBoard_content(text);
			vo.setUser_id(user);
			vo.setReply_code(code); // 해당게시글과 연결
			dao.replyComuWrite(vo);
		}
		if(type == 2) { // 리플수정
			ReplyInfoVO vo = new ReplyInfoVO();
			vo.setBoard_code(replyno);
			vo.setBoard_content(text);
			dao.replyComuModify(vo);
		}
		if(type == 3) { // 리플삭제
			dao.replyComuDelete(replyno);
		}
		if(type == 4) { // 리플좋아요
			System.out.println(type);
			ReplyInfoVO vo = new ReplyInfoVO();
			vo.setBoard_code(replyno);
			vo.setReply_code(code);
			vo.setUser_id(user);
			dao.replyComuLike(vo);
		}
		
		List<ReplyInfoVO> replylist = dao.replyComuList(code, user);
		List<ReplyInfoVO> rank = dao.replyComuRank(code, user);
		map.put("replylist", replylist);
		map.put("rank", rank);
		
		return map;
	}
	
	@Transactional
	@Override
	public Map<String, Object> travelLogCommand(int post, int group, String selectDate, String id) throws Exception {
		// TODO Auto-generated method stub
		
		Map<String, Object> map = new HashMap<>();
		
		dao.viewCount(post);
		
		Calendar cal = Calendar.getInstance();
		String now = cal.get(Calendar.YEAR) + "";
			   now += cal.get(Calendar.MONTH) < 10 ? "0" + (cal.get(Calendar.MONTH)+1) : "" + (cal.get(Calendar.MONTH)+1);
			   now += cal.get(Calendar.DATE)  < 10 ? "0" + cal.get(Calendar.DATE) : "" + cal.get(Calendar.DATE);
			   
		List<LikesVO> likes = dao.likeCounts(); // 좋아요 갯수 리스트
		List<LikesVO> myLike = dao.myLikes(id); // 나의좋아요 my	   
		LogBoardVO travel = dao.travelMain(post, group); 
		
		for(int x=0; x<likes.size(); x++) {
			int likeNum = likes.get(x).getBoard_code();
			if(travel.getBoard_code() == likeNum) {
				travel.setLike_count(likes.get(x).getCnt());
			}
		}
		for(int y=0; y<myLike.size(); y++) {
			int myLikeNum = myLike.get(y).getBoard_code();
			if(travel.getBoard_code() == myLikeNum) {
				travel.setMy_like(1);
			}
		}
		
		List<TravelListVO> place = dao.travelPlace(group, selectDate);
		List<String> nal = DateParserBoxUtils.Paser(travel.getStart_Date(), travel.getEnd_Date());
		
		String first = selectDate + "000000";
		String second = selectDate + "235959";
		DateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String start = sdf.format(formatter.parse(first));
		String end = sdf.format(formatter.parse(second));
		
		List<LogBoardVO> list = dao.travelPost(group, start, end);
		List<FileAndHashVO> logHash = dao.travelHash(group, start, end);
		List<FileAndHashVO> logImage = dao.travelImg(group, start, end);
		List<LogBoardVO> reply = dao.replyTravelList(post, 0, 10);
		int totalCount = dao.replyTravelTotalCount(post);
		
		Double lat, lng;
		String AddressData; 
		
		if(list.size() != 0) { // 결과값이 없으면
			for(int i=0; i<list.size(); i++) {
				
				String hash = "";
				String image = "";
				int listNum = list.get(i).getBoard_code();
				
				for(int a=0; a<logHash.size(); a++) {
					
					int hashNum = logHash.get(a).getBoard_code();
					if(listNum == hashNum) {
						hash += logHash.get(a).getHash_tag_content() + "◆";
					}
					
				}
				
				for(int b=0; b<logImage.size(); b++) {
					int imageNum = logImage.get(b).getBoard_code();
					if(listNum == imageNum) {
							image += logImage.get(b).getFile_content() + "◆";
					}
				}
				
				for(int c=0; c<likes.size(); c++) {
					
					int likeNum = likes.get(c).getBoard_code();
					if(likeNum == listNum) {
						list.get(i).setLike_count(likes.get(c).getCnt());
					}
					
				}
				
				for(int d=0; d<myLike.size(); d++) {
					int myLikeNum = myLike.get(d).getBoard_code();
					if(listNum == myLikeNum) {
						list.get(i).setMy_like(1);
					}
				}
				
				String [] itemA = hash.split("◆");
				list.get(i).setHash_tag_content(itemA);

				String [] itemB = image.split("◆");
				list.get(i).setFile_content(itemB);
				
				if(list.get(i).getLog_latitude() != 0.000000) {
					lat = list.get(i).getLog_latitude();
					lng = list.get(i).getLog_longtitude();
					AddressData = GsonParserUtils.parser(lng, lat);
					list.get(i).setOnAddress(AddressData);
				} 
			
			}
			
			map.put("list", list); // 해당날짜의 데이터
			
		}
		
		map.put("travel", travel); // travel-log 인포정보 
		map.put("place", place); // 맵 장소정보
		map.put("now", now); // 현재날짜 비교 
		map.put("nal", nal); // 1일차 ~ n일차 클릭타겟
		map.put("reply", reply); // 댓글정보
		map.put("totalCount", totalCount); // 댓글페이징
		
		return map;
	}

	@Transactional
	@Override
	public Map<String, Object> changeNalCommand(int group, String selectDate, String id) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<>();
		
		String first = selectDate + "000000";
		String second = selectDate + "235959";
		DateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String start = sdf.format(formatter.parse(first));
		String end = sdf.format(formatter.parse(second));
		
		List<TravelListVO> place = dao.travelPlace(group, selectDate);
		List<LogBoardVO> list = dao.travelPost(group, start, end);
		
		List<FileAndHashVO> logHash = dao.travelHash(group, start, end);
		List<FileAndHashVO> logImage = dao.travelImg(group, start, end);
		List<LikesVO> likes = dao.likeCounts(); // 좋아요 갯수 리스트
		List<LikesVO> myLike = dao.myLikes(id); // 나의좋아요 my
		
		Double lat, lng;
		String AddressData;
		
		if(list.size() != 0) { // 결과값이 없으면
			for(int i=0; i<list.size(); i++) {
				
				String hash = "";
				String image = "";
				int listNum = list.get(i).getBoard_code();
				
				for(int a=0; a<logHash.size(); a++) {
					
					int hashNum = logHash.get(a).getBoard_code();
					if(listNum == hashNum) {
						hash += logHash.get(a).getHash_tag_content() + "◆";
					}
					
				}
				
				for(int b=0; b<logImage.size(); b++) {
					int imageNum = logImage.get(b).getBoard_code();
					if(listNum == imageNum) {
							image += logImage.get(b).getFile_content() + "◆";
					}
				}
				
				for(int c=0; c<likes.size(); c++) {
					
					int likeNum = likes.get(c).getBoard_code();
					if(likeNum == listNum) {
						list.get(i).setLike_count(likes.get(c).getCnt());
					}
					
				}
				
				for(int d=0; d<myLike.size(); d++) {
					int myLikeNum = myLike.get(d).getBoard_code();
					if(listNum == myLikeNum) {
						list.get(i).setMy_like(1);
					}
				}
				
				String [] itemA = hash.split("◆");
				list.get(i).setHash_tag_content(itemA);

				String [] itemB = image.split("◆");
				list.get(i).setFile_content(itemB);
				
				if(list.get(i).getLog_latitude() != 0.000000) {
					lat = list.get(i).getLog_latitude();
					lng = list.get(i).getLog_longtitude();
					AddressData = GsonParserUtils.parser(lng, lat);
					list.get(i).setOnAddress(AddressData);
				} 
			
			}
			
			
		}
		
		map.put("log", list); // 해당날짜의 데이터
		map.put("place", place);
		
		return map;
	}

	@Override
	public Map<String, Object> replyTravelCommand(int no, int replytype, int replyno, String text, String user) throws Exception {
		// TODO Auto-generated method stub
		
		Map<String, Object> map = new HashMap<>();
		
		if(replytype != 3) {
			
			LogBoardVO vo = new LogBoardVO();
			vo.setBoard_content(text);
			vo.setBoard_type_code(6);
			vo.setUser_id(user);
			vo.setReply_code(no);
			vo.setWrite_type(0);
			
			if(replytype == 1) {
				dao.replyWrite(vo); // wirte
			}
			
			if(replytype == 2) {
				vo.setReply_code(replyno); // modify
				dao.replyModify(vo);
			}
			
		} 
		
		if(replytype == 3) {
			dao.replyDelete(replyno); // delete
		}
		
		int totalCount = dao.replyTravelTotalCount(no);
		List<LogBoardVO> list = dao.replyTravelList(no, 0, 10);
		
		map.put("totalCount", totalCount);
		map.put("list", list);

		return map;
	}

	@Override
	public Map<String, Object> replyTravelList(int post, int page) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<>();
		
		int totalCount = dao.replyTravelTotalCount(post);
		int startRecord = (page-1)*10; // 리밋 페이징계산 
		
		List<LogBoardVO> list = dao.replyTravelList(post, startRecord, 10);
		
		map.put("totalCount", totalCount);
		map.put("list", list);
		
		return map;
	}

}
