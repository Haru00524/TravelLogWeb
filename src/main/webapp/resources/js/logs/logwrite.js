$(document).ready(function(){
	
	var unloadCount = 0;
	
	// 스페이스 이벤트 , 엔터 이벤트
	$(".input_hashtag").keyup(function(e){
		if(e.keyCode == 32 || e.keyCode == 13) {
			var hashPound = "#"
			var tagName = $(this).val();
			
			if(tagName.match(" ")) {
				tagName = tagName.replace(" ","");
	        } else if(tagName.match("\n")) {
	        	tagName = tagName.replace("\n","");
	        }
			
			var text = $("<li><b>" + hashPound + tagName + "<a class='onetarget'><i class='isize fa fa-close pull-right'></i></a></b>")
						.css("color","blueviolet")
						.css("background-color","#c8eaea")
						.css("border","1px solid #c3bbbb")
						.css("border-radius","5px")
						.css("margin","8px 0px 0px 10px");
			
			$(".hashTagCopy").append(text);
			$(this).val("");
			
		}
	});
	
	// 해시태그 전체삭제
	$(".hashDel").click(function(e){
		e.preventDefault();
		$(".hashTagCopy").empty();
		$(".addHash").empty();
	});
	
	// 해시태그선택삭제 
	$(".hashTagCopy").on("click",".onetarget", function(){
		$(this).parent().parent().remove();
		
		alert($(s));
		
		
	});
	
	// 취소 
	$("#boardCancel").click(function(e){
		e.preventDefault();
		self.location.href = "./logInfo";
	});
	
	// 등록
	$("#boardSubmit").click(function(e){
		e.preventDefault();
		unloadCount = 1;		
		
		// 해쉬태그전송
		$(".hashTagCopy b").each(function(){
			
			var text = $(this).text();
	        var input = "<input type='hidden' name='hash_tag_content' value='" + text + "'>";
	        $(".addHash").append(input);
	    });
		
		
		// 이미지 전송
		$(".note-editable img").each(function(){
			var image = $(this).attr("src");
			
			// 자체 서버 이미지 파일일경우에  / 링크X
			if(image.match("displayLogs")) {
				idx = image.indexOf("/") + 1;
				image = "/s_" + image.substr(idx);
				
			}
			
			var input = "<input type='hidden' name='file_content' value='" + image + "'>";
			$(".addImage").append(input);
			
		});
		
		// iframe 전송
		$(".note-editable iframe").each(function(){
			var iframe = $(this).attr("src");
			
			var input = "<input type='hidden' name='file_content' value='" + iframe + "'>";
			$(".addImage").append(input);
			
		});
		
		if(confirm("등록하시겠습니까?")) {
			
			$("#formobj").attr("method","POST");
			$('#formobj')[0].submit();
			
		} else {
			return false;
		}
		
	});
	
	
	$(window).on('unload', function(){
		if(unloadCount == 0) {
			
			$(".note-editable img").each(function(){
				
				var imageUrlDel = $(this).attr("src");
				
				// 업로드 이미지이면 
				if(imageUrlDel.match("displayLogs")) {
					idx = imageUrlDel.indexOf("/") + 1;
					imageUrlDel = "/s_" + imageUrlDel.substr(idx);
					
					$.ajax({
    					 url: "./deleteLogs",
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
	        url: './logs',
	        data: formData,
	        dataType: 'text',
	        cache: false,
	        contentType: false,
	        processData: false,
	        type: 'POST',
	        success: function (data) {
	        	
	        	// 이미지 캐싱정보
	        	var cache = "";
	        	cache = "<input type='hidden' name='cache_content' value='" + data + "'>";
	        	$(".cacheImage").append(cache);
	        	
	        	data = "displayLogs?fileName=" + original(data);
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

// textSlice
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
