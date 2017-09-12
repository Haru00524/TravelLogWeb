<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script src="./resources/js/place/placeInfo.js"/></script>
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

		<h1>Place</h1>

		<!-- breadcrumbs -->
		<ol class="breadcrumb">
			<li><a href="main">Main</a></li>
			<li class="active"> Place</li>
		</ol><!-- /breadcrumbs -->

	</div>
</section>
<!-- /PAGE HEADER -->

<!-- -->
<section class="alternate">
	<div class="container">
		<div class="text-center clearfix margin-bottom-20">
				<h2 class="col-sm-10 col-sm-offset-1 nomargin-bottom weight-400 size-25">떠나고 싶은 여행 지역의 장소를 찾아보세요.</h2>
		</div>
		<hr />
		<div class="table-responsive padding-40">
			<table class="table turn-shadow"><!-- table-bordered 속성제거 -->
				<tbody>
					<tr class="text-center clickta">
						<td><b>광역도시</b></td>
						<td rowspan="2" class="locals turntive turn-vertical" data-local="locals_all">모든지역</td>
						<td class="locals" data-local="서울">서울</td>
						<td class="locals" data-local="인천">인천</td>
						<td class="locals" data-local="대전">대전</td>
						<td class="locals" data-local="대구">대구</td>
						<td class="locals" data-local="부산">부산</td>
						<td class="locals" data-local="울산">울산</td>
						<td class="locals" data-local="광주">광주</td>
						<td class="locals" data-local="세종">세종시</td>
					</tr>
					<tr class="text-center">
						<td><b>도 지역</b></td>
						<td class="locals" data-local="충청북도">충청북도</td>
						<td class="locals" data-local="충청남도">충청남도</td>
						<td class="locals" data-local="경상북도">경상북도</td>
						<td class="locals" data-local="경상남도">경상남도</td>
						<td class="locals" data-local="전라북도">전라북도</td>
						<td class="locals" data-local="전라남도">전라남도</td>
						<td class="locals" data-local="강원도">&nbsp;강원도&nbsp;</td>
						<td class="locals" data-local="제주">제주도</td>
					</tr>
					<tr class="text-center turn-td-color">
						<td><b>테마정보 (선택)</b></td>
						<td class="thema turntiveT" data-thema="thema_all">모든테마</td>
						<td class="thema" data-thema="쇼핑몰">쇼핑몰</td>
						<td class="thema" data-thema="맛집">맛&nbsp;&nbsp;집</td>
						<td class="thema" data-thema="실내활동">실내활동</td>
						<td class="thema" data-thema="야외활동">야외활동</td>
						<td class="thema" data-thema="숙박시설">숙박시설</td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
				</tbody>
			</table>
			<!-- 검색코드 -->
			<div class="search-box">
				<div class="input-group">
					<input type="text" name="src" placeholder="원하는 카테고리를 선택후 찾고싶은 장소를 입력해보세요." 
						id="keyup" class="form-control text-center datas" value="" autocomplete="off"/>
					<span class="input-group-btn">
						<button class="btn btn-primary searchon" type="submit">장소검색</button>
					</span>
				</div>
			</div> 
			<!-- /검색코드 -->
		</div>
	</div>
</section>
<!-- / -->


<!-- Carousel 4 -->
<section>
	<div class="container">

		<div class="row">
			<div class="heading-title heading-border margin-bottom-50 pull-right">
			<h3 class="">장소 정보<span></span></h3>
			<p class="font-lato size-14">Place-Info</p>
			</div>
			<div class="pull-left s-style">
				<div class="totalCount"></div>
				<div class="sresult"></div>
			</div>
		</div>
		<br />
		<!-- 
			controlls-over		= navigation buttons over the image 
			buttons-autohide 	= navigation buttons visible on mouse hover only
			
			data-plugin-options:
				"singleItem": true
				"autoPlay": true (or ms. eg: 4000)
				"navigation": true
				"pagination": true
				"items": "4"
		
			owl-carousel item paddings
				.owl-padding-0
				.owl-padding-3
				.owl-padding-6
				.owl-padding-10
				.owl-padding-15
				.owl-padding-20
		-->
		<div class="text-center placeInfos row">
			
			<!-- 장소리스트
			<div class="img-hover col-md-3 col-sm-3 margin-bottom-30">
				<div class="setboard">
					<div class="imagesa">
						<a href="#">
							<img class="img-responsive" src="http://placehold.it/451x300" alt="" style="width:100%; height:170px;">
						</a>
					</div>
					<div class="padding-10">
						<h4 class="text-left"><a href="#">Lorem Ipsum Dolor</a></h4>
						<p class="text-left">Lorem ipsum dolor sit amet, consectetur adipisicing elit.</p>
						<ul class="text-left size-12 list-inline list-separator">
		                    <li>
								<i class="fa fa-calendar-check-o"></i>2017. 06. 09
		                </ul>
	                </div>
                </div>
			</div>
			-->
			
		</div>
		<div class="placePaging text-center"></div>
	</div>
</section>
<!-- /Carousel 4 -->
