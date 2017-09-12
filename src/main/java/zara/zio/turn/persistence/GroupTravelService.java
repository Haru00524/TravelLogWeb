package zara.zio.turn.persistence;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import zara.zio.turn.domain.ChattingVO;
import zara.zio.turn.domain.GroupApplicationVO;
import zara.zio.turn.domain.GroupVO;
import zara.zio.turn.domain.Income_disbursementVO;
import zara.zio.turn.domain.LogBoardVO;
import zara.zio.turn.domain.MaterialVO;
import zara.zio.turn.domain.MemberVO;
import zara.zio.turn.domain.TravelListVO;

public interface GroupTravelService {
	public void create(GroupVO group) throws Exception;
	public int selectGroupCode(GroupVO group) throws Exception;
	public void groupApplicationCreate(GroupApplicationVO groupA) throws Exception ;
	public List<MemberVO> friendList(String mem) throws Exception; 
	
	// --------------- // 
	
	public void create(TravelListVO travel) throws Exception;
	public List<TravelListVO> planDayList(TravelListVO travel) throws Exception ;
	public int travel_place(TravelListVO travel) throws Exception ; // travel_list에 같은 placeCode가 있는지
	public void planPriority(TravelListVO travel) throws Exception;
	public void planDelete(TravelListVO travel) throws Exception;
	
	public List<TravelListVO> planRealTimePriority(TravelListVO travel) throws Exception;
	
	public List<MaterialVO> material_list(MaterialVO material) throws Exception;
	public void material_insert(MaterialVO material) throws Exception;
	public int material_code(MaterialVO material) throws Exception;
	public void material_check(MaterialVO material) throws Exception;
	public List<MaterialVO> material_checked(MaterialVO material) throws Exception;
	public void deleteCheckMaterial(MaterialVO material) throws Exception;
	public void materialDelete(MaterialVO material) throws Exception;
	
	public void limit_cost_update(GroupVO group) throws Exception;
	
	public List<ChattingVO> chattingList(int group_Code) throws Exception ;
	public void chattingStore(ChattingVO chat) throws Exception;
	
	// 그룹에 포함된 회원뽑아옴
	public List<GroupVO> groupGoingList(String mem) throws Exception ;
	public List<GroupVO> groupFinishList(String mem) throws Exception ;
	
	// 지금 그룹에 몇명있는지 체크
	public int user_group_delete_check(int group_Code) throws Exception ;
	// plan 삭제
	public void user_group_delete (int group_Code) throws Exception ;
	public void user_groupApplication_delete(GroupApplicationVO groupA) throws Exception;

    //여행 일정
	public List<TravelListVO> user_plan_list(int group_Code) throws Exception ;
	//여행일정 스마트 코스트 리스트
	public List<Income_disbursementVO> travel_cost_list(int group_Code) throws Exception;
	//스마트 코스트 종류
	public String travel_cost(int group_Code) throws Exception;
	//여행 일정 준비물 리스트
	public List<MaterialVO> travel_supplies_list(int group_Code) throws Exception ; 
	// 여행 일정 수정하기
	public GroupVO travel_modify(int group_Code) throws Exception ;
	

	// 그룹여행 알림
	public List<GroupApplicationVO> group_travel_alarm() throws Exception;
	
	// 여행 상세보기 타임라인
	public List<LogBoardVO> travel_timeline(int group_Code, Timestamp start_Date, Timestamp end_Date) throws Exception;
	
	// 그룹여행 알림 지우기
	public void group_alarm_delete(GroupApplicationVO group_alarm_delete) throws Exception;
	
	// 그룹 여행 알람 수락 시 알람 없애기
	public void group_alarm_update(GroupApplicationVO group_alarm_update) throws Exception;

	//여행 친구 불러오기
	public List<MemberVO> plan_friend_list(String user_id, int group_Code) throws Exception ;
	//여행 친구 검색하기
	public List<MemberVO> friend_search_list(String user_id, String friend_name) throws Exception ;
	//여행 친구 삭제
	public void groupApplication_cancel(GroupApplicationVO groupA) throws Exception;

	//board에 travel_log 추가(여행일정만들기버튼을누르면)
	public void groupTravelBoard(int group_Code, int openset, String user_id, String travel_title, String reserve) throws Exception;
	//memo 추가하기
	public void groupTravelMemo(int group_Code, int travel_priority, Date travel_Date, String memo) throws Exception;
	//memo 불러오기
	public String groupTravelMemoText(int group_Code, int travel_priority, Date travel_Date) throws Exception;
}
