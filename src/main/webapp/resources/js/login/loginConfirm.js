$(document).ready(function(){
   
   $(".reg-go").click(function(){
      self.location = "./register";
   });
   
   $('#loginTest').click(function(){

      $('#id_confirm').css('display','none');
      $('#pass_confirm').css('display','none');
      state = check_input();
      
      if(state==2) {
         
         var query = {
               id : $("#user_id").val(),
               pass : $("#user_pass").val()
         };
         
         $.ajax({
            type : 'POST',
            url : 'user_check',
            async: false,
            data : query,
            success : function(data) { // @ResponseBody의 있는 데이터가 리턴된다.  로그인정보가 리턴된다.
               if (data === "1") {
                  // 로그인에 성공하였을시.  
                  $('#test_submit').attr('method','post');
                  $('#test_submit').attr('action','main');
                  $('#test_submit')[0].submit();
                  // 폼의 입력에 태그에 가지고있는 정보를 가지고 세션을 활성화 시킨다. 
               } else {
                  // 로그인이 실패하였을시. 
                  $('#info_confirm').css('color','red');
                  $('#info_confirm').css('display','block');
                  $('#info_confirm').html("<b>아이디와 비밀번호가 올바른지 확인해주십시오.</b>");
               }
            }
         })
         
      } else if (state==0){
         $('#info_confirm').css('color','red');
         $('#info_confirm').css('display','block');
         $('#info_confirm').html("<b>아이디가 입력되었는지 확인해주십시오.</b>");
      } else if (state==1) {
         $('#info_confirm').css('color','red');
         $('#info_confirm').css('display','block');
         $('#info_confirm').html("<b>패스워드가 입력되었는지 확인해주십시오.</b>");
      }
      
      
      // 로그인 패스워드 입력체크 
      function check_input() {
         if (!$("#user_id").val() && $("#user_id").val().length < 4) { // 아이디를 입력하지 않으면 수행
            $("#user_id").focus();
            return 0;
         }
         if (!$("#user_pass").val()) { // 비밀번호를 입력하지 않으면 수행
            $("#user_pass").focus();
            return 1;
         }
         return 2;
      }
      
   });
});