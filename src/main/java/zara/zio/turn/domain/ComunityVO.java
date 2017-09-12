package zara.zio.turn.domain;

import java.sql.Timestamp;

public class ComunityVO {
	
	private int board_code; // 번호 
	private String board_title; // 제목 
	private String board_content; // 내용
	private Timestamp board_date; // 날짜 
	private int viewCount; // 조회수 
	private String user_id; // 유저정보 
	private String user_profile; // 유저 프로필이미지
	private int reply_count; 
	
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
	public String getUser_profile() {
		return user_profile;
	}
	public void setUser_profile(String user_profile) {
		this.user_profile = user_profile;
	}
	public int getReply_count() {
		return reply_count;
	}
	public void setReply_count(int reply_count) {
		this.reply_count = reply_count;
	}

	public String toString(){
		return "board_code : " + board_code
				+ " board_title : " + board_title
				+ " board_content : " + board_content
				+ " board_date : " + board_date
				+ " viewCount : " + viewCount
				+ " user_id : " + user_id
				+ " user_profile : " + user_profile;
	}
	
}
