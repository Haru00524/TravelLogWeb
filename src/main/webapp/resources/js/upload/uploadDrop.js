$(document).ready(function(){	
	
	loadText();
	
	var hasBeenClicked = false; // 클릭정보
	
	// ******************************* upload.jsp & uploadSet.jsp ******************************* //
	
	$(".backon").click(function(){
		if(confirm("돌아가시겠습니까? - 현재 수정중인 정보가 모두사라집니다. ")) {
			
			var dataA = getQuerystring("page");
			var dataB = getQuerystring("recordPage");
			var dataC = getQuerystring("no");
			
			self.location.href = "./uploadRead?page=" + dataA + "&recordPage=" + dataB + "&no=" + dataC;
		}
	});
	
	$(".cancel").click(function(){
		if(confirm("작성을 취소하시겠습니까? - 현재 작성중인 정보가 모두사라집니다. ")) {
			self.location.href = "./uploadList";
		}
	});
	
	$('#btnsub').on('click', function() {
		hasBeenClicked = true;
		if($(".uploadedList > div").hasClass("classCheck")) { // 
			$('#textbr')[0].submit();
			if($('#btnsub').val() == '등록하기')
				alert("등록이 완료되었습니다.");
			else 
				alert("수정이 완료되었습니다.");
		} else {
			alert("이미지를 하나이상 선택하여주십시오.");
		}
		
	});
	
	// 텍스트 로드 역태그 바꿈
	function loadText() {
		var textbr = $('#place_content').val();
		textbr = textbr.split("<br>").join("\n"); // text 태그바꿈
		$('#place_content').val(textbr);
	}
	
	
	// 다중 일반업로드 
	$('.fileUplodeBtn').on("change", function(){
		// this.files[0]
		// 그외 확장자 제한
		if(!checkImageType((this.files[0].name))){
			alert("파일형식은 이미지 파일만 가능합니다.");
			$(".fileUplodeBtn").val("");
			return false;
		}
		
		var count = this.files.length;
		for(var i=0; i<count; i++) {
			var formData = new FormData();
			formData.append("file", this.files[i]);
			console.log(this.files[i]);
			$.ajax({
				url: "./upload",
				data: formData,
				dataType: "text",
				processData: false,
				// contentType: true => application/x-www-form-urlencoded, 
	            //              false => multipart/form-data
				contentType: false,
				type: "POST",
				success: function(data) {
					var str ="";
					// http://localhost:8082/turn/displayFile?fileName=/2017/04/13/23786db8-a717-4c08-8d77-c6668265e168_muming.png
					str = "<div class='classCheck col-md-3 col-sm-3'>"
						+ "<div class='design-level'>"
						+ "<a href='displayFile?fileName=" + getImageLink(data) + "'>"
						+ "<img src='displayFile?fileName=" + data + "' style='width:100%; height:170px;'/></a>"
						+ "<div><h5 class='tresize'>" + getTextSlice(data) + "<a><i class='tboder fa fa-close fa-border pull-right' data-src=" + data + "></i></a></h5></div>"
						+ "<input type='hidden' name='files' value='" + getImageLink(data) + "' class='datacookie'>" 
						+ "</div>"
						+ "</div>";
					$(".uploadedList").append(str);	
				}
				
			});
			
		}
		$(".fileUplodeBtn").val("");
	})
	
	// 다중 드래그 업로드
	$(".fileDrop").on("dragenter dragover", function(event) {
		event.preventDefault(); 
		// 드래그했을때 폴더의 이미지링크가 열리는 이벤트를 막음. 
	});
	
	// 다중 드래그 업로드 2
	$(".fileDrop").on("drop", function(event) {
		event.preventDefault(); // 방지 
		// 드래그된 파일의 정보	
		
		// console.log(event);
		var files = event.originalEvent.dataTransfer.files;
		
		// 그외 확장자 제한
		if(!checkImageType((files[0].name))){
			alert("파일형식은 이미지 파일만 가능합니다.");
			return false;
		}
		
		// 첫번째 파일
		// var file = files[0];
		// console.log(file);
		
		// 다중파일 업로딩
		for(var i=0; i<files.length; i++) {
			var formData = new FormData();
			formData.append("file", files[i]);
			console.log(files[i]);
		// 폼 객체에 파일추가, append("변수명", 값)
		
			$.ajax({
				url: "./upload",
				data: formData,
				dataType: "text",
				processData: false,
				// contentType: true => application/x-www-form-urlencoded, 
	            //              false => multipart/form-data
				contentType: false,
				type: "POST",
				success: function(data) {
					var str ="";
					// http://localhost:8082/turn/displayFile?fileName=/2017/04/13/23786db8-a717-4c08-8d77-c6668265e168_muming.png
					str = "<div class='classCheck col-md-3 col-sm-3'>"
						+ "<div class='design-level'>"
						+ "<a href='displayFile?fileName=" + getImageLink(data) + "'>"
						+ "<img src='displayFile?fileName=" + data + "' style='width:100%; height:170px;'/></a>"
						+ "<div><h5 class='tresize'>" + getTextSlice(data) + "<a><i class='tboder fa fa-close fa-border pull-right' data-src=" + data + "></i></a></h5></div>"
						+ "<input type='hidden' name='files' value='" + getImageLink(data) + "' class='datacookie'>" 
						+ "</div>"
						+ "</div>";
					$(".uploadedList").append(str);
					
				}
				
			});
			
		}
	
	});
	
	// 업로드한 파일을 목록에서 삭제하기 위해  small엘리먼트를 클릭 이벤트로 설정하고, 
	// ajax 삭제요청이 처리되고 나면 클릭한 small엘리먼트의 부모태그인 div를 삭제하게된다.
	// $(this)를 사용하면 보다 간편하게 코드를 작성가능 $(this)를 알기 전까지는 목록의 레코드마다 id속성을 배열 만들어서로 처리했지만 
	// $(this)를 사용하면 배열로 처리하지 않고 클릭 이벤트만으로 구분을 할 수가 있기 때문
	$(".uploadedList").on("click", "i", function(event) {
		var that = $(this); // this는 클릭한 small태그
		$.ajax({
			url: "./deleteFile",
			type: "POST",
			// data: "fileName="+$(this).attr("date-src") = {fileName:$(this).attr("data-src")}
	        // 태그.attr("속성")
			data: {fileName:$(this).attr("data-src")},
			dataType: "text",
			success: function(result){
				if(result == 'deleted') {
					that.parent("a").parent("h5").parent("div").parent("div").parent("div").remove();
				}
			}
		});
		
	});
	
	$(".smal").on("click", function() {
		var text = $(this).attr("data-src");
		var that = $(this); // this는 클릭한 small태그
		that.parent("a").parent("h5").parent("div").parent("div").parent("div").remove();
		var deleteCookie = "<input type='hidden' name='cookieFile' value='" + text + "'>";
		$(".uploadedList").append(deleteCookie);
			
	});
	
	// 이미지 파일 형식 체크 
	function checkImageType(fileName) {
		// checkImageType 메소드의 정규표현식을 이용하여 
		// 파일확장자의 존재여부 파악 
		// i의 의미는 대,소문자의 구분이 없다. 
		var pattern = /jpg|gif|png|jpeg/i;
		return fileName.match(pattern);
	}
	
	// 이미지파일 링크 - 클릭하면 원본 이미지를 출력
	function getImageLink(fileName) {
		// 파일이름 앞의 's_'를 제거하는 용도 => 원본파일 찾기 
		// s_1566aa00-97bc-40b3-8272-997388640148_muming.png
		// 1566aa00-97bc-40b3-8272-997388640148_muming.png
		var end = "/" + fileName.substr(3);
		// 2017/04/13/1566aa00-97bc-40b3-8272-997388640148_muming.png 리턴 
		return end;
	}
	
	// 이미지 텍스트 추출 
	// 원본 텍스트 /2017/04/13/s_8d67994e-4152-4ae1-adc5-5e808459314a_muming.png
	function getTextSlice(fileName) { // 텍스트 자르게 
		var idx = fileName.indexOf("_") + 1; // /2017/04/13/s_
		var idxA = fileName.substr(idx); //8d67994e-4152-4ae1-adc5-5e808459314a_muming.png
		idx = idxA.indexOf("_")+1 // 8d67994e-4152-4ae1-adc5-5e808459314a_
		var text = idxA.substr(idx); // muming.png
		
		if (text.length > 25) {	
			var pattern = /.jpg|.gif|.png|.jpeg/i;
			text = text.replace(pattern,"").substr(0,20) + "...";
		}
		
		
		return text;
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
	
	// 페이지를 벗어났을시 저장된 이미지, 썸네일 정보의 제거 
	$(window).on('unload', function(){
		 
		var tempArray = new Array();
		$(".datacookie").each(function(){ // 작성한상태에서의 저장된 썸네일과 이미지 정보. 
		    tempArray.push($(this).attr("value"));
		}).delay(800); // unload 시간지연시킴
		
		if(hasBeenClicked == true) {
			
		} else {
			for(var i=0; i<tempArray.length; i++) {
				
				var idx = tempArray[i].indexOf("/") + 1;
				var idxText = "/s_" + tempArray[i].substr(idx);
				$.ajax({
					url: "./deleteFile",
					async: false,
					type: "POST",
					data: {fileName:idxText},
					dataType: "text",
					success: function(result){

					}
				});
			}
		}
		  
	});
	
});


