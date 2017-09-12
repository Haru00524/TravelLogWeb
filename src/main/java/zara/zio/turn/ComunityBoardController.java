package zara.zio.turn;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import zara.zio.turn.domain.ComunityVO;
import zara.zio.turn.domain.LogBoardVO;
import zara.zio.turn.domain.PaginationE;
import zara.zio.turn.domain.ReplyInfoVO;
import zara.zio.turn.persistence.LogBoardService;
import zara.zio.turn.util.MediaUtils;
import zara.zio.turn.util.UploadFileUtils;


@Controller
public class ComunityBoardController {
	
	private static final Logger logger =
			LoggerFactory.getLogger(ComunityBoardController.class);

	// 경로 지정 path
	@Resource(name="comunityPath")
	private String comunityPath;
	
	@Inject
	private LogBoardService service; 
	
	
	@RequestMapping(value="/comuList", method = RequestMethod.GET)
	public String comuList(Model model, PaginationE pagenation, 
		@RequestParam(value="page", defaultValue="1") String page, @RequestParam(value="keyword", defaultValue="") String keyword) throws Exception {
		
		pagenation.setPage(Integer.parseInt(page));
		Map<String, Object> map = service.comunityInfoList(pagenation);
		
		model.addAttribute("map", map);
		model.addAttribute("pagenationE", pagenation);
		
		int totalCount = service.comuTotalCount(pagenation);
		
		System.out.println("totalCount [totalCount=" + totalCount + "]");
		
		pagenation.setTotalCount(totalCount);
		
		return "comunityBorad/comuList";
	}
	
	@RequestMapping(value="/comuWrite", method = RequestMethod.GET)
	public String comuWrite(HttpSession session, RedirectAttributes rttr) {
		
		String username = (String)session.getAttribute("mem");
		String usergrant = (String) session.getAttribute("info");
		
		if(username == null && usergrant == null) {
			rttr.addAttribute("board","4");
			return "redirect:login";
		}
		
		return "comunityBorad/comuWrite";
		
	}
	
	@RequestMapping(value="/comuWrite", method = RequestMethod.POST)
	public String comuWrite(String [] cache_content, LogBoardVO vo, RedirectAttributes rttr, HttpSession session) throws Exception {
		
		String userName = (String)session.getAttribute("mem");
		
		vo.setShare_type(1); // 커뮤니티전체공개 
		vo.setBoard_type_code(4); // 커뮤니티타입
		vo.setUser_id(userName); // 유저아이디
		
		int type = 1; // 이미지 디폴트  1
		
		if(vo.getFile_content() == null) {
			System.out.println("널캐치");
		} else {
			
			if(vo.getFile_content()[0].contains(".youtube")) {
	        	type = 2; 
	        }
			
			// 캐싱데이터 예외처리
			String [] one = vo.getFile_content();
			
			for(int i=0; i<cache_content.length; i++) {
				int count = 0;
				for(int j=0; j<one.length; j++) {
					if(!(cache_content[i].equals(one[j]))) {
						count++;
					}
					if(one.length == count) {
						deleteComunity(cache_content[i]);
					}
				}
			}
			
		}
		
        Map<String,Object> map = service.maxCode(); // 등록할 최댓값 , 카운트 추출
        int max = 0;
        int count = Integer.parseInt(map.get("count").toString());
        
		if(count == 0) { // 최초값이 0이면 
			 max = 1;
		} else {
			 max = Integer.parseInt(map.get("max").toString()) + 1;
		}
		
		vo.setBoard_code(max);
        service.logBoardCreate(vo, max); // 파일정보	
        
        rttr.addAttribute("page", 1);
        rttr.addAttribute("keyword", "");
        
		return "redirect:comuList";
		
	}
	
	@RequestMapping(value="/comuRead", method = RequestMethod.GET)
	public String comuRead(Model model, @RequestParam(value="page") int page, @RequestParam(value="post", defaultValue="0") int post, 
		@RequestParam(value="keyword", defaultValue="") String keyword, HttpSession session) throws Exception {
		
		String user = (String)session.getAttribute("mem");
		Map<String, Object> map = service.comunityInfoRead(post, user);
		model.addAttribute("map", map);
		model.addAttribute("page",page);
		model.addAttribute("keyword",keyword);
		
		return "comunityBorad/comuRead";
	}
	
