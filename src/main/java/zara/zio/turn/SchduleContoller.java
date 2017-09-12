 package zara.zio.turn;

import java.sql.Date;
import java.util.*;
import java.text.SimpleDateFormat;

import javax.inject.Inject;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import zara.zio.turn.domain.ChattingVO;
import zara.zio.turn.domain.GroupApplicationVO;
import zara.zio.turn.domain.GroupVO;
import zara.zio.turn.domain.MaterialVO;
import zara.zio.turn.domain.MemberVO;
import zara.zio.turn.domain.PlaceVO;
import zara.zio.turn.domain.TravelListVO;
import zara.zio.turn.persistence.GroupTravelService;
import zara.zio.turn.persistence.PlaceService;

@Controller
public class SchduleContoller {
   
   @Inject
   private PlaceService service;
   
   @Inject
   private GroupTravelService service1;
   
   @ResponseBody
   @RequestMapping (value="friend_list" ,  method=RequestMethod.POST)
   public List<MemberVO> friend_list(HttpSession session) throws Exception{
      
      String mem = (String) session.getAttribute("mem") ;
      
      List<MemberVO> member01 = service1.friendList(mem);
      System.out.println(member01);
      return member01 ;
   }
   
   @RequestMapping (value="scheduleSet", method=RequestMethod.POST) // 스케쥴 페이지 이동 
   public String schduleSet(GroupVO group , GroupApplicationVO groupA, String scheduleDate, String local, 
		   int openset, String travel_Title, RedirectAttributes rttr, HttpSession session,HttpServletRequest request) throws Exception {

      //그룹에 초대한 친구들 id 가져옴
      String[] friend = (String[]) request.getParameterValues("friend") ;

      String[] date = scheduleDate.split(" - ");
      
      String date01 = date[0];
      String date02 = date[1];
      
      Date start_Date = Date.valueOf(date01);
      Date end_Date = Date.valueOf(date02);
      
      date02 = date[1] + " 23:59:59"; // 변수재활용
      
      group.setStart_Date(start_Date);
      group.setEnd_Date(end_Date);
      group.setLocal(local);
          
      service1.create(group);
         
      // groupCode 뽑아내는 문
      int groupCode = service1.selectGroupCode(group);
       
      // 그룹신청여부
      String mem = (String) session.getAttribute("mem"); // 세션아이디
        
      //개인이 그룹을 만들 때, 
      groupA.setUser_id(mem);
      groupA.setInvite_user(mem);
      groupA.setGroup_Code(groupCode);
      groupA.setGroup_apply(1);
      // 그룹신청
       
      service1.groupApplicationCreate(groupA);
      service1.groupTravelBoard(groupCode, openset, mem, group.getTravel_Title(), date02); // 보드코드로 갈 정보 
      System.out.println("[" + date02 + "]");
       
      if (friend != null) {
    	  // 그룹을 같이 만들 친구가 있을 경우
    	  if (friend.length > 0) {
    		  for (int i = 0; i < friend.length; i++) {
    			  groupA.setUser_id(friend[i]);
    			  groupA.setGroup_apply(0);
    			  System.out.println("friend : " + friend[i]);
    			  service1.groupApplicationCreate(groupA);
	
    		  }
    	  }
      }
      
	  rttr.addAttribute("title", travel_Title);
      rttr.addAttribute("scheduleDate", scheduleDate);
      rttr.addAttribute("local", local);
      rttr.addAttribute("groupCode", groupCode);
      
      
      return "redirect:scheduleSet";
   }
   
   
   
   @RequestMapping (value="scheduleSet", method=RequestMethod.GET) // 스케쥴 페이지 이동 
   public String schduleSetG(String title, String scheduleDate, String local, int groupCode, Model model, HttpSession session) {

	  String mem = (String) session.getAttribute("mem") ;
      
	  model.addAttribute("travelTitle", title);
      model.addAttribute("scheduleDate", scheduleDate);
      model.addAttribute("local", local);
      model.addAttribute("groupCode", groupCode);
      model.addAttribute("mem",mem);

      return "schedulePage/schedulePageA";
      
   }
   
   
   @ResponseBody
   @RequestMapping (value="placeList", method=RequestMethod.GET) // 장소정보 가져오기
   public List<PlaceVO> placeList(HttpServletRequest request) throws Exception {
      
      String local = request.getParameter("localData");
      local = "%" + local + "%";
      List<PlaceVO> list = service.readLocal(local);
      
      System.out.println("local : " + local);
      
      for(int i=0; i<list.size(); i++) {
         System.out.print(list.get(i).getPlace_code() + " ");
         System.out.println(list.get(i).getPlace_address());
      }
      
      return list;
   }
   
