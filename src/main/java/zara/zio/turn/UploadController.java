package zara.zio.turn;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Comparator;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.icafe4j.image.gif.GIFTweaker;
import com.icafe4j.util.FileUtils;

import zara.zio.turn.util.MediaUtils;
import zara.zio.turn.util.UploadFileUtils;

@Controller
public class UploadController {
	private static final Logger logger = LoggerFactory.getLogger(UploadController.class);

	@Resource(name = "gifPath")
	private String gifPath;
	@Resource(name = "resultgifPath")
	private String resultgifPath;
	

	@RequestMapping(value = "layersupuploadForm", method = RequestMethod.GET)
	public void uploadForm() {
		// logger.debug("uploadForm Requested");
		// logger.info("uploadForm Requested");
		// logger.warn("uploadForm Requested");
		// logger.error("uploadForm Requested");
	}

	
	@RequestMapping(value = "layersupupload", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public ResponseEntity<String> upload(MultipartFile file) throws Exception {
		logger.info("originalName:" + file.getOriginalFilename());
		logger.info("size:" + file.getSize());
		logger.info("contentType:" + file.getContentType());
		logger.info("server FileName:" + file.getName());

		// String savedName = uploadFile(file.getOriginalFilename(),
		// file.getBytes());
		String savedName = UploadFileUtils.uploadFile(gifPath, file.getOriginalFilename(), file.getBytes());
		
		System.out.println("what?: " + savedName);
		return new ResponseEntity<String>(savedName, HttpStatus.CREATED);
	}

	@ResponseBody
	@RequestMapping("/displayGifFile") 
	public ResponseEntity<byte[]> displayGifFile(String fileName) throws Exception {
	
		InputStream in = null; // java.io
		ResponseEntity<byte[]> entity = null;
		
		logger.info("Display FILE NAME : " + fileName);
		
		try {
		
			String formatName = fileName.substring(fileName.lastIndexOf(".")+1);
			
			MediaType mType = MediaUtils.getMediaType(formatName);
			
			HttpHeaders headers = new HttpHeaders();
			
			in = new FileInputStream(gifPath+fileName);
			
			if(mType != null) { 
				headers.setContentType(mType);
			} else { 
				fileName = fileName.substring(fileName.indexOf("_")+1);
				
				headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
				
				headers.add("Content-Disposition", "attachment; filename=\"" + 
					new String(fileName.getBytes("UTF-8"), "ISO-8859-1")+"\""); 
				//headers.add("Content-Disposition", "attachment; filename='"+fileName+"'");
			}
			
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED); 
				
		} catch(Exception e) {
			e.printStackTrace();
			
			// HTTP占쎄맒占쎄묶 �굜遺얜굡()
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		} finally {
			in.close();
		}
		return entity;
	}
	@ResponseBody
	@RequestMapping("/resultgifFile") 
	public ResponseEntity<byte[]> resultgifFile(String fileName) throws Exception {
		
		InputStream in = null; // java.io
		ResponseEntity<byte[]> entity = null;
		
		logger.info("Display FILE NAME : " + fileName);
		
		try {
			
			String formatName = fileName.substring(fileName.lastIndexOf(".")+1);
			
			MediaType mType = MediaUtils.getMediaType(formatName);
			
			HttpHeaders headers = new HttpHeaders();
			
			 
			in = new FileInputStream(resultgifPath+"/"+fileName);
			
			if(mType != null) { 
				headers.setContentType(mType);
			} else { 
				fileName = fileName.substring(fileName.indexOf("_")+1);
				
				
				headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
				
				headers.add("Content-Disposition", "attachment; filename=\"" + 
					new String(fileName.getBytes("UTF-8"), "ISO-8859-1")+"\""); 
				//headers.add("Content-Disposition", "attachment; filename='"+fileName+"'");
			}
			
			
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED); 
				
		} catch(Exception e) {
			e.printStackTrace();
			
			
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		} finally {
			in.close();
		}
		return entity;
	}
	
	@RequestMapping(value = "layersupuploadAjax", method = RequestMethod.GET)
	public void uploadAjax() {

	}

	@RequestMapping(value = "layersupuploadAjax", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public ResponseEntity<String> uploadAjax(MultipartFile file, MultipartFile aa) throws Exception {
		System.out.println(aa);
		
		logger.info("originalName:" + file.getOriginalFilename());
		logger.info("size:" + file.getSize());
		logger.info("contentType:" + file.getContentType());
		String savedName = UploadFileUtils.uploadFile(gifPath, file.getOriginalFilename(), file.getBytes());

		return new ResponseEntity<String>(savedName, HttpStatus.CREATED);
	}

	@ResponseBody 
	@RequestMapping("layersupdisplayFile")
	public ResponseEntity<byte[]> displayFile(String fileName) throws Exception {

		ResponseEntity<byte[]> entity = null;

		String ext = fileName.substring(fileName.lastIndexOf(".") + 1);

		MediaType mediaType = MediaUtils.getMediaType(ext);
		InputStream in = null;
		InputStream on = null;

		logger.info("File Name : " + fileName);
		System.out.println(ext);

		HttpHeaders headers = new HttpHeaders();
		
		if(mediaType == MediaType.IMAGE_GIF) {	
			String filePath = gifPath.substring(0, 6);
			filePath = filePath + "/gif";
			System.out.println(filePath);
			
			try {
				on = new FileInputStream(filePath + fileName);
				if (mediaType != null) {
					headers.setContentType(mediaType);
				} else {
					fileName = fileName.substring(fileName.indexOf("_") + 1);
					headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
					String fN = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
					headers.add("Content-Dispostion", "attachment; filename = \"" + fN + "\"");
				}
				
				byte[] data = IOUtils.toByteArray(on);
				entity = new ResponseEntity<byte[]>(data, headers, HttpStatus.CREATED);
			} catch (Exception e) {
				e.printStackTrace();
				entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
			} finally {
				on.close();
			}
		} else{
			try {
				System.out.println("�뜝�룞�삕�뜝�룞�삕�꺗�뜝占�?");
				in = new FileInputStream(gifPath + fileName);
				if (mediaType != null) {
					headers.setContentType(mediaType);
				} else {
					fileName = fileName.substring(fileName.indexOf("_") + 1);
					headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
					String fN = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
					headers.add("Content-Dispostion", "attachment; filename = \"" + fN + "\"");
				}
				byte[] data = IOUtils.toByteArray(in);
				entity = new ResponseEntity<byte[]>(data, headers, HttpStatus.CREATED);
			} catch (Exception e) {
				e.printStackTrace();
				entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
			} finally {
				in.close();
			}
		}

		return entity;
	}
	
	@ResponseBody
	@RequestMapping(value = "layersupmakeGif", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public ResponseEntity<String> makeGif() throws Exception {
		
		File Gfolder = new File(resultgifPath);
		if (Gfolder.exists() == false) {
			Gfolder.mkdirs();
		}
		String savedName = System.currentTimeMillis() +"Make.gif";
		FileOutputStream fout = new FileOutputStream(resultgifPath+"/" + savedName);

		
		File[] files = FileUtils.listFilesMatching(new File(gifPath), "s_.*.jpg");
		
		
		Arrays.sort(files, new ModifiedDate());
		BufferedImage[] images = new BufferedImage[files.length];
		int[] delays = new int[images.length];

		for (int i = 0; i < files.length; i++) {
			FileInputStream fin = new FileInputStream(files[i]);
			BufferedImage image = javax.imageio.ImageIO.read(fin);
			images[i] = image;
			delays[i] = 200;
			fin.close();
		}

		GIFTweaker.writeAnimatedGIF(images, delays, fout);
		fout.close();
		
	
		System.out.println("gif: " + savedName);
		return new ResponseEntity<String>(savedName, HttpStatus.CREATED);
	}

	@ResponseBody
	@RequestMapping(value = "layersupdeleteFile", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteFile(@RequestBody String fileName) throws Exception {
		logger.info("delete file name: " + fileName);
		String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
		
		MediaType mType = MediaUtils.getMediaType(ext); 

		if (mType != null) { // image file �뜝�떛�씛�삕�뜝占� �뜝�떎諭꾩삕
			
//			folderPath = folderPath.replaceAll("%2F", "/");
			String orgName = fileName.substring(22);
//			orgName = orgName.replaceAll("thumbNail_", "");
			System.out.println(orgName);
			// String orgName = fileName.substring(12 + "thumbNail_".length());
			File orgImgFile = new File(gifPath + "/" + orgName);
			System.out.println(orgImgFile);
			orgImgFile.delete();
		}

		
//		folderPath = folderPath.replaceAll("%2F", "/");
		String orgName = fileName.substring(12);
		System.out.println(orgName);
		File orgFile = new File(gifPath + "/" + orgName);
		System.out.println(orgFile);
		orgFile.delete();
		ResponseEntity<String> entity = new ResponseEntity<String>("deleted", HttpStatus.OK);
		System.out.println("�뜝�룞�삕");
		return entity;
	}

	@ResponseBody
	@RequestMapping(value = "layersupAlldelete", method = RequestMethod.POST)
	public String Alldelete() throws Exception {
		File file = new File(gifPath); 
		File[] tempFile = file.listFiles();
		if (tempFile.length > 0) {
			for (int i = 0; i < tempFile.length; i++) {
				if (tempFile[i].isFile()) {
					tempFile[i].delete();
				} else { // �뜝�룞�삕�뜝�룞�삕�뜥�뜝占�
					Alldelete();
				}
				tempFile[i].delete();
			}
			file.delete();
		}
		return "fin";
	}

	// Arrays.sort()

	class ModifiedDate implements Comparator<File> {

		public int compare(File f1, File f2) {
			
			if (f1.lastModified() > f2.lastModified())
				return 1;

			if (f1.lastModified() == f2.lastModified())
				return 0;

			return -1;
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "layersupfCheck", method = RequestMethod.POST)
	public void Check() throws Exception{
		File Path = new File(gifPath);
		if(Path.exists() == false){
			Path.mkdir();
		}else {
			String localPath = gifPath.substring(5);
			System.out.println(localPath);
			Alldelete();
			Path.mkdir();
		}
	}
}