	@RequestMapping(value="/comuSet", method = RequestMethod.GET)
	public String comuSet(@RequestParam(value="post", defaultValue="0") int post, Model model) throws Exception {
		
		ComunityVO vo = service.comunityInfoRead2(post);
		model.addAttribute("vo", vo);
		
		return "comunityBorad/comuSet";
	}

	@RequestMapping(value = "/comuSet", method = RequestMethod.POST)
	public String comuSet(@RequestParam(value="page") int page, @RequestParam(value="post", defaultValue = "0") int post, 
		@RequestParam(value="keyword", defaultValue="") String keyword, LogBoardVO vo, RedirectAttributes rttr, HttpSession session) throws Exception {
		
		String userName = (String) session.getAttribute("mem");
		vo.setShare_type(1); // 커뮤니티전체공개
		vo.setBoard_type_code(4); // 커뮤니티타입
		vo.setUser_id(userName); // 유저아이디

		List<Map<String, Object>> list = service.comunityFileRead(post); // DB서버
		String[] now = vo.getFile_content(); // 클라이언트에서 가져오는거
		int type = 1; // 이미지 디폴트 1

		System.out.println(list.isEmpty());

		if (list.isEmpty() && now != null) {

			System.out.println("Exception_function_1");
			// 서버에 없으며 새로추가하려할때.
			for (int i = 0; i < now.length; i++) {
				if (now[i].contains(".youtube")) {
					type = 2;
				}
				service.comunityFileAdd(now[i], type, post);
			}

		} else if (now == null && !(list.isEmpty())) {
			System.out.println("Exception_function_2");
			// 서버에 있으며 모두삭제하려할때
			for (int i = 0; i < list.size(); i++) {
				String arr = (String) list.get(i).get("file_content");
				int codeTarget = (int) list.get(i).get("file_code");

				deleteComunity(arr); // 폴더데이터 삭제
				service.comunityFileDel(codeTarget); // 데이터 베이스 삭제
			}
		} else if (now != null && !(list.isEmpty())) {

			System.out.println("Exception_function_3");
			System.out.println(now.length + " now값");
			System.out.println(list.size() + " list값");

			if (now.length > list.size()) {
				// 수정 후 추가된거 등록
				System.out.println("수정후 추가된것");
				for (int i = 0; i < now.length; i++) {
					int count = 0;
					String arr = now[i];
					 System.out.println(arr + " nA");
					for (int j = 0; j < list.size(); j++) {
						String err = (String) list.get(j).get("file_content");

						System.out.println(err + " nB");

						if (!arr.equals(err)) {
							System.out.println(count + " countCheck");
							count++;
							if (count == list.size()) {
								if (arr.contains(".youtube")) {
									type = 2;
								}
								service.comunityFileAdd(arr, type, post);
							}
						} 

					}
					
				}
				
				System.out.println("next NowArray"); 
				
				for (int i = 0; i < list.size(); i++) {
					int count = 0;
					String arr = (String) list.get(i).get("file_content");
					int codeTarget = (int) list.get(i).get("file_code");

					for (int j = 0; j < now.length; j++) {
						if (!arr.equals(now[j])) {
							count++;
						}
						if (now.length == count) {
							deleteComunity(arr); // 만족하지못한경우 폴더명삭제
							service.comunityFileDel(codeTarget); // 데이터베이스의 코드삭제
						}

					}
				}

			} else if (now.length < list.size()) {
				// 수정후 없어진거 삭제
				System.out.println("수정후 없어진것");
				for (int i = 0; i < list.size(); i++) {
					int count = 0;
					String arr = (String) list.get(i).get("file_content");
					int codeTarget = (int) list.get(i).get("file_code");

					for (int j = 0; j < now.length; j++) {
						if (!arr.equals(now[j])) {
							count++;
						}
						if (now.length == count) {
							deleteComunity(arr); // 만족하지못한경우 폴더명삭제
							service.comunityFileDel(codeTarget); // 데이터베이스의 코드삭제
						}

					}
				}
				
				System.out.println("next ListArray"); 
				
				for (int i = 0; i < now.length; i++) {
					int count = 0;
					String arr = now[i];
					 System.out.println(arr + " lA");
					for (int j = 0; j < list.size(); j++) {
						String err = (String) list.get(j).get("file_content");

						System.out.println(err + " lB");

						if (!arr.equals(err)) {
							System.out.println(count + " countCheck");
							count++;
							if (count == list.size()) {
								if (arr.contains(".youtube")) {
									type = 2;
								}
								service.comunityFileAdd(arr, type, post);
							}
						} 

					}
					
				}
				
			}

		}

		service.comunityUpdate(vo, post);
		rttr.addAttribute("page", page);
		rttr.addAttribute("post", post);
		rttr.addAttribute("keyword", keyword);

		return "redirect:comuRead";
	}
	