   @ResponseBody
   @RequestMapping (value="planPlaceCodCheck", method=RequestMethod.POST)
   public int planPlaceCodCheck(TravelListVO travel, String plan, String group) throws Exception{
      
   
       int group_Code = Integer.parseInt(group);
       Date travel_Date = Date.valueOf(plan);
    
       travel.setGroup_Code(group_Code);
       travel.setTravel_Date(travel_Date);
      
      int count = service1.travel_place(travel) ;
      //System.out.println("placecheck : " + place);
      System.out.println("countDB  : " + count);
      return count ;
   }
   
   
   @ResponseBody
   @RequestMapping (value="planList", method=RequestMethod.POST)
   public void planList(String place, String plan, String group, TravelListVO travel, int priority) throws Exception{
      int place_code = Integer.parseInt(place);
      int group_Code = Integer.parseInt(group);
      Date travel_Date = Date.valueOf(plan);
       
      System.out.println("priority : " + travel.getTravel_priority());
      
      travel.setTravel_priority(priority);
      travel.setPlace_code(place_code);
      travel.setGroup_Code(group_Code);
      travel.setTravel_Date(travel_Date);
         
       service1.create(travel) ;
         
   }
      
   @ResponseBody
   @RequestMapping (value="planDayList" , method=RequestMethod.POST)
   public List<TravelListVO> planList01(String plan, String group , TravelListVO travel) throws Exception{
      
      int group_Code = Integer.parseInt(group);
      Date travel_Date = Date.valueOf(plan);
      
      travel.setGroup_Code(group_Code);
      travel.setTravel_Date(travel_Date);
      
      List<TravelListVO> place = service1.planDayList(travel);
      for(int i =0; i<place.size();i++){
    	  System.out.println("getPlace_code : "+place.get(i).getPlace_code());
    	  System.out.println("pri : "+place.get(i).getTravel_priority());
      }

      
      return place;
      
      
    }
   
   @ResponseBody
   @RequestMapping (value="planPlacePriority", method=RequestMethod.POST)
   //public String planPlacePriority(@RequestBody List<TravelListVO> array) throws Exception {
   public List<TravelListVO> planPlacePriority(HttpServletRequest request, String plan, String group, TravelListVO travel, int count) throws Exception {

      int count_check = 0 ;
      String[] array = (String[])request.getParameterValues("array") ;
      String[] array01 = (String[])request.getParameterValues("array01") ;
   
      
      int group_Code = Integer.parseInt(group);
      Date travel_Date = Date.valueOf(plan);
       
      travel.setTravel_priority(count);
      travel.setGroup_Code(group_Code);
      travel.setTravel_Date(travel_Date);
      
      if(array != null){
      for(int i=0 ; i<array.length ; i++){
         
         count_check++ ;
         
         if(count >= count_check){
            
            int place_code = Integer.parseInt(array[i]) ;
            int travel_Priority = Integer.parseInt(array01[i]) ;
            
            System.out.println("place_code : " + place_code );
            System.out.println("travel_Priority : " + travel_Priority) ;
            
            travel.setCount(count_check);
            travel.setPlace_code(place_code);
            travel.setTravel_priority(travel_Priority);
            
   
            service1.planPriority(travel);
            
            
         }
         
         if(count == count_check)
            count_check = 1 ;
         
         
         
      }
   }
      
      
      List<TravelListVO> place = service1.planDayList(travel);
      
//       
//      service1.planPriority(travel);
      return place;
    }
   
