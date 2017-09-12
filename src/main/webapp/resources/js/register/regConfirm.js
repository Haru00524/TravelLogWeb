$(document).ready(function() {
	var num = /[0-9]/;
	var spText = /[\[\]{}()<>?|`~!@#$%^&*+=,.;:\"\\\'\\\s]/g;
	var en = /[A-Z]/;
	var ko = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힝]/;
	var mixtext = /[^a-z0-9]+|^([a-z]+|[0-9]+)$/; // 아이디검사 정규식
	var regEmail = /^[-A-Za-z0-9_]+[-A-Za-z0-9_.]*[@]{1}[-A-Za-z0-9_]+[-A-Za-z0-9_.]*[.]{1}[A-Za-z]{2,5}$/; // 이메일 형식 정규식 test@test.com
	var regphone = /^((01[1|6|7|8|9])[1-9]+[0-9]{6,7})|(010[1-9][0-9]{7})$/; // 휴대폰 번호 정규식 010/1234/5678
	var texts;
	var state= true;
	var flags = new Array(5);
	for(var i = 0; i< flags.length; i++){ // 회원정보 전체확인
		flags[i] = 1;
	}
	
	// ================== 가입취소버튼  ================== //
	$('#cancel').click(function() {
		if(confirm("가입을 취소하시겠습니까? ( 작성된 정보는 사라집니다. )")) {
			self.location = "./login";
		} else {
			return false;
		}
	});
	
	// ================== 성별 라디오버튼  ================== //
	$('.male').css('background-color','#e5e5e5');
	$('.male').css('color','gray');
	$('.gender').append('<input type="hidden" id="m" name="user_gender" value="0">');
	
	$(".male").click(function(){
		$('#m').remove();
		$('#f').remove();
		$('.female').css('background-color','white');
		$('.female').css('color','black');
		
		$('.male').css('background-color','#e5e5e5');
		$('.male').css('color','gray');
		$('.gender').append('<input type="hidden" id="m" name="user_gender" value="0">');
	});
	
	$(".female").click(function(){
		$('#m').remove();
		$('#f').remove();
		$('.male').css('background-color','white');
		$('.male').css('color','black');
		
		$('.female').css('background-color','#e5e5e5');
		$('.female').css('color','gray');
		$('.gender').append('<input type="hidden" id="f" name="user_gender" value="1">');
	});
	
	
	
	// ================== 아이디 검사  ================== //
	$('#user_id').blur(function() {
		var check = $('#user_id').val();
		if (check.length > 4 && (!mixtext.test(check) || !num.test(check) && !en.test(check) && !spText.test(check))&& !check.match(" ")) {
			var query = {
				id : $("#user_id").val()
			};
		$.ajax({
			type : 'POST',
			url : 'confirm',
			data : query,
			success : function(data) { // @ResponseBody의 있는 데이터가 리턴된다. 
				if (data == 1) {
					// $('#idconfirm').css('display','block');
					$('#idconfirm').css('color','red');
					$('#idconfirm').html("<b>이미 존재하는 아이디입니다</b>");
					flags[0] = 0;
				} else {
					flags[0] = 1;
					// $('#idconfirm').css('display','block');
					$('#idconfirm').css('color','#9c27b0');
					$('#idconfirm').html("<b>사용가능한 아이디입니다 사용하세요^^</b>");
				}
			}
		})
		} else {
			// $('#idconfirm').css('display','block');
			$('#idconfirm').css('color','red');
			$('#idconfirm').html("<b>5~20자의 영문 소문자, 숫자와 특수기호(_),(-)만 사용 가능합니다.</b>");
			flags[0] = 0;
		}
	});
	
	// ================== 패스워드 검사 ================== //
	$('#user_pass').blur(function() {
		var pass = $('#user_pass').val();
		var check = $('#user_id').val();
		if (pass.length < 4) {
			// $('#passcheck').css('display','block');
			$('#passcheck').css('color','red');
			$('#passcheck').html("<b>비밀번호가 4자리 이하입니다.</b>");
			// $('#passconfirm').css('display','block');
			$('#passconfirm').css('color','red');
			$('#passconfirm').html("<b>필수 정보입니다.</b>");
			flags[1] = 0;
		} else if (pass.match(" ")) {
			$('#passcheck').css('color','red');
			$('#passcheck').html("<b>비밀번호에 공백이 포함될수없습니다.</b>");
			flags[1] = 0;
		} else {
			// $('#passcheck').css('display','block');
			$('#passcheck').html("&nbsp;");
			flags[1] = 1;
			if(!check){
				// $('#passconfirm').css('display','block');
				$('#passconfirm').css('color','red');
				$('#passconfirm').html("<b>필수항목입니다.</b>");
				flags[1] = 0;
			}
			if(pass != check){
				// $('#passconfirm').css('display','block');
				$('#confirm').val("");
				$('#passconfirm').css('color','red');
				$('#passconfirm').html("<b>비밀번호가 일치하지 않습니다.</b>");
				flags[1] = 0;
			}
		}
		if(pass == check){
			// $('#passcheck').css('display','block');
			$('#passcheck').css('color','red');
			$('#passcheck').html("<b>비밀번호가 아이디와 같습니다.</b>");
			$('#user_pass').val("");
			flags[1] = 0;
		}
		if(check === pass){
			flags[1] = 1;
		}
	});
	
	// ================== 패스워드 확인 ================== //
	$('#confirm').blur(function() {
		var pcheck = $('#confirm').val();
		var pass = $('#user_pass').val();
		if (pcheck === pass && !pcheck.match(" ")) {
			// $('#passconfirm').css('display','block');
			$('#passconfirm').html("&nbsp;");
			flags[1] = 1;
		} else {
			$('#confirm').val("");
			// $('#passconfirm').css('display','block');
			$('#passconfirm').css('color','red');
			$('#passconfirm').html("<b>비밀번호를 다시확인해주십시오.</b>");
			flags[1] = 0;
		}
	});
	
	// ================== 날짜 검사 (윤년검사) ================== // 
	
	// 년도 검사 
	$('#yyyy').blur(function() {
		birth();
		// $('#birth_check').css('display','block');
		$('#birth_check').html(texts);
	});
	
	// 날짜 검사
	$('#mm').change(function() {
		 birth();
		 // $('#birth_check').css('display','block');
		$('#birth_check').html(texts);
	});
	
	// 일검사  
	$('#dd').blur(function() {
		birth();
		// $('#birth_check').css('display','block');
		$('#birth_check').html(texts);
	});
		
	// ================== 휴대폰 번호 확인 ================== // 
	$('#user_phone').blur(function() {
		var phoneck = $('#user_phone').val();
		if (!regphone.test(phoneck)) {
			// $('#phone_check').css('display','block');
			$('#phone_check').css('color','red');
			$('#phone_check').html("<b>휴대폰 번호 형식을 올바르게 입력해주세요.</b>");
			flags[3] = 0;
		} else {
			// $('#phone_check').css('display','block');
			$('#phone_check').html("&nbsp;");
			flags[3] = 1;
		}
	});
				
	// ================== 이메일 확인 ================== // 
	$('#user_email').blur(function() {
		var emailck = $('#user_email').val();
		if (!regEmail.test(emailck)) {
			// $('#email_check').css('display','block');
			$('#email_check').css('color','red');
			$('#email_check').html("<b>email 형식을 올바르게 입력해주세요.</b>");
			flags[4] = 0;
		} else {
			$('#email_check').css('display','block');
			$('#email_check').html("&nbsp;");
			flags[4] = 1;
		}
	});
	
	// 보내기 설정 type = button -> submit 변환
	$('#registerTest').click(function() {
		state = checking();
		if(state==true){
			if(confirm("정말로 가입하시겠습니까?")) {
				$('#register_submit')[0].submit();
				alert("회원가입이 완료되었습니다.");
			} else {
				return false;
			}
		} else {
			alert("회원정보를 다시 한번 확인해주십시오.");
		}

	});
	
	// ================== 현재 생일 계산 (미래에서 왔어요~) ================== // 
	function nowcheck(flag){
		var date = new Date();
		var yych = date.getFullYear();
		var mmch = date.getMonth()+1;
		var ddch = date.getDate();
			
		var ycheck = $('#yyyy').val();
		var mcheck = $('#mm').val();
		var dcheck = $('#dd').val();
		
		if(flag==true){
			// 미래에서 왔는지 체크
			if(ycheck >= yych){
				if(ycheck==yych){
					if(mcheck >= mmch){
						if(mcheck == mmch){
							if(dcheck>ddch){
								texts="<b>미래에서 오셧군요</b>";
								flags[2] = 0;
							}
						} else{
							texts="<b>미래에서 오셧군요</b>";
							flags[2] = 0;
						}
					}
				} else{
					texts="<b>미래에서 오셧군요</b>";
					flags[2] = 0;
				}
			}
		}
	}
	
	// ================== 생일 윤년 체크 메소드 ================== // 
	function birth(){
		var ycheck = $('#yyyy').val();
		var mcheck = $('#mm').val();
		var dcheck = $('#dd').val();

		var flag=false;
		
		if(!ycheck || ycheck.match(" ")){
			texts = "<b>태어난 년도 4자리를 입력하세요</b>";
		} else{
			if(mcheck==0){
				texts =  "<b>태어난 월을 선택하세요</b>";
			} else{
				if(!dcheck){
					texts = "<b>태어난 일을 입력하세요</b>";
				} else{
					if(mcheck==2){
				        if(ycheck%4==0 && ycheck%100!=0 || ycheck%400==0){ 
				        	if(dcheck>0 && dcheck<30) {
								flags[2] = 0;
				        		texts = "&nbsp;";
				        		flag= 1;
				        	}
				        	else {
				        		texts = "<b>생년월일을 다시 확인해주세요</b>";
								flags[2] = 0;
				        	}
				        }else{
				        	if(dcheck>0 && dcheck<29){
								flags[2] = 1;
				        		texts =  "&nbsp;";
				        		flag= true;
				        	}
				        	else {
				        		texts = "<b>생년월일을 다시 확인해주세요</b>";
								flags[2] = 0; 
				        	}
				        }
				    } else if(mcheck==4 || mcheck ==6 ||mcheck==9 || mcheck==11){
				    	if(dcheck>0 && dcheck<31){
							flags[2] = 1;
				    		texts = "&nbsp;";
				    		flag= true;
				    	}
				    	else {
				    		texts = "<b>생년월일을 다시 확인해주세요</b>";
							flags[2] = 0;
				    	}
				    } else {
				    	if(dcheck>0 && dcheck<32) {
							flags[2] = 1;
				    		texts = "&nbsp;";
				    		flag= true;
				    	}
			        	else {
			        		texts = "<b>생년월일을 다시 확인해주세요</b>";
							flags[2] = 0;
			        	}
				    }
				}
			}
		}
		nowcheck(flag);
	}
	
	/// ================== 입력란 공백 체크 ================== // 
	function checking() {
		for(var j =0; j<flags.length;j++){
			if(flags[j]==0){
				return false;
			}
		}
		if (!$("#user_id").val() && $("#user_id").val().length < 4) { // 아이디를 입력하지 않으면 수행
			$("#user_id").focus();
			return false;
		}
		if ($("#user_id").val().length < 4) { // 아이디를 입력하지 않으면 수행
			$("#user_id").focus();
			return false;
		}
		if (!$("#user_pass").val()) { // 비밀번호를 입력하지 않으면 수행
			$("#user_pass").focus();
			return false;
		}  

		if (!$("#confirm").val()) { // 비밀번호가 같지 않으면 수행
			$("#confirm").focus();
			return false;
		}

		if (!$("#yyyy").val()) { 
			$("#yyyy").focus();
			return false;
		}

		if (!$("#dd").val()) { 
			$("#dd").focus();
			return false;
		}
		
		if (!$("#user_phone").val()) {
			$("#user_phone").focus();
			return false;
		}
		if(!regphone.test($("#user_phone").val())){
			$("#user_phone").focus();
			return false;
		}
		if (!$("#user_email").val()) {
			$("#user_email").focus();
			return false;
		}
		if(!regEmail.test($("#user_email").val())){
			$("#user_email").focus();
			return false;
		}
		return true;
	}
});