package zara.zio.turn.domain;

public class Pagination {
	private int page = 1; // 페이지 
	private int recordPage = 20; 

	public final static int DISPLAY_PAGE_NUM = 10;
	private int startPage; // 시작페이지
	private int endPage; // 종료페이지
	private boolean prev; 
	private boolean next; 
	private int totalCount;
	

	public int getTotalCount() { // 전체카운트
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		calculate();
	}
	
	private void calculate() {
		// TODO Auto-generated method stub
		/*
		 *  startPage, endPage, prev, next 계산해줌  
		 *  
		 */
		
		
		// 엔드 페이지 계산법
		endPage = (int)Math.ceil(page/(double)DISPLAY_PAGE_NUM)*DISPLAY_PAGE_NUM;
		
		// 스타트 페이지 계산법
		startPage = endPage-DISPLAY_PAGE_NUM + 1;
		
		
		// 끝페이지 계산
		int tempEndPage = (int)Math.ceil((double)totalCount/recordPage);
		if(endPage > tempEndPage) {
			endPage = tempEndPage;
		}
		
		// <, > 스타트, 엔드 페이지 아이콘
		prev = startPage > 1 ? true:false;
		next = endPage*recordPage < totalCount ? true:false;
	}

	public int getStartPage() {
		return startPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public boolean isPrev() {
		return prev;
	}
	public boolean isNext() {
		return next;
	}
	
	// ======================================================= //
	
	public int getStartRecord() { // 페이지 번호에 따라 계산해서 넘긴다. 
		/* page가 1이면 0*10 리턴
		 * page가 2이면 1*10 리턴
		 * page가 3이면 2*10 리턴
		 * page가 4이면 3*10 리턴
		 * 
		 */	
		return (page-1)*recordPage;
	}
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		if(page <= 0)
			this.page = 1;
		else 
			this.page = page;
	}
	public int getRecordPage() {
		return recordPage;
	}
	public void setRecordPage(int recordPage) {
		if(recordPage <= 0 || recordPage > 100)
			this.recordPage = 20;
		else 
			this.recordPage = recordPage;
	}
	
	@Override
	public String toString() {
		return "Pagenation [page="+ page + ", " + "recordPage=" + recordPage + "]";
		
	}
	
}