   @ResponseBody
   @RequestMapping (value="planPlaceDelete", method=RequestMethod.POST)
    public void planPlaceDelete(String place, String plan, String group, TravelListVO travel) throws Exception{
       
      int place_code = Integer.parseInt(place);
       int group_Code = Integer.parseInt(group);
       Date travel_Date = Date.valueOf(plan);
          
       travel.setPlace_code(place_code);
       travel.setGroup_Code(group_Code);
       travel.setTravel_Date(travel_Date);
         
       service1.planDelete(travel);
   }

   @ResponseBody
   @RequestMapping (value="planRealTimePriority", method=RequestMethod.POST)
    public List<TravelListVO> planRealTimePriority(String plan, String group, TravelListVO travel) throws Exception{
       
      
       int group_Code = Integer.parseInt(group);
       Date travel_Date = Date.valueOf(plan);
          
       
       travel.setGroup_Code(group_Code);
       travel.setTravel_Date(travel_Date);
         
       List<TravelListVO> list = service1.planRealTimePriority(travel);
       
      return list;
   }
   
   
   @ResponseBody
   @RequestMapping (value="placeFilterList" , method=RequestMethod.POST)
   public List<PlaceVO> placeFilterList(String local , String placeType, PlaceVO place) throws Exception{
      
      System.out.println("local : " + local  + " ddd : " + placeType);
      
      place.setPlace_type(placeType);
      place.setPlace_address(local);
      
      
      
      List<PlaceVO> list = service.placeFilter(place);
      
      return list ;
   }
   
   
   @ResponseBody
   @RequestMapping (value="placeSearchList" , method=RequestMethod.POST)
   public List<PlaceVO> placeSearchList(String local , String place_name, PlaceVO place) throws Exception{
      List<PlaceVO> list = null;
      System.out.println("local : " + local  + " ddd : " + place_name);
      
      place.setPlace_name(place_name);
      place.setPlace_address(local);
      
      System.out.println(local.equals("local"));
      
      // 전체 지역 검색일 경우
      if(local.equals("local")){
        
         list = service.searchAllFilter(place) ;
         System.out.println(list);
      }else{
         
         list = service.searchLocalFilter(place);
      }
      
      return list ;
   }
   
   @ResponseBody
   @RequestMapping (value="material_list" , method=RequestMethod.POST)
   public List<MaterialVO> material_list(String groupCode , HttpSession session, MaterialVO material) throws Exception{
      
      int group_Code = Integer.parseInt(groupCode);
      String mem = (String) session.getAttribute("mem") ;
      System.out.println("material : "  + "dd : 0" + mem);
      
      material.setGroup_Code(group_Code) ;
      material.setUser_id(mem) ;
      
      
      List<MaterialVO> checked = service1.material_checked(material);
      List<MaterialVO> list = service1.material_list(material);
      
      
      
      for(int i=0 ; i<list.size() ; i++) {
         
         for(int j=0 ; j<checked.size(); j++){
            
            if(list.get(i).getMaterial_code() == checked.get(j).getMaterial_code() ){
               
               list.get(i).setMaterial_check(checked.get(j).getMaterial_code());
               System.out.println("Ddd : "  +list.get(i).getMaterial_check());
            }
            
         }
         
         System.out.println("dsdffd : " + list.get(i).getMaterial_check());
         
      } 
      
      return list ;
   }
   
   
   @RequestMapping (value="userPage/userScheduleList", method=RequestMethod.POST)
   public String userScheduleList(HttpServletRequest request, MaterialVO material,GroupVO group ,String groupCode, HttpSession session) throws Exception{
      
      String mem = (String) session.getAttribute("mem") ; // session에 저장된 아이디값
      String[] materials = (String[])request.getParameterValues("materials"); // 
      String[] materialCheck = (String[])request.getParameterValues("materialCheck"); // 준비물에서 체크된 값
      String[] managers = (String[])request.getParameterValues("managers");// manager 확인 여부
      String[] materialDeleteCheck = (String[])request.getParameterValues("materialDeleteCheck") ; // 체크가 사라진 값 
      String smartCost = request.getParameter("smartCost");  // 스마트 코스트 선택
      String cost = request.getParameter("cost"); // 차감형일때, 비용 한도 
      int limit_cost = Integer.parseInt(cost) ;
      int group_Code = Integer.parseInt(groupCode); 
      int manager_check ;
      material.setUser_id(mem) ;
      material.setGroup_Code(group_Code);
      
      System.out.println("limit : " + limit_cost + "smartCSot : " + smartCost);
      
      group.setGroup_Code(group_Code) ;
      group.setCoin_limit(limit_cost);
      group.setSc_Division(smartCost);
      
      
      
      service1.limit_cost_update(group);
      
      
      if(materialDeleteCheck != null){
         for(int i=0 ; i < materialDeleteCheck.length ; i++){
            System.out.println("materialdelete : " + materialDeleteCheck[i]);
            int material_code = Integer.parseInt(materialDeleteCheck[i]) ;
            material.setMaterial_code(material_code);
            service1.deleteCheckMaterial(material);
         }
      }

      if(materials != null){
         for(int i=0 ; i<materials.length ; i++){
            
            System.out.println(materials[i]);
            
            if(managers[i].equals("0")){
            
               manager_check = Integer.parseInt(managers[i]) ;
               material.setManager_check(manager_check);
               material.setMaterial_name(materials[i]);

               service1.material_insert(material) ;
               
            }
         }
      }
      
      if(materialCheck != null){
         for(int i=0 ; i<materialCheck.length ; i++){
            
            System.out.println(materialCheck[i]);
            
            material.setGroup_Code(group_Code);
            material.setMaterial_name(materialCheck[i]);
            int material_code = service1.material_code(material) ;
            material.setMaterial_code(material_code);
            System.out.println(material_code);
            service1.material_check(material) ;
         }
      }
   
      return null;
   }
   
