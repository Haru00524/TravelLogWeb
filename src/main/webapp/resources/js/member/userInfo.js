var group_code ;
var targetparent ;
var planPlace = [] ;
var map;
var mapId ;
var markers ;

// 사용자의 여행계획 리스트를 불러주는 페이지로 이동 
function travel_plan_list(){
	window.location = "userScheduleList" ;
}

// 계획중인 리스트들을 불러줌.
function going_plan(going){
	
	var box = $("#user_group_list_box") ;
	var count = 0; 
	
	$.ajax({
		url : "userScheduleListCheck" ,
		type : "POST",
		data : {
			state : going 
		},
		dataType : "json",
		success : function(data){
			box.empty();
			if(data.length>0){
				for(var i=0; i<data.length ; i++){
					count++ ;
					$("<div class='user_group_list' data-code='"+ data[i].group_Code+"' data-name='"+ data[i].travel_Title+"' data-start='"+ data[i].start_Date+"' data-end='"+ data[i].end_Date+"' onclick='planDetail($(this))'>"
							+"<div class='plan-list-img'>" 
								+"<div class='plan-list-delete' data-toggle='modal' data-whatever='"+data[i].group_Code+"'>X</div>"
							+"</div>"
							+"<div class='plan-content'>"
								+"<div class='plan-list-title'>"+data[i].travel_Title+"</div>"
								+"<div class='plan-date'>" 
									+"<span>"+data[i].start_Date+"</span>" 
									+"<span> ~ </span>"
									+"<span>"+data[i].end_Date+"</span>"
								+"</div>"
							+"</div>" 
						+"</div>").appendTo(box) ;
				}
			}
			else{
				$("<h4 class='list-empty'>진행중인 계획이 없습니다</h4>").appendTo(box);
			}
		}
	})
}

// 완료된 일정을 불러줌
function finish_plan(finish){
	
	var box = $("#user_group_list_box") ;
	var count = 0 ;
	
	$.ajax({
		url : "userScheduleListCheck" ,
		type : "POST",
		data : {
			state : finish 
		},
		dataType : "json",
		success : function(data){
			box.empty();
			
			if(data.length > 0){
				for(var i=0; i<data.length ; i++){
					count++ ;
					$("<div class='user_group_list' data-code='"+ data[i].group_Code+"' data-name='"+ data[i].travel_Title+"' data-start='"+ data[i].start_Date+"' data-end='"+ data[i].end_Date+"' onclick='planDetail($(this))'>"
							+"<div class='plan-list-img'>" 
								+"<div class='plan-list-delete' data-toggle='modal' data-whatever='"+data[i].group_Code+"'>X</div>"
							+"</div>"
							+"<div class='plan-content'>"
								+"<div class='plan-list-title'>"+data[i].travel_Title+"</div>"
								+"<div class='plan-date'>" 
									+"<span>"+data[i].start_Date+"</span>" 
									+"<span> ~ </span>"
									+"<span>"+data[i].end_Date+"</span>"
								+"</div>"
							+"</div>" 
						+"</div>").appendTo(box) ;
				}
			}
			else{
				$("<h4 class='list-empty'>완료된 계획이 없습니다</h4>").appendTo(box);
			}
			
		}
	})
	
	
}

$(document).ready(function() {
	
	$("#user_group_list_box").on("click", " .plan-list-delete" ,function(event){
		console.log($(this));
		alert($(this));
		event.stopImmediatePropagation();
	
		$("#planlist-delete").modal();
		
		var target = $(this).attr("data-whatever");
		console.log("targe  : " + target );
		targetparent = $(this).parent().parent();
		group_code = $(this).attr("data-whatever"); 
	});
	
	mapId = document.getElementById("plan_map");
	if(mapId == null) {
		return false;
	}
	map = new naver.maps.Map(mapId);
	
	var count = 0 ;
	planPlace = [] ;
	$(".plan-day-list").each(function(){
		planPlace[count] = new Array(2) ;
		console.log($(this).attr("data-lat"));
		planPlace[count][0] = $(this).attr("data-lat") ;
		planPlace[count][1] = $(this).attr("data-lng") ;
		
		count++ ;
		
	})

	placeMarker();

});

