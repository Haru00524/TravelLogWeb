$(document).ready(function(){
	
	var users = $(".comments").attr("data-session"); // 세션데이터
	
	// comuList.jsp
	$(".comu_info").click(function(){
		event.preventDefault();
		
		var tagetPaging = getQuerystring("page");
		var keyword = getQuerykeyword("keyword");
		var targetPost = $(this).attr("href");
		
		self.location.href = "./comuRead?page=" + tagetPaging + "&post=" + targetPost + "&keyword=" + keyword;
	});
	
	// comuList.jsp / pagination
	$(".pagination li a").on("click",function(){
		event.preventDefault();
		var keyword = getQuerykeyword("keyword");
		var tagetPaging = $(this).attr("href");
		
		self.location.href = "./comuList?page=" + tagetPaging + "&keyword=" + keyword;
	});
	
	// comuRead.jsp
	$(".comuDel").click(function(){
		event.preventDefault();
		if(confirm("정말로 삭제하시겠습니까?")) {
			var delNum = $(".numberPost").attr("data-src");
			self.location.href = "./comuDel?post=" + delNum;
		}
	});
	
	// comuRead.jsp ====================== // 
	
	$(".write-buttom").click(function(){
		
		if(users == "") {
			alert("로그인후 이용해주십시오");
			return false;
		}
		
		var boardCode =  $(this).attr("data-board-num");
		var replyText = $(this).prev().val();
		
		replyCommand(boardCode, replyText, 0, 1);
		
	});
	
	$(".comments").on("click",".modify-buttom",function(){
		
		var name = $(this);
		var boardCode = $(this).attr("data-board"); // 게시글번호
		var replyno = $(this).attr("data-reply-code"); // 댓글번호
		var target = $(this).parent().next().next();
		
		if(name.text() == "수정") {
		
			var newElement = "<textarea maxlength='5000' rows='3' class='form-control margin-bottom-6'>" + target.text() + "</textarea>" + 
							 "<button class='btn btn-3d btn-reveal btn-black pull-right modify-on' data-reply-code='" + replyno + "' data-board='" + boardCode + "'>" + 
								 "<i class='fa fa-check'></i>" + 
								 "<span>수정 하기</span>" +
							 "</button>";
			
			name.text("수정취소");
			target.empty();
			target.append(newElement);
			
		} else {
			
			var text = target.children("textarea").val();
			$(this).text("수정");
			target.empty();
			target.html(text);
			
		}
		
	});
	
	$(".comments").on("click",".modify-on",function(){
		var boardCode = $(this).attr("data-board");
		var replyno = $(this).attr("data-reply-code");
		var replyText = $(this).prev().val();
		
		replyCommand(boardCode, replyText, replyno, 2);
		
	});
	
	$(".comments").on("click",".remove-buttom",function(){
		
		var boardCode = $(this).attr("data-board");
		var replyno = $(this).attr("data-reply-code");
		
		if(confirm("댓글을 삭제하시겠습니까?")) { 
			replyCommand(boardCode, null, replyno, 3);
		}
		
	});
	
	
	// 리플추천
	$(".comments").on("click",".replylike",function(){
		var element = $(this);
		var replyno = $(this).attr("data-replyno"); // 해당댓글번호
		var boardCode = $(this).attr("data-no"); // 해당게시글번호
		var likeConfirm = $(this).attr("data-like");
		
		if(users == "") {
			alert("로그인후 이용해주십시오.");
			return false;
		}		
		if(users == likeConfirm) {// 추천검증
			alert("이미 추천한 댓글입니다.");
			return false;
		}
		
		replyCommand(boardCode, null, replyno, 4);
		
	});

	
	
	function replyCommand(boardCode, replyText, replyno, type) {

		$.ajax({
			type : 'POST',
			url : 'comuReplyCommand',
			data : {
				code : boardCode,
				text : replyText,
				replyno : replyno,
				type : type
			}, 
			success : function(map) {
				
				console.log(map);
				
				var element = "";
				$(".comments").empty();
				
				element += "<h4 class='page-header size-20'>댓글 <span>" + map.replylist.length + "</span> 개</h4>";
				// 베스트 댓글 
				element += "<div class='rankItem'>";
				for(var j=0; j<map.rank.length; j++) {
					if(map.rank[j].count != 0) {
						element += "<div class='comuItem'>" + 
					 					"<span class='user-avatar'>" + 
											"<img class='pull-left media-object c-profile' src='displayProfile?fileName=" + map.rank[j].user_profile + "' width='64' height='64' alt=''>" + 
					 					"</span>" + 
										"<div class='media-body'>" + 											
											"<a class='pull-right size-20 replylike replyColor' data-no='" + map.rank[j].reply_code + "' data-replyno='" + map.rank[j].board_code + "' data-like='" + map.rank[j].confirm_id + "'><i class='fa fa-thumbs-o-up'></i> " + map.rank[j].count + "</a>" + 
											"<div class='pull-right topReply'>TOP " + (j+1) + "</div>" + 
											"<h4 class='media-heading bold size-20 nonbottom'>" + map.rank[j].user_id + "</h4>" + 
											"<b class='block'>" + dateParse(map.rank[j].board_date) + "</b>" + 
											"<div>" + map.rank[j].board_content + "</div>" + 
										"</div>" + 
					 				"</div>";
					} else {
						
					}
				}
				
				
				element += "</div><hr><div class='replyItem'>";
				
				// 일반댓글
				for(var i=0; i<map.replylist.length; i++) {
					element += "<div class='comuItem'>" + 
				 					"<span class='user-avatar'>" + 
										"<img class='pull-left media-object c-profile' src='displayProfile?fileName=" + map.replylist[i].user_profile + "' width='64' height='64' alt=''>" + 
				 					"</span>" + 
									"<div class='media-body'>" + 
										"<a class='pull-right size-20 replylike replyColor' data-no='" + map.replylist[i].reply_code + "' data-replyno='" + map.replylist[i].board_code + "' data-like='" + map.replylist[i].confirm_id + "'><i class='fa fa-thumbs-o-up'></i> " + map.replylist[i].count + "</a>" + 
										"<h4 class='media-heading bold inline-block size-20 nonbottom'>" + map.replylist[i].user_id + "</h4>";
										if(users == map.replylist[i].user_id) {
											element += "<div class='inline-block'>" + 
															"<button class='btn btn-aqua btn-xs modify-buttom' style='margin-left:19px;' data-reply-code='" + map.replylist[i].board_code + "' data-board='" + map.replylist[i].reply_code + "'>수정</button>" + 
															"<button class='btn btn-brown btn-xs remove-buttom' style='margin-left:4px;' data-reply-code='" + map.replylist[i].board_code + "' data-board='" + map.replylist[i].reply_code + "'>삭제</button>" + 
													   "</div>";
										}
					element += 			"<b class='block'>" + dateParse(map.replylist[i].board_date) + "</b>" + 
										"<div>" + map.replylist[i].board_content + "</div>" + 
									"</div>" + 
				 				"</div>";
				}
				
				element += "<div>";
				
				$(".comments").append(element);
				
			}, error:function(){
				alert("에러");
			}
			
		})
		
		
		
	}

	
});


