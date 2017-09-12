package zara.zio.turn;

import java.sql.Date;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import zara.zio.turn.domain.GroupApplicationVO;
import zara.zio.turn.domain.GroupVO;
import zara.zio.turn.domain.TravelListVO;
import zara.zio.turn.persistence.GroupTravelService;

@Controller
public class AlarmController {

	private static final Logger logger = LoggerFactory.getLogger(AlarmController.class);

	@Inject
	private GroupTravelService service;

	@ResponseBody
	@RequestMapping(value = "invite", method = RequestMethod.GET)
	public List<GroupApplicationVO> groupAlarm(Model model, int alarmCnt, HttpSession session) throws Exception {

		List<GroupApplicationVO> invite = service.group_travel_alarm();

//		System.out.println("받은 알림 갯수:" + alarmCnt);
		
		return invite;
	}

	@ResponseBody
	@RequestMapping(value = "groupTravel", method = RequestMethod.POST)
	public GroupVO groupTravel(HttpSession session, String group, String user, GroupApplicationVO change) throws Exception {

		int group_Code = Integer.parseInt(group);
		String user_id = user;

		GroupVO list = service.travel_modify(group_Code);
		change.setUser_id(user_id);
		change.setGroup_Code(group_Code);
		
		service.group_alarm_update(change);

		return list;
	}

	@ResponseBody
	@RequestMapping(value = "group_alarm_delete", method = RequestMethod.POST)
	public void planPlaceDelete(String group, String user, GroupApplicationVO alarm) throws Exception {
		System.out.println("그룹코드:" + group);
		System.out.println("초대받은 유저아이디:" + user);

		int group_Code = Integer.parseInt(group);
		String user_id = user;

		alarm.setUser_id(user_id);
		alarm.setGroup_Code(group_Code);

		service.group_alarm_delete(alarm);
	}

}
