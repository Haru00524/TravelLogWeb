$(document).ready(function(){
   
   var my_id = $(".friendList").attr("data-my");
   friendList();
   
   $(".searchon").click(function(){
      
      reSearch();
      
   });
   
   // 친구요청
   $(".searchlist").on("click",".req",function(){
      
      $.ajax({
          url: "./friendReq",
          type: "POST",
          data: {
             user_id:$(this).attr("data-user"),
             my_id:my_id
          },
          success: function(data){
             if(data == 'success') {
                alert("친구요청을 보냈습니다.");
             }
             friendList();
             reSearch();
          }
      });
      
   });
   
   // 요청취소
   $(".friendList").on("click",".cancel",function(){
      
      $.ajax({
          url: "./friendCancel",
          type: "POST",
          data: {
             user_id:$(this).attr("data-c"),
             my_id:my_id
          },
          success: function(data){
             if(data == 'success') {
                alert("요청을 취소하였습니다.");
             }
             friendList();
             reSearch();
          }
      });
      
   });
   
   // 친구삭제 
   $(".friendList").on("click",".delete",function(){
      $.ajax({
          url: "./friendDel",
          type: "POST",
          data: {
             user_id:$(this).attr("data-d"),
             my_id:my_id
          },
          success: function(data){
             if(data == 'success') {
                alert("삭제되었습니다.");
             }
             friendList();
             reSearch();
          }
          
      });
      
      
   });
   
   // 요청수락
   $(".friendList").on("click",".accept",function(){
      $.ajax({
          url: "./friendAccept",
          type: "POST",
          data: {
             user_id:$(this).attr("data-a"),
             my_id:my_id
          },
          success: function(data){
             if(data == 'success') {
                alert("완료되었습니다.");
             }
             friendList();
             reSearch();
          }
      });
      
   });
   
   
   
   
   
});

function friendList() {
   
   $(".friendList").empty();
   
   var myId = $(".friendList").attr("data-my");
   
   $.ajax({
       url: "./friendList",
       type: "GET",
       data: {},
       success: function(data) {
         console.log(data);
         var friendList = "";
         for(var i=0; i<data.length; i++) {
            
            if(data[i].friend_accept == 0 && data[i].user_id == myId) {
               friendList = "<tr>" + 
                           "<td><img src='displayProfile?fileName=" + data[i].user_profile + "' class='turn-shadow' style='width:45px; height:45px; border:1px solid #b4b4b4;'></td>" +
                           "<td class='verticals distinct'>" + data[i].friend_id + "</td>" +
                           "<td class='verticals'>친구대기</td>" +
                           "<td class='verticals'><button class='btn btn-blue btn-3d btn-xs cancel' data-c=" + data[i].friend_id +">요청취소</button></td>" +
                         "</tr>";
               $(".friendList").append(friendList);
            } else if((data[i].friend_accept == 1 && data[i].user_id == myId) && (data[i].friend_id != myId && data[i].friend_accept == 1)) {
               friendList = "<tr>" + 
                           "<td><img src='displayProfile?fileName=" + data[i].user_profile + "' class='turn-shadow' style='width:45px; height:45px; border:1px solid #b4b4b4;'></td>" + 
                           "<td class='verticals distinct'>" + data[i].friend_id +"</td>" +
                           "<td class='verticals'>친구</td>" +
                           "<td class='verticals'><button class='btn btn-red btn-3d btn-xs delete' data-d=" + data[i].friend_id +">친구삭제</button></td>" + 
                         "</tr>";
               $(".friendList").append(friendList);
            } else if(data[i].user_id != myId && data[i].friend_id == myId && data[i].friend_accept == 1) {
               friendList = "<tr>" + 
                           "<td><img src='displayProfile?fileName=" + data[i].user_profile + "' class='turn-shadow' style='width:45px; height:45px; border:1px solid #b4b4b4;'></td>" + 
                           "<td class='verticals distinct'>" + data[i].user_id +"</td>" +
                           "<td class='verticals'>친구</td>" +
                           "<td class='verticals'><button class='btn btn-red btn-3d btn-xs delete' data-d=" + data[i].user_id +">친구삭제</button></td>" + 
                         "</tr>";
               $(".friendList").append(friendList);
            } else if(myId == data[i].friend_id && data[i].friend_accept == 0) {
               friendList = "<tr>" + 
                           "<td><img src='displayProfile?fileName=" + data[i].user_profile + "' class='turn-shadow' style='width:45px; height:45px; border:1px solid #b4b4b4;'></td>" +
                           "<td class='verticals distinct'>" + data[i].user_id + "</td>" +
                           "<td class='verticals'>친구요청</td>" +
                           "<td class='verticals'>" +
                              "<button class='btn btn-green btn-3d btn-xs accept' data-a=" + data[i].user_id +">수락</button>" +
                              "<button class='btn btn-teal btn-3d btn-xs cancel' data-c=" + data[i].user_id +">취소</button>" + 
                           "</td>" +
                          "</tr>";
               $(".friendList").append(friendList);
            } 
            
         }
         
         distinct();
         
       }, error: function(){
          alert("에러");
       }
   }); 
   
}

function distinct() {
   
   var arr = [];
   
   $(".distinct").each(function(){
      arr.push($(this).text()); 
   });
   
   var ont = [];
   
   $(".distinct").each(function(){
      err = $(this).text();
      var cnt = 0;
      for(var i=0; i<arr.length; i++) {
         if(err == arr[i]) {
            cnt++;
            if(cnt >= 2) {
               ont.push($(this).text());
               console.log(ont);
               if(ont.length == 2) { // 같은아이디가 반복되었을때.
                  $(this).parent().remove();
               }
            }
         }
      }
      
   });
   
}

function reSearch() {
   
   var my_id = $(".friendList").attr("data-my");
   var input = $("#keyons").val();
   
   if(input.length == 0) {
      return false;
   }
   
   $.ajax({
       url: "./friendSearch",
       type: "POST",
       data: {search:input},
       success: function(data){
          
          $(".searchlist").empty();
          
          if(data.length == 0) {
             
          }
          
          var searchData = ""
          for(var i=0; i<data.length; i++) {
             
             if(data[i].user_id == my_id) {
                
             } else {
                searchData = "<tr>" + 
                           "<td width='6%' class='text-center' ><img src='displayProfile?fileName=" + data[i].user_profile + "' class='turn-shadow' style='width:45px; height:45px; border:1px solid #b4b4b4;'></td>" + 
                           "<td width='40%' class='verticals'>" + data[i].user_id + "</td>" + 
                           "<td class='verticals'><button class='req btn btn-blue btn-3d btn-xs' data-user='" + data[i].user_id +"'>친구요청</button></td>" + 
                          "</tr>";
             }
                
             $(".searchlist").append(searchData);
                
          }
          
       }, error: function(){
          alert("검색결과가 존재하지않습니다."); 
          // error null 처리
       }
   }); 
   
}