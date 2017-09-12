package zara.zio.turn;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils; // 실제 데이터를 읽는 라이브러리 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import zara.zio.turn.domain.Pagination;
import zara.zio.turn.domain.PlaceVO;
import zara.zio.turn.persistence.PlaceService;
import zara.zio.turn.util.MediaUtils;
import zara.zio.turn.util.UploadFileUtils;

@Controller
public class AdminPlaceController {
	
	private static final Logger logger =
			LoggerFactory.getLogger(AdminPlaceController.class);
	
	@Inject
	private PlaceService service;
	
	// 경로 지정 path
	@Resource(name="uploadPath")
	private String uploadPath;
	
	@RequestMapping(value="/uploadSet", method=RequestMethod.POST) 
	public String uploadSet(@RequestParam(value="no", defaultValue="-1") int no, String [] cookieFile, PlaceVO placeVO, 
		@ModelAttribute Pagination pagination, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception{
		String [] maps = request.getParameterValues("files");
		
		String text = placeVO.getPlace_content().replaceAll("(\r\n|\r|\n|\n\r)", "<br>");
		placeVO.setPlace_content(text);
				
		if(cookieFile != null) {
			service.pimg_delete(no); // db정보 삭제
			for(int i=0; i<cookieFile.length; i++) {
				deleteFile(cookieFile[i]); // 업로드 경로삭제 
			}
		}
		
		service.place_update(placeVO, no); // 정보업데이트
		System.out.println(placeVO);
		
		List<PlaceVO> list = new ArrayList<PlaceVO>();
		int subr = 0;
		
		for(int i=0; i<maps.length; i++) {
			PlaceVO place = new PlaceVO();
			subr = maps[i].indexOf("_") + 1; // 문자열자르게
			
			place.setPlace_code(no);
			place.setImg_code(i);
			place.setPlace_img(maps[i]);
			place.setFile_name(maps[i].substring(subr));
			System.out.println("정보 : "+ maps[i].substring(subr));
			
			list.add(place);
		}
		service.pimg_delete(no); // db정보 삭제
		service.img_insert(list); // 이미지 재전송 
		
		// 작성한 뷰로 이동 
		PlaceVO place = service.read(no); // 자바빈 객체를 반환
		list = service.readimg(no);
		model.addAttribute("place", place); // 해당정보반환 
		model.addAttribute("list", list);// 이미지 리스트 반환
		
		return "adminPage/uploadRead";
	}
	
	@RequestMapping(value="/uploadSet", method=RequestMethod.GET)
	public String uploadSet(@RequestParam(value="no", defaultValue="-1") int no, @ModelAttribute Pagination pagination, Model model) throws Exception {
		
		PlaceVO place = service.read(no); // 자바빈 객체를 반환
		List<PlaceVO> list = service.readimg(no);
		model.addAttribute("place", place);
		model.addAttribute("list", list);
		
		return "adminPage/uploadSet";
	}
	
	@RequestMapping(value="/uploadDel", method=RequestMethod.GET)
	public String uploadDel(String [] imgDel, int no) throws Exception{
		
		if(imgDel != null) { // 이미지값이 있을때 // if조건이 없으면 exception오류    
			for(int i=0; i<imgDel.length; i++) { 
				deleteFile(imgDel[i]); // 폴더상의 이미지를 제거
			}
		} // 이미지값이 없을때 건너뜀 
		// 이미지 있으나 없으나 상관없이 제거
		service.placeAll_delete(no);
		
		return "redirect:uploadList";
	}
	
	@RequestMapping(value="/uploadRead", method=RequestMethod.GET)
	public String uploadRead(@RequestParam(value="no", defaultValue="-1") int no, @ModelAttribute Pagination pagination, Model model) throws Exception {
		// (read?bno=?? 라는 주소로 접근한다.) 
		PlaceVO place = service.read(no); // 자바빈 객체를 반환
		List<PlaceVO> list = service.readimg(no);
		model.addAttribute("place", place); // 해당정보반환 
		model.addAttribute("list", list);// 이미지 리스트 반환
		return "adminPage/uploadRead";
		
	}
	
	// 업로드된 페이지 리스트 맵핑
	@RequestMapping(value="/uploadList", method=RequestMethod.GET)
	public String uploadList(Model model, Pagination pagination) throws Exception {
		
		System.out.println(pagination);
		
		List<PlaceVO> list = service.listPage(pagination);
		
		model.addAttribute("list", list);
		model.addAttribute("pagination", pagination);
		
		int totalCount = service.getTotalCount();
		
		System.out.println("totalCount [totalCount=" + totalCount + "]"); // 전체카운트
		
		pagination.setTotalCount(totalCount); // pagination 계산
		
		return "adminPage/uploadList";
	}
	
	// Ajax업로드 페이지 맵핑
	@RequestMapping(value="/upload", method=RequestMethod.GET)
	public String uploadAjax() {
		return "adminPage/upload";
//		return "adminPage/handlebarTemple";
	}
	
	// Ajax업로드 처리 매핑
    // 파일의 한글처리 : produces="text/plain;charset=utf-8"
	@RequestMapping(value="/upload", method=RequestMethod.POST, produces="text/plain;charset=UTF-8")
	public ResponseEntity<String> uploadAjax(MultipartFile file) throws Exception {
		
		logger.info("OriginalName : " + file.getOriginalFilename()); // 파일이름 
//		logger.info("Size : " + file.getSize()); // 파일사이즈 
//		logger.info("ContentType : " + file.getContentType()); // 파일타입 jpg, png
		
		return new ResponseEntity<String>(UploadFileUtils.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes()), HttpStatus.CREATED);
	}
	
	
	// 이미지 표시 맵핑
	@ResponseBody
	@RequestMapping("/displayFile") 
	public ResponseEntity<byte[]> displayFile(String fileName) throws Exception {
		// 서버의 파일을 다운로드하기 위한 스트림
		InputStream in = null; // java.io
		ResponseEntity<byte[]> entity = null;
		
		logger.info("Display FILE NAME : " + fileName);
		
		try {
			// 확장자를 추출하여 formatName에 저장
			String formatName = fileName.substring(fileName.lastIndexOf(".")+1);
			
			// 추출한 확장자를 MediaUtils클래스에서  이미지파일여부를 검사하고 리턴받아 mType에 저장
			MediaType mType = MediaUtils.getMediaType(formatName);
			
			// 헤더 구성 객체(외부에서 데이터를 주고받을 때에는 header와 body를 구성해야하기 때문에)
			HttpHeaders headers = new HttpHeaders();
			
			 // InputStream 생성
			in = new FileInputStream(uploadPath+fileName);
			
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
	
	// 첨부파일 삭제 맵핑 
	@ResponseBody
	@RequestMapping(value="/deleteFile", method=RequestMethod.POST)
	public ResponseEntity<String> deleteFile(String fileName) {
		
		logger.info("delete file : " + fileName);
		
		// 파일의 확장자 추출
		String formatName = fileName.substring(fileName.lastIndexOf(".")+1);
		
		// 이미지 파일 여부 검사
		MediaType mType = MediaUtils.getMediaType(formatName);
		
		// 이미지의 경우(썸네일 + 원본파일 삭제), 이미지가 아니면 원본파일만 삭제
        // 이미지 파일이면
		if(mType != null) {
			String che = "/" + fileName.substring(3);
			// 썸네일 이미지 삭제
			
			new File(uploadPath + (che).replace('/', File.separatorChar)).delete();
		} 
		// 원본 파일 삭제

		new File(uploadPath + fileName.replace('/', File.separatorChar)).delete();
		
		// 데이터와 http 상태 코드 전송
		return new ResponseEntity<String>("deleted", HttpStatus.OK);
		
	}
	
	
	@RequestMapping(value="/uploadForm" , method=RequestMethod.POST) 
	public String uploadForm(PlaceVO placeVO, HttpServletRequest request, HttpServletResponse response) throws Exception{
		String [] maps = request.getParameterValues("files");
		
		String text = placeVO.getPlace_content().replaceAll("(\r\n|\r|\n|\n\r)", "<br>");
		placeVO.setPlace_content(text);
		
		service.place_insert(placeVO); // 정보 전송    
		int max = service.place_max();// 맥스값을 받아옴
		
		List<PlaceVO> list = new ArrayList<PlaceVO>();
		int subr = 0;
		
		for(int i=0; i<maps.length; i++) {
			PlaceVO place = new PlaceVO();
			subr = maps[i].indexOf("_") + 1; // 문자열자르게
			
			place.setPlace_code(max);
			place.setImg_code(i);
			place.setPlace_img(maps[i]);
			place.setFile_name(maps[i].substring(subr));
			System.out.println("정보 : "+ maps[i].substring(subr));
			
			list.add(place);
		}
		service.img_insert(list); // 이미지 전송 
		
		
		return "redirect:uploadList"; // 리스트로 리턴.
	}

}
