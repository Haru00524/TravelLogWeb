package zara.zio.turn;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import zara.zio.turn.persistence.LayersService;
import zara.zio.turn.util.MediaUtils;


@Controller
public class LayersUpController {
	
	@Resource(name = "gifPath")
	private String gifPath;
	
	@Inject 
	private LayersService service ;
	
	@RequestMapping(value="/layersUp", method = RequestMethod.GET)
	public String layersUp(HttpSession session, RedirectAttributes rttr) {
		
		String username = (String)session.getAttribute("mem");
		String usergrant = (String) session.getAttribute("info");
		
		if(username == null && usergrant == null) {
			rttr.addAttribute("board","3");
			return "redirect:login";
		}
		
		return "LayersPage/layers";
		
	}
	
	@ResponseBody
	@RequestMapping(value="/gif_image_list", method = RequestMethod.POST)
	public List<String> gif_image_list(HttpSession session) throws Exception{
		
		String mem = (String) session.getAttribute("mem") ;
		
		List<String> list = service.gif_image_list(mem);

		return list;
	}

	
	@ResponseBody
	@RequestMapping(value="/gif_list", method = RequestMethod.POST)
	public String gif_list(String imagesA) throws Exception{

	
		//System.out.println("imagesA : "  + imagesA);
		//String fileNm = imagesA.substring(imagesA.lastIndexOf("/") + 1) ;
		//String imagePath = "C:/saveImage/" + fileNm ;
		//System.out.println("imagesA : "  + imagePath);
		
		BufferedImage image = null ;
		
		image = ImageIO.read(new URL(imagesA)) ;
		
	
		int width = image.getWidth();
		int height = image.getHeight() ;
		BufferedImageOp op = createImageOp();

		BufferedImage bufferedImage =  new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR) ;
			
		bufferedImage.createGraphics().drawImage(image, op, 0,  0) ;
		String fileNm = imagesA.substring(imagesA.lastIndexOf("/") + 1) ;
		if(!fileNm.matches(".*s_.*")){
			fileNm = "s_" + fileNm ;
		}
		System.out.println("filenNm :" + fileNm);
		System.out.println("path"+gifPath);
		// 생성
		
		if(fileNm.contains(".png")) {
			ImageIO.write(bufferedImage, "png", new File(gifPath+"/"+ fileNm)) ;
		}else{
			ImageIO.write(bufferedImage, "jpg", new File(gifPath+"/"+ fileNm)) ;
		}
			
		String imagesB = "/"+fileNm;
			
		return imagesB;

	}

	private BufferedImageOp createImageOp() {
		// TODO Auto-generated method stub
		return null;
	}

	@ResponseBody
	@RequestMapping("/displaygifFile") 
	public ResponseEntity<byte[]> displaygifFile(String fileName) throws Exception {
		// 서버의 파일을 다운로드하기 위한 스트림
		InputStream in = null; // java.io
		ResponseEntity<byte[]> entity = null;
		
		
		try {
			// 확장자를 추출하여 formatName에 저장
			String formatName = fileName.substring(fileName.lastIndexOf(".")+1);
			
			// 추출한 확장자를 MediaUtils클래스에서  이미지파일여부를 검사하고 리턴받아 mType에 저장
			MediaType mType = MediaUtils.getMediaType(formatName);
			
			// 헤더 구성 객체(외부에서 데이터를 주고받을 때에는 header와 body를 구성해야하기 때문에)
			HttpHeaders headers = new HttpHeaders();
			
			 // InputStream 생성
			in = new FileInputStream(gifPath+fileName);
			
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
	

}
