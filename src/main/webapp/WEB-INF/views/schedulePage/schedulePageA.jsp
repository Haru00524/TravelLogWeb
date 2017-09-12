<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script src="https://openapi.map.naver.com/openapi/v3/maps.js?clientId=Ao5To6qq05xL8fmhSOTK&callback=initMap"></script>
<script src="./resources/js/sockjs.js"></script>
<script src="./resources/js/schedule/schedule.js"/></script>
<script src="./resources/js/schedule/chat.js"></script>
<script src="./resources/js/jquery-ui-1.10.4.custom.js" /></script>
<div id="header" class="clearfix bg-dark">
   <!-- TOP NAV -->
   <header id="topNav">
      <div class="full-container">

         <!-- Logo -->
         <a class="logo pull-left" href="main">
            <img src="./resources/img/homeLogo/large_logo.png" alt="" />
         </a>
         
         <div class="navbar-collapse pull-right nav-main-collapse collapse">
            <nav class="nav-main">

               <ul id="topMain" class="nav nav-pills nav-main">
                  <li><!-- Smart Cost -->
                     <a href="#" data-toggle="modal" data-target="#smartCostShow">Smart Cost</a>
                  </li>
                  <li><!-- 준비물 -->
                     <a href="#" data-toggle="modal" data-target="#materialShow">준비물</a>
                  </li>
                  <li><!-- 채팅 -->
                     <a href="javascript:chattingWrap()">채팅</a>
                  </li>
                  <li><!-- 친구추가 -->
                     <a href="#" id="friend-show" data-toggle="modal" data-target="#friendShow">친구추가</a>
                  </li>
                  <li class="turn-margin-top-28"><!-- 완료 -->
                     <button id="scheduleSuccess" class="btn btn-purple margin-left-10"> 완료  </button>
                  </li>

               </ul>
            </nav>
            
            <div id="FullTitle" style="position:absolute; top:30px; left:250px;">
	         	<div id="Title" style="color:white; font-size:2.0em; font-weight:bold;">${travelTitle}
	         		<input id="vlaueTitle" type="text" style="display:none" value="${travelTitle}" />
	         		<!-- <input id="modPlanTitle" class="btn" type="button" style="background-color:black; color:white; font-size:0.5em;" value="변경" /> -->
	        	</div>
           </div>
         </div>

      </div>
   </header>
   <!-- /Top Nav -->
</div>

<!-- 제목 변경을 위한 스크립트 잠시 주석 -->
<!-- <script>
+$("#modPlanTitle").click(function(){
+	var title = $("#vlaueTitle").val();
+	$("#Title").empty();
+	var plus = '<input id="vlaueTitle" type="text" style="color:black;" value="' + title + '"/>'+
+			'<input id="okPlanTitle" class="btn" type="button" style="background-color:black; color:white; font-size:0.5em;" value="확인" />';
+	
+	$("#Title").append(plus);
+});
 
+$("#okPlanTitle").on("click", function(){
+	alert("????");
+	var title = $("#vlaueTitle").val();
+	$("#Title").empty();
+	var plus = title +
+			'<input id="modPlanTitle" class="btn" type="button" style="background-color:black; color:white; font-size:0.5em;" value="변경" />';
+	
+	$("#Title").append(plus);
+});
+</script> -->

