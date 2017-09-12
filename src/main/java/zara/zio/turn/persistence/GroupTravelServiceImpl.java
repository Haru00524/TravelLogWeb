package zara.zio.turn.persistence;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import zara.zio.turn.dao.GroupTravelDAO;
import zara.zio.turn.domain.ChattingVO;
import zara.zio.turn.domain.GroupApplicationVO;
import zara.zio.turn.domain.GroupVO;
import zara.zio.turn.domain.Income_disbursementVO;
import zara.zio.turn.domain.LogBoardVO;
import zara.zio.turn.domain.MaterialVO;
import zara.zio.turn.domain.MemberVO;
import zara.zio.turn.domain.TravelListVO;

@Service
public class GroupTravelServiceImpl implements GroupTravelService {

	@Inject
	private GroupTravelDAO dao;

	@Override
	public void create(GroupVO group) throws Exception {
		// TODO Auto-generated method stub
		dao.create(group);
	}

	@Override
	public int selectGroupCode(GroupVO group) throws Exception {
		// TODO Auto-generated method stub
		return dao.selectGroupCode(group);
	}

	// --------------- //

	@Override
	public void create(TravelListVO travel) throws Exception {
		// TODO Auto-generated method stub
		dao.create(travel);
	}

	@Override
	public List<TravelListVO> planDayList(TravelListVO travel) throws Exception {
		// TODO Auto-generated method stub
		return dao.planDayList(travel);
	}

	@Override
	public int travel_place(TravelListVO travel) throws Exception {
		// TODO Auto-generated method stub
		return dao.travel_place(travel);
	}

	@Override
	public void planPriority(TravelListVO travel) throws Exception {
		// TODO Auto-generated method stub
		dao.planPriority(travel);
	}

	@Override
	public void planDelete(TravelListVO travel) throws Exception {
		// TODO Auto-generated method stub
		dao.planDelete(travel);
	}

	@Override
	public List<TravelListVO> planRealTimePriority(TravelListVO travel) throws Exception {
		// TODO Auto-generated method stub
		return dao.planRealTimePriority(travel);
	}

	@Override
	public List<MaterialVO> material_list(MaterialVO material) throws Exception {
		// TODO Auto-generated method stub
		return dao.material_list(material);
	}

	@Override
	public void material_insert(MaterialVO material) throws Exception {
		// TODO Auto-generated method stub
		dao.material_insert(material);
	}

	@Override
	public int material_code(MaterialVO material) throws Exception {
		// TODO Auto-generated method stub
		return dao.material_code(material);
	}

	@Override
	public void material_check(MaterialVO material) throws Exception {
		// TODO Auto-generated method stub
		dao.material_check(material);
	}

	@Override
	public List<MaterialVO> material_checked(MaterialVO material) throws Exception {
		// TODO Auto-generated method stub
		return dao.material_checked(material);
	}

	@Override
	public void deleteCheckMaterial(MaterialVO material) throws Exception {
		// TODO Auto-generated method stub
		dao.deleteCheckMaterial(material);
	}

	@Override
	public void materialDelete(MaterialVO material) throws Exception {
		// TODO Auto-generated method stub
		dao.materialDelete(material);
	}

	@Override
	public void groupApplicationCreate(GroupApplicationVO groupA) throws Exception {
		// TODO Auto-generated method stub
		dao.groupApplicationCreate(groupA);
	}

	@Override
	public List<MemberVO> friendList(String mem) throws Exception {
		// TODO Auto-generated method stub
		return dao.friendList(mem);
	}

	@Override
	public void limit_cost_update(GroupVO group) throws Exception {
		// TODO Auto-generated method stub
		dao.limit_cost_update(group);
	}

	@Override
	public List<ChattingVO> chattingList(int group_Code) throws Exception {
		// TODO Auto-generated method stub
		return dao.chattingList(group_Code);
	}

	@Override
	public void chattingStore(ChattingVO chat) throws Exception {
		// TODO Auto-generated method stub
		dao.chattingStore(chat);
	}

