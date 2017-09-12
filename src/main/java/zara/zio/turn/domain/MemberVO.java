package zara.zio.turn.domain;

import java.sql.Timestamp;

public class MemberVO {

	private String user_id; // 회원 아이디 
	private String user_pass; // 회원 패스워드
	private String user_birth; // 회원 생년월일 
	private int user_gender; // 회원성별
	private String user_phone; // 회원 폰넘버
	private String user_email; // 회원 이메일
	private Timestamp user_date; // 회원가입일자 
	private String yyyy, mm, dd; //생년월일
	private String user_profile; // 유저이미지
	
	private int group_apply; //초대 승인 유무
	
	public String getUser_profile() {
		return user_profile;
	}
	public void setUser_profile(String user_profile) {
		this.user_profile = user_profile;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_pass() {
		return user_pass;
	}
	public void setUser_pass(String user_pass) {
		this.user_pass = user_pass;
	}
	public String getUser_birth() {
		return user_birth;
	}
	public void setUser_birth(String user_birth) {
		this.user_birth = user_birth;
	}
	public int getUser_gender() {
		return user_gender;
	}
	public void setUser_gender(int user_gender) {
		this.user_gender = user_gender;
	}
	public String getUser_phone() {
		return user_phone;
	}
	public void setUser_phone(String user_phone) {
		this.user_phone = user_phone;
	}
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	public Timestamp getUser_date() {
		return user_date;
	}
	public void setUser_date(Timestamp user_date) {
		this.user_date = user_date;
	}
	public String getYyyy() {
		return yyyy;
	}
	public void setYyyy(String yyyy) {
		this.yyyy = yyyy;
	}
	public String getMm() {
		return mm;
	}
	public void setMm(String mm) {
		this.mm = mm;
	}
	public String getDd() {
		return dd;
	}
	public void setDd(String dd) {
		this.dd = dd;
	}
	
	public String toString() {
		return "( 회원아이디 : " + user_id + 
				" 회원패스워드 : " + user_pass + 
				" 회원생일 : " + user_birth + 
				" 회원성별 : " + user_gender + 
				" 회원폰넘버 : " + user_phone + 
				" 회원이메일 : " + user_email + 
				" 회원가입일자 : " + user_date + 
				" 유저이미지 : " + user_profile + " )";
	}
	public int getGroup_apply() {
		return group_apply;
	}
	public void setGroup_apply(int group_apply) {
		this.group_apply = group_apply;
	}
	
}
