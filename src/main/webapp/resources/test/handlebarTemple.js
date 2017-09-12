$(document).ready(function(){
	
	var template = Handlebars.compile($("#template").html());
	
	$(".fileDrop").on("dragenter dragover", function(event) {
		event.preventDefault(); 
		// 드래그했을때 폴더의 이미지링크가 열리는 이벤트를 막음. 
	});
	
	$(".fileDrop").on("drop", function(event) {
		event.preventDefault(); // 방지 
		
		// 드래그된 파일의 정보
		var files = event.originalEvent.dataTransfer.files;
		
		// 첫번째 파일
		var file = files[0];
	//	console.log(file);
		
		// ajax로 전달할 폼 객체
		var formData = new FormData();
		
		// 폼 객체에 파일추가, append("변수명", 값)
		formData.append("file", file);
		
		$.ajax({
			url: "./uploadAjax",
			data: formData,
			dataType: "text",
			processData: false,
			contentType: false,
			type: "POST",
			success: function(data) {
				var fileInfo = getFileInfo(data);
				
				var html = template(fileInfo);
				
				$(".uploadedList").append(html);
			}
		});
	});
	
	$('#registerForm').click(function(event) {
		alert("눌려짐");
		event.preventDefault();
		
		var that = $(this);
		
		var str="";
		
		$('.uploadedList .delbtn').each(function(index) {
			str += "<input type='hidden' name='files[" + index + "]' value='" + $(this).attr('href') +"'>";
		});
		
		that.append(str);
		
		
		that.get(0).submit();
		
	});
	
	//============================================================//
	
	// 이미지 파일 형식 체크 
	function checkImageType(fileName) {
		// checkImageType 메소드의 정규표현식을 이용하여 
		// 파일확장자의 존재여부 파악 
		// i의 의미는 대,소문자의 구분이 없다. 
		var pattern = /jpg|gif|png|jpeg/i;
		return fileName.match(pattern);
	}
	
	function getFileInfo(fullName) {
		
		var fileName, imgsrc, getLink;
		var fileLink;
		
		
		if(checkImageType(fullName)) {
			imgsrc = "./displayFile?fileName=" + fullName;
			fileLink = fullName.substr(14);
			
			var front = fullName.substr(0,12); // /2017/00/00/
			var end = fullName.substr(14);
			
			getLink = "./displayFile?fileName=" + front + end;
		} else {
			imgsrc = "./resources/img/folder.png"
			fileLink = fullName.substr(12);
			getLink = "./displayFile?fileName="+fullName;
		}
		
		fileName = fileLink.substr(fileLink.indexOf("_")+1);
		fullName = "./displayFile?fileName=" + fullName;
		
		return {fileName:fileName, imgsrc:imgsrc, getLink:getLink, fullName:fullName};
		
	}
	
	
	
	
});