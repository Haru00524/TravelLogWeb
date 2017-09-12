$(document).ready(function(){
	
	$(window).scrollTop(0);// 초기화
	
	var value = 1500; // 디폴트 스크롤
	var currentPage = 1; // 현재페이지 - 변수
	var recordTimeline = 20; // 보여줄 타임라인수 - 상수
	
	var clickData; // 가져온 오브젝트 리스트 카피 속도개선
	var slideIndex = 1; // defaultSlider 
	var clickTarget; // 클릭한 엘리먼트타겟
	
	var lifeObj; // 스텝로그 라이프정보 데이터
	var infowindows = []; // 구글맵 infowindows 데이터
	
	var mapCookieData; // 맵저장데이터 
	var mapLocation;  // 맵저장데이터2
	
	var clickDealy
	
	clickData = new Array();
	var promise = Timeline();
	promise.done(function() {
		animations();
    });
	
	
	// 해당엘리먼트 핸들링
	$(".bars").on("click",function(){
		
		$(".logbar").hide();
		var barsData = $(this);
		
		setTimeout(function() {
			
			if(barsData.hasClass("currentive")) {
				return false;
			} else {
			
				$(".divTarget").remove();
				$(".travelTarget").remove();
				$(".notTarget").remove();
				$("#allCell").css("opacity","0");
				$("#imgToggle").attr("style", "visibility:hidden");
				
				$(".bars").removeClass('currentive');
				barsData.addClass("currentive");
				$(window).scrollTop(0); // 스크롤을 초기화해준다.
				currentPage = 1; // 현재페이지로 초기화
				value = 1500 // 다시초기화해준다.
				
				clickData = new Array();
	
				promise = Timeline();
				promise.done(function() {
					animations();
			    });
				
			}
			
		}, 50);
		
	});
	
	// 스크롤 타임라인 핸들링
	$(window).scroll(function(){
		console.log($(this).scrollTop()); // 현재스크롤 위치를 반환 
		
		if($(this).scrollTop() > value) {
			currentPage++;
			value += 1500;
			Timeline();
		}
		
	});
	
	// 타임라인 리스트박스 출력
	function Timeline() {
		var deferred = $.Deferred(); // 순차핸들링
		
		var logType = $(".currentive").attr("data-onlog");
		startRecord = getStartRecord(currentPage,recordTimeline);
		
		setTimeout(function() {
			$.ajax({
				type : 'POST',
				url : 'logList',
				data : { 
					logType : logType,
					startRecord : startRecord,
					recordTimeline : recordTimeline
				},
				success : function(data) { // @ResponseBody
					
					console.log(data);
					console.log("List");
					
					bindAppend = 0; // 바인딩셋
					
					for(var i=0; i<data.length; i++) {
						
						clickData.push(data[i]); // 배열쌓기 
						
						var onImage = ""; // 이미지 타임라인
						var onTravelOrText = ""; // 그냥 Travel or 텍스트 타임라인
						var onMapOrVideo = ""; // 맵 or 비디오 타임라인 
						
						if(data[i].file_content != ""){ // 파일이 존재할때  --> 이미지 파일 && 비디오파일
							
							if(checkImageType(data[i].file_content[0])) { // 이미지일때 
								
								// 이미지 on
								onImage = "<div class='img-hover margin-bottom-30 divTarget' data-board='" + data[i].board_code + "' data-index='" + (clickData.length-1) + "' data-type='1'>" +
												"<div class='timeline'>" +
													"<div class='padding-10'>" + 
														"<img class='thumbnail pull-left' src='displayProfile?fileName=" + data[i].user_profile + "' style='width:50px; height:50px;'>" + 
														"<div class='time-id'>" + data[i].user_id + "</div>" + 
														"<div class='time-title'>" + data[i].board_title + "</div>" + 
									 				"</div>" + 
													"<div class='images'>" +
							 							"<img class='img-responsive' src='" + webappType(data[i].write_type, data[i].file_content[0]) + "' alt=''>" +
													"</div>" + 
													"<div class='padding-10'>" + 
														"<div class='resource' style='clear:both;'>" + 
															"<p> " + data[i].board_content + "</p>" + 
															"<ul class='list-inline nomargin hashTagList'>";
															for(var j=0; j<data[i].hash_tag_content.length; j++) {
																onImage += "<li class='hashStyle'><a>" + data[i].hash_tag_content[j] + "</a></li>";
															} 
								onImage +=					"</ul>" + 
															"<ul class='text-left size-14 list-inline list-separator ultop targeting'>" + 
										      	              	"<li>" + 
																	"<i class='fa fa-calendar-check-o'></i><b>" + dateParse(data[i].board_date) + "</b>" + 
																"</li>" + 
																"<li>" + 
																	"<b>" + checkLogType(data[i].board_type_code) + "</b>" + 
																"</li>";
																if(data[i].my_like == 1) {
																	onImage += "<li>" + 
																				   "<i class='fa fa-heart'></i><b>" + data[i].like_count + "</b>" +
																				"</li>";
																} else {
																	onImage += "<li>" + 
																	  			   "<i class='fa fa-heart-o'></i><b>" + data[i].like_count + "</b>" +
																				"</li>";
																}
								onImage +=			        "</ul>" + 
										                "</div>" + 
								    	            "</div>" + 
							    	          	"</div>" + 
							    	       "</div>";
								
								if(bindAppend == 0) {
									$(".columnA").append(onImage);
									bindAppend++;
								} else if (bindAppend == 1) {
									$(".columnB").append(onImage);
									bindAppend++;
								} else if (bindAppend == 2) {
									$(".columnC").append(onImage);
									bindAppend = 0;
								}
													
							} else { // 이미지가 아닌 다른파일일때
								
								// 비디오 on
								onMapOrVideo = "<div class='img-hover margin-bottom-30 divTarget' data-board='" + data[i].board_code + "' data-index='" + (clickData.length-1) + "' data-type='3'>" +
												"<div class='timeline'>" +
													"<div class='padding-10'>" + 
														"<img class='thumbnail pull-left' src='displayProfile?fileName=" + data[i].user_profile + "' style='width:50px; height:50px;'>" + 
														"<div class='time-id'>" + data[i].user_id + "</div>" + 
														"<div class='time-title'>" + data[i].board_title + "</div>" + 
													"</div>" + 
													"<div class='videoTag'>";
													if(data[i].file_content[0].match("●")) { // 맵일시 
														onMapOrVideo += "<img width='100%' height='250' src=" + webappType(data[i].write_type, data[i].file_content[0]) + ">";
													} else { // 동영상일시 
														onMapOrVideo += "<iframe frameborder='0' src=" + data[i].file_content[0] +" width='100%' height='250' class='note-video-clip'></iframe>";
													}
								onMapOrVideo += 	"</div>" + 
													"<div class='padding-10'>" + 
														"<div class='resource' style='clear:both;'>";
															if(data[i].file_content[0].match("●")) {
																
															} else {
																onMapOrVideo += "<p class='text-left'> " + data[i].board_content + "</p>" + 
																"<ul class='list-inline nomargin hashTagList'>";
																for(var j=0; j<data[i].hash_tag_content.length; j++) {
																	onMapOrVideo += "<li class='hashStyle'><a>" + data[i].hash_tag_content[j] + "</a></li>";
																} 
																onMapOrVideo += "</ul>";
															}
								onMapOrVideo +=					"<ul class='text-left size-14 list-inline list-separator ultop targeting'>" + 
										      	              	"<li>" + 
																	"<i class='fa fa-calendar-check-o'></i><b>" + dateParse(data[i].board_date) + "</b>" + 
																"</li>" + 
																"<li>" + 
																	"<b>" + checkLogType(data[i].board_type_code) + "</b>" + 
																"</li>"; 
																if(data[i].my_like == 1) {
																	onMapOrVideo += "<li>" + 
																				   "<i class='fa fa-heart'></i><b>" + data[i].like_count + "</b>" +
																				"</li>";
																} else {
																	onMapOrVideo += "<li>" + 
																	  			   "<i class='fa fa-heart-o'></i><b>" + data[i].like_count + "</b>" + 
																				"</li>";
																}
								onMapOrVideo +=				    "</ul>" + 
										                "</div>" + 
								    	            "</div>" + 
							    	          	"</div>" + 
							    	       "</div>";
								
								if(bindAppend == 0) {
									$(".columnA").append(onMapOrVideo);
									bindAppend++;
								} else if (bindAppend == 1) {
									$(".columnB").append(onMapOrVideo);
									bindAppend++;
								} else if (bindAppend == 2) {
									$(".columnC").append(onMapOrVideo);
									bindAppend = 0;
								}
								
							}
							
						} else {
							
							// 텍스트일때
							
							// travel-log 타겟 
							if(data[i].board_type_code == 2) {
								
								if(data[i].place_img == null) {
									onTravelOrText = "<div class='img-hover margin-bottom-30 notTarget' data-board='" + data[i].board_code + "'>";
								} else {
									onTravelOrText = "<div class='img-hover margin-bottom-30 travelTarget' data-board='" + data[i].board_code + "' group-num='" + data[i].group_Code + "' start-date='" + dateParse3(data[i].start_Date) + "'>";
								}
								onTravelOrText += 		"<div class='timeline'>" +
															"<div class='padding-10'>" + 
																"<div>" + 
														 			"<img class='thumbnail pull-left' src='displayProfile?fileName=" + data[i].user_profile + "' style='width:50px; height:50px;'>" + 
														 			"<div class='time-id'>" + data[i].user_id + "</div>" + 
														 			"<div class='time-title'>" + data[i].board_title + "</div>" + 
																"</div>" + 
															"</div>";
															if(data[i].place_img != null) {
																onTravelOrText += "<div class='images'>" +
													 									"<img class='img-responsive' src='displayFile?fileName=" + webappTypeC(data[i].place_img) + "' alt=''>" +
													 							  "</div>";
															} else {
																onTravelOrText += "<div class='padding-10'><div class='text-center travelCreate'>여행기 생성중</div></div>" 
															}
								onTravelOrText += 	 		"<div class='padding-10'>" + 	
																"<div class='resource' style='clear:both;'>" + 
																	"<ul class='text-left size-14 list-inline list-separator ultop targeting'>" + 
												      	              	"<li>" + 
																			"<i class='fa fa-calendar-check-o'></i><b>" + dateParse(data[i].board_date) + "</b>" + 
																		"</li>" + 
																		"<li>" + 
																			"<b>" + checkLogType(data[i].board_type_code) + "</b>" + 
																		"</li>";
																		if(data[i].my_like == 1) {
																			onTravelOrText += "<li>" + 
																						   "<i class='fa fa-heart'></i><b>" + data[i].like_count + "</b>" + 
																						"</li>";
																		} else {
																			onTravelOrText += "<li>" + 
																			  			   "<i class='fa fa-heart-o'></i><b>" + data[i].like_count + "</b>" + 
																						"</li>";
																		}
								onTravelOrText +=						"<li>" + 
																			"<i class='fa fa-user'></i><b>" + data[i].group_count + "</b>" + 
																		"</li>" + 
																	"</ul>" + 
												                "</div>" + 
										    	            "</div>" + 
									    	          	"</div>" + 
									    	       "</div>";
							} else {
								// 일반텍스트 타겟 
								onTravelOrText = "<div class='img-hover margin-bottom-30 divTarget' data-board='" + data[i].board_code + "' data-index='" + (clickData.length-1) + "' data-type='1'>" +
														"<div class='timeline'>" +
															"<div class='padding-10'>" + 
																"<div>" + 
														 			"<img class='thumbnail pull-left' src='displayProfile?fileName=" + data[i].user_profile + "' style='width:50px; height:50px;'>" + 
														 			"<div class='time-id'>" + data[i].user_id + "</div>" + 
														 			"<div class='time-title'>" + data[i].board_title + "</div>" + 
																"</div>" + 
																"<div class='resource' style='clear:both; border-top: 1px solid #e6e6e6;'>" + 
																	"<p class='text-left margin-top-3'>" + data[i].board_content + "</p>" + 
																	"<ul class='list-inline nomargin hashTagList'>";
																	for(var j=0; j<data[i].hash_tag_content.length; j++) {
																		onTravelOrText += "<li class='hashStyle'><a>" + data[i].hash_tag_content[j] + "</a></li>";
																	} 
								onTravelOrText +=					"</ul>" + 
																	"<ul class='text-left size-14 list-inline list-separator ultop targeting'>" + 
												      	              	"<li>" + 
																			"<i class='fa fa-calendar-check-o'></i><b>" + dateParse(data[i].board_date) + "</b>" + 
																		"</li>" + 
																		"<li>" + 
																			"<b>" + checkLogType(data[i].board_type_code) + "</b>" + 
																		"</li>";
																		if(data[i].my_like == 1) {
																			onTravelOrText += "<li>" + 
																						   "<i class='fa fa-heart'></i><b>" + data[i].like_count + "</b>" + 
																						"</li>";
																		} else {
																			onTravelOrText += "<li>" + 
																			  			   "<i class='fa fa-heart-o'></i><b>" + data[i].like_count + "</b>" + 
																						"</li>";
																		}
								onTravelOrText +=		             "</ul>" + 
												                "</div>" + 
										    	            "</div>" + 
									    	          	"</div>" + 
									    	       "</div>";
							}

							if(bindAppend == 0) {
								$(".columnA").append(onTravelOrText);
								bindAppend++;
							} else if (bindAppend == 1) {
								$(".columnB").append(onTravelOrText);
								bindAppend++;
							} else if (bindAppend == 2) {
								$(".columnC").append(onTravelOrText);
								bindAppend = 0;
							}
							
						}
						
					}
		
					$(".resource img").remove(); // 컨텐츠 이미지 삭제
					$(".resource iframe").remove(); // 동영상 아이프레임 삭제
					
					$(".logbar").show();
					
				}, error : function() {
					alert("에러");
				}
			});
		
		deferred.resolve(); // 모든결과가 완료되었을시 1 
		}, 0);
		
		return deferred.promise(); // 모든결과가 완료되었을시 2 리턴
	}
	
	/** ============================ 타임라인 LightBox ============================ **/
	
	function openInfo(board_code, index, obj, type) {
		
		var no = 1;
		
		if(clickData[index].board_code == board_code) {
			if(clickData[index].states == 0) {
				no = 0
				clickData[index].states = 1
			} else {
				no = 1
			}
		}
		 
		$.ajax({ 
			type : 'GET',
			url : 'commendTwo',
			data : {
				no : board_code,
				state : no,
				type : type
			},
			success : function(objmap) {
				
				console.log("step -> objmap -> clickData");
				console.log(objmap.step); // 스텝로그 (라이프로그 리스트 데이터);
				console.log(objmap);
				console.log(clickData[index]);
				console.log("=========================");
				
				lifeObj = new Array(); 
				lifeObj = objmap.step;
				
				console.log("lifeObj");
				console.log(lifeObj);
				console.log("=========================");
				
				$(".lightBox-content").empty();
				
				// 전역배열데이터 clickData
				// 세션체크유저 checkUser
				var checkUser = $("#users").attr("data-check");
					
					if(clickData[index].board_code == board_code) {

						var lightbox = "<div class='lightAlls'>";
						
						if(objmap.step.length != 0 || clickData[index].file_content[0].match("●")) { // 맵일시
							
							lightbox += "<div class='lightA-step inline-block'>" + 
											"<div class='light-1'>" +
												"<div id='maps' style='width:100%; height:678px; background-color:#262626; border:1px solid black;'>" +
			      										
			      								"</div>" + 
			      							"</div>" + 
						    			"</div>";
						} else { // 맵이 아닐시
							
							lightbox += "<div class='lightA inline-block'>" + 
											"<div class='light-1'>";
												
												for(var j=0; j<clickData[index].file_content.length; j++) {
													if(j == 1) {
														lightbox +=	"<a class='prevs'>&#10094;</a><a class='nexts'>&#10095;</a>";
													}
													if(clickData[index].file_content[j] == "") { // 이미지가 없을시 
														lightbox +=	 "<div style='width:100%; height:500px; background-color:#262626; border:1px solid black;'>" +
								      										"<h2 style='text-align:center; padding:230px 0px 0px 0px; color:white;'>이미지가 없습니다.<h2>" + 
								      								 "</div>";
													} else if(!(clickData[index].file_content[j] == "") && clickData[index].file_content.length == 1) {	
														// 라이트박스의 이미지가 한장일시 
														lightbox +=	 "<div class='mySlides'>";
												      					if(clickData[index].file_content[j].match("youtube")) {
												      						lightbox +=	"<iframe frameborder='0' src=" + clickData[index].file_content[j] +" width='100%' height='495' class='note-video-clip'></iframe>";
												      					} else {
												      						lightbox +=	"<img src='" + webappTypeB(clickData[index].write_type, clickData[index].file_content[j]) + "' style='width:100%; height:500px;'>";
												      					}
												      	lightbox +=	"</div>";
													} else {
														// 라이트 박스의 이미지가 여러장일시
														lightbox +=	"<div class='mySlides'>";
																	if(clickData[index].file_content[j].match("youtube")) {
																		lightbox +=	"<div class='numbertext'>" + (j+1) + " / " + clickData[index].file_content.length + "</div>" + 
																		"<iframe frameborder='0' src=" + clickData[index].file_content[j] +" width='100%' height='495' class='note-video-clip'></iframe>";
																	} else {
																		lightbox +=	"<div class='numbertext'>" + (j+1) + " / " + clickData[index].file_content.length + "</div>" + 
												      					"<img src='" + webappTypeB(clickData[index].write_type, clickData[index].file_content[j]) + "' style='width:100%; height:500px;'>";
																	}
														lightbox +=	"</div>";
													}
												}
												
							lightbox +=		"</div>" + 
									    	"<div class='light-2 text-overset inline-block'>" +
									    		"<ul class='list-inline nomargin hashTagList'>";
													
													for(var j=0; j<clickData[index].hash_tag_content.length; j++) { 
														// 해시태그 반복문 
														lightbox += "<li class='hashStyle2'><a>" + clickData[index].hash_tag_content[j] + "</a></li>"
													}
									    			
							lightbox +=			 "</ul>" +
									    	"</div>" +
									    	"<div class='light-3 text-overset inline-block'>";
									    	if(clickData[index].onAddress == null) {
									    		lightbox += "<h4 style='text-align:center; padding:55px 0px 0px 0px; color:white;'>해당 주소정보없음.</h4>";
									    	} else {
									    		lightbox += "<h4 style='text-align:center; padding:55px 0px 0px 0px; color:white;'>" + clickData[index].onAddress + "</h4>";
									    	} 
							lightbox +=		"</div>" + 
						    			"</div>";
						}
					    			// 오른쪽 박스 부분
						lightbox +=	"<div class='lightB inline-block resizeinfo'>" + 
								    	"<div class='nomargin sky-form boxed restyle'>" + 
								    		"<header class='size-18' style='background:rgba(0, 0, 0, 0.1) !important;'>" +
								    			"<b>" + clickData[index].board_title + "</b>" + 
								    		"</header>" +
								    		"<fieldset class='nomargin light-content text-overset'>";
								    			if(objmap.step.length != 0) {
								    				for(var x=0; x<objmap.step.length; x++) { // 지도이면서 폴리라인 마커포함 
								    					lightbox +=	"<div class='mapset3'>" + 
								    									"<div class='inline-block' style='margin: 0px 10px 5px 0px;'><button class='btn btn-primary btn-xs btnMarker" + x + "'>Marker</button></div>" + 
								    									"<div class='inline-block'>" + objmap.step[x].board_title + " / " + dateParse(objmap.step[x].board_date) + "</div>" +  
								    								"<div>";
								    				}
								    			} else {
								    				if(clickData[index].board_type_code == 3) { // 지도이면서 폴리라인만 포함
								    					lightbox +=	"<div></div>";
								    				} else { // 지도가 아닐시
								    					lightbox +=	"<div>" + clickData[index].board_content +"</div>";
								    				}
								    			}
						lightbox +=			"</fieldset>" +
								    		"<fieldset class='nomargin light-user size-16'>" +
								    			"<div class='inline-block'>" +
								    				"<img src='displayProfile?fileName=" + clickData[index].user_profile + "' style='width:40px; height:40px;'>&nbsp; &nbsp;" +
								    				"<b>" + clickData[index].user_id +"</b>&nbsp; &nbsp;" +
								    			"</div>" +
								    			"<div class='light-day inline-block' data-target=" + clickData[index].board_code + " data-idx=" + index + ">";
												if(clickData[index].my_like == 1) { // 좋아요 부분
													lightbox +=	"<a class='log-like'><i class='fa fa-heart'></i><b>" + clickData[index].like_count + "</b></a>";
												} else {
													lightbox +=	"<a class='log-like'><i class='fa fa-heart-o'></i><b>" + clickData[index].like_count + "</b></a>";
												}
						lightbox +=		    		"<i class='fa fa-eye'></i><b>" + objmap.view + "&nbsp;</b>" + // 조회수 부분 
								    				"<i class='fa fa-calendar-check-o'></i><b>" + dateParse(clickData[index].board_date) +"</b>" +
								    			"</div>" + 
								    		"</fieldset>" + 
								    		"<fieldset class='nomargin light-reply text-overset'>" +
									    		"<div class='form-group apply-all'>" +
													"<div class='replyText'>댓글 " + objmap.reply.length + " 개</div>" +
													"<div class='userComments'>";
													if(objmap.reply.length == 0) { // 댓글이 없을때 
														
													} else {
														for(var a=0; a<objmap.reply.length; a++) { // 댓글 불러오기
															if(objmap.reply[a].reply_state == 1) { // 자신의 댓글 상태를 보여준다 // 수정삭제기능 부여 
																lightbox +=	"<div>" + 
																				"<span class='profile-img'>" + 
																					"<img src='displayProfile?fileName=" + objmap.reply[a].user_profile + "' style='width:30px; height:30px;'>" +
																				"</span>" +
																				"<div class='user-reply-info'>" + 
																					"<div class='reply-a'>" + 
																						"<button class='btn btn-brown btn-xs margin-right-3 replyDelete' data-reply-code='" + objmap.reply[a].board_code + "' data-target-board='" + objmap.reply[a].reply_code + "'>삭제</button>" + 
																						"<button class='btn btn-purple btn-xs margin-right-6 margin-left-0 replyModify' data-reply-code='" + objmap.reply[a].board_code + "' data-target-board='" + objmap.reply[a].reply_code + "'>수정</button>" +
																						"<b>" + dateParse(objmap.reply[a].board_date) + "</b>" +
																					"</div>" +
																					"<div class='reply-b'><b>" + objmap.reply[a].user_id + "</b></div>" +
																					"<div class='reply-c margin-top-6'>" + objmap.reply[a].board_content + "</div>" +
																				"</div>" +
																			"</div>";
															} else { // 로그인을 하지않았거나 다른아이디일때 // 댓글만 읽기가능
																lightbox +=	"<div>" +
																				"<span class='profile-img'>" + 
																					"<img src='displayProfile?fileName=" + objmap.reply[a].user_profile + "' style='width:30px; height:30px;'>" +
																				"</span>" + 
																				"<div class='user-reply-info'>" + 
																					"<div class='reply-a'><b>" + dateParse(objmap.reply[a].board_date) + "</b></div>" +
																					"<div class='reply-b'><b>" + objmap.reply[a].user_id + "</b></div>" + 
																					"<div class='reply-c margin-top-6'>" + objmap.reply[a].board_content + "</div>" +
																				"</div>" +
																			"</div>";
															}
															
														}
													}
													if(checkUser != "") { // 세션이 활성화 되어있으면 댓글을 쓸수있음 
														lightbox +=	"<textarea class='commentReply' maxlength='5000' rows='2' required></textarea>" +
																	"<button class='replyWrite margin-top-3 btn btn-3d btn-reveal btn-black pull-right'>" + 
																		"<i class='fa fa-plus'></i>" +
																		"<span>댓글 쓰기</span>" +
																	"</button>" + 
																	"<div data-reply='" + board_code +"'></div>";
													} else {
														lightbox += "<div>";
																		if(objmap.reply.length == 0) {  // 댓글이 아예 없을때 댓글 마진없애기
																			lightbox += "<div class='loginAccess text-center'>" +
																							"<b>로그인후 이용하실수 있습니다.</b>" +
																							"<a href=''>로그인하기</a>"
																						"</div>";
																		} else { // 댓글이 있을때  댓글 마진조절 
																			lightbox += "<div class='loginAccess text-center' style='margin-top:25px;'>" +
																							"<b>로그인후 이용하실수 있습니다.</b>" +
																							"<a href=''>로그인하기</a>"
																						"</div>";
																		}
														lightbox += "</div>";
													}
						lightbox +=					"</div>" +
												"</div>" +
								    		"</fieldset>" +
								    	"</div>" +
									"</div>";
						
						lightbox +=	"</div>";
						
						$(".lightBox-content").append(lightbox);
						
					}

				
				$(".text-overset p img").remove();
				showSlides(slideIndex);
				if(objmap.step.length != 0) {
					mapCookieData = clickData[index].file_content[0];
					initialize(objmap.step, mapCookieData);
				}
				
				if(clickData[index].file_content[0].match("●") && objmap.step.length == 0) {
					initializeE(clickData[index].file_content[0]);
				}
				
			}, error:function(){
				alert("오류B");
			}
		 })
			
	
		
	}
	
	// 댓글 작성
	$(".lightBox-content").on("click",".replyWrite",function(){
		
		var replyAppend = $(this).parent(); // append 타겟

		var textarea = $(this).prev().val(); // 작성내용
		var no = $(this).next().attr("data-reply"); // 글번호
		
		replyCommend(textarea, no, 1, 0, replyAppend);
		
	});
	
	// 댓글 삭제 클릭
	$(".lightBox-content").on("click",".replyDelete",function(){
		
		var replyAppend = $(this).parent().parent().parent().parent(); // append 타겟
		
		var replyno = $(this).attr("data-reply-code");
		var no = $(this).attr("data-target-board");
		
		if(confirm("댓글을 삭제하시겠습니까?")) {
			replyCommend("Delete!", no, 3, replyno, replyAppend);
		}
		
	});
	
	// 댓글 수정클릭
	$(".lightBox-content").on("click",".replyModify",function(){
		
		var buttonName = $(this).text();
		
		if(buttonName == "수정") {
			var replyno = $(this).attr("data-reply-code");
			var no = $(this).attr("data-target-board");
			var textarea = $(this).parent().next().next();
			var text = textarea.text();
			textarea.empty();
			var areaElement = "<textarea class='commentReply' maxlength='5000' rows='2' required>" + text + " </textarea>" +
							  "<button class='replyConfirm margin-top-3 btn btn-3d btn-reveal btn-black pull-right' data-reply-code=" + replyno + " data-target-board='" + no + "'>" + 
							  	  "<i class='fa fa-plus'></i>" +
							  	  "<span>수정하기</span>" +
							  "</button>";
			textarea.append(areaElement);
			$(this).text("수정취소");
			
		} else {
			
			var textarea = $(this).parent().next().next();
			var text = textarea.children("textarea").text();
			
			textarea.empty();
			textarea.text(text);
			$(this).text("수정");
				
		}
		
	});
	
	// 댓글 수정하기 클릭
	$(".lightBox-content").on("click",".replyConfirm",function(){
		
		var replyAppend = $(this).parent().parent().parent().parent(); // append 타겟
		
		var no = $(this).attr("data-target-board");	
		var replyno = $(this).attr("data-reply-code");
		var textarea = $(this).prev().val();

		replyCommend(textarea, no, 2, replyno, replyAppend);

	});
	
	function replyCommend(textarea, no, replytype, replyno, replyAppend) {
		
		if(textarea == "") {
			alert("내용을 입력해주십시오.");
			return false;
		}
		
		$.ajax({ 
			type : 'POST',
			url : 'replyGo',
			data : {
				no : no,
				replytype : replytype,
				replyno : replyno,
				text : textarea
			},
			success : function(reply) {
				
				console.log(reply);
				$(replyAppend.prev()).text("댓글 " + reply.length + " 개");
				$(replyAppend).empty();
				var checkUser = $("#users").attr("data-check");
				
				var replybox = "";
				
					
				if(reply.length == 0) {
					
				} else {
					for(var i=0; i<reply.length; i++) {
						if(reply[i].reply_state == 1) {
							replybox +=	"<div>" + 
											"<span class='profile-img'>" + 
												"<img src='displayProfile?fileName=" + reply[i].user_profile + "' style='width:30px; height:30px;'>" +
											"</span>" +
											"<div class='user-reply-info'>" + 
												"<div class='reply-a'>" + 
													"<button class='btn btn-brown btn-xs margin-right-3 replyDelete' data-reply-code='" + reply[i].board_code + "' data-target-board='" + reply[i].reply_code + "'>삭제</button>" + 
													"<button class='btn btn-purple btn-xs margin-right-6 margin-left-0 replyModify' data-reply-code='" + reply[i].board_code + "' data-target-board='" + reply[i].reply_code + "'>수정</button>" +
													"<b>" + dateParse(reply[i].board_date) + "</b>" +
												"</div>" +
												"<div class='reply-b'><b>" + reply[i].user_id + "</b></div>" +
												"<div class='reply-c margin-top-6'>" + reply[i].board_content + "</div>" +
											"</div>" +
										"</div>";
						} else {
							replybox +=	"<div>" +
											"<span class='profile-img'>" + 
												"<img src='displayProfile?fileName=" + reply[i].user_profile + "' style='width:30px; height:30px;'>" +
											"</span>" + 
											"<div class='user-reply-info'>" + 
												"<div class='reply-a'><b>" + dateParse(reply[i].board_date) + "</b></div>" +
												"<div class='reply-b'><b>" + reply[i].user_id + "</b></div>" + 
												"<div class='reply-c margin-top-6'>" + reply[i].board_content + "</div>" +
											"</div>" +
										"</div>";
						}
						
					}
				}
				
				if(checkUser != "") {
					replybox +=	"<textarea class='commentReply' maxlength='5000' rows='2' required></textarea>" +
								"<button class='replyWrite margin-top-3 btn btn-3d btn-reveal btn-black pull-right'>" + 
									"<i class='fa fa-plus'></i>" +
									"<span>댓글 쓰기</span>" +
								"</button>" + 
								"<div data-reply='" + no +"'></div>";
				} else {
					replybox += "<div>";
									if(reply.length == 0) {
										replybox += "<div class='loginAccess text-center'>" +
														"<b>로그인후 이용하실수 있습니다.</b>" +
														"<a href=''>로그인하기</a>"
													"</div>";
									} else {
										replybox += "<div class='loginAccess text-center' style='margin-top:25px;'>" +
														"<b>로그인후 이용하실수 있습니다.</b>" +
														"<a href=''>로그인하기</a>"
													"</div>";
									}
					replybox += "</div>";
	
				}
				
				$(replyAppend).append(replybox);
				
			}, error:function(){
				alert("에러");
			}
		})
		
	}
	
	// 타겟 좋아요 클릭
	$(".lightBox-content").on("click",".log-like",function(){
	
		var checkUser = $("#users").attr("data-check"); // 체크유저
		
		if(checkUser == "") {
			alert("로그인후 이용해주십시오.");
			return false;
		}
		
		var mapsOnClick = $(this).attr("data-maps"); // 리스트클릭 or 맵클릭 확인데이터 
		var thisData = $(this).parent("div").attr("data-target"); // board값 
		var index = $(this).parent("div").attr("data-idx"); // index값
		
		var clickTimeline = $(this);
		var whylike = $(this).children("i").prop("class");
		var idx = whylike.indexOf(" ") + 1;
		whylike = whylike.substr(idx);
		
		if(mapsOnClick != 1) {
		
				var states = 1 // 현재상태
				var count = index; // 현재인덱스 번호
				
				if(whylike == 'fa-heart') {
					states = 0;
				} 
				
				if(whylike == 'fa-heart-o') {
					states = 1;
				}
				
				$.ajax({
					type : 'GET',
					url : 'likeConfirm',
					data : { 
						states : states,
						no : thisData
					},
					success : function(data) {
						
						if(whylike == 'fa-heart') {
							// 좋아요 취소
							clickData[count].like_count = data;
							clickData[count].my_like = 0
							clickTimeline.html("<i class='fa fa-heart-o'></i><b>" + (data) + "</b>");
							clickTarget.html("<i class='fa fa-heart-o'></i><b>" + (data) + "</b>");
						} 
						
						if(whylike == 'fa-heart-o') {
							// 좋아요 구독
							clickData[count].like_count = data;
							clickData[count].my_like = 1;
							clickTimeline.html("<i class='fa fa-heart'></i><b>" + (data) + "</b>");
							clickTarget.html("<i class='fa fa-heart'></i><b>" + (data) + "</b>");
						}
						
					}, error:function(){ }
					
				});
				
			} else {
				
				var states = 1 // 현재상태
				var count = index; // 현재인덱스 번호
				
				if(whylike == 'fa-heart') {
					states = 0;
				} 
				
				if(whylike == 'fa-heart-o') {
					states = 1;
				}
				
				$.ajax({
					type : 'GET',
					url : 'likeConfirm',
					data : { 
						states : states,
						no : thisData
					},
					success : function(data) {
						
						if(whylike == 'fa-heart') {
							// 좋아요 취소
							lifeObj[count].likecount = data;
							lifeObj[count].mylike = 0
							clickTimeline.html("<i class='fa fa-heart-o'></i><b>" + (data) + "</b>");
						} 
						
						if(whylike == 'fa-heart-o') {
							// 좋아요 구독
							lifeObj[count].likecount = data;
							lifeObj[count].mylike = 1;
							clickTimeline.html("<i class='fa fa-heart'></i><b>" + (data) + "</b>");
						}
						
					}, error:function(){ }
					
				});
				
			}
		
	});
	
	// travel-log click 이동 타겟
	$("#allCell").on("click",".travelTarget",function(){
		var travelNum = $(this).attr("data-board");
		var groupNum = $(this).attr("group-num");
		var dateNum = $(this).attr("start-date");
		var pageTarget = "./travelLog?post=" + travelNum + "&group=" + groupNum + "&startDate=" + dateNum;
		window.open(pageTarget,"_blank");
	});
	
	
	
	// 타겟 타임라인 클릭
	$("#allCell").on("click",".divTarget",function(){
		
		clickTarget = $(this).children("div")
							 .children("div")
							 .children(".resource")
							 .children(".targeting")
							 .children("li").eq(2); // 저장된 타겟정보
		
		var board_code = $(this).attr("data-board");
		var index = $(this).attr("data-index");
		var board_type_code = $(this).attr("data-type");
		var obj = []
		
		slideIndex = 1;
		
		$(".lightBox-content").empty(); // 내용비우기 
		
		openInfo(board_code, index, obj, board_type_code); // 정보열기
		
		
		openLightBox(); // 라이트박스배경 출력
		
	});
	
	// 타임라인 x표시 클릭 
	$(".lightClose").click(function(){
		closeLightBox();
	});
	
	// 컨텐츠 클릭부분 바탕클릭
	$(document).on("click",function(e){
		if(!$(e.target).has(".lightBox-content").length) {
	
		} else {
			closeBackground();
		}
	});
	
	// logs 상세클릭 function;
	$(".lightBox-content").on("click",".logs", function() {
		
		var log = $(this).attr("data-log");
		var index = $(this).attr("data-map-index");
		var board_code = lifeObj[index].board_code;
	
		$.ajax({ 
			type : 'GET',
			url : 'commendTwo',
			data : {
				no : board_code,
				state : 0,
				type : 1
			},
			success : function(objmap) {
	
				console.log(objmap);
				
				// 전역배열데이터 clickData
				// 세션체크유저 checkUser
				var checkUser = $("#users").attr("data-check");
				
				var lightbox = "<div class='Anothers'>";
						
					lightbox += "<div class='lightA inline-block'>" + 
									"<div class='light-1'>";
									if(lifeObj[index].fileimg == null) { // 이미지가 없을시 
										lightbox +=	 "<div style='width:100%; height:500px; background-color:#262626; border:1px solid black;'>" +
				      										"<h2 style='text-align:center; padding:230px 0px 0px 0px; color:white;'>이미지가 없습니다.<h2>" + 
				      								 "</div>";
									} else {	
										// 라이트박스의 이미지가 한장일시 
										lightbox +=	 "<div>" +
								      					"<img src='" + webappTypeB(lifeObj[index].write_type, lifeObj[index].fileimg) + "' style='width:100%; height:500px;'>" +
								      				 "</div>";
									} 
					lightbox +=		"</div>" + 
							    	"<div class='light-2 text-overset inline-block'>" +
							    		"<ul class='list-inline nomargin hashTagList'>";
											if(lifeObj[index].hash_tag_content != null) {
												for(var j=0; j<lifeObj[index].hash_tag_content.length; j++) { 
													// 해시태그 반복문 
													lightbox += "<li class='hashStyle2'><a>" + lifeObj[index].hash_tag_content[j] + "</a></li>"
												}
											}
					lightbox +=			 "</ul>" +
							    	"</div>" +
							    	"<div class='light-3 text-overset inline-block'>" + 
							    		"<div class='size-30'><a class='steps' data-step='" + lifeObj[index].step_log_code + "'><i class='fa fa-reply'></i></a></div>" + 
							    		"<h4 style='text-align:center; padding:55px 0px 0px 0px; color:white;'>" + lifeObj[index].onAddress + "</h4>" + 
							    	"</div>" + 
				    			"</div>";

			    	// 오른쪽 박스 부분
					lightbox +=	"<div class='lightB inline-block resizeinfo'>" + 
							    	"<div class='nomargin sky-form boxed restyle'>" + 
							    		"<header class='size-18' style='background:rgba(0, 0, 0, 0.1) !important;'>" +
							    			"<b>" + lifeObj[index].board_title + "</b>" + 
							    		"</header>" +
							    		"<fieldset class='nomargin light-content text-overset'>" +
							    			"<div>" + lifeObj[index].board_content + "</div>" + 
							    		"</fieldset>" +
							    		"<fieldset class='nomargin light-user size-16'>" +
							    			"<div class='inline-block'>" +
							    				"<img src='displayProfile?fileName=" + lifeObj[index].user_profile + "' style='width:40px; height:40px;'>&nbsp; &nbsp;" +
							    				"<b>" + lifeObj[index].user_id +"</b>&nbsp; &nbsp;" +
							    			"</div>" +
							    			"<div class='light-day inline-block' data-target=" + lifeObj[index].board_code + " data-idx=" + index + ">";
											if(lifeObj[index].mylike == 1) { // 좋아요 부분
												lightbox +=	"<a class='log-like' data-maps='1'><i class='fa fa-heart'></i><b>" + lifeObj[index].likecount + "</b></a>";
											} else {
												lightbox +=	"<a class='log-like' data-maps='1'><i class='fa fa-heart-o'></i><b>" + lifeObj[index].likecount + "</b></a>";
											}
					lightbox +=		    		"<i class='fa fa-eye'></i><b>" + objmap.view + "&nbsp;</b>" + // 조회수 부분 
							    				"<i class='fa fa-calendar-check-o'></i><b>" + dateParse(lifeObj[index].board_date) +"</b>" +
							    			"</div>" + 
							    		"</fieldset>" + 
							    		"<fieldset class='nomargin light-reply text-overset'>" +
								    		"<div class='form-group apply-all'>" +
												"<div class='replyText'>댓글 " + objmap.reply.length + " 개</div>" +
												"<div class='userComments'>";
												if(objmap.reply.length == 0) { // 댓글이 없을때 
													
												} else {
													for(var a=0; a<objmap.reply.length; a++) { // 댓글 불러오기
														if(objmap.reply[a].reply_state == 1) { // 자신의 댓글 상태를 보여준다 // 수정삭제기능 부여 
															lightbox +=	"<div>" + 
																			"<span class='profile-img'>" + 
																				"<img src='displayProfile?fileName=" + objmap.reply[a].user_profile + "' style='width:30px; height:30px;'>" +
																			"</span>" +
																			"<div class='user-reply-info'>" + 
																				"<div class='reply-a'>" + 
																					"<button class='btn btn-brown btn-xs margin-right-3 replyDelete' data-reply-code='" + objmap.reply[a].board_code + "' data-target-board='" + objmap.reply[a].reply_code + "'>삭제</button>" + 
																					"<button class='btn btn-purple btn-xs margin-right-6 margin-left-0 replyModify' data-reply-code='" + objmap.reply[a].board_code + "' data-target-board='" + objmap.reply[a].reply_code + "'>수정</button>" +
																					"<b>" + dateParse(objmap.reply[a].board_date) + "</b>" +
																				"</div>" +
																				"<div class='reply-b'><b>" + objmap.reply[a].user_id + "</b></div>" +
																				"<div class='reply-c margin-top-6'>" + objmap.reply[a].board_content + "</div>" +
																			"</div>" +
																		"</div>";
														} else { // 로그인을 하지않았거나 다른아이디일때 // 댓글만 읽기가능
															lightbox +=	"<div>" +
																			"<span class='profile-img'>" + 
																				"<img src='displayProfile?fileName=" + objmap.reply[a].user_profile + "' style='width:30px; height:30px;'>" +
																			"</span>" + 
																			"<div class='user-reply-info'>" + 
																				"<div class='reply-a'><b>" + dateParse(objmap.reply[a].board_date) + "</b></div>" +
																				"<div class='reply-b'><b>" + objmap.reply[a].user_id + "</b></div>" + 
																				"<div class='reply-c margin-top-6'>" + objmap.reply[a].board_content + "</div>" +
																			"</div>" +
																		"</div>";
														}
														
													}
												}
												if(checkUser != "") { // 세션이 활성화 되어있으면 댓글을 쓸수있음 
													lightbox +=	"<textarea class='commentReply' maxlength='5000' rows='2' required></textarea>" +
																"<button class='replyWrite margin-top-3 btn btn-3d btn-reveal btn-black pull-right'>" + 
																	"<i class='fa fa-plus'></i>" +
																	"<span>댓글 쓰기</span>" +
																"</button>" + 
																"<div data-reply='" + board_code +"'></div>";
												} else {
													lightbox += "<div>";
																	if(objmap.reply.length == 0) {  // 댓글이 아예 없을때 댓글 마진없애기
																		lightbox += "<div class='loginAccess text-center'>" +
																						"<b>로그인후 이용하실수 있습니다.</b>" +
																						"<a href=''>로그인하기</a>"
																					"</div>";
																	} else { // 댓글이 있을때  댓글 마진조절 
																		lightbox += "<div class='loginAccess text-center' style='margin-top:25px;'>" +
																						"<b>로그인후 이용하실수 있습니다.</b>" +
																						"<a href=''>로그인하기</a>"
																					"</div>";
																	}
													lightbox += "</div>";
												}
					lightbox +=					"</div>" +
											"</div>" +
							    		"</fieldset>" +
							    	"</div>" +
								"</div>";
					
					lightbox += "</div>";
					
					$(".lightAlls").hide();// 원본
					
					$(".lightBox-content").append(lightbox);

					$(".text-overset p img").remove();
	
				
				
				
			}, error:function(){
				alert("오류B");
			}
		})
		
	});
	
	// 돌아가기 클릭
	$(".lightBox-content").on("click",".steps",function(){
		var board_code = $(this).attr("data-step");
		
		$.ajax({ 
			type : 'GET',
			url : 'commendTwo',
			data : {
				no : board_code,
				state : 1,
				type : 3
			},
			success : function(objmap) {
				objmap.reply
				mapLocation = objmap.step;
				console.log(objmap);
				
				
				for(var i=0; i<mapLocation.length; i++) {
					
					// 정보갱신 
					var divRender = "<div class='map-life-info'>" +
					  					"<div class='map-info-box'>"; 
												if(mapLocation[i].fileimg == null) {
													
												} else {
													divRender += "<img src='" + webappType(mapLocation[i].write_type, mapLocation[i].fileimg) + "' style='width:300px; height:200px;' class='onMap'>";
												}
						divRender	+=		"<div class='text-left'>" + 
												"<div class='mapset margin-top-6' style='line-height:1.5; border-bottom:1px solid;'>" + mapLocation[i].board_title +"</div>" +
												"<div class='mapset margin-top-3'>" + "주소 : " + mapLocation[i].onAddress + "</div>" + 
												"<div class='margin-top-6'>" + 
													"<div class='inline-block'><img src='displayProfile?fileName=" + mapLocation[i].user_profile + "' style='width:40px; height:40px; padding:2px; border:1px solid black;'></div>"  +
													"<div class='inline-block margin-left-10 mapset2'>" + mapLocation[i].user_id + " / " + "<a class='logs' data-map-index='" + i + "' data-log='1'>LIFE-LOG</a>" + "</div>" + 
												"</div>" + 
												"<ul class='mapul text-left size-14 list-inline list-separator ultop targeting'>" +
													"<li><i class='fa fa-calendar-check-o'></i><b> " + dateParse(mapLocation[i].board_date) + " </b></li>" +
													"<li><i class='fa fa-eye'></i><b>" + mapLocation[i].viewCount + "</b></li>";
						  								if(mapLocation[i].mylike == 1) {
						  									divRender += "<li><i class='fa fa-heart'></i><b>" + mapLocation[i].likecount + "</b></li>";
						  								} else {
						  									divRender += "<li><i class='fa fa-heart-o'></i><b>" + mapLocation[i].likecount + "</b></li>";
						  								}
					    divRender += 			"</ul>" +
						 					"</div>" +
										"</div>" + 
					  			  "</div>";
			  		infowindows[i].setContent(divRender);
			  		infowindows[i].close();
			  	}
				
				
				$(".Anothers").remove();
				$(".lightAlls").show();
				
			}
		});
	
	});
	
	// 구글맵 폴리라인만 // 
	function initializeE(st) {
		
		var one = [];
		var two = [];
		var flightPlanCoordinates = [];
		var latlng;
		
		var lines = st.substring(0, (st.length-1));
		one = lines.split("|");
		
		for(var j=0; j<1; j++) { // kml 경로 polyline 넣기  
			two = one[j].split(",");
			latlng = new google.maps.LatLng(two[0], two[1]); 
		}
		
	    var mapOptions = { //구글 맵 옵션 설정
	    		zoom : 18, //기본 확대율
	    		center : latlng, // 지도 중앙 위치
	    		scrollwheel : true, //마우스 휠로 확대 축소 사용 여부
	    		mapTypeControl : true, //맵 타입 컨트롤 사용 여부
	    		mapTypeId: 'roadmap'
	    };
	    
	    map = new google.maps.Map(document.getElementById('maps'), mapOptions); //구글 맵을 사용할 타겟
	    
		for(var j=0; j<one.length; j++) { // kml 경로 polyline 넣기  
			two = one[j].split(",");
			flightPlanCoordinates.push(new google.maps.LatLng(two[0], two[1]));
		}
		  
		var flightPath = new google.maps.Polyline({
			  path: flightPlanCoordinates,
			  strokeColor: "#FF0000",
			  strokeOpacity: 1.0,
			  strokeWeight: 3
		});

		flightPath.setMap(map); 
		
	}
	
	
	// 구글맵 정보 // 
	function initialize(obj, st) {
		
		  var one = [];
		  var two = [];
		  var flightPlanCoordinates = [];
		  
		  var lines = st.substring(0, (st.length-1));
		  one = lines.split("|");
		  
		  var lat = obj[0].log_latitude;
		  var lng = obj[0].log_longtitude;
		
		  var latqlng = new google.maps.LatLng(lat, lng); 
		  
		  var mapOptions = { //구글 맵 옵션 설정
		      zoom : 18, //기본 확대율
		      center : latqlng, // 지도 중앙 위치
		      scrollwheel : true, //마우스 휠로 확대 축소 사용 여부
		      mapTypeControl : true, //맵 타입 컨트롤 사용 여부
		      mapTypeId: 'roadmap'
		  };
		  
		  map = new google.maps.Map(document.getElementById('maps'), mapOptions); //구글 맵을 사용할 타겟
		  
		  for(var i=0; i<obj.length; i++) {// 라이프로그 마커넣기 
			  addMarker(obj[i], i);
		  }
		  
		  for(var j=0; j<one.length; j++) { // kml 경로 polyline 넣기  
			  two = one[j].split(",");
			  flightPlanCoordinates.push(new google.maps.LatLng(two[0], two[1]));
		  }
		  
		  var flightPath = new google.maps.Polyline({
			    path: flightPlanCoordinates,
			    strokeColor: "#FF0000",
			    strokeOpacity: 1.0,
			    strokeWeight: 3
		  });

		  flightPath.setMap(map);  
			
	}
	
	function addMarker(e, x) {
		  
		  var copyData = new google.maps.LatLng(e.log_latitude, e.log_longtitude);
		  var image = './resources/img/placeholder.png'; //마커 이미지 설정
		  var divRender = "<div class='map-life-info'>" +
			  					"<div class='map-info-box'>"; 
		  							if(e.fileimg == null) {
		  								
		  							} else {
		  								divRender += "<img src='" + webappType(e.write_type, e.fileimg) + "' style='width:300px; height:200px;' class='onMap'>";
		  							}
		  	  divRender	+=				"<div class='text-left'>" + 
										"<div class='mapset margin-top-6' style='line-height:1.5; border-bottom:1px solid;'>" + e.board_title +"</div>" +
										"<div class='mapset margin-top-3'>" + "주소 : " + e.onAddress + "</div>" + 
										"<div class='margin-top-6'>" + 
											"<div class='inline-block'><img src='displayProfile?fileName=" + e.user_profile + "' style='width:40px; height:40px; padding:2px; border:1px solid black;'></div>"  +
											"<div class='inline-block margin-left-10 mapset2'>" + e.user_id + " / " + "<a class='logs' data-map-index='" + x + "' data-log='1'>LIFE-LOG</a>" + "</div>" + 
										"</div>" + 
										"<ul class='mapul text-left size-14 list-inline list-separator ultop targeting'>" +
											"<li><i class='fa fa-calendar-check-o'></i><b> " + dateParse(e.board_date) + " </b></li>" +
											"<li><i class='fa fa-eye'></i><b>" + e.viewCount + "</b></li>";
		  	  								if(e.mylike == 1) {
		  	  									divRender += "<li><i class='fa fa-heart'></i><b>" + e.likecount + "</b></li>";
		  	  								} else {
		  	  									divRender += "<li><i class='fa fa-heart-o'></i><b>" + e.likecount + "</b></li>";
		  	  								}
		  	  divRender += 				"</ul>" +
									"</div>" +
								"</div>" + 
			  			  "</div>";
		  
		  var marker = new google.maps.Marker({ //마커 설정
		      map : map,
		      position : copyData, //마커 위치
		      icon : image //마커 이미지
		  });
		  
		  var infowindow = new google.maps.InfoWindow({
		      content: divRender
		  });
		  
		  infowindows.push(infowindow);
		  
		  google.maps.event.addListener(marker, 'click', function() {
			  	  for(var i=0; i<infowindows.length; i++) {
			  		infowindows[i].close();
			  	  }
                  infowindow.open(map,marker);
		  });
		  
		  $(".lightBox-content").on('click',".btnMarker" + x, function() {
				
			  	 for(var i=0; i<infowindows.length; i++) {
			  		 infowindows[i].close();
			  	 }
		         infowindow.open(map,marker);
		         
		  });
		
	} 
	
	
	/***** slide handle *****/
	
	$(".lightBox-content").on("click",".prevs",function(){
		plusSlides(-1);
	});
	
	$(".lightBox-content").on("click",".nexts",function(){
		plusSlides(1);
	});
	
	function plusSlides(n) {
		  showSlides(slideIndex += n);
	}

	function currentSlide(n) {
		  showSlides(slideIndex = n);
	}

	function showSlides(n) {
		  var i;		  
		  var slides = $(".mySlides");
		  
		  console.log(slides);
		  
		  if (n > slides.length) {slideIndex = 1}
		  if (n < 1) {slideIndex = slides.length}
		  for (i = 0; i < slides.length; i++) {
		    $(slides[i]).css("display","none");
		  }
		  $(slides[slideIndex-1]).css("display","block");
	}
	
	/***** slide hover *****/
	
	$(".lightBox-content").on("mouseenter",".mySlides", function(){
		$(".numbertext").show();
		$(".prevs").show();
		$(".nexts").show();
	});
	$(".lightBox-content").on("mouseenter",".prevs", function(){
		$(".numbertext").show();
		$(".prevs").show();
		$(".nexts").show();
	});
	$(".lightBox-content").on("mouseenter",".nexts", function(){
		$(".numbertext").show();
		$(".prevs").show();
		$(".nexts").show();
	});
	
	
	$(".lightBox-content").on("mouseleave",".mySlides", function(){
		$(".numbertext").hide();
		$(".prevs").hide();
		$(".nexts").hide();
	});
	$(".lightBox-content").on("mouseleave",".prevs", function(){
		$(".numbertext").hide();
		$(".prevs").hide();
		$(".nexts").hide();
	});
	$(".lightBox-content").on("mouseleave",".nexts", function(){
		$(".numbertext").hide();
		$(".prevs").hide();
		$(".nexts").hide();
	});
	
	
	/** ============================ 타임라인 LightBox ============================ **/
	
	
});


