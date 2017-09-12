package zara.zio.turn.domain;

import java.sql.Timestamp;

public class StepLogVO {
	
	// 스텝로그 지도 정보 추출;
	
	private int board_code; // 보드코드 
	private String board_title; // 라이프로그 제목 
	private String board_content; // 보드 컨텐츠  
	private Timestamp board_date; // 등록일자 
	private Double log_longtitude; // 경도 
	private Double log_latitude; // 위도 
	private int step_log_code; // 스텝로그 
	private int viewCount; // 조회수 
	private String fileimg; // 정보이미지 
	private int likecount; // 좋아요 카운트 
	private String user_id; // 유저아이디 
	private String user_profile; // 유저 프로필이름
	private int write_type; // 작성타입 웹 or 모바일 
	private int mylike; // 나의 좋아요 state
	
	// 해시태그 부분
	private String [] hash_tag_content; // 해시태그정보 

	private String onAddress; // 역지오코딩 데이터;
	
	
	public String[] getHash_tag_content() {
		return hash_tag_content;
	}
	public void setHash_tag_content(String[] hash_tag_content) {
		this.hash_tag_content = hash_tag_content;
	}
	public String getOnAddress() {
		return onAddress;
	}
	public void setOnAddress(String onAddress) {
		this.onAddress = onAddress;
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
	public int getStep_log_code() {
		return step_log_code;
	}
	public void setStep_log_code(int step_log_code) {
		this.step_log_code = step_log_code;
	}
	public int getViewCount() {
		return viewCount;
	}
	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}
	public String getFileimg() {
		return fileimg;
	}
	public void setFileimg(String fileimg) {
		this.fileimg = fileimg;
	}
	public int getLikecount() {
		return likecount;
	}
	public void setLikecount(int likecount) {
		this.likecount = likecount;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
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
	public int getMylike() {
		return mylike;
	}
	public void setMylike(int mylike) {
		this.mylike = mylike;
	}
	
	
	
}
