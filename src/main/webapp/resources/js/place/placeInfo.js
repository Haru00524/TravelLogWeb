$(document).ready(function(){
	
	var status = 0; // 검색상태 확인
	var search = $(".datas").val(); // 검색 디폴트 데이터
	var local;
	var thema;
	
	var totalCount = 0; // 페이징 전체카운트 ajax 95개  = 변수 
	var pageCount = 10; // 현재페이지 정보 // 상수고정 
	var curentPage = 1; // 현재페이지 // 변수 
	var recordPage = 20; // 보여줄 레코드갯수 // 고정
	var startPage;
	var endPage;
	var prev;
	var next;
	
	// 장소정보 디폴트 실행
	dataLoad();
	
	// 장소클릭 디폴트 모든장소
	$(".locals").on("click",function(){
			if($(this).hasClass("turntive")) {
				return false;
			} 
			
			$(".locals").removeClass("turntive");
			$(this).addClass("turntive");
			curentPage = 1;
			dataLoad();
			
	});
	
	// 장소클릭이벤트 모든테마
	$(".thema").on("click",function(){
			if($(this).hasClass("turntiveT")) {
				return false;
			} 
			
			$(".thema").removeClass("turntiveT");
			$(this).addClass("turntiveT");
			curentPage = 1;
			dataLoad();
	});
	
	// 검색정보를 담는다
	$(".searchon").on("click", function() {
		curentPage = 1;
		status = 1;
		dataLoad();
	});
	
	// 포커싱
	$(".datas").focus(function(){
		$(this).attr("placeholder","");
	});
	
	// 검색결과 닫기함수
	$(".sresult").on('click','.t-close', function() {
		curentPage = 1;
		search = "";
		$(".sresult").html("");
		dataLoad();
		
	});
	
	// 키입력함수
	$("#keyup").keyup(function(e){
		if(e.keyCode == 13) {
			curentPage = 1;
			status = 1;
			dataLoad()
		}
	});
	
	// 장소정보 읽기 
	$(".placeInfos").on("click",".infoboard",function(){
		var postNum = $(this).attr("data-num");
		var pageTarget = "./placeRead?post=" + postNum;
		window.open(pageTarget,"_blank"); // 새창에서 열기
	});
	
	
	function dataLoad() {
		
		local = $(".turntive").attr("data-local"); // 로컬의 데이터 정보를 가져옴
		thema = $(".turntiveT").attr("data-thema");
		if(status == 1) {
			search = $(".datas").val();
			$(".sresult").html("검색결과 : &Prime;" + search  + "&Prime;&nbsp;" + "<spna class='t-close'><i class='fa fa-close fa-border'></i><span>");
			status = 0;
		}
		
		pagenation(); // 토탈카운트 정보
		var startRecord = getStartRecord(curentPage,recordPage); // 페이지조회정보
		calculate();
		
		$(".delelement").remove(); // 엘리먼트 삭제
		$(".pagination").remove();
		
		$.ajax({
			type : 'GET',
			url : 'placeInfoList',
			data : { 
				startRecord : startRecord, 
				recordPage : recordPage,
				local_value : local,
				thema_value : thema,
				search_value : search
			},
			success : function(data) { // @ResponseBody
				console.log(data);
				var cnt = data.length;
				var elemen = ""; // 엘리먼트 생성&상태 데이터
				for(var i=0; i<cnt; i++) {
					elemen = "<div class='delelement col-md-3 col-sm-3 margin-bottom-30'>" + 
						   	 	"<div class='infoboard turn-shadow' data-num='" + data[i].place_code + "'>" + 
						   	 		"<div class='images'>" +
										"<img class='img-responsive' src='displayFile?fileName=" + thumb(data[i].place_img) + "' alt=''>" + 
									"</div>" + 
									"<div class='padding-10'>" + 
										"<h4 class='text-left'>" + textTitle(data[i].place_name) + "</h4>" + 
										"<h6 class='text-left'>" + data[i].place_type + "</h6>" + 
										"<p class='text-left'>" + textSize(data[i].place_content) + "</p>" + 
										"<ul class='text-left size-12 list-inline list-separator'>" + 
						                    "<li><i class='fa fa-calendar-check-o'></i>" + dateParse(data[i].add_date) +
						                    "<li><i class='fa fa-eye'></i>" + data[i].view;
											if(data[i].wish != null) {
												elemen += "<li><i class='wishiconB'></i>" + data[i].wishcount;
											}
											else {
												elemen += "<li><i class='wishiconA'></i>" + data[i].wishcount;
											}
					elemen +=	    	"</ul>" +
					                "</div>" +
						        "</div>" +
						      "</div>";
					$(".placeInfos").append(elemen);
					
				}
				
				if(cnt == 0) {
					elemen = "<div class='delelement margin-top-60 margin-bottom-80 col-md-8 col-md-offset-2'>" + 
				        	 	"<div>" +
				        	 		"<h2>장소정보가 존재하지않습니다.</h2>" +
								"</div>" +
							 "</div>";
			        $(".placeInfos").append(elemen);
				}
				
				// 페이징정보
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
				$(".placePaging").append(paging);
			}
		})
		
		$(".totalCount").html("검색정보 : " + totalCount + "개");
		$(".datas").val("");
		$(".datas").attr("placeholder", "원하는 카테고리를 선택후 찾고싶은 장소를 입력해보세요.");
		
	}
	
	// 썸네일 변환
	function thumb(data) {
		var idx = data.indexOf("/") + 1;
		var idxA = "/s_" + data.substr(idx);
		return idxA;
	} 
	
	// 타이틀 사이즈 변환
	function textTitle(data) {
		var text = data;
		if(text.length > 10)
			text = text.substr(0, 10) + "..."
		return text;
	}
	
	// 텍스트 사이즈 변환
	function textSize(data) {
		var text = data.split("<br>").join("\n")
		if(text.length > 15) {
			text = text.substr(0, 15) + "...";
		}
		return text;
	}
	
	// 날짜 데이터 파싱 
	function dateParse(data) {
		var sysdate = new Date(data);
		var year = sysdate.getFullYear();
		var month = sysdate.getMonth()+1;
		var day = sysdate.getDate();
		return year + "-" 
				+ (month < 10 ? "0" + month : month) + "-"
				+ (day < 10 ? "0" + day : day);

	}
	
// ======================================== 페이지네이션 함수 ===================================== //
	
	$(".placePaging").on("click", '.onlink', function(){
		// 전역변수에 저장
		curentPage = parseInt($(this).attr("data-page"));
		dataLoad();
	});
	
	// pagination.. 
	function pagenation() { 
		
		$.ajax({
			type : 'GET',
			url : 'listPaging',
			async:false,
			data : {
				local_value : local,
				thema_value : thema,
				search_value : search
			},
			success : function(data) { // @ResponseBody
				totalCount = data;
			}, error : function() {
				alert("error!!")
			}
			
		});
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