// 웹 앱 업로드타입 (타임라인 리스트)
function webappType(dataA, dataB) {
	
	if(dataA == '1') {
		dataB = "http://114.201.41.248:8084/turn/resources/upload/logs" + dataB; // 모바일 이미지 출력 
	} else if (dataB.match("http")){
		dataB = dataB;
	} else if (dataB.match("●")){
		
		var googleMap = "http://maps.googleapis.com/maps/api/staticmap?size=300x300&scale=2&path=color:0x0000ff|weight:5|";
		
		googleMap += dataB.replace("●","");
		
		googleMap += "&key=AIzaSyAF8iTF3JtdLLhprWyASWE8APl6RM6BGBQ";
		
		dataB = googleMap;
		
	} else {
		dataB = "displayLogs?fileName=" + dataB; // 이미지 출력 
	}
	
	return dataB;
	
}

// 웹 앱 원본 업로드 타입 (타임라인상세보기)
function webappTypeB(dataA, dataB) { // 클릭해서 볼때
	
	var idx = dataB.indexOf("_") + 1;
	var dataB = dataB.substr(idx);
	
	if(dataA == '1') {
		dataB = "http://114.201.41.248:8084/turn/resources/upload/logs/" + dataB; // 모바일 이미지 출력 
	} else if (dataB.match("http")){
		dataB = dataB;
	} else {
		dataB = "displayLogs?fileName=/" + dataB; // 이미지 출력
	}
	
	return dataB;
}

