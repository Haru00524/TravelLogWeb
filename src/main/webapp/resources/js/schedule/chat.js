/**
 * 
 */
var wsocket;
var groupCode ;
var user_id ;
var scrollData;

$(document).ready(function(){
	groupCode = $("#groupCode").text(); //schdulePageA groupCode
	user_id = $("#user_id").text();

		// enter 버튼 누르면 글자가 쳐진다.
		$('#message').keypress(function(event){
			var keycode = (event.keyCode ? event.keyCode : event.which);
				
			if(keycode == '13'){
				send(); 
			}
			
			event.stopPropagation();
		  
		});
			  
		$('#sendBtn').click(function() {  send(); });
		

})


function chatConnect(){
	wsocket = new SockJS("/turn/chat.sockjs");
	wsocket.onopen = onOpen;
	wsocket.onmessage = onMessage;
	wsocket.onclose = onClose;
}
	 
function disconnect() {
	wsocket.close();
}
	 
function onOpen(evt) {
	 
}
		 
function onClose(evt) {
	  
}
	 
function onMessage(evt) {
	var data = evt.data;
	var afterStr = data.split(',');
	console.log("data : " + evt.data);
	appendMessage(afterStr);
	  
}
	 
	 
function send() {
	console.log("usefd00 :" +user_id);
	var msg = $("#message").val();
	
	
	console.log("msg :" +msg);
	wsocket.send(user_id+","+msg );
	$.ajax({
			type : 'POST',
			url : 'chatStore',
			data : {
				groupCode : groupCode,
				content : msg 
			},
			success : function(){
				console.log("");	
			},
			error : function(){
				window.alert("오류가 발생하였습니다yy") ;
			}
		})
		
		$("#message").val("");
		
		  
}

function appendMessage(msg) {
	console.log("1번"+msg[0] );
	console.log("2번"+msg[1] );
		 
	var date = new Date() ;
	date = date.getHours() +"."+date.getMinutes() ;
		
	var target = $("#chat") ;
	
	//msg[0] : 사용자 id
	if(msg[0] == user_id){
		
		var message = $("<div></div>").addClass("self").css("clear","both") ;

		$("<div></div>").addClass("avatar user").append("<i class='fa fa-user margin-right-6'></i><span>"+user_id+"</span>").appendTo(message) ;
		$("<div></div>").addClass("msg").append("<p>"+msg[1]+"</p>").append("<time>"+ date+"</time>").appendTo(message);
		
	}else{
		
		var message = $("<div></div>").addClass("other").css("clear","both") ;
			 
		$("<div></div>").addClass("avatar user").append("<i class='fa fa-user margin-right-6'></i><span>"+msg[0]+"</span>").appendTo(message) ;
		$("<div></div>").addClass("msg").append("<p>"+msg[1]+"</p>").append("<time>"+ date+"</time>").appendTo(message);
		
	} 
		message.appendTo(target);
		setScrollDown();
}
	 
function setScrollDown(){
	//alert($(".chat").height());
	$(".chat").scrollTop($(".chat").height()*999);
}
 
// chatting 창 보이게 하기
function chattingWrap(){ 

	var chat =  $("#chattingWrap");
			
	if(chat.attr("data-check") == 0){
		$("#chat").empty() ;
		chat.css("display","block");
		chat.attr("data-check",1) ;
		chatConnect() ;
	}
	else if(chat.attr("data-check") == 1){
		chat.css("display","none") ;
		chat.attr("data-check",0) ;
		disconnect();
	} 
		 
	
	$.ajax({
		url : "chatting",
		type : "POST",
		data : {
			groupCode : groupCode
		},
		success : function(data){
			if(data.length == 0){
				console.log("ddd");
			}else{
				console.log("dddd");
				for(var i=0; i<data.length ; i++){
					if(data[i].user_id == user_id){
						$("<div class='self'>" 
							+"<div class='avatar user'>" 
								+"<i class='fa fa-user margin-right-6'></i>"
								+"<span>"+data[i].user_id +"</span>"
							+"</div>"
						  +"<div class='msg'>"
								+"<p>"+data[i].chatting_content+"</p>"
								+"<time>"+data[i].chatting_date+"</time>"
						  +"</div>"
							+"</div>").appendTo($("#chat"));
						}else{
						$("<div class='other'>" 
							+"<div class='avatar user'>" 
								+"<i class='fa fa-user margin-right-6'></i>"
								+"<span>"+data[i].user_id +"</span>"
							+"</div>"
							+"<div class='msg'>"
								+"<p>"+data[i].chatting_content+"</p>"
								+"<time>"+data[i].chatting_date+"</time>"
							+"</div>"
						 +"</div>").appendTo($("#chat"));;
						}
					}
				}
				
			}
		}) 	
	 	
}