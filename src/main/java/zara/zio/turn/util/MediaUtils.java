package zara.zio.turn.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;

public class MediaUtils {
	
	private static Map<String, MediaType> mediaMap;
	
	static {
		mediaMap = new HashMap<String, MediaType>();
		mediaMap.put("JPG", MediaType.IMAGE_JPEG);
		mediaMap.put("JPEG", MediaType.IMAGE_JPEG);
		mediaMap.put("GIF", MediaType.IMAGE_GIF);
		mediaMap.put("PNG", MediaType.IMAGE_PNG);
	}
	
	public static MediaType getMediaType(String type) {
		return mediaMap.get(type.toUpperCase());
	}
	
	// UploadFileUtils클래스에서 추출한 파일의 확장자명을 대문자로 변환하고, 
	// mediaMap에 담긴 값을 호출한 뒤 리턴.
	// 3가지(jpg,gif,png) 이미지 파일일 경우에는 값이 복사되어 리턴되지만, 아닐경우에는 null상태로 리턴.
	/*
	 *  MediaUtils은 확장자를 가지고 이미지 타입인지를 판단해 주는역할
	 *  별도의 클래스로 구성한이유는 브라우저에서 파일을 다운로드 할것인지 보여줄것인지 결정하기 위함. 
	 */
}
