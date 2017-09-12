$(document).ready(function(){
	
	var map;
	var infowindows = [];
	var flightPlanCoordinates = [];
	
	var place = replaceLineParser($("#dataClassA").text()); 
	var jsonPlace = JSON.parse(place);
	var log = replaceLineParser($("#dataClassB").text());
	var jsonlog;
	
	var user = $(".sky-form").attr("data-session"); // 유저정보 
	var totalCount = $(".travelReply").attr("data-total"); // 페이징 전체카운트 ajax 95개  = 변수 
	var pageCount = 10; // 현재페이지 정보 // 상수고정 
	var curentPage = 1; // 현재페이지 // 변수 
	var recordPage = 10; // 보여줄 레코드갯수 // 고정
	var startPage;
	var endPage;
	var prev;
	var next;
	
	var startRecord = getStartRecord(curentPage, recordPage); // 페이지조회정보
	pagination();
	
	console.log(jsonPlace);
	if(log != "") {
		jsonlog = JSON.parse(log);
		console.log(jsonlog);
	}
	
	if(jsonPlace.length != 0) {
		initialize(jsonPlace, jsonlog);
	} else {
		var mapText = "<div class='text-center' style='margin-top: 260px;'><h2>등록된 장소일정이 존재하지않습니다.</h2></div>";
		$("#google_map").append(mapText);
	}
	
	function initialize(jsonPlace, jsonlog) {
		  
		  var lat = jsonPlace[0].place_lat;
		  var lng = jsonPlace[0].place_lng;
		
		  var latLng = new google.maps.LatLng(lat, lng); 
				
		  var mapOptions = { //구글 맵 옵션 설정
		      zoom : 13, //기본 확대율
		      center : latLng, // 지도 중앙 위치
		      scrollwheel : true, //마우스 휠로 확대 축소 사용 여부
		      mapTypeControl : true //맵 타입 컨트롤 사용 여부
		  };
		
		  map = new google.maps.Map(document.getElementById('google_map'), mapOptions); //구글 맵을 사용할 타겟
		
		  for(var i=0; i<jsonPlace.length; i++) {
			  addMarker(jsonPlace[i], 1, 0);
			  flightPlanCoordinates.push(new google.maps.LatLng(jsonPlace[i].place_lat, jsonPlace[i].place_lng));
		  }
		  
		  if(log != "") {
			  for(var j=0; j<jsonlog.length; j++) {
				  addMarker(jsonlog[j], 0, j);
			  }
		  }
		  
		  var flightPath = new google.maps.Polyline({
			    path: flightPlanCoordinates,
			    strokeColor: "#b166f7",
			    strokeOpacity: 1.0,
			    strokeWeight: 3
		  });

		  flightPath.setMap(map);  
		
//		  google.maps.event.trigger(map, "resize");
		
	}
	
	function addMarker(obj, type, status) {
		
		var image;
		var markerRender;
		var copyData;
		
		if(type == 1) { // placeMarker
			copyData = new google.maps.LatLng(obj.place_lat, obj.place_lng);
			image = './resources/img/notebook.png'; //마커 이미지 설정
			markerRender = "<div class='element text-left size-14' style='font-weight:600; width:250px;'>" +  
								"<div><img src='displayFile?fileName=" + obj.place_img +"' style='width:250px; border:1px solid black;'></div>" +
								"<div class='pull-right margin-top-6' style='border:1px solid black; padding:8px;'>" + obj.travel_priority + "</div>" +
								"<div class='margin-top-6'>장소명 : " + obj.place_name + "</div>" + 
								"<div class='margin-top-6'>여행타입 : " + obj.place_type + "</div>" + 
								"<div class='margin-top-6'>여행일 : " + obj.travel_Date + "</div>" + 
								"<div class='margin-top-6'>장소상세 : " + "<a href='placeRead?post=" + obj.place_code + "'>정보보기</a>" + "</div>" + 
								"<div class='margin-top-6'>" + obj.place_address + "</div>" + 
							"</div>";
		}
		
		if(type == 0) { // logMarker
			
			obj.board_date += ""; // 문자열변환 Timestamp match접근
			
			copyData = new google.maps.LatLng(obj.log_latitude, obj.log_longtitude);
			image = './resources/img/placeholder.png';
			markerRender = "<div class='element text-left size-18' style='font-weight:600; width:280px;'>";
							if(obj.file_content != "") {
								markerRender += "<div><img src='displayLogs?fileName=" + obj.file_content + "' style='width:280px; border:1px solid black;'></div>";
							} else {
								
							}
			markerRender += 	"<div class='margin-top-6'>" + 
									"<div class='time-index pull-right'>" + (status+1) + " 번째</div>" + 
									"<img class='thumbnail pull-left' src='displayProfile?fileName=" + obj.user_profile + "' style='width:50px; height:50px; margin:0 15px 5px 0;'>" +
									"<div class='time-id'>" + obj.user_id + "</div>" + 
									"<div class='time-title'>" + obj.board_title + "</div>" + 
								"</div>" + 
								"<div class='size-14' style='clear:both;'>" + 
									"<div>" + obj.board_content + "</div>" + 
									"<div class='margin-top-3'>위치 : " + obj.onAddress + "</div>" + 
									"<ul class='text-left size-14 list-inline list-separator ultop targeting' style='margin-bottom:0px;'>" + 
										"<li>"; 
											if(obj.board_date.match("-")) {
												markerRender += "<i class='fa fa-calendar-check-o'></i><b>" + stringDateParese(obj.board_date) + "</b>";
											} else {
												markerRender += "<i class='fa fa-calendar-check-o'></i><b>" + timestampDateParse(obj.board_date) + "</b>";
											}
			markerRender += 			"</li>" + 
										"<li>" + 
											"<b>Travel-Log</b>" +  
										"</li>";
										if(obj.my_like == 1) {
											markerRender += "<li><i class='fa fa-heart'></i><b>" + obj.like_count +"</b></li>";
										} else {
											markerRender += "<li><i class='fa fa-heart-o'></i><b>" + obj.like_count +"</b></li>";
										}
			markerRender += 		"</ul>" + 
								"</div>" + 
							"</div>";
			
		}
		
		var marker = new google.maps.Marker({ //마커 설정
		      map : map,
		      position : copyData, //마커 위치
		      icon : image //마커 이미지
		});
		
		var infowindow = new google.maps.InfoWindow({
		      content: markerRender
		});
		  
		infowindows.push(infowindow);
		  
		google.maps.event.addListener(marker, 'click', function() {
	  	  	for(var i=0; i<infowindows.length; i++) {
	  	  		infowindows[i].close();
	  	  	}
	  	  	infowindow.open(map,marker);
		});
		
	}
	
	// 날짜 클릭함수
	$(".travelTab").click(function(){
		
		var tab = $(this);
		
		if(tab.hasClass("blueActive")) {
		
			return false;
			
		} else {
			
			$(".travelTab").removeClass("blueActive");
			$(".travelTab").addClass("travel-hover");
			tab.addClass("blueActive");
			tab.removeClass("travel-hover");
			
			infomation(tab.attr("data-num"), tab.attr("data-group"));
			
		}
		
	});
	
	function infomation (nal, group) {
		
		$.ajax({
			type : 'GET',
			url : './changeNal',
			data : {
				selectDate : nal,
				group : group,
			},
			success : function(map) {
				
				console.log(map);
				
				$("#google_map").remove(); // 구글맵 제거 
				
				infowindows = []; // 오픈타겟초기화 
				flightPlanCoordinates = []; // 폴리라인 초기화
				
				// 엘리먼트 생성 and 구글맵 실행
				var maps = "<div id='google_map' style='width:100%; height:600px; border:#ddd 2px solid; margin-top:20px;'></div>";
				$(".tab-content").append(maps);
				if(map.place.length != 0) {
					initialize(map.place, map.log);
				} else {
					var mapText = "<div class='text-center' style='margin-top: 260px;'><h2>등록된 장소일정이 존재하지않습니다.</h2></div>";
					$("#google_map").append(mapText);
				}
				
				// Travel 타임라인 비움
				$(".travel-log-data").empty();
				var Timeline = "";
				
				if(map.log.length != 0) {
					
					for(var i=0; i<map.log.length; i++) {
						
						Timeline =	"<div class='col-md-4 col-sm-4'>" + 
										"<div class='img-hover margin-bottom-30 divTarget' data-board='" + map.log[i].board_code + "' data-index='" + i + "' data-type='1'>" + 
											"<div class='timeline-t'>" + 
												"<div class='padding-10'>" + 
													"<div class='time-index pull-right'>" + (i+1) + " 번째</div>" + 
													"<img class='thumbnail pull-left' src='displayProfile?fileName=" + map.log[i].user_profile + "' style='width:50px; height:50px;'>" + 
													"<div class='time-id'>" + map.log[i].user_id + "</div>" + 
													"<div class='time-title'>" + map.log[i].board_title + "</div>" + 
												"</div>"; 
												if(map.log[i].file_content[0] != '') {
													Timeline += "<div class='images'>" + 
												 					"<img class='img-responsive' src='displayLogs?fileName=" + map.log[i].file_content + "' alt=''>" +  // 라이프로그이미지 
												 				"</div>";
												} else {
													
												}
						Timeline += 			"<div class='padding-10'>" + 
													"<div class='resource' style='clear:both;'>" + 
														"<p>" + map.log[i].board_content + "</p>" + 
														"<ul class='list-inline nomargin hashTagList'>";
															for(var j=0; j<map.log[i].hash_tag_content.length; j++) {
																Timeline += "<li class='hashStyle'><a>" + map.log[i].hash_tag_content[j] + "</a></li>"
															}
						Timeline += 					"</ul>" + 
														"<ul class='text-center size-18 list-inline ultop targeting'>" + 
															"<li>" +  
																"<i class='fa fa-calendar-check-o'></i><b>" + timestampDateParse(map.log[i].board_date) + "</b>" + 
															"</li>" + 
															"<li><b>-</b></li>"
															"<li class='t-log-like' data-code='" + map.log[i].board_code + "'>";
																if(map.log[i].my_like == 1) {
																	Timeline += "<i class='fa fa-heart'></i><b>" + map.log[i].like_count + "</b>";
																} else {
																	Timeline += "<i class='fa fa-heart-o'></i><b>" + map.log[i].like_count + "</b>";
																}
						Timeline += 						"</li>" + 
														"</ul>" + 
													"</div>" + 
												"</div>" + 
	 										"</div>" + 
										"</div>" + 
									"</div>";
						
						$(".travel-log-data").append(Timeline);
						
					}
				} else {
					
					Timeline = "<div class='text-center'>" + 
									"<div class='margin-top-100 margin-bottom-100'><h2>진행된 여행로그 정보가 없습니다.</h2></div>" + 
							   "</div>";
					
					$(".travel-log-data").append(Timeline);
					
				}
				
			}, error:function(){
				alert("에러발생");
			}
		});
		
	}
	
	// 좋아요 태그
	$(".travel-like").on("click", function(){
		
		var like = $(this).children("i").prop("class"); // like 하트정보 
		var target = $(this); // 타겟정보 변경 
		var states; // 상태
		
		if(user == "") {
			alert("로그인후 이용해주십시오.");
			return false;
		}
		
		if(like.match("fa-heart")) {
			states = 0;
		} 
		
		if(like.match("fa-heart-o")) {
			states = 1;
		}
		
		$.ajax({
			type : 'GET',
			url : 'likeConfirm',
			data : { 
				states : states,
				no : target.attr("data-code")
			}, 
			success : function(data) {
				
				if(like.match("fa-heart")) {
					
					target.html("<i class='fa fa-heart-o'></i><div>" + (data) + "</div>");
					
				} 
				
				if(like.match("fa-heart-o")) {
					
					target.html("<i class='fa fa-heart'></i><div>" + (data) + "</div>");
					
				}
				
			}, error:function(){
				alert("에러");
			}
		})
		
	});
	
	
	// 좋아요 태그 2
	$(".travel-log-data").on("click",".t-log-like",function(){
		
		var like = $(this).children("i").prop("class"); // like 하트정보
		var target = $(this);
		var states; // 상태
		
		if(user == "") {
			alert("로그인후 이용해주십시오.");
			return false;
		}
		
		if(like.match("fa-heart")) {
			states = 0;
		} 
		
		if(like.match("fa-heart-o")) {
			states = 1;
		}
		
		$.ajax({
			type : 'GET',
			url : 'likeConfirm',
			data : { 
				states : states,
				no : target.attr("data-code")
			}, 
			success : function(data) {
				
				if(like.match("fa-heart")) {
					
					target.html("<i class='fa fa-heart-o'></i><b>" + (data) + "</b>");
					
				} 
				
				if(like.match("fa-heart-o")) {
					
					target.html("<i class='fa fa-heart'></i><b>" + (data) + "</b>");
					
				}
				
			}, error:function(){
				alert("에러");
			}
		})
		
		
		
	});
	
	
	
	
	// 댓글입력 
	$(".write-buttom").click(function(){
		
		curentPage = 1;
		
		var no = $(this).attr("data-num");
		var text = $(this).prev("textarea").val();
		
		if(user == "") {
			alert("로그인후 이용해주십시오");
			return false;
		}
		
		if(text.length < 5) {
			alert("글자수는 5자 이상 입력가능합니다.")
			return false;
		}
		
		replyCommand(no, text, 1, 0);
		
	});
	
	// 댓글수정
	$(".travelReply").on("click",".reply-buttom", function(){
		
		var buttonName = $(this).text();
		
		if(buttonName == "수정") { 
			
			var no = $(this).attr("data-code");
			var replyno = $(this).attr("data-reply");
			var textarea = $(this).parent().parent().next();
			var text = textarea.text();
			
			textarea.empty();
			
			var addElement = "<textarea class='form-control' maxlength='5000' rows='2' required>" + text + " </textarea>" +
							 "<button class='reply-confirm btn btn-3d btn-reveal btn-black pull-right' style='margin-top:12px;' data-code=" + no + " data-reply='" + replyno + "'>" + 
							  	 "<i class='fa fa-check'></i>" +
							  	 "<span>수정하기</span>" +
						     "</button>";
			
			textarea.append(addElement);
			$(this).text("수정취소");
			
		} else {
			
			var textarea = $(this).parent().parent().next();
			var text = textarea.children("textarea").text();
			
			textarea.empty();
			textarea.text(text);
			$(this).text("수정");
			
		}
		
	});
	
	// 댓글수정 확인
	$(".travelReply").on("click",".reply-confirm", function(){
		
		var replyno = $(this).attr("data-code");
		var no = $(this).attr("data-reply");
		var text = $(this).prev().val();
		
		if(text.length < 5) {
			alert("글자수는 5자 이상 입력가능합니다.")
			return false;
		}
		
		replyCommand(no, text, 2, replyno);
		
	});
	
	// 댓글삭제
	$(".travelReply").on("click",".delete-buttom", function(){
		
		var replyno = $(this).attr("data-code");
		var no = $(this).attr("data-reply");
		
		if(confirm("댓글을 삭제하시겠습니까?")) {
			replyCommand(no, null, 3, replyno);
		}
		
	});
	
	
	
	
	// 댓글커맨드 
	function replyCommand(no, text, replytype, replyno) {
		
		$.ajax({ 
			type : 'POST',
			url : 'replyTravel',
			data : {
				no : no,
				replytype : replytype,
				replyno : replyno,
				text : text
			}, 
			success:function(reply) {
				
				console.log(reply);
				curentPage = 1;
				
				totalCount = reply.totalCount;
				
				$(".travelReply").empty();
				$(".pagingbox").empty();
				
				var replylist = "";
				
				for(var i=0; i<reply.list.length; i++) {
					
					replylist += "<div class='comment-item'>" + 
									// replyinfo
									"<div class='t-reply-info'>" + 
										"<img class='pull-left travel-img' src='displayProfile?fileName=" + reply.list[i].user_profile + "'>" + 
										"<div class='pull-left travel-name'>" + reply.list[i].user_id + "</div>" + 
										"<div class='pull-right travel-day'>" + dateParse(reply.list[i].board_date) + "</div>" +
										"<div class='pull-right'>";
										if(reply.list[i].user_id == user) {
											replylist += "<button class='btn btn-primary btn-xs reply-buttom' style='margin:0px 4px 0px 17px;' data-code='" + reply.list[i].board_code + "' data-reply='" + reply.list[i].reply_code + "'>수정</button>" + 
											 			 "<button class='btn btn-red btn-xs delete-buttom' style='margin:0px 5px 0px 0px;' data-code='" + reply.list[i].board_code + "' data-reply='" + reply.list[i].reply_code + "'>삭제</button>";
										}
					replylist += 		"</div>" + 
									"</div>" + 	
									"<div class='media-body' style='width:800px; word-break:break-all;'>" + reply.list[i].board_content + "</div>" + 
								 "</div>";
				}
				
				$(".travelReply").append(replylist);
				pagination();
				
			}, error:function() {
				alert("ddd");
			}
		})
	}
	
	
	// 페이징 박스
	$(".pagingbox").on("click", '.onlink', function(){
		
		var no = $(".write-buttom").attr("data-num");
		
		// 전역변수에 저장
		curentPage = parseInt($(this).attr("data-page"));
		
		$.ajax({ 
			type : 'GET',
			url : 'replyTravelList',
			data : {
				post : no,
				page : curentPage
			}, 
			success:function(reply) {
				
				totalCount = reply.totalCount;
				
				$(".travelReply").empty();
				$(".pagingbox").empty();
				
				var replylist = "";
				
				for(var i=0; i<reply.list.length; i++) {
					
					replylist += "<div class='comment-item'>" + 
									// replyinfo
									"<div class='t-reply-info'>" + 
										"<img class='pull-left travel-img' src='displayProfile?fileName=" + reply.list[i].user_profile + "'>" + 
										"<div class='pull-left travel-name'>" + reply.list[i].user_id + "</div>" + 
										"<div class='pull-right travel-day'>" + dateParse(reply.list[i].board_date) + "</div>" +
										"<div class='pull-right'>";
										if(reply.list[i].user_id == user) {
											replylist += "<button class='btn btn-primary btn-xs reply-buttom' style='margin:0px 4px 0px 17px;' data-code='" + reply.list[i].board_code + "' data-reply='" + reply.list[i].reply_code + "'>수정</button>" + 
											 			 "<button class='btn btn-red btn-xs delete-buttom' style='margin:0px 5px 0px 0px;' data-code='" + reply.list[i].board_code + "' data-reply='" + reply.list[i].reply_code + "'>삭제</button>";
										}
					replylist += 		"</div>" + 
									"</div>" + 	
									"<div class='media-body' style='width:800px; word-break:break-all;'>" + reply.list[i].board_content + "</div>" + 
								 "</div>";
				}
				
				$(".travelReply").append(replylist);
				pagination();
				
			}, error:function(){
				
			}
		})
		
	});
	
	function pagination() {
		
		calculate();
		
		var paging = "<ul class='pagination pagination-simple'>"; // 페이징생성데이터
		if(prev == true) {
			paging += "<li><a class='onlink' data-page='" + (startPage-1) + "'>" + "&laquo;" + "</a></li>"
		}
		for(var j=startPage; j<=endPage; j++) {
				if(curentPage == j) 
					paging += "<li class='active'><a class='onlink' data-page='" + j + "'>" + j + "</a></li>"
				else 
					paging += "<li><a class='onlink' data-page='" + j + "'>" + j + "</a></li>"
		}
		if(next == true) {
			paging += "<li><a class='onlink' data-page='" + (endPage+1) + "'>" + "&raquo;" + "</a></li>"
		}
		paging += "</ul>";
		$(".pagingbox").append(paging);
		
	}
	
	// limit StartRecord
	function getStartRecord (page,recordPage) {
		return ((page-1)*recordPage);
	}
	
	function calculate() {
		
		/*
		 *  startPage, endPage, prev, next 계산해줌  
		 *  
		 */
		// 엔드 페이지 계산법
		
		endPage = Math.ceil(curentPage/pageCount)*pageCount;

		// 스타트 페이지 계산법
		startPage = endPage-pageCount + 1;
		
		// 끝페이지 계산
		var tempEndPage = Math.ceil(totalCount/recordPage);
		if(endPage > tempEndPage) {
			endPage = tempEndPage;
		}
		
		// <, > 스타트, 엔드 페이지 아이콘
		prev = startPage > 1 ? true:false;
		next = endPage*recordPage < totalCount ? true:false;
	}
	
});

