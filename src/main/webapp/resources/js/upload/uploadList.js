$(document).ready(function(){
			
			var pageForm = $("#pageForm"); // 객체가 넘어옴
			
			$(".pagination li a").on("click",function(){
				event.preventDefault();
				var targetPage = $(this).attr("href"); // 어트리뷰트
				pageForm.find("[name=page]").val(targetPage); // value="${pagination.page} 값을 변경 
				pageForm.attr("action","uploadList");
				pageForm.attr("method", "get");
				pageForm.submit();
			});
			
			$(".place_info").on("click", function(){
				event.preventDefault();
				var no = $(this).attr("href");
				pageForm.attr("action","uploadRead");
				pageForm.attr("method", "get");
				$("<input type='hidden' name='no' value='"+no+"'>").appendTo(pageForm);
				pageForm.submit();
			});
			
			
});