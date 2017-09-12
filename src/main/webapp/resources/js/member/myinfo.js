$(document).ready(function(){

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
      
      var profileDel; // 프로필이미지 삭제정보
      var delCount = 0; // 삭제정보 
      var addCount = 0; // 추가정보 
      var unloadCount = 0; // 언로드정보
      var addCnt = parseInt($(".cookieData").attr("data-cnt"));
      
      var preventId = $('#request-id').text(); // 현재 사용자 아이디
      
      // ================== 아이디 검사  ================== //
      $('#user_id').blur(function() {
      
         var check = $('#user_id').val(); // 새로 입력되어 체크해야할 아이디
         
         if(preventId == check) {
            $('#idconfirm').css('color','#9c27b0');
            $('#idconfirm').html("<b>현재 사용하고계신 아이디입니다. ( 사용가능 )</b>");
            flags[0] = 1;
         } else {
            if (check.length > 4 && (!mixtext.test(check) || !num.test(check) && !en.test(check) && !spText.test(check))&& !check.match(" ")) {
               var query = { id : $("#user_id").val() };
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
                     $('#idconfirm').html("<b>수정가능한 아이디 입니다.</b>");
                  }
               }
            });
            } else {
               // $('#idconfirm').css('display','block');
               $('#idconfirm').css('color','red');
               $('#idconfirm').html("<b>5~20자의 영문 소문자, 숫자와 특수기호(_),(-)만 사용 가능합니다.</b>");
               flags[0] = 0;
            }
         }
      });
      
      // ================== 패스워드 검사 ================== //
      $('#user_pass').blur(function() {
         var pass = $('#user_pass').val();
         var check = $('#user_id').val();
         
         if(!pass) {
            $('#passconfirm').css('color','red');
            $('#passconfirm').html("<b>비밀번호를 확인하여주십시오</b>");
            // $('#passconfirm').css('display','block');
            flags[1] = 0;
         } else if (pass.length < 4) {
            // $('#passcheck').css('display','block');
            $('#passconfirm').css('color','red');
            $('#passconfirm').html("<b>비밀번호가 4자리 이하입니다.</b>");
            // $('#passconfirm').css('display','block');
            flags[1] = 0;
         } else if (pass.match(" ")) {
            $('#passconfirm').css('color','red');
            $('#passconfirm').html("<b>비밀번호에 공백이 포함될수없습니다.</b>");
            flags[1] = 0;
         } else if(pass == check) {
            $('#passconfirm').css('color','red');
            $('#passconfirm').html("<b>비밀번호가 아이디와 같습니다.</b>");
            $('#user_pass').val("");
         } else {
            flags[1] = 1;
            $('#passconfirm').html("&nbsp;");
         }
      });
      // ====================== 날짜 입력 ====================== //
      var request_birth = $('#request-birth').text();
      var yyyy = request_birth.substring(0, 4);
      var mm = request_birth.substring(5, 7);
      var dd = request_birth.substring(8);
      
      if(mm < 10){
         mm = mm.substring(1);
      }
      
      $('.select').val(mm);
      $('#yyyy').val(yyyy);
      $('#dd').val(dd);
      
      
      
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
      
      // ================== 현재 생일 계산 (미래) ================== // 
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
                           texts="<b>미래날짜</b>";
                           flags[2] = 0;
                        }
                     } else{
                        texts="<b>미래날짜</b>";
                        flags[2] = 0;
                     }
                  }
               } else{
                  texts="<b>미래날짜</b>";
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
         
         if(!ycheck || ycheck.match(" ") || ycheck.length <= 3){
            texts = "<b>태어난 년도 4자리를 입력하세요</b>";
            flags[2] = 0;
         } else{
            if(mcheck==0){
               texts =  "<b>태어난 월을 선택하세요</b>";
               flags[2] = 0;
            } else{
               if(!dcheck){
                  texts = "<b>태어난 일을 입력하세요</b>";
                  flags[2] = 0;
               } else{
                  if(mcheck==2){
                       if(ycheck%4==0 && ycheck%100!=0 || ycheck%400==0){ 
                          if(dcheck>0 && dcheck<30) {
                        	 flags[2] = 0;
                             texts = "&nbsp;";
                             flag = 1;
                          }
                          else {
                             texts = "<b>생년월일을 다시 확인해주세요</b>";
                           flags[2] = 0;
                          }
                       }else{
                          if(dcheck>0 && dcheck<29){
                           flags[2] = 1;
                             texts =  "&nbsp;";
                             flag = true;
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
                         flag = true;
                      }
                      else {
                         texts = "<b>생년월일을 다시 확인해주세요</b>";
                        flags[2] = 0;
                      }
                   } else {
                      if(dcheck>0 && dcheck<32) {
                        flags[2] = 1;
                         texts = "&nbsp;";
                         flag = true;
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
      
      // ================== 성별 확인 ================== //
      var gender = $('.user_gender').val();
      
      if(gender == 0){
         $('.male').css('background-color','#e5e5e5');
         $('.male').css('color','gray');
         $('.gender').append('<input type="hidden" id="m" name="user_gender" value="0">');
      }else{
         $('.female').css('background-color','#e5e5e5');
         $('.female').css('color','gray');
         $('.gender').append('<input type="hidden" id="f" name="user_gender" value="1">');
      }
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
         
      // 전송버튼
      $('#btnsub').on('click', function() {
         state = checking();
         if(state==true){
            if(confirm("정말로 수정하시겠습니까?")) {
               unloadCount = 1; // 언로드함수 카운터
            	
               // 삭제 쿠키확인
          	   if(profileDel != null && delCount == 1) {
          		  if(profileDel.match("/default.png")) {
          	
          		  } else {
          			  $.ajax({
          				  url: "./deleteProfile",
          				  type: "POST",
          				  data: {fileName:profileDel},
          				  dataType: "text",
          				  success: function(result){
          					  if(result == 'deleted') {
          						  delCount = 0;
          					  }
          				  }	
          			  }); 
          		  }
          	
        	   }
         
          	   // 추가쿠키확인
          	   if(addCount == 1) {
              	   $(".cookieData").each(function(){
              		   var cookieDel = $(this).attr("data-cookie");
              		   var core = parseInt($(this).attr("data-cnt"));
              		   if(core == addCnt) {// 마지막인덱스와 일치할경우
             
              		   } else {
              			   if(cookieDel.match("/default.png")) {
                 			 
              			   } else {
              				   $.ajax({
              					   url: "./deleteProfile",
                				   type: "POST",
                				   data: {fileName:cookieDel},
                				   dataType: "text",
                				   success: function(result){
                					   if(result == 'deleted') {
  	      		   						
                					   }
                				   }	
                			   });
              			   }
              		   }
              	   })
          	   }
          	   
               $("#textbr").append('<input type="hidden" name="nowid" value="' + preventId + '">');
               $('#textbr')[0].submit();
               alert("회원정보 수정이 완료되었습니다.");
               
            } else {
               return false;
            }
         } else {
            alert("회원정보를 다시 한번 확인해주십시오.");
         }
      });
      
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
      
      
// ================== 프로필 사진업로드 =================== //       
      
      // 프로필사진업로드 
      $(".image_add").click(function(e){
    	  e.preventDefault();
    	  $("#targetfile").click()
      });
      
      $("#targetfile").change(function() {
    	  if(!checkImageType((this.files[0].name))){
    		  alert("파일형식은 이미지 파일만 가능합니다.");
    		  $("#targetfile").val("");
    		  return false;
    	  }
    	  
    	  addCount = 1;
    	  
    	  console.log(this.files[0]);
    	  
    	  var formData = new FormData();
    	  formData.append("file", this.files[0]);
    	  console.log(this.files[0]);
    		  
		  $.ajax({
			  url: "./profile",
			  data: formData,
			  dataType: "text",
			  processData: false,
			  contentType: false,
			  type: "POST",
			  success: function(data) {
				  
				  var name = "displayProfile?fileName=";
				  $(".profile img").attr("src", name + data).attr("data-src",data);
				  $(".profile input").val(data);
				  
				  var divdel = $("<div></div>")
				  .addClass("cookieData")
				  .attr("data-cookie", data)
				  .attr("data-cnt", ++addCnt)
				  .css("display","none");
				  
				  $(".profile").append(divdel);
			  }, error:function() {
				  alert("오류");
			  }
		  })
		  
      });
      
      // 프로필이미지 삭제 
      $(".image_del").click(function(){
    	  delCount = 1;
    	  profileDel = $(".profile img").attr("data-src");
    	  
    	  $(".profile img").attr("src","displayProfile?fileName=/default.png").attr("data-src","/default.png");
		  $(".profile input").val("/default.png");
		  
      });
      
      // 이미지를 등록한상태에서 나갔을시. 
      $(window).on('unload', function(){
    	  $(".cookieData").each(function(){
    		 var unCnt = parseInt($(this).attr("data-cnt"));
        	 var unDel = $(this).attr("data-cookie");
    		 if(unCnt == 0) {
    			 
    		 } else {
    			 if(unloadCount == 0) {
    				 if(unDel.match("/default.png")) {
            			  // 디폴트이미지 존재
         			 } else {
         				 $.ajax({
         					 url: "./deleteProfile",
         					 type: "POST",
         					 data: {fileName:unDel},
         					 dataType: "text",
         					 success: function(result){
         						 if(result == 'deleted') {
   			   						
         						 }
         					 }	
         				 }); 
         			 }
    			 }
    		 }
    	  }).delay(800);
      });
      
      // 이미지 파일 형식 체크
      function checkImageType(fileName) {
		// checkImageType 메소드의 정규표현식을 이용하여 
		// 파일확장자의 존재여부 파악 
		// i의 의미는 대,소문자의 구분이 없다. 
		var pattern = /jpg|gif|png|jpeg/i;
		return fileName.match(pattern);
      }

});