package zara.zio.turn.domain;

public class FileAndHashVO { // Log 페이지 전용 사용하지말것
	
	private int board_code;
	private String hash_tag_content;
	private String file_content;
	
	public int getBoard_code() {
		return board_code;
	}
	public void setBoard_code(int board_code) {
		this.board_code = board_code;
	}
	public String getHash_tag_content() {
		return hash_tag_content;
	}
	public void setHash_tag_content(String hash_tag_content) {
		this.hash_tag_content = hash_tag_content;
	}
	public String getFile_content() {
		return file_content;
	}
	public void setFile_content(String file_content) {
		this.file_content = file_content;
	}
	
	
}
