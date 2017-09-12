package zara.zio.turn.dao;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import zara.zio.turn.domain.ChattingVO;
import zara.zio.turn.domain.GroupApplicationVO;
import zara.zio.turn.domain.GroupVO;
import zara.zio.turn.domain.Income_disbursementVO;
import zara.zio.turn.domain.LogBoardVO;
import zara.zio.turn.domain.MaterialVO;
import zara.zio.turn.domain.MemberVO;
import zara.zio.turn.domain.TravelListVO;

@Repository
public class GroupTravelDAOImpl implements GroupTravelDAO {
	
	@Inject
	private SqlSession sqlSession;
	
	private static final String NAMESPACE = "zara.zio.groupTravelMapper";
	
	@Override
	public void create(GroupVO group) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.insert(NAMESPACE + ".group_insert", group) ;
	}
	
	@Override
	public int selectGroupCode(GroupVO group) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(NAMESPACE+ ".groupCode", group);
	}
	
	// --------------- // 
	
	@Override
	public void create(TravelListVO travel) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.insert(NAMESPACE + ".planList_insert", travel) ; 
	}

	@Override
	public List<TravelListVO> planDayList(TravelListVO travel) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(NAMESPACE + ".planDayList" , travel) ;
	}

	@Override
	public int travel_place(TravelListVO travel) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(NAMESPACE + ".travel_place", travel);
	}

	@Override
	public void planPriority(TravelListVO travel) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.selectOne(NAMESPACE + ".planPriority", travel);
	}

	@Override
	public void planDelete(TravelListVO travel) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.delete(NAMESPACE + ".planDelete", travel);
	}

	@Override
	   public List<TravelListVO> planRealTimePriority(TravelListVO travel) throws Exception {
	      // TODO Auto-generated method stub
	      return sqlSession.selectList(NAMESPACE + ".planRealTimePriority", travel);
	      
	}

	@Override
	public List<MaterialVO> material_list(MaterialVO material) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(NAMESPACE + ".material_list", material);
	}

	@Override
	public void material_insert(MaterialVO material) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.insert(NAMESPACE + ".material_insert", material); 
	}

	@Override
	public int material_code(MaterialVO material) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(NAMESPACE + ".material_code", material);
	}

	@Override
	public void material_check(MaterialVO material) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.insert(NAMESPACE + ".material_check", material);
	}

	@Override
	public List<MaterialVO> material_checked(MaterialVO material) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(NAMESPACE + ".material_checked", material);
	}

	@Override
	public void deleteCheckMaterial(MaterialVO material) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.delete(NAMESPACE + ".deleteCheckMaterial", material);
	}

	@Override
	public void materialDelete(MaterialVO material) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.delete(NAMESPACE + ".materialDelete", material);
	}
	
	@Override
	public void groupApplicationCreate(GroupApplicationVO groupA) throws Exception {	
		// TODO Auto-generated method stub
		sqlSession.insert(NAMESPACE + ".groupApplicationCreate", groupA) ;
	}
	
	@Override
	public List<MemberVO> friendList(String member) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(NAMESPACE + ".friendList" , member);
	}

	@Override
	public void limit_cost_update(GroupVO group) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.update(NAMESPACE + ".limit_cost_update", group) ;
	}

	@Override
	public List<ChattingVO> chattingList(int group_Code) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(NAMESPACE + ".chattingList",  group_Code);
	}

	@Override
	public void chattingStore(ChattingVO chat) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.insert(NAMESPACE + ".chattingStore", chat) ;
	}

	@Override
	public List<GroupVO> groupGoingList(String mem) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(NAMESPACE + ".groupGoingList", mem);
	}

	@Override
	public List<GroupVO> groupFinishList(String mem) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(NAMESPACE + ".groupFinishList", mem);
	}

	@Override
	public int user_group_delete_check(int group_Code) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(NAMESPACE + ".user_group_delete_check", group_Code);
	}

	@Override
	public void user_group_delete(int group_Code) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.delete(NAMESPACE + ".user_group_delete",group_Code);
	}

	@Override
	public void user_groupApplication_delete(GroupApplicationVO groupA) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.delete(NAMESPACE + ".user_groupApplication_delete",groupA) ;
	}

	@Override
	public List<TravelListVO> user_plan_list(int group_Code) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(NAMESPACE +".user_plan_list", group_Code);
	}

	@Override
	public List<Income_disbursementVO> travel_cost_list(int group_Code) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(NAMESPACE + ".travel_cost_list", group_Code);
	}

	@Override
	public List<MaterialVO> travel_supplies_list(int group_Code) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(NAMESPACE + ".travel_supplies_list", group_Code);
	}

	@Override
	public GroupVO travel_modify(int group_Code) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(NAMESPACE + ".travel_modify", group_Code);
	}

	@Override
	public String travel_cost(int group_Code) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(NAMESPACE + ".travel_cost",group_Code);
	}

	@Override
	public List<GroupApplicationVO> group_travel_alarm() throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.selectList(NAMESPACE + ".group_travel_alarm");
		
	}

	public List<MemberVO> plan_friend_list(String user_id, int group_Code) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> plan = new HashMap<String, Object>();
		plan.put("user_id", user_id);
		plan.put("group_Code", group_Code);
		return sqlSession.selectList(NAMESPACE + ".plan_friend_list", plan);
	}

	@Override
	public List<MemberVO> friend_search_list(String user_id, String friend_name) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> plan = new HashMap<String, Object>();
		plan.put("user_id", user_id);
		plan.put("friend_name", friend_name);
		return sqlSession.selectList(NAMESPACE + ".friend_search_list",plan) ;
	}

	@Override
	public void groupApplication_cancel(GroupApplicationVO groupA) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.delete(NAMESPACE + ".groupApplication_cancel", groupA);
	}
	
	@Override
	public void group_alarm_delete(GroupApplicationVO group_alarm_delete) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.delete(NAMESPACE + ".group_alarm_delete", group_alarm_delete);
	}

	@Override
	public void group_alarm_update(GroupApplicationVO group_alarm_update) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.update(NAMESPACE + ".group_alarm_update", group_alarm_update);
	}

	@Override
	public List<LogBoardVO> travel_timeline(int group_Code, Timestamp start_Date, Timestamp end_Date) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> timeline = new HashMap<String, Object>();
		
		timeline.put("group_Code", group_Code);
		timeline.put("start_Date", start_Date);
		timeline.put("end_Date", end_Date);
		
		return sqlSession.selectList(NAMESPACE + ".travel_timeline", timeline);
	}

	@Override
	public void groupTravelBoard(int group_Code, int openset, String user_id, String travel_title, String reserve) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> board = new HashMap<String, Object>();
		board.put("group_Code", group_Code);
		board.put("openset", openset);
		board.put("user_id", user_id);
		board.put("travel_title", travel_title);
		board.put("reserve", reserve);
		
		sqlSession.insert(NAMESPACE + ".groupTravelBoard", board);
	}

	@Override
	public void groupTravelMemo(int group_Code, int travel_priority, Date travel_Date, String memo) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> memoC = new HashMap<String, Object>();
		memoC.put("group_Code",group_Code);
		memoC.put("travel_priority",travel_priority);
		memoC.put("travel_Date",travel_Date);
		memoC.put("memo",memo);
		
		sqlSession.update(NAMESPACE + ".groupTravelMemo", memoC);
	}

	@Override
	public String groupTravelMemoText(int group_Code, int travel_priority, Date travel_Date) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> memoC = new HashMap<String, Object>();
		memoC.put("group_Code",group_Code);
		memoC.put("travel_priority",travel_priority);
		memoC.put("travel_Date",travel_Date);
		
		return sqlSession.selectOne(NAMESPACE + ".groupTravelMemoText", memoC);
	}





}
