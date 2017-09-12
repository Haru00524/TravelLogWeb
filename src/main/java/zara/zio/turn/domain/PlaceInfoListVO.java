package zara.zio.turn.domain;

public class PlaceInfoListVO {
	
	private String users; // 유저상태
	private String local_value;
	private String thema_value;
	private String search_value;
	private int startRecord; // 페이징 정보 
	private int recordPage; // 페이징 정보
	
	
	public String getUsers() {
		return users;
	}
	public void setUsers(String users) {
		this.users = users;
	}
	
	
	public int getStartRecord() {
		return startRecord;
	}
	public void setStartRecord(int startRecord) {
		this.startRecord = startRecord;
	}
	public int getRecordPage() {
		return recordPage;
	}
	public void setRecordPage(int recordPage) {
		this.recordPage = recordPage;
	}

	
	public String getLocal_value() {
		return local_value;
	}
	public void setLocal_value(String local_value) {
		this.local_value = local_value;
	}
	public String getThema_value() {
		return thema_value;
	}
	public void setThema_value(String thema_value) {
		this.thema_value = thema_value;
	}
	public String getSearch_value() {
		return search_value;
	}
	public void setSearch_value(String search_value) {
		this.search_value = search_value;
	}
	
	public String toString() {
		return "local_value : " + local_value
			+ " thema_value : " + thema_value
			+ " search_value : " + search_value
			+ " startRecord : " + startRecord
			+ " recordPage : " + recordPage;
	}
}
