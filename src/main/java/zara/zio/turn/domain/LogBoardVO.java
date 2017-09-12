package zara.zio.turn.domain;

import java.sql.Date;
import java.sql.Timestamp;

public class LogBoardVO {
	
	private int board_code; // 번호 
	private String board_title; // 제목 
	private String board_content; // 내용
	private Timestamp board_date; // 날짜 
	private int share_type; // 전체공개, 그룹공개, 비공개 
	private int board_type_code; // 종류 카테고리  default : log / 1
	private int viewCount; // 조회수 
	private String user_id; // 유저정보 
	private int step_log_code; // 스텝로그정보
	
	private Double log_longtitude; // 경도
	private Double log_latitude; // 위도 

	private String user_profile; // 유저프로필이미지;	
	private int write_type; // 작성타입 웹 or 모바일 
	private int reply_code; // 게시글 댓글타겟코드	
	
	// 해시태그 부분
	private String [] hash_tag_content; // 해시태그정보
	// 파일업로드 정보 
	private String [] file_content; // 업로드 파일정보
	
	// 각 게시물 좋아요 수
	private int like_count; 
	private int my_like;

	private String place_img; // travel-log 로그이미지
	private String group_count; // 그룹카운트
	private int group_Code; // 그룹코드 번호
	private Date start_Date;
	private Date end_Date;

	// 상태객체
	private int states;
	// 댓글아이디 == 세션아이디 상태 
	private int reply_state;
	// 역지오코딩 데이터
	private String onAddress;
	
	
	public String getOnAddress() {
		return onAddress;
	}
	public void setOnAddress(String onAddress) {
		this.onAddress = onAddress;
	}
	
	public String getPlace_img() {
		return place_img;
	}
	public void setPlace_img(String place_img) {
		this.place_img = place_img;
	}
	public String getGroup_count() {
		return group_count;
	}
	public void setGroup_count(String group_count) {
		this.group_count = group_count;
	}
	public int getGroup_Code() {
		return group_Code;
	}
	public void setGroup_Code(int group_Code) {
		this.group_Code = group_Code;
	}
	public Date getStart_Date() {
		return start_Date;
	}
	public void setStart_Date(Date start_Date) {
		this.start_Date = start_Date;
	}
	public Date getEnd_Date() {
		return end_Date;
	}
	public void setEnd_Date(Date end_Date) {
		this.end_Date = end_Date;
	}
	
	
	public void setStates(int states) {
		this.states = states;
	}
	public int getStates() {
		return states;
	}
	public int getReply_state() {
		return reply_state;
	}
	public void setReply_state(int reply_state) {
		this.reply_state = reply_state;
	}
	public String getUser_profile() {
		return user_profile;
	}
	public void setUser_profile(String user_profile) {
		this.user_profile = user_profile;
	}
	public int getWrite_type() {
		return write_type;
	}
	public void setWrite_type(int write_type) {
		this.write_type = write_type;
	}
	public String[] getHash_tag_content() {
		return hash_tag_content;
	}
	public void setHash_tag_content(String[] hash_tag_content) {
		this.hash_tag_content = hash_tag_content;
	}
	public int getReply_code() {
		return reply_code;
	}
	public void setReply_code(int reply_code) {
		this.reply_code = reply_code;
	}
	public String[] getFile_content() {
		return file_content;
	}
	public void setFile_content(String[] file_content) {
		this.file_content = file_content;
	}
	public int getLike_count() {
		return like_count;
	}
	public void setLike_count(int like_count) {
		this.like_count = like_count;
	}
	public int getMy_like() {
		return my_like;
	}
	public void setMy_like(int my_like) {
		this.my_like = my_like;
	}
	
	
	public Double getLog_longtitude() {
		return log_longtitude;
	}
	public void setLog_longtitude(Double log_longtitude) {
		this.log_longtitude = log_longtitude;
	}
	public Double getLog_latitude() {
		return log_latitude;
	}
	public void setLog_latitude(Double log_latitude) {
		this.log_latitude = log_latitude;
	}
	
	
	public int getBoard_code() {
		return board_code;
	}
	public void setBoard_code(int board_code) {
		this.board_code = board_code;
	}
	public String getBoard_title() {
		return board_title;
	}
	public void setBoard_title(String board_title) {
		this.board_title = board_title;
	}
	public String getBoard_content() {
		return board_content;
	}
	public void setBoard_content(String board_content) {
		this.board_content = board_content;
	}
	public Timestamp getBoard_date() {
		return board_date;
	}
	public void setBoard_date(Timestamp board_date) {
		this.board_date = board_date;
	}
	public int getShare_type() {
		return share_type;
	}
	public void setShare_type(int share_type) {
		this.share_type = share_type;
	}
	public int getBoard_type_code() {
		return board_type_code;
	}
	public void setBoard_type_code(int board_type_code) {
		this.board_type_code = board_type_code;
	}
	public int getViewCount() {
		return viewCount;
	}
	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public int getStep_log_code() {
		return step_log_code;
	}
	public void setStep_log_code(int step_log_code) {
		this.step_log_code = step_log_code;
	}
	
	public String toString() {
		return "{\"board_code\" : \"" + board_code
			   + "\", \"board_title\" : \"" + board_title
			   + "\", \"board_content\" : \"" + board_content
			   + "\", \"board_date\" : \"" + board_date
			   + "\", \"share_type\" : \"" + share_type
			   + "\", \"board_type_code\" : \"" + board_type_code
			   + "\", \"viewCount\" : \"" + viewCount
			   + "\", \"user_id\" : \"" + user_id
			   + "\", \"step_log_code\" : \"" + step_log_code
			   + "\", \"user_profile\" : \"" + user_profile
			   + "\", \"log_longtitude\" : \"" + log_longtitude
			   + "\", \"log_latitude\" : \"" + log_latitude
			   + "\", \"file_content\" : \"" + file_content[0]
			   + "\", \"like_count\" : \"" + like_count
			   + "\", \"onAddress\" : \"" + onAddress 
			   + "\", \"my_like\" : \"" + my_like + "\"}";
			   
			   
		
//		private int board_code; // 번호 
//		private String board_title; // 제목 
//		private String board_content; // 내용
//		private Timestamp board_date; // 날짜 
//		private int share_type; // 전체공개, 그룹공개, 비공개 
//		private int board_type_code; // 종류 카테고리  default : log / 1
//		private int viewCount; // 조회수 
//		private String user_id; // 유저정보 
//		private int step_log_code; // 스텝로그정보
//		private String user_profile; // 유저프로필이미지;	
//		
//		private Double log_longtitude; // 경도
//		private Double log_latitude; // 위도 
		
//		private String [] file_content; // 업로드 파일정보
//		private int like_count; 
//		private int my_like;
	}
	
}
