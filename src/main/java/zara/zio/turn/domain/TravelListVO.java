package zara.zio.turn.domain;

import java.sql.Date;

public class TravelListVO {
   
    private int travel_Code; // 여행리스트코드
    private int travel_priority; // 여행리스트우선순위
    private String travel_Memo; // 장소마다 메모
    private Date travel_Date; // 여행리스트 : 장소가 추가된날짜
    private int place_code; // 장소코드
    private int group_Code; // 그룹코드
    private int count;
    private double place_lat; // 장소 위도 
    private double place_lng; // 장소 경도 
    private String place_img ; // 장소 이미지
    private String place_name ; // 장소 이름
      
    // 추가한것
    private String place_content; // 장소내용
    private String place_address; // 장소주소 
    private String place_type; // 장소타입
      
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
	public String getPlace_type() {
		return place_type;
	}
	public void setPlace_type(String place_type) {
		this.place_type = place_type;
	}
	
	
	public int getTravel_Code() {
        return travel_Code;
    }
	public void setTravel_Code(int travel_Code) {
	    this.travel_Code = travel_Code;
	}
	public int getTravel_priority() {
	    return travel_priority;
	}
	public void setTravel_priority(int travel_Priority) {
	    this.travel_priority = travel_Priority;
	}
	public String getTravel_Memo() {
	    return travel_Memo;
	}
	public void setTravel_Memo(String travel_Memo) {
		this.travel_Memo = travel_Memo;
	}
	public Date getTravel_Date() {
	    return travel_Date;
	}
	public void setTravel_Date(Date travel_Date) {
		this.travel_Date = travel_Date;
	}
	public int getPlace_code() {
	    return place_code;
	}
	public void setPlace_code(int place_code) {
	    this.place_code = place_code;
	}
	public int getGroup_Code() {
	    return group_Code;
	}
	public void setGroup_Code(int group_Code) {
	    this.group_Code = group_Code;
	}
	public int getCount() {
	    return count;
	}
	public void setCount(int count) {
	    this.count = count;
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
	public String getPlace_img() {
	    return place_img;
	}
	public void setPlace_img(String place_img) {
	    this.place_img = place_img;
	}
	public String getPlace_name() {
	    return place_name;
	}
	public void setPlace_name(String place_name) {
	    this.place_name = place_name;
	}
    
	public String toString(){
		return "{\"travel_Code\" : \"" + travel_Code
				+ "\", \"travel_priority\" : \"" + travel_priority
				+ "\", \"travel_Memo\" : \"" + travel_Memo
				+ "\", \"travel_Date\" : \"" + travel_Date
				+ "\", \"place_code\" : \"" + place_code
				+ "\", \"group_Code\" : \"" + group_Code
				+ "\", \"place_lat\" : \"" + place_lat
				+ "\", \"place_lng\" : \"" + place_lng
				+ "\", \"place_img\" : \"" + place_img
				+ "\", \"place_name\" : \"" + place_name
				+ "\", \"place_content\" : \"" + place_content
				+ "\", \"place_address\" : \"" + place_address
				+ "\", \"place_type\" : \"" + place_type + "\"}";
	}
	
}