   @ResponseBody
   @RequestMapping (value="materialDelete" , method=RequestMethod.POST)
   public void materialDelete(String groupCode , String materialCode ,HttpSession session, MaterialVO material) throws Exception{
      
      int group_Code = Integer.parseInt(groupCode);
      int material_code = Integer.parseInt(materialCode) ;
      String mem = (String) session.getAttribute("mem") ;
   
      material.setGroup_Code(group_Code) ;
      material.setUser_id(mem) ;
      material.setMaterial_code(material_code);
      
      service1.deleteCheckMaterial(material);
      service1.materialDelete(material) ;
      
      
   }
   
   @ResponseBody
   @RequestMapping (value="chatting" , method=RequestMethod.POST)
   public List<ChattingVO> chatting(String groupCode, HttpSession session, ChattingVO chat) throws Exception{
      System.out.println("groupc :  " + groupCode);
      int group_Code = Integer.parseInt(groupCode);
      String mem = (String) session.getAttribute("mem") ;
      
      
      List<ChattingVO> list = service1.chattingList(group_Code) ;
      
      //System.out.println("list : " + list.get(0).getUser_id());
      
      
     
      return list ; 
   }
   
   @ResponseBody
   @RequestMapping (value="chatStore", method=RequestMethod.POST)
   public void chatStore(String groupCode, String content, ChattingVO chat, HttpSession session) throws Exception{
	   
	   String mem = (String) session.getAttribute("mem") ;
	   int group_Code = Integer.parseInt(groupCode);
	   
	   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss") ;
	   String date = format.format(new java.util.Date());
	   
	   chat.setChatting_date(date);
	   chat.setGroup_Code(group_Code) ;
	   chat.setChatting_content(content) ;
	   chat.setUser_id(mem);
	   
	   System.out.println(mem) ;
	   
	   service1.chattingStore(chat) ;
   }
   
   @ResponseBody
   @RequestMapping (value="placeDetail", method=RequestMethod.POST)
   public PlaceVO placeDetail(String place) throws Exception{
	   
	   int place_code = Integer.parseInt(place) ;
	   
	   PlaceVO list = service.placeDetail(place_code) ;
	   
	   return list;
   }
   
   
   @ResponseBody
   @RequestMapping (value="plan_friend_list", method=RequestMethod.POST)
   public JSONObject friend_show(String groupCode ,HttpSession session) throws Exception{
	   
	   String user_id = (String) session.getAttribute("mem") ;
	   int group_code = Integer.parseInt(groupCode) ;
	   
	   JSONArray array = new JSONArray() ;
	   JSONObject main = new JSONObject();
	   
	   List<MemberVO> mem = service1.plan_friend_list(user_id, group_code) ;
	   
	   for(int i=0 ; i<mem.size() ; i++){
		   JSONObject obj = new JSONObject() ;
		   System.out.printf("ememe : " +  mem.get(i).getUser_id());
		   obj.put("user_id", mem.get(i).getUser_id());
		   obj.put("user_profile", mem.get(i).getUser_profile());
		   obj.put("group_apply", mem.get(i).getGroup_apply());
		   
		   array.add(obj);
	   }
	  
	   main.put("array", array);
	   
	   return main;
   }
   
