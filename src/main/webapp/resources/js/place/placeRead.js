$(document).ready(function(){
	
	var starIndex = 0;
	var starIndexE = 0;
	var id = $(".reply-group").attr("data-session");
	
	$(".wishEvent").on("click",".wishon", function(){
		var thisDiv = $(this);
		var place = $(this).attr("data-place");
		
		if(id == "") {
			alert("로그인후 이용해주십시오");
			return false;
		}
		
		$.ajax({
			type : 'GET',
			url : 'wishlike',
			data : {
				place : place,
				state : 1
			}, 
			success : function(data) { // @ResponseBody
				thisDiv.addClass("wishoff");
				thisDiv.removeClass("wishon");
				thisDiv.text(data);
			}, error:function(){
				alert("에러다");
			}
		})
		
	});
	
	$(".wishEvent").on("click",".wishoff",function(){
		var thisDiv = $(this);
		var place = $(this).attr("data-place");
		
		$.ajax({
			type : 'GET',
			url : 'wishlike',
			data : {
				place : place,
				state : 0
			}, 
			success : function(data) { // @ResponseBody
				thisDiv.addClass("wishon");
				thisDiv.removeClass("wishoff");
				thisDiv.text(data);
			}, error:function(){
				alert("에러다");
			}
		})
		
	});
	
	// 마우스 오버 별점
	$(".fastar").mouseover(function(){
		
		$(".fastar").removeClass("fa-star");
		$(".fastar").addClass("fa-star-o");
		var overindex = $(this).attr("data-scroe");
		
		$(".fastar").each(function(index){
			$(this).removeClass("fa-star-o");
			$(this).addClass("fa-star");
			if(overindex == index) {
				return false;
			} else {
		
			}
		});
		
		starIndex = overindex; // 해당 점수인덱스
	});
	
	$(".reply-group").on("mouseover",".fastar2",function(){
		
		$(".fastar2").removeClass("fa-star");
		$(".fastar2").addClass("fa-star-o");
		var overindex = $(this).attr("data-scroe");
		
		$(".fastar2").each(function(index){
			$(this).removeClass("fa-star-o");
			$(this).addClass("fa-star");
			if(overindex == index) {
				return false;
			} else {
		
			}
		});
		
		starIndexE = overindex; // 해당 점수인덱스
	});
	
	
	
	$(".write-buttom").click(function(){
		
		if(id == "") {
			alert("로그인후 이용하여주십시오");
			return false;
		}
		
		var boardCode = $(this).attr("data-place"); // 해당게시물 번호
		var replyText = $(".reply-write").val(); // 리플텍스트 
		var starScore = parseInt(starIndex) + 1; // 평점 점수
		
		if(replyText.length > 5) {
			replyCommand(boardCode, starScore, 1, replyText, 0);
		} else {
			alert("글자수는 5자 이상 가능합니다.")
		}
		
	});
	
	
	$(".reply-group").on("click",".modify-buttom",function(){
		
		var replyno = $(this).attr("data-reply-code");
		var board = $(this).attr("data-board");
		var target = $(this).parent().next().next().next();
		var cookieData = target.text();
		
		if($(this).text() == "수정") {
			
			var element = "<div class='reply-item-box'>" + 
								"<textarea required='required' maxlength='5000' rows='3' class='form-control reply-margin reply-modiy' id='comment'>" + cookieData + "</textarea>" + 
								"<div class='pull-left'>" + 
									"<div class='size-20 coment-star-set2'>" + 
										"<b>재평가  : </b>" + 
										"<i class='fa fa-star fastar2' data-scroe='0' style='margin-left:5px;'></i>" +
										"<i class='fa fa-star-o fastar2' data-scroe='1' style='margin-left:5px;'></i>" +
										"<i class='fa fa-star-o fastar2' data-scroe='2' style='margin-left:5px;'></i>" +
										"<i class='fa fa-star-o fastar2' data-scroe='3' style='margin-left:5px;'></i>" +
										"<i class='fa fa-star-o fastar2' data-scroe='4' style='margin-left:5px;'></i>" +
									"</div>" +
								"</div>" +
								"<button class='btn btn-3d btn-reveal btn-black pull-right modify-on' data-reply='" + replyno + "' data-board='" + board + "'>" +
									"<i class='fa fa-check'></i>" + 
									"<span>수정 하기</span>" + 
								"</button>" + 
							"</div>";
			
			$(this).text("수정취소");
			target.empty();
			target.append(element);
			
		} else {
			
			var text = target.children().children("textarea").val();
			$(this).text("수정");
			target.empty();
			target.html(text);
			
		}
		
	});
	
	$(".reply-group").on("click",".modify-on",function(){
		var boardCode = $(this).attr("data-board");
		var replyno = $(this).attr("data-reply"); //
		var starScore = parseInt(starIndexE) + 1;
		var replyText = $(this).prev().prev().val();
		
		replyCommand(boardCode, starScore, 2, replyText, replyno);
		
	});
	
	
	$(".reply-group").on("click",".remove-buttom",function(){
		
		var replyno = $(this).attr("data-reply-code");
		var boardCode = $(this).attr("data-board");
		
		if(confirm("댓글을 삭제하시겠습니까?")) {
			
			replyCommand(boardCode, 0, 3, null, replyno);
			
		}
		
	});
	
	
	
	function replyCommand(boardCode, starScore, type, replyText, replyno) {
		
		$.ajax({
			type : 'POST',
			url : 'placeReplyCommend',
			data : {
				code : boardCode,
				score : starScore,
				type : type,
				text : replyText,
				replyno : replyno
			}, 
			success : function(reply) { // @ResponseBody
				
				console.log(reply);
				
				$(".reply-group").empty();
				
				var replyRander = "";
				
				replyRander = "<label class='size-20 reply-margin'>댓글 " + reply.length + " 개</label>";
				
				for(var i=0; i<reply.length; i++) {
					
					replyRander += "<div class='comment-item pull-left reply-margin'>" + 
										"<span class='user-avatar'>" + 
											"<img class='pull-left p-profile' src='displayProfile?fileName=" + reply[i].user_profile + "' width='64' height='64' alt=''>" + 
										"</span>" + 
						
										"<div class='media-body'>" + 
											"<div class='pull-right size-20 star-set'>" + scores(reply[i].place_score) + "</div>" + 
											"<h4 class='zerobottom bold inline-block'>" + reply[i].user_id + "</h4>"; 
											if(id == reply[i].user_id) {
												replyRander +=  "<div class='inline-block'>" + 
																	"<button class='btn btn-aqua btn-xs modify-buttom' style='margin-left:20px;' data-reply-code='" + reply[i].board_code + "' data-board='" + reply[i].place_reply_code + "'>수정</button>" + 
																	"<button class='btn btn-brown btn-xs remove-buttom' style='margin-left:4px;' data-reply-code='" + reply[i].board_code + "' data-board='" + reply[i].place_reply_code + "'>삭제</button>" + 
																"</div>";
											}
					replyRander +=  		"<div></div>" + 
											"<b>" + dateParse(reply[i].board_date) + "</b>" + 
											"<div class='place-reply'>" + reply[i].board_content +"</div>" + 
										"</div>" + 
									"</div>";
											
				}
				
				$(".reply-group").append(replyRander);
				
			}, error:function(){
				alert("에러다");
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

function scores(data) {
	var star1 = "<i class='fa fa-star-o' style='margin-left: 5px !important;'></i>";
	var star2 = "<i class='fa fa-star' style='margin-left: 5px !important;'></i>";
	var score = data - 1; 
	var element = "";
	
	for(var i=0; i<5; i++) {
		if(score >= i) {
			element += star2;
		} else {
			element += star1;
		}
	}
	
	return element;
}