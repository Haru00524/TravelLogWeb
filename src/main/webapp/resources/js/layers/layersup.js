var pic;

$(document).ready(function(){
	gifMakeList();
	
	function gifMakeList(){
		$('#Category_select').empty();
		
		   var str = "<div class='uploadGif text-center'>" + 
		   				"<div class='gif-text'> G I F </div>" + 
			         	"<img src='displayProfile?fileName=/b848d269-2f45-4704-98f7-f9a4514656ae_16.png' style='width:100%; height:500px;'>" + 
					 "</div>" + 
					 "<div class='stateImg margin-top-20'>" + 
					 "</div>" + 
					 "<div class='copyData'><div>" + 
					 "<form id='form1' enctype='multipart/form-data'>" + 
						"<div class='col-md-12'>" + 
							"<label>" + 
								"<small class='text-muted'> 여러 이미지 업로드가능</small>" + 
							"</label>" + 
							"<div class='input-group'>" + 
								"<input class='target-input custom-file-upload fileUplodeBtn custom-file-upload-hidden' type='file' data-btn-text='Select a File' multiple='' tabindex='-1' style='position: absolute; left: -9999px;'>" +
								"<span class='input-group-btn' tabindex='-1'>" +
									"<button id='makeGif' class='margin-left-6 btn btn-red btn-3d'>GIF 생성</button>" + 
									"<a id='undown' class='margin-left-6 btn btn-default btn-3d' download>다운로드</a>" + 
								"</span>" +
							"</div>" + 
							"<small class='text-muted block'>Max file size: 10Mb (jpg/png/gif)</small>" + 
						"</div>" + 
					 "</form>" + 
				     "<div class='uploadList pull-left margin-top-30' style='clear:both;'>" + 
					 "</div>";
		
		$('#Category_select').append(str);
		
		$.ajax({
			type: "post" ,
			url : "gif_image_list",
			success : function(data){
				if(data.length > 0){
					var add = ""; 
					for(var i=0 ; i<data.length ; i++){
						console.log(data[i]);
						add += "<img src='http://114.201.41.248:8084/turn/resources/upload/logs"+data[i]+"' onclick='input_image($(this))'>"
					}
					
					$(".uploadList").append(add);
				}
			},
			error : function(data){
				
			}
			
		})
		
	}
	
	
	
	
});


function bookMakeList(){
	
	$('#Category_select').empty();
	
}

function input_image(ddd){
//	alert("ok?");
	
	console.log(ddd.attr("src"));
	
	$.ajax({
		url : "gif_list" ,
		type : "POST",
		data : {
			imagesA : ddd.attr("src")
		},
		success : function(data){
//			alert(data);
			var img = '<img src="displaygifFile?fileName='+ data + '">';
			$('.stateImg').append(img);
		}
	})
	
}



$(document).on("change","#file",function(){

//	alert("ok?");
	event.preventDefault();
	
	var form = $('#form1');
	var formData = new FormData();
	formData.append("file", this.files[0]);
	console.log(this.files[0]);
	$.ajax({
		url: 'layersupupload',
		data: formData,
		dataType: 'text',
		processData: false,
		contentType: false,
		type: 'POST',
		success: function(data){
			var str = "";
				
			if(checkImageType(data)){
//				alert(data);
		        str = 
			     "<a href='displayGifFile?fileName="+getImageLink(data)+"' target='_blank'>"
			     + "<img src = 'displayGifFile?fileName=" + data + "'/>" //+ getImageLink(data)
			     + "</a><small data-src='" + data + "'>X</small>"
			}else {
				alert("이미지 파일만 가능합니다.");
			}
			
			$(".uploadList").append(str);
			$(".uploadList").on("click", "small", function(event){
				var small = $(this);
				$.ajax({
					url: "layersupdeleteFile",
					type: "delete",
					headers: {
						"Content-Type": "application/json",
						"X-HTTP-Method-Override": "DELETE"
					},
					data: {fileName: small.attr("data-src")},
					dataType: "text",
					success: function(result){	// 파일 데이터 삭제
						if(result == "deleted"){
							alert("deleted!");
							small.parent("div").remove(); // 화면에서 제거
						}
					}
				});
			});
		},error:function(){
		}
	});
	
	function checkImageType(fileName) {
		var pattern = /jpg|gif|png|jpeg/i;
		return fileName.match(pattern);
	}
	
	function getImageLink(fileName){	// 원본 파일 이름 찾기
		if(!checkImageType(fileName)){
			return;
	        }
		var end = fileName.substr(11);		// 파일 이름 앞의 'thumbNail_'까지를 제거
		return "/" + end;	         	// thumbNail_을 제외
	} 
});

$(document).on("click","#makeGif",function(){
	event.preventDefault();
	
	$.ajax({
		url: 'layersupmakeGif',
		data: {},
		dataType: 'text',
		type: 'POST',
		success: function(data){
			var str = "";
			
			if(checkImageType(data)){
//				if($(".uploadGif").children("div").length > 0){
//					alert("기존 gif를 지웁니다.");
//					img.parent("div").remove();
//				}
//				alert(data);
				str = "<img src = 'resultgifFile?fileName=" + data + "'/ style='width:500px; height:400px; margin-top:50px;'>"; //+ getImageLink(data)
				pic = data;
			}else{ }
			
			
			$("#undown").prop("id","down");
	         $("#down").html("생성중..&nbsp;&nbsp;");
	         $("#down").attr("class","margin-left-6 btn btn-blue btn-3d");
	         setTimeout(function(){
	            $("#down").attr("href","http://114.201.41.248:8084/turn/resources/upload/gif/resultgif/"+pic);
	            $("#down").html("다운로드");
	         }, 4000)
	         $(".uploadGif").empty();
			$(".uploadGif").append(str);
		}
	});
	
	function checkImageType(fileName) {
		var pattern = /jpg|gif|png|jpeg/i;
		return fileName.match(pattern);
	}
	
});

$(document).on("click","#undown",function(){
	event.preventDefault();
	alert("gif를 생성후에 클릭해주십시오.");
});

//$(document).on("click","#down",function(){
//	event.preventDefault();
//	var page = "https://www.naver.com/";
//	window.open(page,"_blank");
//});

