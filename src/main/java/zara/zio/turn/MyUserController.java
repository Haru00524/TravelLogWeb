package zara.zio.turn;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;

import zara.zio.turn.domain.GroupApplicationVO;
import zara.zio.turn.domain.GroupVO;
import zara.zio.turn.domain.Income_disbursementVO;
import zara.zio.turn.domain.LogBoardVO;
import zara.zio.turn.domain.MaterialVO;
import zara.zio.turn.domain.MemberVO;
import zara.zio.turn.domain.TravelListVO;
import zara.zio.turn.persistence.GroupTravelService;
import zara.zio.turn.persistence.MemberService;
import zara.zio.turn.util.MediaUtils;
import zara.zio.turn.util.UploadFileUtils;

@Controller
public class MyUserController {
   
   private static final Logger logger =
         LoggerFactory.getLogger(MyUserController.class);
   
   @Inject
   private MemberService service;
   
   @Inject 
   private GroupTravelService service1 ;
   
   // 경로 지정 path
   @Resource(name="profilePath")
   private String profilePath;
   
   @RequestMapping(value="/myinfo", method = RequestMethod.GET)
   public String myInfo() {
      
      return "userPage/myInfo";
   }
   
   @RequestMapping(value="/myModify", method = RequestMethod.GET)
   public String myModify(HttpSession session, Model model) throws Exception {
      
      String myName = (String)session.getAttribute("mem");
      MemberVO userInfo = service.read(myName); // 현재아이디
      model.addAttribute("my",userInfo);
      
      return "userPage/myModify";
   }
   
   @RequestMapping(value="/myModify", method = RequestMethod.POST)
   public String myModify(MemberVO vo, String nowid, HttpSession session) throws Exception {
      
      System.out.println(nowid); // 현재아이디
      System.out.println(vo);
      
      String yyyy = vo.getYyyy();
      String mm = vo.getMm();
      String dd = vo.getDd();
      String birth = yyyy + "-" + mm + "-" + dd;
      vo.setUser_birth(birth);
      
      service.modify(vo, nowid);
      String newid = vo.getUser_id();
      session.setAttribute("mem", newid);
      
      return "redirect:myinfo";
   }
   
   @RequestMapping(value="/myDelete", method = RequestMethod.GET)
   public String myDelete(HttpSession session) throws Exception {
      
      String userid = (String)session.getAttribute("mem");
      service.remove(userid);
      session.invalidate();
      
      return "redirect:main";
   }
   
   
   // 프로필사진
   @RequestMapping(value="/profile", method=RequestMethod.POST, produces="text/plain;charset=UTF-8")
   public ResponseEntity<String> profileAjax(MultipartFile file) throws Exception {
      
      logger.info("ProfileName : " + file.getOriginalFilename()); // 프로필 사진정보
      
      return new ResponseEntity<String>(UploadFileUtils.uploadFile(profilePath, file.getOriginalFilename(), file.getBytes()), HttpStatus.CREATED);
   }
   