	@Override
	public List<GroupVO> groupGoingList(String mem) throws Exception {
		// TODO Auto-generated method stub
		return dao.groupGoingList(mem);
	}

	@Override
	public List<GroupVO> groupFinishList(String mem) throws Exception {
		// TODO Auto-generated method stub
		return dao.groupFinishList(mem);
	}

	@Override
	public int user_group_delete_check(int group_Code) throws Exception {
		// TODO Auto-generated method stub
		return dao.user_group_delete_check(group_Code);
	}

	@Override
	public void user_group_delete(int group_Code) throws Exception {
		// TODO Auto-generated method stub
		dao.user_group_delete(group_Code);
		
	}

	@Override
	public void user_groupApplication_delete(GroupApplicationVO groupA) throws Exception {
		// TODO Auto-generated method stub
		dao.user_groupApplication_delete(groupA);
	}

	@Override
	public List<TravelListVO> user_plan_list(int group_Code) throws Exception {
		// TODO Auto-generated method stub
		return dao.user_plan_list(group_Code);
	}

	@Override
	public List<Income_disbursementVO> travel_cost_list(int group_Code) throws Exception {
		// TODO Auto-generated method stub
		return dao.travel_cost_list(group_Code);
	}

	@Override
	public List<MaterialVO> travel_supplies_list(int group_Code) throws Exception {
		// TODO Auto-generated method stub
		return dao.travel_supplies_list(group_Code);
	}

	@Override
	public GroupVO travel_modify(int group_Code) throws Exception {
		// TODO Auto-generated method stub
		return dao.travel_modify(group_Code);
	}

	@Override
	public String travel_cost(int group_Code) throws Exception {
		// TODO Auto-generated method stub
		return dao.travel_cost(group_Code);
	}

	@Override
	public List<GroupApplicationVO> group_travel_alarm() throws Exception {
		// TODO Auto-generated method stub
		return dao.group_travel_alarm();
	}
	
	public List<MemberVO> plan_friend_list(String user_id, int group_Code) throws Exception {
		// TODO Auto-generated method stub
		return dao.plan_friend_list(user_id, group_Code);

	}

	@Override
	public List<MemberVO> friend_search_list(String user_id, String friend_name) throws Exception {
		// TODO Auto-generated method stub
		return dao.friend_search_list(user_id, friend_name);
	}

	@Override
	public void groupApplication_cancel(GroupApplicationVO groupA) throws Exception {
		// TODO Auto-generated method stub
		dao.groupApplication_cancel(groupA);
	}
	
	@Override
	public void group_alarm_delete(GroupApplicationVO group_alarm_delete) throws Exception {
		// TODO Auto-generated method stub
		dao.group_alarm_delete(group_alarm_delete);
	}

	@Override
	public void group_alarm_update(GroupApplicationVO group_alarm_update) throws Exception {
		// TODO Auto-generated method stub
		dao.group_alarm_update(group_alarm_update);
	}

	@Override
	public List<LogBoardVO> travel_timeline(int group_Code, Timestamp start_Date, Timestamp end_Date) throws Exception {
		// TODO Auto-generated method stub
		return dao.travel_timeline(group_Code, start_Date, end_Date);
	}

	@Override
	public void groupTravelBoard(int group_Code, int openset, String user_id, String travel_title, String reserve) throws Exception {
		// TODO Auto-generated method stub
		dao.groupTravelBoard(group_Code, openset, user_id, travel_title, reserve);
	}

	@Override
	public void groupTravelMemo(int group_Code, int travel_priority, Date travel_Date, String memo) throws Exception {
		// TODO Auto-generated method stub
		dao.groupTravelMemo(group_Code, travel_priority, travel_Date, memo);
	}

	@Override
	public String groupTravelMemoText(int group_Code, int travel_priority, Date travel_Date) throws Exception {
		// TODO Auto-generated method stub
		return dao.groupTravelMemoText(group_Code, travel_priority, travel_Date);
	}

	
	
	
	
	

}