	@RequestMapping(value="/comuDel", method = RequestMethod.GET)
	public String comuDel(@RequestParam(value="post") int post, RedirectAttributes rttr) throws Exception {
		
		List<Map<String, Object>> list = service.comunityFileRead(post);
		for(int i=0; i<list.size(); i++) {
			String arr = (String)list.get(i).get("file_content");
			deleteComunity(arr); // 이미지 타겟경로 삭제
		}
		service.boardAllDel(post);
		
		rttr.addAttribute("page", 1);
        rttr.addAttribute("keyword", "");
		
		return "redirect:comuList";
	}

	// comu-image 업로드  
	@RequestMapping(value="/comunity", method=RequestMethod.POST, produces="text/plain;charset=UTF-8")
	public ResponseEntity<String> comunityAjax(MultipartFile file) throws Exception {
		
		logger.info("comunityName : " + file.getOriginalFilename()); // 파일명
		return new ResponseEntity<String>(UploadFileUtils.uploadFile(comunityPath, file.getOriginalFilename(), file.getBytes()), HttpStatus.CREATED);
	}
	
	// comu-image 표시맵핑
	@ResponseBody
	@RequestMapping("/displayComunity") 
	public ResponseEntity<byte[]> displayComunity(String fileName) throws Exception {
		// 서버의 파일을 다운로드하기 위한 스트림
		InputStream in = null; // java.io
		ResponseEntity<byte[]> entity = null;
		
		logger.info("DisplayComunity FILE NAME : " + fileName);
		
		try {
			// 확장자를 추출하여 formatName에 저장
			String formatName = fileName.substring(fileName.lastIndexOf(".")+1);
			
			// 추출한 확장자를 MediaUtils클래스에서  이미지파일여부를 검사하고 리턴받아 mType에 저장
			MediaType mType = MediaUtils.getMediaType(formatName);
			
			// 헤더 구성 객체(외부에서 데이터를 주고받을 때에는 header와 body를 구성해야하기 때문에)
			HttpHeaders headers = new HttpHeaders();
			
			 // InputStream 생성
			in = new FileInputStream(comunityPath+fileName);
			
			if(mType != null) { // 이미지 파일일때 
				headers.setContentType(mType);
			} else { // 이미지파일이 아닐때
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
		
	// comu-image 삭제맵핑
	@ResponseBody
	@RequestMapping(value="/deleteComunity", method=RequestMethod.POST)
	public ResponseEntity<String> deleteComunity(String fileName) {
		
		logger.info("deleteComunity : " + fileName);
		
		// 파일의 확장자 추출
		String formatName = fileName.substring(fileName.lastIndexOf(".")+1);
		
		// 이미지 파일 여부 검사
		MediaType mType = MediaUtils.getMediaType(formatName);
		
		// 이미지의 경우(썸네일 + 원본파일 삭제), 이미지가 아니면 원본파일만 삭제
        // 이미지 파일이면
		if(mType != null) {
			String che = "/" + fileName.substring(3);
			// 썸네일 이미지 삭제
			new File(comunityPath + (che).replace('/', File.separatorChar)).delete();
		} 
		// 원본 파일 삭제
		
		new File(comunityPath + fileName.replace('/', File.separatorChar)).delete();
		
		// 데이터와 http 상태 코드 전송
		return new ResponseEntity<String>("deleted", HttpStatus.OK);
		
	}
	
	@ResponseBody
	@RequestMapping(value="comuReplyCommand", method = RequestMethod.POST)
	public Map<String, Object> comuReplyCommand(int code, int replyno, int type, String text, HttpSession session) throws Exception {
		
		String user = (String)session.getAttribute("mem");
		Map<String, Object> map = service.comuReplyCommand(code, replyno, type, text, user);
		
		return map;
		
	}
	

	// qna 리스트
	@RequestMapping(value="/qnaList", method = RequestMethod.GET)
	public String QnAList() {
		
		return "comunityBorad/qnaList";
	}
	
}
