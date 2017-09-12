package zara.zio.turn.domain;

public class FriendListVO {
	
	private String user_id; // 유저아이디 
	private String friend_id; // 친구아이디 
	private int friend_accept; // 친구확인
   
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getFriend_id() {
		return friend_id;
	}
	public void setFriend_id(String friend_id) {
		this.friend_id = friend_id;
	}
	public int getFriend_accept() {
		return friend_accept;
	}
	public void setFriend_accept(int friend_accept) {
		this.friend_accept = friend_accept;
	}
}