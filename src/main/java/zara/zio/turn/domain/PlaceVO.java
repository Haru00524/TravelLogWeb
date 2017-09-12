package zara.zio.turn.domain;

import java.sql.Timestamp;

public class PlaceVO {
	
	// 주소정보
	private int place_code;
	private String place_name; // 장소이름
	private Timestamp add_date; // 추가된날짜, 생성된날짜
	private String place_content; // 장소내용
	private String place_address; // 장소주소 
	private double place_lat; // 장소 위도 
	private double place_lng; // 장소 경도 
	private String place_type; // 장소 분류 
	private int place_on; // 장소 온 오프 enable, disable
	
	// 이미지정보 
	private int img_code; // 이미지순차정보
	private String place_img; // 파일경로
	private String file_name; // 파일이름
	
	// 유저정보 , 유저프로필이미지, 조회수
	private String user_id;
	private String user_profile;
	private int view;
	
	// 위시카운트 , 내위시정보
	private int wishcount;
	private String wish;
	
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
	public int getView() {
		return view;
	}
	public void setView(int view) {
		this.view = view;
	}
	public int getWishcount() {
		return wishcount;
	}
	public void setWishcount(int wishcount) {
		this.wishcount = wishcount;
	}
	public String getWish() {
		return wish;
	}
	public void setWish(String wish) {
		this.wish = wish;
	}
	
	
	public int getImg_code() {
		return img_code;
	}
	public void setImg_code(int img_code) {
		this.img_code = img_code;
	}
	public String getPlace_img() {
		return place_img;
	}
	public void setPlace_img(String place_img) {
		this.place_img = place_img;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public int getPlace_code() {
		return place_code;
	}
	public void setPlace_code(int place_code) {
		this.place_code = place_code;
	}
	public String getPlace_name() {
		return place_name;
	}
	public void setPlace_name(String place_name) {
		this.place_name = place_name;
	}
	public Timestamp getAdd_date() {
		return add_date;
	}
	public void setAdd_date(Timestamp add_date) {
		this.add_date = add_date;
	}
	public String getPlace_content() {
		return place_content;
	}
	public void setPlace_content(String place_content) {
		this.place_content = place_content;
	}
	public String getPlace_address() {
		return place_address;
	}
	public void setPlace_address(String place_address) {
		this.place_address = place_address;
	}
	public double getPlace_lat() {
		return place_lat;
	}
	public void setPlace_lat(double place_lat) {
		this.place_lat = place_lat;
	}
	public double getPlace_lng() {
		return place_lng;
	}
	public void setPlace_lng(double place_lng) {
		this.place_lng = place_lng;
	}
	public String getPlace_type() {
		return place_type;
	}
	public void setPlace_type(String place_type) {
		this.place_type = place_type;
	}
	public int getPlace_on() {
		return place_on;
	}
	public void setPlace_on(int place_on) {
		this.place_on = place_on;
	}
	
	
	
	
//	private int place_code;
//	private String place_name; // 장소이름
//	private Timestamp add_date; // 추가된날짜, 생성된날짜
//	private String place_content; // 장소내용
//	private String place_address; // 장소주소 
//	private double place_lat; // 장소 위도 
//	private double place_lng; // 장소 경도 
//	private String place_type; // 장소 분류 
//	private int place_on; // 장소 온 오프 enable, disable
	
	
	public String toString() {
		return "( 코드 : " + place_code + 
				" 장소내용 : " + place_content + 
				" 장소주소 : " + place_address + 
				" 위도 : " + place_lat + 
				" 경도 : " + place_lng + 
				" 장소분류 : " + place_type + 
				" 장소온오프 : " + place_on + " )";
	}
	
}