// 리스트를 삭제
function plan_confirm(){
	
	$.ajax({
		url : "plan_list_delete",
		type : "POST",
		data : {
			group  : group_code 
		}, 
		success : function(){
		
			targetparent.remove();
		}
	})
}

//상세 일정 페이지로 넘어가기
function planDetail(plan){
	console.log(plan.children().eq(1))
    var aa = plan.children().eq(0).attr("class") ;
	console.log(aa);
	console.log(event.target.className) ;
	
	// 이중이벤트 방지
	if(event.target.className != aa) return false ;
	
	var group = plan.attr("data-code");
	var title = plan.attr("data-name");
	var start = plan.attr("data-start");
	var end = plan.attr("data-end");
	window.location = "userPlanDetail?group_Code=" + group +"&&travel_Title="+title+"&&startDate="+start+"&&endDate="+end;

}

// 여행일정부분 보여주기
function travel_plan() {
	$("#plan_contain").empty() ;
	
	var group_code = $("#plan-title").attr("data-code");
	
	$.ajax({
		url : "plandetailAsnyc" ,
		type : "POST" ,
		data : {
			group_code : group_code 
		},
		dataType: "json",
		success : function(data){
			if(data.days.length > 0){
				var str ="<div id='plan_list_contain'>";
				for(var i=0 ; i<data.list.length ; i++){
					str +="<div class='plan-day-list' data-lat='"+ data.list[i].place_lat +"' data-lng='"+ data.list[i].place_lng +"' >";
					if(data.list.length == 1 || data.days[i-1] != data.days[i]){
						str+= "<div class='plan-day-select'>DAY"+ data.days[i]+"</div>"
					}
					str+="<div class='plan-day-content'>"
						 	+ "<div class='plan-place-priority'>"+data.list[i].travel_priority+"</div>" 
						 	+ "<div class='plan-place-img'><img src='displayFile?fileName="+thumb(data.list[i].place_img)+"'></div>"
						 	+ "<div class='plan-place-name'>"+data.list[i].place_name+"</div>"
						 +"</div></div>";
				}
				str += "</div><div id='plan_map'>"
				+"</div>";
				console.log(str);
				
				$("#plan_contain").append(str) ;
				
				//지도 불러주기
				mapId = document.getElementById("plan_map") ;
				map = new naver.maps.Map(mapId) ;
				
				// 마커띄워주기
				placeMarker();
				
			}
			console.log(data.days[0]) ;
			console.log(data.list[0].place_name);
		},
		error : function(data){
			alert("ddd");
		}
		
	})
	
	
	
}
function travel_cost(){
	$('#plan_contain').empty();
	var group_code = $("#plan-title").attr("data-code");
	
	console.log('gro : ' + group_code);
	$.ajax({
		url : "travel_cost_list" ,
		type : "POST",
		data : {
			group : group_code
		},
		dataType: "json",
		success :function(data){
			console.log(data) ;
			console.log(data.sc_Division);
			var plan_contain = $("#plan_contain"); 
			var division="<div>" 
						+"<h3>여행 경비 구분 : "+ data.sc_Division+"</h3>" 
					+"</div>" 
					
			plan_contain.append(division) ;		
					
			if(data.list.length > 0){
			
				var	str ="<table class='expense_table'>"
						  	+"<tr class='expense_tr'>" 
						  		+"<th>사용자 아이디</th>"
						  		+"<th>지출 내역</th>"
						  		+"<th>지출 비용</th>"
						  		+"<th>지출 날짜</th>"
						  	+"</tr>"
						  	
				for(var i=0 ; i<data.length; i++){
					str += "<tr class='expense_tr'>"
								+"<td>"+ data.list[i].user_id  +"</td>"
								+"<td>"+data.list[i].expense_Content+"</td>"
								+"<td>"+data.list[i].expense_Cost+"</td>"
								+"<td>"+data.list[i].expense_Date+"</td>"
						  +"</tr>"
				}
					
					str+="</table>"
				
			
			}else{
				
				$("<h4 class='list-empty'>완료된 계획이 없습니다</h4>").appendTo(plan_contain);
			}
			
			
		},
		error : function(){
			console.log("Dd머야");
		}
	})
}

