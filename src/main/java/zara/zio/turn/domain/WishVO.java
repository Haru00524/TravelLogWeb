package zara.zio.turn.domain;

public class WishVO {
	
	private int place_code;
	private String user_id;
	private int myWish;
	private int wishCount;
	
	public int getPlace_code() {
		return place_code;
	}
	public void setPlace_code(int place_code) {
		this.place_code = place_code;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public int getMyWish() {
		return myWish;
	}
	public void setMyWish(int myWish) {
		this.myWish = myWish;
	}
	public int getWishCount() {
		return wishCount;
	}
	public void setWishCount(int wishCount) {
		this.wishCount = wishCount;
	}
	
}