function dateParse(data) {
	var sysdate = new Date(data);
	var year = sysdate.getFullYear();
	var month = sysdate.getMonth()+1;
	var day = sysdate.getDate();
	return year + "-" 
			+ (month < 10 ? "0" + month : month) + "-"
			+ (day < 10 ? "0" + day : day);
}

// 쿼리스트링 주소데이터
function getQuerystring(paramName){ 
	
	var tempUrl = window.location.search.substring(1); //url에서 처음부터 '?'까지 삭제 
	var tempArray = tempUrl.split('&'); // '&'을 기준으로 분리하기 
	
	if(tempUrl == "") {
		return 1;
	}
	
	for(var i=0; tempArray.length; i++) { 
		var keyValuePair = tempArray[i].split('='); // '=' 을 기준으로 분리하기
		
		if(keyValuePair[0] == paramName){ // keyValuePair[0] : 파라미터 명 
			// keyValuePair[1] : 파라미터 값 
			return keyValuePair[1];
		}
	}
}

function getQuerykeyword(paramName){ 
	
	var tempUrl = window.location.search.substring(1); //url에서 처음부터 '?'까지 삭제 
	var tempArray = tempUrl.split('&'); // '&'을 기준으로 분리하기 
	
	if(tempUrl == "") {
		return "";
	}
	
	for(var i=0; tempArray.length; i++) { 
		var keyValuePair = tempArray[i].split('='); // '=' 을 기준으로 분리하기
		
		if(keyValuePair[0] == paramName){ // keyValuePair[0] : 파라미터 명 
			// keyValuePair[1] : 파라미터 값 
			return keyValuePair[1];
		}
	}
}