// 문자열 파싱
function stringDateParese(date) {
	
	var year = date.indexOf(" ");
	date = date.substring(0, year);
	var day = date.split("-");
	
	return day[0].substring(2,4) + "." + day[1] + "." + day[2];
	
}

// timestamp 파싱 
function timestampDateParse(data) {
	
	var parseDate = parseInt(data);
	var sysdate = new Date(parseDate);
	var year = sysdate.getFullYear();
	
	var yearData = year + "";
	yearData = yearData.substr(2, 2);
	
	var month = sysdate.getMonth()+1;
	var day = sysdate.getDate();
	return yearData + "." 
			+ (month < 10 ? "0" + month : month) + "."
			+ (day < 10 ? "0" + day : day);
}

// date 파싱
function dateParse(data) {
	var sysdate = new Date(data);
	var year = sysdate.getFullYear();
	
	var yearData = year + "";
	yearData = yearData.substr(2, 2);
	
	var month = sysdate.getMonth()+1;
	var day = sysdate.getDate();
	return yearData + "." 
			+ (month < 10 ? "0" + month : month) + "."
			+ (day < 10 ? "0" + day : day);
}

// 줄바꿈 파싱
function replaceLineParser(value) {
	
	if(value != null && value != "") {
		return value.replace(/\n/g, "\\n");
	} else {
		return value;
	}
	
}

