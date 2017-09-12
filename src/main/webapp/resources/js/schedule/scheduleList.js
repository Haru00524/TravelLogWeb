///**
// * 
// */
//
//var socket;
//var groupCode ;
//var user_id ;
//var planDay ;
//
//$(document).ready(function(){
//	groupCode = $("#groupCode").text(); //schdulePageA groupCode
//	user_id = $("#user_id").text();
//	
//	socket = new SockJS("/turn/chat.sockjs");
//	socket.onopen = onOpen;
//	socket.onmessage = onMessage2;
//	socket.onclose = onClose;
//
//})
//
//	 
//function disconnect() {
//	socket.close();
//}
//	 
//function onOpen(evt) {
//	 
//}
//		 
//function onClose(evt) {
//	  
//}
//	 
//function onMessage2(evt) {
//	var data = evt.data; 
//	
//	console.log("messager admin kakao data : " + data );
//	modifyScheduleList(data);
//}
//	 
//	 
//function esend(day) {
//	
//	  alert("dddsend");
//	  planDay = day ;
//	  socket.send(planDay);
//	  console.log("day : " + day) ;
//}
//
//function modifyScheduleList(data){
//	console.log("admin, kakao");
//	
//	planDay = data ;
//	
//    $(".selectPlace").each(function(){
//        
//        var selectThis = $(this) ;
//        var select = $(this).attr("data-nal");
//        console.log("select  : " + select  + " planday : " + planDay) ;
//        if(select == planDay){
//           $(this).css("display", "block");
//           console.log("select = planDay");
//           $.ajax({
//              type : "POST" ,
//              url :  'planDayList',
//              data : {
//                 group : groupCode,
//                   plan : planDay
//              },
//              dataType : "json",
//              success :function(data){    
//                 selectThis.empty();
//                 for(var i=0 ; i<data.length; i++){
//                    $("<div data-code="+data[i].place_code+" data-pri="+ data[i].travel_priority +" data-lat="+ data[i].place_lat 
//                    +" data-lng="+ data[i].place_lng +" data-name="+ data[i].place_name + " style='width:100%; padding:5px; height:160px; background : #333333; border-bottom : 1px solid #3a3c3f'></div>")
//                    .addClass("planList")
//                    .addClass("priority")
//                    .append("<img src='displayFile?fileName="+ thumb(data[i].place_img) + "' style='width:150px ; height:150px'><span>"+data[i].place_name+"</span>")
//                    .append("<div class='planPlaceDelete' data-code='"+data[i].place_code+"'><a href='#'>삭제</a></div>") 
//                    .css("border-bottom","1px solid #3a3c3f").appendTo(selectThis) ;
//                 }
//                 
//                 
//              }
//           
//           })
//        }
//     
//     });
//}
//
//	 
//
//	

	