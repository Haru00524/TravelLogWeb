package zara.zio.turn.domain;

public class ChattingVO {

	private int chatting_code;
	private int group_Code;
	private String chatting_content;
	private String chatting_date;
	private String user_id;
	
	public int getChatting_code() {
		return chatting_code;
	}
	public void setChatting_code(int chatting_code) {
		this.chatting_code = chatting_code;
	}
	public int getGroup_Code() {
		return group_Code;
	}
	public void setGroup_Code(int group_Code) {
		this.group_Code = group_Code;
	}
	public String getChatting_content() {
		return chatting_content;
	}
	public void setChatting_content(String chatting_content) {
		this.chatting_content = chatting_content;
	}
	public String getChatting_date() {
		return chatting_date;
	}
	public void setChatting_date(String chatting_date) {
		this.chatting_date = chatting_date;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
}
