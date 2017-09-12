package zara.zio.turn.domain;

import java.sql.Date;

public class GroupVO {
   
   private int group_Code; // 그룹코드 
   private String travel_Title; // 여행제목
   private Date start_Date; // 여행출발날짜
   private Date end_Date; // 여행도착날짜
   private int coin_limit; // 여행한도비
   private String sc_Division ; //여행구분
   private String local ;
   
   
   public int getGroup_Code() {
      return group_Code;
   }
   public void setGroup_Code(int group_Code) {
      this.group_Code = group_Code;
   }
   public String getTravel_Title() {
      return travel_Title;
   }
   public void setTravel_Title(String travel_Title) {
      this.travel_Title = travel_Title;
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
   public int getCoin_limit() {
      return coin_limit;
   }
   public void setCoin_limit(int coin_limit) {
      this.coin_limit = coin_limit;
   }
   public String getSc_Division() {
		
	   return sc_Division;
   }
   public void setSc_Division(String sc_Division) {
	   this.sc_Division = sc_Division;
   }
public String getLocal() {
	return local;
}
public void setLocal(String local) {
	this.local = local;
}
   
   
}