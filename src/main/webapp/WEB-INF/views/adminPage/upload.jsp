<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="<c:url value="./resources/css/upload/uploadDrop.css"/>" />
<script src="<c:url value="./resources/js/upload/uploadDrop.js"/>" ></script>
<script>
	var map;
	
	function initialize() {
	
	  var mapOptions = { //구글 맵 옵션 설정
	      zoom : 16, //기본 확대율
	      center : new google.maps.LatLng(37.5651, 126.98955), // 지도 중앙 위치
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

		<h1>장소등록</h1>

		<!-- breadcrumbs -->
		<ol class="breadcrumb">
			<li><a href="main">메인</a></li>
			<li><a href="uploadList">장소 리스트</a></li>
			<li class="active">장소등록</li>
		</ol><!-- /breadcrumbs -->

	</div>
</section>
<!-- /PAGE HEADER -->
<section class="alternate">
	<div class="container">
		<form action="uploadForm" method="post" enctype="multipart/form-data" id="textbr" class="nomargin sky-form boxed" onsubmit="return false;">
			<header class="size-18 margin-bottom-20" style="background:rgba(199, 199, 199, 0.1) !important;">
                  <i class="fa fa-dot-circle-o"></i>&nbsp;&nbsp;PlaceInfo
            </header>
            <fieldset class="nomargin">
				<label class="input margin-bottom-30">
					<input id="place_name" name="place_name" type="text"
					size="60" maxlength="60" class="form-control" placeholder="장소명" autofocus>
				</label>
				
				<label class="input margin-bottom-30">
					<textarea rows="4" id="place_content" name="place_content" class="form-control" placeholder="장소내용"></textarea>
				 </label>
				
				<label class="input margin-bottom-30">
					<input id="place_address" name="place_address" type="text"
					size="80" maxlength="80" class="form-control" placeholder="주소" autofocus>
				</label>
				
				<div id="google_map" style="width:100%; height:400px; border:#ddd 2px solid;"></div>
				
				<div class="row margin-top-30">
					<div class="col-md-3">
						<label class="input">
							<input id="place_lat" name="place_lat" type="text"
							size="80" maxlength="80" class="form-control" placeholder="위도" autofocus>
						</label>
					</div>
					
					<div class="col-md-3">
						<label class="input">
							<input id="place_lng" name="place_lng" type="text"
							size="80" maxlength="80" class="form-control" placeholder="경도" autofocus>
						</label>
					</div>
					
					<div class="col-md-2">
						<label class="margin-bottom-6 margin-top-0 fancy-form fancy-form-select">
							<select name="place_type" class="form-control">
								<option value="맛집">맛집</option>
								<option value="숙박시설">숙박시설</option>
								<option value="실내활동">실내활동</option>
								<option value="야외활동">야외활동</option>
								<option value="쇼핑몰">쇼핑몰</option>
							</select>
							<i class="fancy-arrow"></i>
						</label>
					</div>
					
					<div class="col-md-4" style="margin: -6px 0px 0px 0px;">
						<label class="inline-block">장소 &nbsp;&nbsp;&nbsp;</label>
						<label class="radio inline-block">
							<input id="place_on" name="place_on" value ="1" type="radio" checked>
							<i></i>활성화
						</label>
						<label class="radio inline-block">
							<input id="place_off" name="place_on" value ="0" type="radio">
							<i></i>비활성화
						</label>
					</div>
					
				</div>
				
				<div class="row">
					<div class="form-group">
						<div class="col-md-12">
							<label>
								파일 업로드
								<small class="text-muted"> 여러 이미지 업로드가능</small>
							</label>
		
							<!-- custom file upload -->
							<input class="custom-file-upload fileUplodeBtn" type="file" data-btn-text="Select a File" multiple>
							<small class="text-muted block">Max file size: 10Mb (jpg/png/gif)</small>
		
						</div>
					</div>
				</div>
				
				<label>파일 업로드 2</label>
				<div class="fileDrop" id="check">
					<div class="text-center margin-top-30">
						<img src="./resources/img/dragUpload/upload-cloud.png" style="width:100px; height:63px;">
						<h4 class="turn-upload-color">Drag &amp; Drop</h4>
					</div>
				</div>
				<div class="uploadedList row"></div>
				<div class="text-center">
					<input type="submit" class="btn btn-primary" value="등록하기" id="btnsub">
					<button class="btn btn-green cancel">등록취소</button>
				</div>
			</fieldset>
		</form>
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