   // 프로필사진 표시맵핑
   @ResponseBody
   @RequestMapping("/displayProfile") 
   public ResponseEntity<byte[]> displayProfile(String fileName) throws Exception {
      // 서버의 파일을 다운로드하기 위한 스트림
      InputStream in = null; // java.io
      ResponseEntity<byte[]> entity = null;
      
      logger.info("DisplayProfile FILE NAME : " + fileName);
      
      try {
         // 확장자를 추출하여 formatName에 저장
         String formatName = fileName.substring(fileName.lastIndexOf(".")+1);
         
         // 추출한 확장자를 MediaUtils클래스에서  이미지파일여부를 검사하고 리턴받아 mType에 저장
         MediaType mType = MediaUtils.getMediaType(formatName);
         
         // 헤더 구성 객체(외부에서 데이터를 주고받을 때에는 header와 body를 구성해야하기 때문에)
         HttpHeaders headers = new HttpHeaders();
         
         // InputStream 생성
         in = new FileInputStream(profilePath+fileName);
         
         if(mType != null) { // 이미지파일일때
            headers.setContentType(mType);
         } else { // 이미지파일아닐때
            fileName = fileName.substring(fileName.indexOf("_")+1);
            
            // 다운로드용 컨텐트 타입지정 application/octet-stream
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            
            // 바이트배열을 스트링으로 : 
            // new String(fileName.getBytes("utf-8"),"iso-8859-1") * iso-8859-1 서유럽언어, 큰 따옴표 내부에  " \" 내용 \" "
                // 파일의 한글 깨짐 방지
            headers.add("Content-Disposition", "attachment; filename=\"" + 
               new String(fileName.getBytes("UTF-8"), "ISO-8859-1")+"\""); 
            //headers.add("Content-Disposition", "attachment; filename='"+fileName+"'");
         }
         
         // 바이트 배열, 헤더, HTTP 상태코드 
         // 대상파일에서 데이터를 읽어내는 IOUtils의 toByteArray()메소드 
         entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED); 
            
      } catch(Exception e) {
         e.printStackTrace();
         
         // HTTP상태 코드()
         entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
      } finally {
         in.close();
      }
      return entity;
   }
   
   
   @ResponseBody
   @RequestMapping(value="/deleteProfile", method=RequestMethod.POST)
   public ResponseEntity<String> deleteProfile(String fileName) {
      
      logger.info("deleteProfile : " + fileName);
      
      // 파일의 확장자 추출
      String formatName = fileName.substring(fileName.lastIndexOf(".")+1);
      
      // 이미지 파일 여부 검사
      MediaType mType = MediaUtils.getMediaType(formatName);
      
      // 이미지의 경우(썸네일 + 원본파일 삭제), 이미지가 아니면 원본파일만 삭제
        // 이미지 파일이면
      if(mType != null) {
         String che = "/" + fileName.substring(3);
         // 썸네일 이미지 삭제
         new File(profilePath + (che).replace('/', File.separatorChar)).delete();
      } 
      // 원본 파일 삭제

      new File(profilePath + fileName.replace('/', File.separatorChar)).delete();
      
      // 데이터와 http 상태 코드 전송
      return new ResponseEntity<String>("deleted", HttpStatus.OK);
      
   }
   
   // 사용자가 계획한 여행일정을 모두 뽑아옴
   @RequestMapping(value="userScheduleList", method=RequestMethod.GET)
   public String userScheduleList(RedirectAttributes rttr, HttpSession session, Model model , String state) throws Exception{  
	   
      String mem = (String) session.getAttribute("mem");
      String usergrant = (String) session.getAttribute("info");
      
      // 빠른 텝메뉴 리다이렉션 // 로그인세션정보가 없을시 -> 로그인창으로 리다이렉션
      if(mem == null && usergrant == null) {
			rttr.addAttribute("board","2");
			return "redirect:login";
	  }
      //
      
      List<GroupVO> list ; 
      if(state == "finish"){
         list = service1.groupFinishList(mem) ;
         
      }else{
         list = service1.groupGoingList(mem) ;
      }
      System.out.println(state + " : state");
      
      model.addAttribute("group",list);
      
      return "userPage/userScheduleList";
   }
   
   // 사용자가 계획한 여행일정을 모두 뽑아옴
   @ResponseBody
   @RequestMapping(value="userScheduleListCheck", method=RequestMethod.POST)
   public List<GroupVO> userScheduleListCheck(HttpSession session, Model model , String state) throws Exception{
      
      String mem = (String) session.getAttribute("mem");
      List<GroupVO> list ; ;
      if(state.equals("finish")){
         list = service1.groupFinishList(mem) ;
         System.out.println(state + " : state");
      }else{
         list = service1.groupGoingList(mem) ;
         System.out.println(state + " : state");
      }

      System.out.println(list.toString());

      return list;
   }
   
   // 사용자가 계획한 여행일정을 삭제 시킴.
   @ResponseBody
   @RequestMapping(value="plan_list_delete", method=RequestMethod.POST)
   public void plan_list_delete(HttpSession session, Model model , String group) throws Exception{
      
      String mem = (String) session.getAttribute("mem");
      int group_code = Integer.parseInt(group) ;
      
      GroupApplicationVO groupA = new GroupApplicationVO() ;
      
      groupA.setGroup_Code(group_code);
      groupA.setUser_id(mem);
      
      // group_application에서 group_code 삭제
      service1.user_groupApplication_delete(groupA);
      
      // group_code를 가지는 user들 체크
      int check = service1.user_group_delete_check(group_code);
      
      System.out.println(check);
      
      if(check == 0){
         // user들이 없으면 travel_group , travel_list에 group_code를 다 삭제
         service1.user_group_delete(group_code);
      }


   }
   
   // 사용자가 계획한 여행일정을 모두 뽑아옴
   @RequestMapping(value="userPlanDetail", method=RequestMethod.GET)
   public String plandetail(HttpSession session, Model model , String group_Code, String travel_Title, String startDate, String endDate ) throws Exception{
      
      String mem = (String) session.getAttribute("mem");
      int group_code = Integer.parseInt(group_Code);
      System.out.println(group_Code);
      System.out.println(travel_Title);
      
      model.addAttribute("title",travel_Title);
      model.addAttribute("startDate",startDate);
      model.addAttribute("endDate",endDate);
      model.addAttribute("group_Code",group_code);
      
      List<TravelListVO> list = service1.user_plan_list(group_code);
      List<Integer> days = new ArrayList<Integer>();
      
      // SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
      
      // 서브스트링 데이 뽑아내기 위한 순서
      if(list.size()>0){
	      int index = list.get(0).getTravel_Date().toString().lastIndexOf("-")+1;
	      
	      // 데이 뽑아내기
	      String dayStr = list.get(0).getTravel_Date().toString().substring(index);
	      
	      int day = Integer.parseInt(dayStr)-1;
	            
	      for(int i=0; i<list.size(); i++) {
	         String toDayStr = list.get(i).getTravel_Date().toString().substring(index);
	         System.out.println(list.get(i).getTravel_Date().toString());
	         int toDay = Integer.parseInt(toDayStr);
	         
	         days.add((toDay-day));
	         System.out.println("오늘날짜 = " + list.get(0).getTravel_Date().toString() + "  데이 = " + day + "   투데이 = " + toDay + "  계산값 = " + (toDay-day));
	         
	         
	      }
      }
      
      model.addAttribute("days",days) ;
      model.addAttribute("list",list);
      
      System.out.println(list.toString());

      
      return "userPage/userPlanDetail";
   }
   
   
   // 사용자가 계획한 여행일정을 모두 뽑아옴 비동기식으로 처리
   @ResponseBody
   @RequestMapping(value="plandetailAsnyc", method=RequestMethod.POST , produces = "application/text; charset=utf8")
   public String plandetailAsnyc(HttpSession session , String group_code, String travel_Title, String startDate, String endDate ) throws Exception{
      
      String mem = (String) session.getAttribute("mem");
      int group_Code = Integer.parseInt(group_code);

      List<TravelListVO> list = service1.user_plan_list(group_Code);
      List<Integer> days = new ArrayList<Integer>();
      
      // SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
      
      // 서브스트링 데이 뽑아내기 위한 순서
      int index = list.get(0).getTravel_Date().toString().lastIndexOf("-")+1;
      
      // 데이 뽑아내기
      String dayStr = list.get(0).getTravel_Date().toString().substring(index);
      
      int day = Integer.parseInt(dayStr)-1;
            
      for(int i=0; i<list.size(); i++) {
         String toDayStr = list.get(i).getTravel_Date().toString().substring(index);
         System.out.println(list.get(i).getTravel_Date().toString());
         int toDay = Integer.parseInt(toDayStr);
         
         days.add((toDay-day));
         System.out.println("오늘날짜 = " + list.get(0).getTravel_Date().toString() + "  데이 = " + day + "   투데이 = " + toDay + "  계산값 = " + (toDay-day));
         
         
      }
      
      Map<String, Object> map = new HashMap<String, Object>();
      map.put("days", days);
      map.put("list", list);
      
      String str = new Gson().toJson(map);
      
      System.out.println(list.toString());

      
      return str;
   }
   

   @ResponseBody
   @RequestMapping(value="travel_cost_list", method=RequestMethod.POST, produces = "application/text; charset=utf8" )
   public String travel_cost_list(HttpSession session , String group ) throws Exception{
      

      int group_Code = Integer.parseInt(group);

      String sc_Division = service1.travel_cost(group_Code);
      
      System.out.println(sc_Division);
      List<Income_disbursementVO> list = service1.travel_cost_list(group_Code);

      Map<String, Object> map = new HashMap<String, Object>();
      map.put("sc_Division", sc_Division);
      map.put("list", list);
      
      String str = new Gson().toJson(map);
      
      return str;
   }

   @ResponseBody
   @RequestMapping(value="travel_supplies_list", method=RequestMethod.POST )
   public List<MaterialVO> travel_supplies_list(HttpSession session , String group ) throws Exception{
      
      int group_Code = Integer.parseInt(group);

      List<MaterialVO> list = service1.travel_supplies_list(group_Code);

      System.out.println("list : " + list.toString());
      return list;
   }
   
   @ResponseBody
   @RequestMapping(value="travel_modify", method=RequestMethod.POST )
   public GroupVO travel_modify(HttpSession session , String group ) throws Exception{
      
      int group_Code = Integer.parseInt(group);

      GroupVO list = service1.travel_modify(group_Code);

      return list;
   }
   
   @RequestMapping(value="/myFriend", method = RequestMethod.GET)
      public String myfriend(Model model, HttpSession session) throws Exception {
         
         String users = (String)session.getAttribute("mem");
         MemberVO vo = service.read(users);
         
         model.addAttribute("vo",vo);
         
         
         return "userPage/myFriend";
      }
      
      @ResponseBody
      @RequestMapping(value="/friendSearch", method = RequestMethod.POST)
      public List<Map<String,Object>> friendSearch(String search, HttpSession session) throws Exception {
         
         String users = (String)session.getAttribute("mem");
         List<Map<String,Object>> list = service.friends(search); // user_info 조회 
         List<Map<String,Object>> fist = service.friendAll(users); // friend_list 조회 
         
         Iterator<Map<String, Object>> iter = list.iterator(); // 삭제하기위한 원소
         
         while(iter.hasNext()) {
            int cnt = 0;
            Map<String, Object> s = iter.next();
            String arr = (String)s.get("user_id");
      
            for(int j=0; j<fist.size(); j++) {
               String err = (String)fist.get(j).get("user_id");
               String frr = (String)fist.get(j).get("friend_id");
               
               if(cnt == 0 && arr.equals(err) || cnt == 0 && arr.equals(frr)) {
                  cnt++;
                  iter.remove(); //
               }
               
            }

         }
         
         return list;
         
      }
      
      
      @ResponseBody
      @RequestMapping(value="/friendList", method = RequestMethod.GET)
      public List<Map<String,Object>> friendList(HttpSession session) throws Exception {
         String users = (String)session.getAttribute("mem");
         List<Map<String,Object>> lists = service.friendList(users);
         return lists;
      }
      
      // 검색창의 친구요청보내기
      @ResponseBody
      @RequestMapping(value="/friendReq", method = RequestMethod.POST)
      public String friendReq(String my_id, String user_id) throws Exception {
         
         service.friendReq(my_id, user_id);
         
         String req = "success";
         
         return req;
      }
      
      // 친구요청취소.
      @ResponseBody
      @RequestMapping(value="/friendCancel", method = RequestMethod.POST)
      public String friendCancel(String my_id, String user_id) throws Exception {
         
         service.friendDelCancel(my_id, user_id);
         
         String cancel = "success";
         
         return cancel;
      }
      
      // 친구삭제
      @ResponseBody
      @RequestMapping(value="/friendDel", method = RequestMethod.POST)
      public String friendDel(String my_id, String user_id) throws Exception {
         
         service.friendDelCancel(my_id, user_id);
         
         String del = "success";
         
         return del;
      }
      
      // 친구요청수락
      @ResponseBody
      @RequestMapping(value="/friendAccept", method = RequestMethod.POST)
      public String friendAccept(String my_id, String user_id) throws Exception {
         
         service.friendAccept(my_id, user_id);
         
         String accept = "success";
         
         return accept;
      }
      
      // 여행일정에서 타임라인
      @ResponseBody
      @RequestMapping(value="travel_timeline", method=RequestMethod.GET)
      public List<LogBoardVO> travel_timeline(String group, String start, String end) throws Exception{

    	  start = start + " 00:00:00";
    	  end = end + " 23:59:59";
    	  System.out.println(group);
    	  System.out.println(start);
    	  System.out.println(end);
    	  
    	  int group_Code = Integer.parseInt(group);
    	  Timestamp start_Date = Timestamp.valueOf(start);
    	  Timestamp end_Date = Timestamp.valueOf(end);
    	  
    	  List<LogBoardVO> list = service1.travel_timeline(group_Code, start_Date, end_Date);
    	  
    	  return list;
      }
      
}
   