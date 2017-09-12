$(document).ready(function () {
	
	// ******************************* uploadRead.jsp ******************************* //
	var formObj = $("form[role='form']"); // 폼태그의 role속성의 ='form'을 가져온 폼태그의 객체   role='form'자체를 찾음
	
	$(".btn-warning").on("click", function(){
		formObj.attr("action", "./uploadSet");
		formObj.append($("#page"));
		formObj.append($("#recordPage"));
		formObj.append($(".no"));
		formObj.submit();
	});
	
	$(".btn-danger").on("click", function(){
		var answer = confirm("정말로 삭제 하시겠습니까?");
		if(!answer) return false;
		formObj.attr("action", "./uploadDel");
		formObj.append($(".no")); // 게시글 번호정보 
		formObj.append($(".imgDel")); // 게시글 이미지정보
		formObj.submit(); // 
	});
	
	$(".btn-primary").on("click", function(){
		// self.location = "listPage?page=${criteria.page}&recordNumPerPage=${criteria.recordNumPerPage}";
		formObj.attr("action", "./uploadList");
		formObj.attr("method", "get");
		formObj.append($("#page"));
		formObj.append($("#recordPage"));
		formObj.submit();
		// 작성자의 주소가 남아있음 
	});
	
	// ******************************* uploadSet.jsp ******************************* //
	var check = $('#checkradio').val();
	var offBox = $("#place_off");
	var onBox = $("#place_on");
	
	if(check == 1){
		onBox.prop("checked", true);
	}else{
		offBox.prop("checked", true);
	}
	
	$('.choose').val($('#selectData').val());
});