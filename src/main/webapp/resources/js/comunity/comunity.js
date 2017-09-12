$(document).ready(function(){

	var unloadCount = 0;
	
	$("#comuSubmit").click(function(e){
		e.preventDefault();
		unloadCount = 1;
		
		if(confirm("등록하시겠습니까?")) {
			
			$(".note-editable img").each(function(){
				var image = $(this).attr("src");
				
				if(image.match("displayComunity")) {
					idx = image.indexOf("/") + 1;
					image = "/s_" + image.substr(idx);
				}
				
				var input = "<input type='hidden' name='file_content' value='" + image + "'>";
				$(".addImage").append(input);
			});
			
			
			$("#formComunity").attr("method","POST");
			$('#formComunity')[0].submit();
			
		}
		
	});
	
	$("#comuCancel").click(function(e){
		e.preventDefault();
		self.location = "./comuList";
	});
	
	// 언로드 함수
	$(window).on('unload', function(){
		if(unloadCount == 0) {
			
			$(".note-editable img").each(function(){
				
				var imageUrlDel = $(this).attr("src");
				
				// 업로드 이미지이면 
				if(imageUrlDel.match("displayComunity")) {
					idx = imageUrlDel.indexOf("/") + 1;
					imageUrlDel = "/s_" + imageUrlDel.substr(idx);
					
					$.ajax({
    					 url: "./deleteComunity",
    					 type: "POST",
    					 data: {fileName:imageUrlDel},
    					 dataType: "text",
    					 success: function(result){
    						 if(result == 'deleted') {
			   						
    						 }
    					 }	
    				}); 
					
				} 
				
				// 링크파일이면
				
			}).delay(800);
			
		} 
	});
	
//	// 썸네일 삭제 클릭펑션
//	$(".addImage").on("click","a", function(){
//		
//		var dataOn = $(this);
//		
//		$.ajax({
//			url: "./deleteComunity",
//			type: "POST",
//			// data: "fileName="+$(this).attr("date-src") = {fileName:$(this).attr("data-src")}
//	        // 태그.attr("속성")
//			data: {fileName:dataOn.children().attr("data-src")},
//			dataType: "text",
//			success: function(result){
//				if(result == 'deleted') {
//					
//					dataOn.parent().parent().parent().parent().remove();
//					
//					$(".note-editable img").each(function(){
//						
//						var datas = $(this).attr("src").replace("displayComunity?fileName=","");
//						if(original(dataOn.children().attr("data-src")) == datas) {
//							$(this).remove();
//						}
//						
//					});
//
//				}
//			}, error:function(){
//				alert("전송오류");
//			}
//		});
//		
//	});	
	
});


function sendFile($summernote, file) {
	
	// 파일형식제어 
	for(var i=0; i<file.length; i++) {
		if(!checkImageType((file[i].name))){
			alert("파일형식은 이미지 파일만 가능합니다.");
			return false;
		}
	}
	
	// 서버로컬업로드
	for(var i=0; i<file.length; i++) {
		
	    var formData = new FormData();
	    formData.append("file", file[i]);
	    
	    $.ajax({
	        url: './comunity',
	        data: formData,
	        dataType: 'text',
	        cache: false,
	        contentType: false,
	        processData: false,
	        type: 'POST',
	        success: function (data) {
	        	console.log(data);
	        	
	        	// 이미지 캐싱정보저장
	        	var cache = "";
	        	cache = "<input type='hidden' name='cache_content' value='" + data + "'>";
	        	$(".cacheImage").append(cache);
	        	
	        	data = "displayComunity?fileName=" + original(data);
	            $summernote.summernote('insertImage', data, function ($image) {
	                $image.attr('src',data);
	                $image.css('width','80%');
	            });
	        }, error:function(){
	        	alert("업로드에러");	
	        }
	    });
	}
	
}

//원본교체
function original(fileName) {
	var idx = fileName.indexOf("_") + 1;
	var text = "/" + fileName.substr(idx);
	return text;
}

// imageCheck;
function checkImageType(fileName) {
	var pattern = /jpg|gif|png|jpeg/i;
	return fileName.match(pattern);
}
