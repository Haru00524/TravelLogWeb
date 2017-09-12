<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script src="https://openapi.map.naver.com/openapi/v3/maps.js?clientId=Ao5To6qq05xL8fmhSOTK"></script>
<script src="<c:url value="./resources/js/member/mydel.js"/>" ></script>
<script src="<c:url value="./resources/js/member/userInfo.js"/>" ></script>

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

<c:if test="${info == 'user' || info == 'admin'}">
<section class="page-header page-header-xs shadow-before-1">
	<div class="container">

		<h1>Travel-Plan</h1>

		<!-- breadcrumbs -->
		<ol class="breadcrumb">
			<li><a href="main">MyPage</a></li>
			<li class="active">Travel-Plan</li>
			<li class="active">${title}</li>
		</ol><!-- /breadcrumbs -->

	</div>
</section>
<!-- /PAGE HEADER -->


<section class="alternate">
	<div class="container">
		<div class="row plan-row">
			<div class="plan_header">
				<div>
					<div id="plan-title" data-code="${group_Code}">${title}</div>	
					<div class="plan-date">
						<span>${startDate}</span>
						<span>  ~ </span>
						<span>${endDate}</span>
					
					</div>
				</div>
				<div id="plan-start" data-code="${startDate}" style="display:none"></div>
				<div id="plan-end" data-code="${endDate}" style="display:none"></div>
			</div>
			<div class="plan_nav">
				<div onclick="travel_plan()">여행일정</div>
				<div onclick="travel_cost()">여행경비</div>
				<div onclick="travel_supplies()">여행준비물</div>
				<div onclick="travel_timeline()">타임라인</div>
				<div onclick="travel_modify()">수정하기</div>
			</div>
			<div class="plan_section">
				<div id="plan_contain" style="height:auto;">
					<div id="plan_list_contain">
					<c:forEach items="${list}" var="list" varStatus="status">
						<div class="plan-day-list" data-lat = "${list.place_lat}" data-lng = "${list.place_lng}">
							<c:if test="${ status.index == 0 || days[status.index-1] != days[status.index]}" >
								<div class="plan-day-select">DAY${days[status.index]}</div>
							</c:if>
							<div class="plan-day-content">
								<div class="plan-place-priority">${list.travel_priority }</div>
								
								<div class="plan-place-img"><img src="displayFile?fileName=${list.place_img}"></div>
								<div class="plan-place-name">${list.place_name}</div>
							</div>
						</div>
					</c:forEach>
					</div>
					<div id="plan_map">
						
					</div>
				</div>
			</div>
		</div>
	</div>
	
   <!-- plan delete Modal -->
   <div class="modal fade " id="planlist-delete">
      <div class="modal-dialog planlist-size ">
         <div class="modal-content planlist-size">
            <div class="modal-header planlist-title">
               <h5>여행계획을 지우시겠습니까?</h5>
            </div>
            <div class="modal-body">
               <div id="planlist-body">
                  <button onclick="plan_confirm()" data-dismiss="modal">확인</button>
                  <button data-dismiss="modal">취소</button>
               </div>
            </div>      
         </div>
      </div>
   </div>
</section>
</c:if>
<c:if test="${info == null}">
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