package zara.zio.turn.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;

public class UploadFileUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(UploadFileUtils.class);
	
	// 업로드 기능의 설계 
	public static String uploadFile(String uploadPath, 
			String originalName, byte[] fileData) throws Exception {
		// uploadPath : 파일의 저장경로
		// originalName : 원본파일의 이름
		// fileData : 파일 데이터 byte[] 
		
		// UUID 발급 
		UUID uid = UUID.randomUUID();
		
		// 저장할 파일명 = UUID + 원본이름
		String saveName = uid.toString() + "_" + originalName;
		
		// 파일 경로(기존의 업로드경로+날짜별경로), 파일명을 받아 파일 객체 생성
		File target = new File(uploadPath, saveName);
		
		 // 임시 디렉토리에 업로드된 파일을 지정된 디렉토리로 복사
		FileCopyUtils.copy(fileData, target);
		
		// 썸네일을 생성하기 위한 파일의 확장자 검사
        // 파일명이 aaa.bbb.ccc.jpg일 경우 마지막 마침표를 찾기 위해
		String formatName = originalName.substring(originalName.lastIndexOf(".")+1);
		String uploadedFileName = null;
		
		
		 // 이미지 파일은 썸네일 사용
		if(MediaUtils.getMediaType(formatName) != null) {
			// 썸네일생성 
			
			uploadedFileName = makeThumbnail(uploadPath, saveName);
			
		} else { // jpg png gif 클라이언트에서 걸러져서 사실상 안씀
			// 나머지 아이콘생성 
			uploadedFileName = makeIcon(uploadPath, saveName);
		}
		
		return uploadedFileName;
	}
	
	// 썸네일 생성 
	private static String makeThumbnail(String uploadPath, String fileName) throws Exception{
		// 이미지를 읽어들이기 위한 버퍼
		BufferedImage sourceImg = ImageIO.read(new File(uploadPath, fileName));
		// 100 픽셀단위 썸네일 생성
		
		BufferedImage destImg = Scalr.resize(sourceImg, 600, null, null);
//		Scalr.resize(sourceImg, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_HEIGHT, 100);
		
		// 썸네일의 이름생성 "s_"를 붙임
		String thumbnailName = uploadPath + File.separator + "s_" + fileName;
		
		File newFile = new File(thumbnailName);
		
		String formatName = fileName.substring(fileName.lastIndexOf(".")+1);
		// 썸네일 생성
		ImageIO.write(destImg, formatName.toUpperCase(), newFile);
		
		// 썸네일의 이름을 리턴 
		return thumbnailName.substring(uploadPath.length()).replace(File.separatorChar, '/');
	}
	
	// 아이콘 생성 
	private static String makeIcon(String uploadPath, String fileName) throws Exception {
		// 아이콘의 이름
		String iconName = uploadPath + File.separator + fileName;
		
		// 아이콘 이름을 리턴
        // File.separatorChar : 디렉토리 구분자
        // 윈도우 \ , 유닉스(리눅스) /  
		
		System.out.println("icon");
		return iconName.substring(uploadPath.length()).replace(File.separatorChar, '/');
	}
	
}


/*
 * 
package zara.zio.turn.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;

public class UploadFileUtils {
	
	private static final Logger logger =
			LoggerFactory.getLogger(UploadFileUtils.class);
	
	// 업로드 기능의 설계 
	public static String uploadFile(String uploadPath, 
			String originalName, byte[] fileData) throws Exception {
		// uploadPath : 파일의 저장경로
		// originalName : 원본파일의 이름
		// fileData : 파일 데이터 byte[] 
		
		// UUID 발급 
		UUID uid = UUID.randomUUID();
		
		// 저장할 파일명 = UUID + 원본이름
		String saveName = uid.toString() + "_" + originalName;
		
		// 업로드할 디렉토리(날짜별 폴더) 생성 
		String savePath = calcPath(uploadPath);
		
		// 파일 경로(기존의 업로드경로+날짜별경로), 파일명을 받아 파일 객체 생성
		File target = new File(uploadPath + savePath, saveName);
		
		 // 임시 디렉토리에 업로드된 파일을 지정된 디렉토리로 복사
		FileCopyUtils.copy(fileData, target);
		
		// 썸네일을 생성하기 위한 파일의 확장자 검사
        // 파일명이 aaa.bbb.ccc.jpg일 경우 마지막 마침표를 찾기 위해
		String formatName = originalName.substring(originalName.lastIndexOf(".")+1);
		String uploadedFileName = null;
		
		 // 이미지 파일은 썸네일 사용
		if(MediaUtils.getMediaType(formatName) != null) {
			// 썸네일생성 
			uploadedFileName = makeThumbnail(uploadPath, savePath, saveName);
		} else {
			// 나머지 아이콘생성 
			uploadedFileName = makeIcon(uploadPath, savePath, saveName);
		}
		
		return uploadedFileName;
	}
	
	
	// 날짜 생성처리 
	private static String calcPath(String uploadPath) {
		Calendar cal = Calendar.getInstance(); // 캘린더 함수
		
		// File.separator : 디렉토리 구분자(\\)
		
		String yearPath = File.separator+cal.get(Calendar.YEAR); // 연도, ex) \\2017 
		String monthPath = yearPath + File.separator +
				new DecimalFormat("00").format(cal.get(Calendar.MONTH)+1); // 월, ex) \\2017\\03
		String datePath = monthPath + File.separator + 
				new DecimalFormat("00").format(cal.get(Calendar.DATE)); // 날짜, ex) \\2017\\03\\01
		 
		// 디렉토리 폴더 생성 메서드 호출
		makeDir(uploadPath, yearPath, monthPath, datePath);
		logger.info(datePath);
		return datePath;
	}
	
	// 디렉토리 폴더 생성 
	private static void makeDir(String uploadPath, String ...paths) {
		// 디렉토리가 존재할때. 
		if(new File(paths[paths.length-1]).exists()) {
			return;
		}
		
		// 디렉토리가 존재하지 않을때..
		for(String path : paths) {
			File dirPath = new File(uploadPath + path);
			// 존재않으면 
			if(! dirPath.exists()) {
				dirPath.mkdir();
			}
		}
		
	}
	
	// 썸네일 생성 
	private static String makeThumbnail(String uploadPath, String path, String fileName) throws Exception{
		// 이미지를 읽어들이기 위한 버퍼
		BufferedImage sourceImg = 
				ImageIO.read(new File(uploadPath + path, fileName));
		// 100 픽셀단위 썸네일 생성
		BufferedImage destImg = 
				Scalr.resize(sourceImg, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_HEIGHT, 100);
		// 썸네일의 이름생성 "s_"를 붙임
		String thumbnailName = 
				uploadPath + path + File.separator + "s_" + fileName;
		File newFile = new File(thumbnailName);
		String formatName = 
				fileName.substring(fileName.lastIndexOf(".")+1);
		// 썸네일 생성
		ImageIO.write(destImg, formatName.toUpperCase(), newFile);
		
		// 썸네일의 이름을 리턴 
		return thumbnailName.substring(uploadPath.length()).replace(File.separatorChar, '/');
	}
	
	// 아이콘 생성 
	private static String makeIcon(String uploadPath, String path, String fileName) throws Exception {
		// 아이콘의 이름
		String iconName = uploadPath + path + File.separator + fileName;
		
		// 아이콘 이름을 리턴
        // File.separatorChar : 디렉토리 구분자
        // 윈도우 \ , 유닉스(리눅스) /  
		return iconName.substring(uploadPath.length()).replace(File.separatorChar, '/');
	}
	
	
}
 * 
 */
