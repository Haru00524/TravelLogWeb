<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script src="<c:url value="./resources/js/upload/uploadRead.js"/>" ></script>
<c:if test="${info == 'admin'}">
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

		<h1>장소정보</h1>

		<!-- breadcrumbs -->
		<ol class="breadcrumb">
			<li><a href="main">메인</a></li>
			<li><a href="uploadList">장소 리스트</a></li>
			<li class="active">장소정보</li>
		</ol><!-- /breadcrumbs -->

	</div>
</section>
<!-- /PAGE HEADER -->
<section class="alternate">
	<div class="container">
		<form role="form" style="display:none;">
		</form>
		<div class="nomargin sky-form boxed">
			<header class="size-18 margin-bottom-20" style="background:rgba(199, 199, 199, 0.1) !important;"><b>${place.place_name}</b></header>
			<fieldset class="nomargin">
				<div class="textc margin-bottom-30 size-15"><p class="contentLine">${place.place_content}</p></div>
				<ul class="blog-post-info ul-line"></ul>
				<div class="margin-bottom-30 size-15">주소 : ${place.place_address}</div>
				<div id="google_map" style="width:100%; height:400px; border:#ddd 2px solid;"></div>

				<div class="row margin-top-20 margin-bottom-20">
					<div class="col-md-2 margin-top-10 text-left">
						<div class="uploadFont upEle margin-left-3">위도 : ${place.place_lat}</div>
						<div id="lat" style="display:none;">${place.place_lat}</div>
					</div>
					<div class="col-md-2 margin-top-10 text-left">
						<div class="uploadFont upEle margin-left-10">경도 : ${place.place_lng}</div>
						<div id="lng" style="display:none;">${place.place_lng}</div>
					</div>
					<div class="col-md-2 margin-top-10 text-left">
						<div class="uploadFont upEle margin-left-10">장소분류 : ${place.place_type}</div>
					</div>
					<div class="col-md-2 margin-top-10 text-left">
						<div class="uploadFont margin-left-10">장소설정 : 
							<c:choose>
				        		<c:when test="${place.place_on == 0}">OFF</c:when>
				        		<c:otherwise>ON</c:otherwise>
		    				</c:choose>
	    				</div>
					</div>
					<div class="col-md-4 text-right">
						<button type="submit" class="btn btn-warning btn-set-margin">수정</button>
						<button type="submit" class="btn btn-danger btn-set-margin">삭제</button>
						<button type="submit" class="btn btn-primary btn-set-margin">목록</button>
					</div>
				</div>
				<ul class="blog-post-info ul-line ul-margins-0"></ul>
				
				<div class="imgOn row">
					<c:forEach items="${list}" var="list">
						<div class="box col-md-3 col-sm-3">
							<div class='design-level'>
							<img src="displayFile?fileName=${fn:replace(list.place_img,'/','/s_')}" style="width:100%; height:170px;"/>
							<div>
								<h5 class='tresize'>
									<c:choose>
				      					<c:when test="${fn:length(list.file_name) > 25}">
							            	<c:out value="${fn:substring(list.file_name,0,20)}"/>...
							           	</c:when>
							           	<c:otherwise>
							            	<c:out value="${list.file_name}"/>
							           	</c:otherwise> 
							        </c:choose>
								</h5>
							</div>
							<input type="hidden" class="imgDel" name="imgDel" value="${fn:replace(list.place_img,'/','/s_')}">
							</div>
						 </div>
					</c:forEach>
				</div>
				<div class="pagelist margin-top-20">
					<input type="hidden" id="page" name="page" value="${pagination.page}">
					<input type="hidden" id="recordPage" name="recordPage" value="${pagination.recordPage}">
					<input type="hidden" class="no" name="no" value="${place.place_code}">
				</div>
				<script>
				var map;
				
				var lat = document.getElementById("lat").innerHTML;
				var lng = document.getElementById("lng").innerHTML;
				
				function initialize() {
				
				  var mapOptions = { //구글 맵 옵션 설정
				      zoom : 16, //기본 확대율
				      center : new google.maps.LatLng(lat, lng), // 지도 중앙 위치
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
				
				  google.maps.event.addDomListener(window, "resize", function() { //리사이즈에 따른 마커 위치
				      var center = map.getCenter();
				      google.maps.event.trigger(map, "resize");
				      map.setCenter(center); 
				  });
				
				}
				</script>
				<script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAF8iTF3JtdLLhprWyASWE8APl6RM6BGBQ&callback=initialize"></script>
			</fieldset>
		</div>
	</div>
</section>
</c:if>
<c:if test="${info == null || info == 'user'}">
<section class="page-header">
	<div class="container">

		<h1>PAGE ACCESS ERROR</h1>

		<!-- breadcrumbs -->
		<ol class="breadcrumb">
			<li class="active">엑세스 오류</li>
		</ol><!-- /breadcrumbs -->

	</div>
</section>
<!-- /PAGE HEADER -->

<!-- -->
<section class="padding-xlg alternate">
	<div class="container">
		<div class="col-md-8 col-md-offset-3">
			<h2>페이지 접근오류, <br><br><strong>요청하신 페이지에 엑세스 할 수 없습니다. <br><br> 요청 권한을 다시 확인해주십시오.</strong></h2><br>
			<a class="size-20 font-lato" href="main"><i class="glyphicon glyphicon-menu-left margin-right-10 size-16"></i>메인 페이지로 이동</a>
		</div>
	</div>
</section>
<!-- / -->
</c:if>