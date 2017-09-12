package zara.zio.turn.domain;

public class LikesVO { // Log 페이지 전용 사용하지말것
	
	private int board_code; // 번호
	private String user_id; // 아이디
	private int cnt; // 갯수
	
	public int getBoard_code() {
		return board_code;
	}
	public void setBoard_code(int board_code) {
		this.board_code = board_code;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	
	public String toString() {
		return "board_code : " + board_code + 
			   " user_id : " + user_id +
			   " cnt : " + cnt;
	}
	
	
}
