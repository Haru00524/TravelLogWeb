$(document).ready(function(){
	
	var alarm = "";
	var groupAlarm = $('#groupAlarm').attr('value');
	var alarmCnt = 0;
	var countA = 0;
 	var groupCode = "";
	var user_id = "";
	var count = 0;
	var ClassNam = "";
	
	// 3초마다 알림 불러오기
	playAlert = setInterval(function() {
		console.log("알림 갯수:"+$("div[name=alarm]").length);
		alarmCnt = $("div[name=alarm]").length;
		
		$.ajax({
			url: "invite",
			type: "GET",
			data: {alarmCnt: alarmCnt},
			success: function(data){
				if(groupAlarm == ""){
					console.log("로그인 안했다");
				}else{
					if($(".alarm").length > 0){
						$(".alarm").remove();
						console.log("지웠다");
					}
					if($(".CountA").length > 0){
						$(".CountA").remove();
					}
					if(alarmCnt != 0){
						countA = "<span class='CountA' class='badge badge-aqua btn-xs badge-corner'>" + alarmCnt + "</span>";
						$("#CountA").append(countA);
					}
					
					for(var i =0; i<data.length; i++){
						
						if(groupAlarm == data[i].user_id && data[i].invite_user != groupAlarm){
							alarm =
								"<div class='alarm' name='alarm' class='quick-cart-wrapper' style='margin:10px 0px 10px 0px; border:1px solid #c1a7a7; padding:10px;'>" + 
									"<img src='displayProfile?fileName=" + data[i].user_profile + "' class='pull-left' style='margin: 0 10px 10px 0; width:50px; height:50px;' alt=''>" +
									"<span style='position:absolute; font-size:12px; padding: 5px;'><b> " + data[i].invite_user + "님</b>께서 <br> 그룹여행에  초대하셨습니다.</span>" +
									"<input class='Del_user_id' name='user_id' value='" + data[i].user_id +"' style='display:none'/>" +
									"<input style='margin:45px 0px 0px 75px;' class='btn btn-green btn-3d btn-xs accept sure' type='submit' value='수락' name='" + data[i].group_Code + "'/>" +
									"<input style='margin:45px 0px 0px 5px;' class='btn btn-teal btn-3d btn-xs cancel nope' type='submit' value='거절' name='" + data[i].group_Code + "'/>" +
								"</div>";
							
							$("#alarm").append(alarm);
							console.log("생성했다");
						}
					}
				}
			}
		});
	}, 10000);
	
	// 알람 수락버튼 누를 시 
	$("#alarm").on("click", ".sure", function(){
//		alert("수락한다.");
		groupCode = $(this).attr('name');
		user_id = $('.Del_user_id').attr('value');
		
//		alert(groupCode);
		
		$.ajax({
			url: "groupTravel",
			type: "POST",
			data: {group: groupCode, user: user_id},
			success: function(data){
//				alert(data.travel_Title);
				window.location = "scheduleSet?title="+ data.travel_Title +"&&groupCode=" + data.group_Code +"&&scheduleDate="+data.start_Date+"+-+"+data.end_Date+"&&local="+data.local;
			}
		});
	});
	
	// 알람 거절 버튼 누를 시
	$("#alarm").on("click",".nope", function(){
//		alert("거절한다.");
		
		groupCode = $(this).attr('name');
		user_id = $('.Del_user_id').attr('value');
		
//		alert(groupCode);
		
		$.ajax({
			url: "group_alarm_delete",
			type: "POST",
			data: {group: groupCode, user: user_id},
			success: function(data){
				alert("갔다왔다!");
			}
		});
	});
	// ========여기까지 알람관련======== //
	
	// 일정등록버튼클릭했을때 
	$("#modals").click(function(){
		
		$(".turn-modal").css("display","none");
	});
	
	// 친구목록의 그룹창을 닫았을시
	$(".closeGroup").click(function(){
		
		$(".turn-modal").css("display","none");
		count = 0;
	});
	
	// 그룹추가 버튼을 클릭했을시
	$("#addGroup").click(function(){
		if(count == 0) {
			$(".turn-modal").css("display","block");
			$(".friend-list").empty();
			
			friend_list();
			count++;
			
		} else {
			$(".turn-modal").css("display","none");
			count = 0;
		}
	});
	
	// 모달버튼이 사라질때 이벤트 발생함수
	$('#myModal').on('hidden.bs.modal', function (e) {
		  count = 0;
	});
	
	// =========== 페이지 이동 함수 =============== // 
	
	$(".nextPage").on("click",function(){
		
		if(confirm("다음으로 넘어가시겠습니까?")) {
			
			$('#submitData').attr('method','post');
			$('#submitData').attr('action','scheduleSet');
			$('#submitData')[0].submit();
			
			// 페이지이동
		}
	});
	
});


//친구 리스트를 띄움
function friend_list(){
   
	$.ajax({
		url : "friend_list" ,
		type : "POST" ,
		success : function(data){
			var content = "<div>" ;
			for(var i=0 ; i<data.length ; i++) {
				content += "<div class='friendList' data-userId='"+data[i].user_id +"' data-check='0' onClick='select(this)' style='width : 100% ;height : 62px; border:1px solid black; margin-bottom : 8px; padding : 5px; cursor: pointer;'>" 
						+ "<div class='friendProfile' style='float:left; width:65px; height:50px;  line-height : 50px'><img src='displayProfile?fileName=" + data[i].user_profile + "' style='width:65px ;height:50px'></div>"
						+ "<p style='line-height : 50px'>"+ data[i].user_id +"</p>"
						+ "</div>";
            
            
			}
			content += "</div>"
            console.log("content : " + content)
         
            $(".friend-list").append(content);
		},
		error : function(){
          alert("에러");
		}
   })
}

// 친구목록에서 같이 여행할 친구 추가! 할때!
// data-check를 통해서 1이면 추가, 0이면 제외 => 1인 친구들을 다 array에 넣어줌.
function select(user_Id){

	if($(user_Id).attr("data-check")==0){
         
		$(user_Id).attr("data-check",1);
		$(user_Id).css("background", "#9e9e9e");
		$(user_Id).css("color","white");
      
		friend_inputCheck($(user_Id).attr("data-userId"));
       
         
	} else if($(user_Id).attr("data-check") == 1){
		$(user_Id).attr("data-check",0);
		$(user_Id).css("background", "") ;
		$(user_Id).css("color","black") ;
		var a = $(user_Id).attr("data-userId")

		$("#"+a).remove() ;
      
	}

}

function friend_inputCheck(user_Id){
   
	var submitData = $("#submitData");
	$("<input type='hidden' id='"+user_Id+"' name='friend' value='"+user_Id+"' checked >").appendTo(submitData);
   
}