function travel_supplies(){
	$('#plan_contain').empty();
	
	var group_code = $("#plan-title").attr("data-code");
	
	console.log('gro : ' + group_code);
	$.ajax({
		url : "travel_supplies_list" ,
		type : "POST",
		data : {
			group : group_code
		},
		success :function(data){
			var plan_contain = $("#plan_contain"); 
			if(data.length > 0){
				
				var str = "<h4>개인</h4>"
						  +"<table class='material_table'>" ;
				var i=0;
					for(; i<data.length ; i++){
						if(i%4==0){
							str+="<tr class='material_tr'>";
						}
						str+= "<td class='material_td'>"+data[i].material_name+"</td>";
						if(i%4==3){
							str += "</tr>";
						}
					}
					
					$(str).appendTo(plan_contain);
						
			}else{
				$("<h4 class='list-empty'>완료된 계획이 없습니다</h4>").appendTo(plan_contain);
			}
			
			console.log(data) ;
		},
		error : function(){
			console.log("Dd머야");
		}
	})
}
function travel_modify(){
	var group_code = $("#plan-title").attr("data-code");
	
	$.ajax({
		url : "travel_modify",
		type :"POST",
		data : {
			group : group_code 
		},
		success : function(data){
			window.location = "scheduleSet?title="+ data.travel_Title +"&&groupCode=" + data.group_Code +"&&scheduleDate="+data.start_Date+"+-+"+data.end_Date+"&&local="+data.local;
		}
	})
}
// 마커 찍어주기 
function placeMarker(){
	if(planPlace.length == 0){
		return false ;
	}
	
	markers = [] ;
	
	 map = new naver.maps.Map(mapId , {
         center : new naver.maps.LatLng(planPlace[0][0],planPlace[0][1]) ,
         zoom : 5
      })
	
	for(var i=0 ; i<planPlace.length ; i++){
		
		var marker = new naver.maps.Marker({
			position : new naver.maps.LatLng(planPlace[i][0],planPlace[i][1]),
			map : map,
			icon : {
                content: [
                   '<div class="cs_mapbridge marker">',
                '<img src="./resources/img/map/place_marker.png">',
                '<div class="priority2">'+(i+1)+'</div>',
                '</div>'
             ].join(''),
             size: new naver.maps.Size(22, 35),
               anchor: new naver.maps.Point(25,48)
           },
             zIndex : 100 
			
		})
		
		markers.push(marker) ;
	}
	

	
	naver.maps.Event.addListener(map,'idle', function(){
		updateMarkers(map, markers) ;
	})
	
	var polyline = new naver.maps.Polyline({
        map: map,
        path: [],
        endIcon: naver.maps.PointingIcon.OPEN_ARROW,
        //아이콘의 크기를 지정합니다. 단위는 픽셀입니다.
        endIconSize: 20,
        strokeColor: '#5347AA',
        strokeWeight: 2
    });
	
	for(var i=0 ; i<planPlace.length ; i++){
        var path = polyline.getPath() ;
        
        path.push(new naver.maps.LatLng(planPlace[i][0], planPlace[i][1]));
        console.log(path);
     }
	
	
}

function updateMarkers(map , markers) {
    var mapBounds = map.getBounds() ; // 현재 지도의 좌표경계
    var marker , position; 
    
    for(var i=0 ; i < markers.length ; i++ ){
       marker = markers[i] ;
       position = marker.getPosition(); // 마커의 위치 반환
       
       if(mapBounds.hasLatLng(position)){
          showMarker(map, marker) ;             
       }else {
          hideMarker(map, marker) ;
       }
    }
 }

function showMarker(map, marker) {

    if (marker.setMap()) return;
    marker.setMap(map);
}

function hideMarker(map, marker) {

    if (!marker.setMap()) return;
    marker.setMap(null);
}

//이미지 썸네일
function thumb(data){

 console.log(data)
 var idx = data.indexOf("/") + 1 ;
 var idxA = "/s_" + data.substr(idx) ;
 
 return idxA ;
}