   @ResponseBody
   @RequestMapping (value="friend_search_list", method=RequestMethod.POST)
   public JSONObject friend_search_list(String friend_name, HttpSession session, HttpServletRequest request) throws Exception{
	   
	   String[] arrays = (String[]) request.getParameterValues("arrays"); 
	   String user_id  = (String) session.getAttribute("mem");
	 
	   System.out.println("aa : " + friend_name + arrays );
	   

	   List<MemberVO> mem = service1.friend_search_list(user_id, friend_name);
	   System.out.println(mem.toString());
	   JSONArray array = new JSONArray();
	   JSONObject main = new JSONObject();
	   
	   for(int i=0; i<mem.size() ; i++){
		   

			   JSONObject obj = new JSONObject();
			   obj.put("user_id",mem.get(i).getUser_id());
			   obj.put("user_profile",mem.get(i).getUser_profile());
			   
			   array.add(obj);
		   
	   }
	   
	   System.out.println("mem : " + mem.toString() + "mdm : " + mem.size());
	   System.out.println("string : " + array.toString() +"string : " + array.size());
	   main.put("array", array) ;
	
	   
	   return main;
   }
   
   
   @ResponseBody
   @RequestMapping (value="group_Application", method=RequestMethod.POST)
   public void group_Application(String friend_name, String groupCode, HttpSession session) throws Exception{
	  
	   String user_id  = (String) session.getAttribute("mem");
	   int group_Code = Integer.parseInt(groupCode);
	   
	   GroupApplicationVO groupA = new GroupApplicationVO();
	   groupA.setGroup_Code(group_Code);
	   groupA.setUser_id(friend_name);
	   groupA.setInvite_user(user_id);

	   service1.groupApplicationCreate(groupA) ;
   }
   
   @ResponseBody
   @RequestMapping (value="groupApplication_cancel", method=RequestMethod.POST)
   public void  groupApplication_cancel(String friend_name, String groupCode, HttpSession session) throws Exception{
	 
	   int group_Code = Integer.parseInt(groupCode);
	   
	   GroupApplicationVO groupA = new GroupApplicationVO();
	   groupA.setGroup_Code(group_Code);
	   groupA.setUser_id(friend_name);
	   
	   System.out.println("user : " + friend_name);
	 

	   service1.groupApplication_cancel(groupA) ;
   }
   
   @ResponseBody
   @RequestMapping (value="memoSave", method=RequestMethod.POST)
   public void memoSave(String priority, String groupCode, String plan, String memo ) throws Exception{
	 
	   int group_Code = Integer.parseInt(groupCode);
	   int travel_priority = Integer.parseInt(priority);
	   Date travel_Date = Date.valueOf(plan);
	   
	   System.out.println(priority + group_Code + travel_Date + memo);
	   
	   service1.groupTravelMemo(group_Code, travel_priority, travel_Date, memo) ;
   }

   @ResponseBody
   @RequestMapping (value="memoText", method=RequestMethod.POST , produces = "application/text; charset=utf8")
   public String  memoSave(String priority, String groupCode, String plan) throws Exception{
	 
	   int group_Code = Integer.parseInt(groupCode);
	   int travel_priority = Integer.parseInt(priority);
	   Date travel_Date = Date.valueOf(plan);
	   
	   System.out.println(priority + group_Code + travel_Date );
	   
	   String memo = service1.groupTravelMemoText(group_Code, travel_priority, travel_Date) ;
	   System.out.println(memo);
	   return memo;
   }
  
   
   
   
}