<div class="wrapper">
   <div id="mainMenu" class="sidebar-vertical sidebar-dark turn-scroll-auto turn-a">
      <div class="sidebar-nav">
         <div class="navbar navbar-default turn-bottom-zero" role="navigation">
         
            <!--.nav-collapse -->
            <div class="navbar-collapse sidebar-navbar-collapse collapse" aria-expanded="false">

               <!-- MENU -->
               <ul class="nav navbar-nav turn-nav addMenu">
                  <li class="turn-border-bottom first-list">
                     <div>
                        <label class="text-center turn-font-set dateName">${scheduleDate}</label>
                     </div>
                     <div id="groupCode" style="display:none">${groupCode}</div>
                     <div id="user_id" style="display:none">${mem}</div>
                  </li>
               </ul>
               <!-- /MENU -->
               
            </div>
            <!--/.nav-collapse -->

         </div>
      </div>

      <!-- Social Icons -->
      <!-- 
      <div class="social-icons hidden-md hidden-sm hidden-xs text-center">
         &nbsp;
         <a href="#" class="social-icon social-icon-sm social-icon-light social-facebook" data-toggle="tooltip" data-placement="top" title="Facebook">
            <i class="icon-facebook"></i>
            <i class="icon-facebook"></i>
         </a>
         <a href="#" class="social-icon social-icon-sm social-icon-light social-twitter" data-toggle="tooltip" data-placement="top" title="Twitter">
            <i class="icon-twitter"></i>
            <i class="icon-twitter"></i>
         </a>
         <br/>
         <a href="#" class="social-icon social-icon-sm social-icon-light social-pinterest" data-toggle="tooltip" data-placement="top" title="Pinterest">
            <i class="icon-pinterest"></i>
            <i class="icon-pinterest"></i>
         </a>
         <a href="#" class="social-icon social-icon-sm social-icon-light social-linkedin" data-toggle="tooltip" data-placement="top" title="Linkedin">
            <i class="icon-linkedin"></i>
            <i class="icon-linkedin"></i>
         </a>
         <a href="#" class="social-icon social-icon-sm social-icon-light social-gplus" data-toggle="tooltip" data-placement="top" title="Google Plus">
            <i class="icon-gplus"></i>
            <i class="icon-gplus"></i>
         </a>
      </div>
       -->
      <!-- /Social Icons -->

      <!-- Paragraph -->
      <p class="text-center hidden-xs">
      </p>
      <!-- /Paragraph -->

   </div>   
   
   
   <div id="mainMenu" class="sidebar-vertical sidebar-dark turn-b">
      <div class="sidebar-nav">
         <div class="navbar navbar-default turn-bottom-zero liAdd" role="navigation">
         
            <!--.nav-collapse -->
            <div class="first-list navbar-collapse sidebar-navbar-collapse  turn-border-bottom" style="height:50px">
               <label class="text-center turn-font-set dayChoose"></label>
            </div>
            <!--/.nav-collapse -->
      
            <!-- div 태그생성 -->

         </div>
         
      </div>
      
   </div>   
   
   <div id="mainMenu" class="sidebar-vertical sidebar-dark turn-c local-position">
      <div class="sidebar-nav turn-heightful">
         <div class="navbar navbar-default turn-heightful turn-bottom-zero" role="navigation">
            
            <div class="turn-local first-list">
               <p>${local}</p>
               <div class="local-change" data-check="0"> 장소변경 ▼ </div>
            </div>
            <div class="local-changeBox">
               <div class="local-check" data-check="0">서울</div>
               <div class="local-check" data-check="0">대전</div>
               <div class="local-check" data-check="0">대구</div>
               <div class="local-check" data-check="0">부산</div>
               <div class="local-check" data-check="0">인천</div>
               <div class="local-check" data-check="0">울산</div>
               <div class="local-check" data-check="0">광주</div>
               <div class="local-check" data-check="0">제주</div>
               <div class="local-check" data-check="0">강원도</div>
               <div class="local-check" data-check="0">경기도</div>
               <div class="local-check" data-check="0">충청북도</div>
               <div class="local-check" data-check="0">충청남도</div>
               <div class="local-check" data-check="0">경상북도</div>
               <div class="local-check" data-check="0">경상남도</div>
               <div class="local-check" data-check="0">전라북도</div>
               <div class="local-check" data-check="0">전라남도</div>
            </div>
            
            <!--.nav-collapse -->
            <div class="navbar-collapse sidebar-navbar-collapse collapse turn-border">

                  <!-- INLINE SEARCH -->
                     <div class="inline-search clearfix turn-tb-16 margin-left-15 margin-right-15">
                        <div class="place_search">
                           <input type="search" placeholder="장소검색..." id="s" name="s" class="search-input">
                           <button id="searchButton">
                              <i class="fa fa-search"></i>
                           </button>
                        </div>
                        <div class="search_condition">
                           <input type="radio" id="localCheck" name="localCheck" value="${local}" checked>
                           <label for="localCheck">지역 내 검색</label>
                        </div>
                        <div class="search_condition">
                           <input type="radio" id="wholeCheck" name="localCheck" value="local" >
                           <label for="wholeCheck">전체 검색</label>
                        </div>
                     </div>
                  <!-- /INLINE SEARCH -->
               
            </div>
            <!-- 장소 필터  -->
            <div id="placeFilter">
               <div class="placeIcon" data-placeType="야외활동"><img src="./resources/img/place/outside.png"></div>
               <div class="placeIcon" data-placeType="실내활동"><img src="./resources/img/place/inside.png"></div>
               <div class="placeIcon" data-placeType="맛집"><img src="./resources/img/place/food.png"></div>
               <div class="placeIcon" data-placeType="숙박시설"><img src="./resources/img/place/hotel.png"></div>
               <div class="placeIcon" data-placeType="쇼핑몰"><img src="./resources/img/place/shopping.png"></div>
            </div>
            <!--/.nav-collapse -->
            <div></div>
            
            <div class="placeList turn-scroll-auto" style="width:100%; height:599px;"></div>

         </div>
      </div>

   </div>   
   
   
   
   <section id="slider" class="heightfull" style="position:relative">
      <div id="mapArea" style="width:100%; height:853px;"></div>
   </section>
   
   
   <!-- smart-cost Modal -->
   <div class="modal fade" id="smartCostShow">
      <div class="modal-dialog smart-size ">
         <div class="modal-content smart-size">
            <div class="modal-header material-title">
               <h3>Smart Cost</h3>
               <button type="button" data-dismiss="modal">X</button>
            </div>
            <div class="modal-body">
               <div id="smartBodyTitle">
                  <img src="./resources/img/checked.png">
                  <div class="smart-small-title"><b>여행 경비 타입을 체크하세요</b></div>
               </div>
               <div id="smart-select">
                  <div>
                     <input type="radio" id="smart_accumulate" name="smart-check" value="누적" checked>
                     <label for="smart_accumulate">누적형</label>
                  </div>
                  <div>
                     <input type="radio" id="smart_ deduction" name="smart-check" value="차감">
                     <label for="smart_ deduction">차감형</label>
                  </div>
                  <div id="smart-cost"  >
                     <p>금액 : </p>
                     <input type="text" onkeypress="onlyNumber();"/>
                     <p>원</p> 
                  </div>
               </div>
            
            </div>      
         </div>
      </div>
   </div>
   
   
   <!-- 준비물 Modal -->
   <div class="modal fade" id="materialShow">
      <div class="modal-dialog material-size ">
         <div class="modal-content material-size">
            <div class="modal-header material-title">
               <h3>준비물</h3>
               <button type="button" data-dismiss="modal">X</button>
            </div>
            <div class="modal-body">
               <div id="bodyTitle">
                  <img src="./resources/img/checked.png">
                  <div class="material-small-title"><b>완료한 준비물은 체크하세요</b></div>
               </div>
               <div id="material-add">
                  <input type="text" placeholder="ex)물통">
                  <button class="btn btn-default">ADD</button>
               </div>
               <div id="material-list">
         
               </div>
            </div>      
         </div>
      </div>
   </div>
   
   
   <!-- 친구추가 Modal -->
   <div class="modal fade" id="friendShow">
      <div class="modal-dialog friend-size ">
         <div class="modal-content friend-size">
            <div class="modal-header friend-title">
               <h3>친구</h3>
               <button type="button" data-dismiss="modal">X</button>
            </div>
            <div class="modal-body friend-body">
               <div id="friend-list">
         			
               </div>
               <div id="friend-search">
               		<div>
               			<input type="text" placeholder="같이 여행할 친구를 검색하세요">
               			<button class="btn btn-default" id="friend-search-btn">검색</button>
               		</div>
               		<div></div>
               </div>
            </div>      
         </div>
      </div>
   </div>
   <!-------- 채팅-------->
   <div id="chattingWrap" data-check="0">
	   <div class="chat" id="chat">
		</div>
		<input type="text" id="message">
		<input type="submit" id="sendBtn" value="전송">
	</div>
	
   <!-- ------장소 자세히 보기 -------------->
   <div id="placeDetail">
   		<div id="detail_view">
   			<div id="detail_main">
   				<p></p>
   				<span><a href="javascript:placeDetailClose()">X</a></span>	
   			</div>
   			<div id="detail_body">
   				<img src="">
   				<div id="detail_content"></div>
   				<div id="detail_content_box">
   					<div class="detail_wrap">
   						<div class="detail_title">카테고리</div><div></div>
   					</div>
   					<div class="detail_wrap">
   						<div class="detail_title">주&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;소</div><div style="width:190px"></div>
   					</div>
   				</div>
   			</div>
   		</div>
   		
   </div>
   

   <!-- ------memo 추가하기  -------------->
   <div id="memoDetail">
   		<div id="memo_view">
   			<div id="memo_main">
   				<p>메모</p>
   				<span><a href="javascript:memoClose()">X</a></span>	
   			</div>
   			<div id="memo_body">
   				<img src="">
   				<h3></h3>
   				<textarea></textarea>
   			</div>
   		</div>
   		
   </div>
   
</div>

