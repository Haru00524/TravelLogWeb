<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script src="./resources/js/place/placeRead.js"/></script>

<!-- 
	PAGE HEADER 
	
	CLASSES:
		.page-header-xs	= 20px margins
		.page-header-md	= 50px margins
		.page-header-lg	= 80px margins
		.page-header-xlg= 130px margins
		.dark			= dark page header
	
		.shadow-before-1 	= shadow 1 header top
		.shadow-after-1 	= shadow 1 header bottom
		.shadow-before-2 	= shadow 2 header top
		.shadow-after-2 	= shadow 2 header bottom
		.shadow-before-3 	= shadow 3 header top
		.shadow-after-3 	= shadow 3 header bottom
-->
<section class="page-header page-header-xs shadow-before-1">
	<div class="container">

		<h1>Place Info</h1>

		<!-- breadcrumbs -->
		<ol class="breadcrumb">
			<li><a href="main">Mian</a></li>
			<li><a href="placeInfo">Place</a></li>
			<li class="active">Place Info</li>
		</ol><!-- /breadcrumbs -->

	</div>
</section>
<!-- /PAGE HEADER -->
<section class="alternate">
	<div class="container">
		<div class="row">
			<!-- LEFT -->
			<div class="col-md-9 col-sm-9">

				<!-- POST ITEM -->
				<div class="nomargin sky-form boxed">
					<header class="size-18" style="background:rgba(199, 199, 199, 0.1) !important;">
						<b>${map.place.place_name}</b>
						<div class="pull-right">
							<i class="fa fa-eye"></i>&nbsp; 
							<span class="font-lato">${map.place.view}</span>&nbsp;&nbsp; 
							<i class="fa fa-clock-o"></i>&nbsp; 
							<span class="font-lato">
								<fmt:formatDate pattern="yyyy-MM-dd" value="${map.place.add_date}" />
							</span>
						</div>
					</header>
					
					<fieldset class="nomargin">
					
						<div class="tab-content">
							
							<!-- OWL SLIDER -->
							<div id="tab_a" class="tab-pane active">
								<c:choose>
			      					<c:when test="${fn:length(map.list) == 1}">
						            	<div class="owl-carousel buttons-autohide controlls-over" style="border:1px solid rgb(221, 221, 221); height:400px; margin-bottom:0px;" data-plugin-options='{"items": 1, "autoHeight": false, "navigation": true, "pagination": true, "transitionStyle":"fadeUp", "progressBar":"false"}'>
											<c:forEach items="${map.list}" var="pimg">
												<div>
													<img class="img-responsive" src="displayFile?fileName=${pimg.place_img}" alt="" style="height:400px;">
												</div>
											</c:forEach>
										</div>
						           	</c:when>
						           	<c:otherwise>
						            	<div class="owl-carousel buttons-autohide controlls-over" style="border:1px solid rgb(221, 221, 221); height:400px; margin-bottom:0px;" data-plugin-options='{"items": 1, "autoPlay": 3000, "autoHeight": false, "navigation": true, "pagination": true, "transitionStyle":"fadeUp", "progressBar":"false"}'>
											<c:forEach items="${map.list}" var="pimg">
												<div>
													<img class="img-responsive" src="displayFile?fileName=${pimg.place_img}" alt="" style="height:400px;">
												</div>
											</c:forEach>
										</div>
						           	</c:otherwise> 
						        </c:choose>
							<!-- /OWL SLIDER -->
							</div>
							
							<!-- MAP -->
							<div id="tab_b" class="tab-pane">
								<div id="google_map" style="width:100%; height:400px; border:#ddd 2px solid;"></div>
								<div id="latLng" style="display:none;" data-lat="${map.place.place_lat}" data-lng="${map.place.place_lng}"></div>
							</div>
							<!-- /MAP -->
							
							<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAF8iTF3JtdLLhprWyASWE8APl6RM6BGBQ"></script>
							<script>
								var map;
								// 지도 중앙 위치
								
								$(document).ready(function(){
									
									$("#targetMapA").on("click",function(){
										$("#targetMapB").parent().removeClass("active");
										$("#tab_b").removeClass("active");
										$("#tab_a").addClass("active");
										$(this).parent().addClass("active");
									});
									
									$("#targetMapB").on("click",function(){
										$("#targetMapA").parent().removeClass("active");
										$("#tab_a").removeClass("active");
										$("#tab_b").addClass("active");
										$(this).parent().addClass("active");
										initialize();
									});
									
									function initialize() {
										  
										  var lat = $("#latLng").attr("data-lat")
										  var lng = $("#latLng").attr("data-lng")
										
										  var latLng = new google.maps.LatLng(lat, lng); 
												
										  var mapOptions = { //구글 맵 옵션 설정
										      zoom : 16, //기본 확대율
										      center : latLng, // 지도 중앙 위치
										      scrollwheel : true, //마우스 휠로 확대 축소 사용 여부
										      mapTypeControl : true //맵 타입 컨트롤 사용 여부
										  };
										
										  map = new google.maps.Map(document.getElementById('google_map'), mapOptions); //구글 맵을 사용할 타겟
										  
										  var image = './resources/img/placeholder.png'; //마커 이미지 설정
										
										  var marker = new google.maps.Marker({ //마커 설정
										      map : map,
										      position : map.getCenter(), //마커 위치
										      icon : image //마커 이미지
										  });
										
										  google.maps.event.trigger(map, "resize");
										
									}
								});
							</script>
			
							
						</div>
						
						<!-- 이미지 , 지도  -->
						<div class="row margin-top-10 margin-bottom-20">
							<div class="col-md-5 col-sm-5">
								<ul id="myTab" class="nav nav-tabs nav-bottom-border nav-justified tabStyle">
									<li class="active">
										<a id="targetMapA">이미지</a>
									</li>
									<li>
										<a id="targetMapB">지도</a>
									</li>
								</ul>
							</div>
							<div class="col-md-2 col-sm-2 tabStyle text-center wishEvent">
								<c:choose>
									<c:when test='${map.wish.myWish == 1}'>
										<div class="wishoff" data-place='${map.place.place_code}'>${map.wish.wishCount}</div>
									</c:when>
									<c:otherwise>
										<div class="wishon" data-place='${map.place.place_code}'>${map.wish.wishCount}</div>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
						<!-- <ul class="blog-post-info left-line"></ul> -->
						
						<div>
							<div class="size-15" style="font-weight:600;">
								<p class="contentLine-two">${map.place.place_content}</p>
							</div>
						</div>
						
					 	<ul class="blog-post-info left-line content-line"></ul>
						
						<div class="form-group reply-group" data-session="${mem}">
						
								<label class="size-20 reply-margin replyIndex">댓글 ${fn:length(map.replylist)} 개</label>
								
								<c:forEach items="${map.replylist}" var="reply">
									
									<div class="comment-item pull-left reply-margin">

										<span class="user-avatar">
											<img class="pull-left p-profile" src="displayProfile?fileName=${reply.user_profile}" width="64" height="64" alt="">
										</span>
	
										<div class="media-body">
											<div class='pull-right size-20 star-set'>
												<c:forEach var="i" begin="1"  end="5">
													<c:choose>
														<c:when test='${reply.place_score >= i}'>
															<i class='fa fa-star'></i>
														</c:when>
														<c:otherwise>
															<i class='fa fa-star-o'></i>
														</c:otherwise>
													</c:choose>
												</c:forEach>
											</div>
											<h4 class="zerobottom bold inline-block">${reply.user_id}</h4>
											<c:if test="${mem == reply.user_id}">
											<div class='inline-block'>
												<button class='btn btn-aqua btn-xs modify-buttom' style='margin-left:16px;' data-reply-code="${reply.board_code}" data-board="${reply.place_reply_code}">수정</button>
												<button class='btn btn-brown btn-xs remove-buttom' style='margin-left: 1px;' data-reply-code="${reply.board_code}" data-board="${reply.place_reply_code}">삭제</button>
											</div>
											</c:if>
											<div></div>
											<b><fmt:formatDate pattern="yyyy-MM-dd" value="${reply.board_date}" /></b>
											<div class="place-reply">${reply.board_content}</div>
										</div>
									
									</div>
								
								</c:forEach>
				
						</div>
					
						<div class='reply-item-box'>
							<textarea required="required" maxlength="5000" rows="4" class="form-control reply-margin reply-write" id="comment"></textarea>
							<div class="pull-left">
								<div class='size-20 coment-star-set'>
									<b>평가  : </b>
									<i class='fa fa-star fastar' data-scroe="0"></i>
									<i class='fa fa-star-o fastar' data-scroe="1"></i>
									<i class='fa fa-star-o fastar' data-scroe="2"></i>
									<i class='fa fa-star-o fastar' data-scroe="3"></i>
									<i class='fa fa-star-o fastar' data-scroe="4"></i>
								</div>
							</div>
							<button class="btn btn-3d btn-lg btn-reveal btn-black pull-right write-buttom" data-place="${map.place.place_code}">
								<i class="fa fa-check"></i>
								<span>댓글 쓰기</span>
							</button>
						</div>
						
					</fieldset>
				
					
				</div>
				
				<!-- /POST ITEM -->

			</div>


			<!-- RIGHT -->
			<div class="col-md-3 col-sm-3">

				<!-- side navigation -->
				<div class="side-nav margin-bottom-50">
					<div class="nomargin sky-form boxed">
						<header class="size-18 margin-bottom-20" style="background:rgba(199, 199, 199, 0.1) !important;">
							<img src="displayProfile?fileName=${map.place.user_profile}" style="width:45px; height:45px; border:1px solid rgb(221, 221, 221);">
							<b class="margin-left-10">${map.place.user_id}</b>
						</header>
						<fieldset class="nomargin">
							
							<div style="font-weight:600;">
								<div><b class="sideFont">주소</b></div>
								<div>${map.place.place_address}<br><br></div>
								<div><b class="sideFont">위도</b></div>
								<div>${map.place.place_lat}<br><br></div>
								<div><b class="sideFont">경도</b></div>
								<div>${map.place.place_lng}<br><br></div>
								<div><b class="sideFont">카테고리</b></div>
								<div>${map.place.place_type}<br><br></div>
							</div>
							
							<!-- /side navigation -->
						</fieldset>
					</div>
				</div>


				<!-- JUSTIFIED TAB -->
				<div class="tabs nomargin-top hidden-xs margin-bottom-60">

					<!-- tabs -->
					<ul class="nav nav-tabs nav-bottom-border nav-justified">
						<li class="active">
							<a href="#tab_1" data-toggle="tab">
								인기 장소정보
							</a>
						</li>
						<li>
							<a href="#tab_2" data-toggle="tab">
								최신 장소정보
							</a>
						</li>
					</ul>

					<!-- tabs content -->
					<div class="tab-content margin-bottom-60 margin-top-30">

						<!-- POPULAR -->
						<div id="tab_1" class="tab-pane active">
							<c:forEach items="${map.popular}" var="popular">
								<div class="row tab-post"><!-- post -->
									<div class="col-md-3 col-sm-3 col-xs-3">
										<img src="displayFile?fileName=${fn:replace(popular.place_img,'/','/s_')}" width="60" alt="" />
									</div>
									<div class="col-md-9 col-sm-9 col-xs-9">
										<a href="./placeRead?post=${popular.place_code}" class="tab-post-link">
											<c:choose>
						      					<c:when test="${fn:length(popular.place_content) > 25}">
									            	<c:out value="${fn:substring(fn:replace(popular.place_content, '<br>', ''),0,25)}"/>...
									           	</c:when>
									           	<c:otherwise>
									            	<c:out value="${popular.place_content}"/>
									           	</c:otherwise> 
									        </c:choose>
										</a>
										<small><fmt:formatDate pattern="yyyy-MM-dd" value="${popular.add_date}" /></small>
									</div>
								</div><!-- /post -->
							</c:forEach>

						</div>
						<!-- /POPULAR -->


						<!-- RECENT -->
						<div id="tab_2" class="tab-pane">
							<c:forEach items="${map.recent}" var="recent">
							
								<div class="row tab-post"><!-- post -->
									<div class="col-md-3 col-sm-3 col-xs-3">
										<img src="displayFile?fileName=${fn:replace(recent.place_img,'/','/s_')}" width="60"  alt="" />
									</div>
									<div class="col-md-9 col-sm-9 col-xs-9">
										<a href="./placeRead?post=${recent.place_code}" class="tab-post-link">
											<c:choose>
						      					<c:when test="${fn:length(recent.place_content) > 25}">
									            	<c:out value="${fn:substring(fn:replace(recent.place_content, '<br>', ''),0,25)}"/>...
									           	</c:when>
									           	<c:otherwise>
									            	<c:out value="${recent.place_content}"/>
									           	</c:otherwise> 
									        </c:choose>
										</a>
										<small><fmt:formatDate pattern="yyyy-MM-dd" value="${recent.add_date}" /></small>
									</div>
								</div><!-- /post -->
								
							</c:forEach>

						</div>
						<!-- /RECENT -->

					</div>

				</div>
				<!-- JUSTIFIED TAB -->


				<!-- TAGS -->
				<!--
				<h3 class="hidden-xs size-16 margin-bottom-20">TAGS</h3>
				<div class="hidden-xs margin-bottom-60">

					<a class="tag" href="#">
						<span class="txt">RESPONSIVE</span>
						<span class="num">12</span>
					</a>
					<a class="tag" href="#">
						<span class="txt">CSS</span>
						<span class="num">3</span>
					</a>
					<a class="tag" href="#">
						<span class="txt">HTML</span>
						<span class="num">1</span>
					</a>
					<a class="tag" href="#">
						<span class="txt">JAVASCRIPT</span>
						<span class="num">28</span>
					</a>
					<a class="tag" href="#">
						<span class="txt">DESIGN</span>
						<span class="num">6</span>
					</a>
					<a class="tag" href="#">
						<span class="txt">DEVELOPMENT</span>
						<span class="num">3</span>
					</a>
				</div>
				-->

			</div>

		</div>
	</div>
</section>