function travel_timeline(){
	$('#plan_contain').empty();
	
	var group_code = $("#plan-title").attr("data-code");
	var start_Date = $("#plan-start").attr("data-code");
	var end_Date = $("#plan-end").attr("data-code");
	
	$.ajax({
		url: "travel_timeline",
		type: "GET",
		data: {group: group_code, start: start_Date, end: end_Date},
		success: function(data){
			
			for(var i =0; i<data.length; i++){
				var timestamp = data[i].board_date;
				var date = new Date(timestamp);
				var year = date.getFullYear();
				var month = date.getMonth()+1;
				var day = date.getDate();
				var hour = date.getHours();
				var min = date.getMinutes();
				var sec = date.getSeconds();
				var retVal =   year + "-" + (month < 10 ? "0" + month : month) + "-" 
				                        + (day < 10 ? "0" + day : day) + " " 
				                        + (hour < 10 ? "0" + hour : hour) + ":"
				                        + (min < 10 ? "0" + min : min) + ":" 
				                        + (sec < 10 ? "0" + sec : sec);
				var monthE = "";
				
				day = (day < 10 ? "0" + day : day);
				hour = (hour < 10 ? "0" + hour : hour);
				min = (min < 10 ? "0" + min : min);
				sec = (sec < 10 ? "0" + sec : sec);
				
			switch(month){
				case 01: monthE = "Jan"; break;
				case 02: monthE = "Feb"; break;
				case 03: monthE = "Mar"; break;
				case 04: monthE = "Apr"; break;
				case 05: monthE = "May"; break;
				case 06: monthE = "Jun"; break;
				case 07: monthE = "Jul"; break;
				case 08: monthE = "Aug"; break;
				case 09: monthE = "Sep"; break;
				case 10: monthE = "Oct"; break;
				case 11: monthE = "Nov"; break;
				case 12: monthE = "Dec"; break;
			default: break;
			
			}
			
			var content = data[i].board_content;
			var image = data[i].file_content;
			console.log(image);
			console.log(data[i]);
			
			var board_content = content.replace(/[<][^>]*[>]/gi, "");
			
			alarm = "<div class='timeline' style='cursor:default;border:none;box-shadow:none;'>" +
			"<div class='timeline-hline'></div>" +
			"<div class='blog-post-item' style='margin:0px 0px 0px 100px;'>" +
			"<div class='timeline-entry rounded' style='background-color:white;'>" +
			day + "<span>"+ monthE +"</span>" +
			"<div class='timeline-vline'></div>" +
			"</div>" +
/*			"<div class='flexslider' data-arrowNav='false' data-slideshowSpeed='3000'>" +
			"<ul class='slides'>" +
			"<li>" +
			"<a href='#'>" +
			'<img class="img-fluid" src="demo_files/images/720x400/1-min.jpg" width="600" height="399" alt="">' +
			"</a>" +
			"</li>" +
			'</ul>'+
			'</div>'+*/
			'<div style="background-color:white;padding:20px 0px 0px 0px;">' +
			'<h2><a href="">' + data[i].board_title + '</a></h2>'+
			'<ul class="blog-post-info list-inline">' + 
			'<li>'+
			'<a href="#">'+
			'<i class="fa fa-clock-o"></i>'+
			'<span class="font-lato">작성일: ' + retVal + '</span>'+
			'</a>'+
			'</li>'+
			'<li>'+
			'<a href="#">'+
			'<i class="fa fa-comment-o"></i>'+
			'<span class="font-lato">28 Comments</span>'+
			'</a>'+
			'</li>'+
/*			'<li>'+
			'<i class="fa fa-folder-open-o"></i>'+
			'<a class="category" href="#">'+
			'<span class="font-lato">Design</span>'+
			'</a>'+
			'<a class="category" href="#">'+
			'<span class="font-lato">Photography</span>'+
			'</a>'+
			'</li>'+*/
			'<li>'+
			'<a href="#">'+
			'<i class="fa fa-user"></i>'+
			'<span class="font-lato">' + data[i].user_id + '</span>'+
			'</a>'+
			'</li>'+
			'</ul>'+ "</div>" +
			'<p>' + board_content + '<p>' +
			'</div>';
			
			$("#plan_contain").append(alarm);
			}
		}
	});	
}