// place 업로드타입

function webappTypeC(data) {
	var idx = data.indexOf("/") + 1;
	var thumbnail = "/s_" + data.substr(idx);
	return thumbnail;
}


// 타임라인 페이지정보
function getStartRecord(current,timeLineNum) {
	return ((current-1)*timeLineNum);
}

//로그 타입
function checkLogType(data) {
	var logtype = "";
	
	if(data == '1') {
		logtype = "Life-Log";
	} else if(data == '2')
		logtype = "Travel-Log";
	else if(data == '3'){
		logtype = "Step-Log";
	}
	return logtype;
}

// 이미지 체크 
function checkImageType(fileName) {
	var pattern = /jpg|gif|png|jpeg/i;
	return fileName.match(pattern);
}

// 날짜 데이터 파싱 
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

//날짜 데이터 파싱 2
function dateParse2(data) {
	var sysdate = new Date(data);
	var year = sysdate.getFullYear();
	var month = sysdate.getMonth()+1;
	var day = sysdate.getDate();
	return year + "" 
			+ (month < 10 ? "0" + month : month) + ""
			+ (day < 10 ? "0" + day : day);
}

function dateParse3(data) {
	
	return data.split("-").join("");
	
}

function animations() {
	
	$("#allCell").animate({
		opacity: 1
	}, 1000);
	
	setTimeout(function() {
		$("#imgToggle").attr("style", "visibility:visible");
	}, 1000);
	
}


/***************** Lightbox 부분 *******************/

function openLightBox() {
	$(".lightBox").fadeIn();
	$(".lightBox").css("display","block");
	$(".menu-vertical").parent().css("overflow","hidden");
	$(".menu-vertical").parent().css("padding-right","17px");
}

function closeLightBox() {
	$(".lightBox-content").empty();
	$(".lightBox").fadeOut();
	$(".lightBox").css("display","none");
	$(".menu-vertical").parent().removeAttr("style");
}

function closeBackground() {
	$(".lightBox-content").empty();
	$(".lightBox").fadeOut();
	$(".lightBox").css("display","none");
	$(".menu-vertical").parent().removeAttr("style");
}


