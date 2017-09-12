package zara.zio.turn.util;

import java.util.ArrayList;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

public class GsonParserUtils {
	
	public static String parser(Double lng, Double lat) {
		
		RestTemplate rest = new RestTemplate();
		String uri = "https://openapi.naver.com/v1/map/reversegeocode?encoding=utf-8&coordType=latlng&query=" + lng + "," + lat;
		HttpHeaders headers = new HttpHeaders();
		
		headers.set("Host", "openapi.naver.com");
		headers.set("User-Agent", "curl/7.43.0");
		headers.set("Accept", "*/*");
		headers.set("Content-Type", "application/json");
		headers.set("X-Naver-Client-Id", "Ao5To6qq05xL8fmhSOTK");
		headers.set("X-Naver-Client-Secret", "u7l9GNVOBl");
		
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		ResponseEntity<String> result = rest.exchange(uri, HttpMethod.GET, entity, String.class);
		String pasing = result.getBody();
		
		Gson gson = new Gson();
		parserA datas = gson.fromJson(pasing, parserA.class);
		String outData = "";
		int cnt = 0;
		
		for(parserC mo : datas.result.items) {
			if(cnt == 1) {
				break;
			}
			outData += mo.addrdetail.sido + " ";
			outData += mo.addrdetail.sigugun + " ";
			outData += mo.addrdetail.dongmyun;
			cnt++;
		}
		
		return outData;
		
	}
	
	class parserA {
		parserB result;
	}
	
	class parserB {
		ArrayList<parserC> items;
	}
	
	class parserC {
		String address;
		parserD addrdetail;
	}
	
	class parserD {
		String country;
		String sido;
		String sigugun;
		String dongmyun;
		String rest;
	}
	
//	{
//		"result": {
//			"total": 3,
//			"userquery": "127.1052133,37.3595316",
//			"items": [
//				{
//					"address": "경기도 성남시 분당구 정자동 178-1",
//					"addrdetail": {
//						"country": "대한민국",
//						"sido": "경기도",
//						"sigugun": "성남시 분당구",
//						"dongmyun": "정자동",
//						"rest": "178-1"
//					},
//					"isAdmAddress": false,
//					"isRoadAddress": false,
//					"point": {
//						"x": 127.1052208,
//						"y": 37.3595122
//					}
//				},
//				{
//					"address": "경기도 성남시 분당구 불정로 6 그린팩토리",
//					"addrdetail": {
//						"country": "대한민국",
//						"sido": "경기도",
//						"sigugun": "성남시 분당구",
//						"dongmyun": "불정로",
//						"rest": "6 그린팩토리"
//					},
//					"isAdmAddress": false,
//					"isRoadAddress": true,
//					"point": {
//						"x": 127.1052133,
//						"y": 37.3595316
//					}
//				}
//			]
//		}
//	}
	
}
