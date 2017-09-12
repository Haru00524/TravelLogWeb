$(document).ready(function(){
	$(".memDel").click(function(){
		if(confirm("회원을 탈퇴하면 모든정보가 사라지게됩니다.")) {
			if(confirm("정말로 회원을 탈퇴하시겠습니까?")) {
				$(".memDel").attr("href","myDelete");
			} else {
				return false;
			}
		} else {
			return false;
		}
	});
});