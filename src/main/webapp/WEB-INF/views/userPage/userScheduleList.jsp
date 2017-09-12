<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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

		<h1>MyPage</h1>

		<!-- breadcrumbs -->
		<ol class="breadcrumb">
			<li><a href="main">Main</a></li>
			<li class="active">MyPage</li>
		</ol><!-- /breadcrumbs -->

	</div>
</section>
<!-- /PAGE HEADER -->


<section class="alternate">
	<div class="container">
		<div class="row">
			<span class="group_list_title" onclick="going_plan('going')">
				<h4>계획중인 일정</h4>
			</span>
			<span>|</span>
			<span class="group_list_title" onclick="finish_plan('finish')">
				<h4>완성된 일정</h4>
			</span>
			<div id="user_group_list_box">
			<c:if test="${group != null}">
				<c:forEach items="${group}" var="group">
					<div class="user_group_list" data-code="${group.group_Code}" data-name="${group.travel_Title}" data-start="${group.start_Date}" data-end="${group.end_Date}" onclick="planDetail($(this))">
						<div class="plan-list-img">
							<div class="plan-list-delete" data-toggle="modal" data-whatever="${group.group_Code}">X</div>
						</div>
						<div class="plan-content">
							<div class="plan-list-title">${group.travel_Title}</div>
							<div class="plan-date">
								<span>${group.start_Date}</span>
								<span>~</span>
								<span>${group.end_Date}</span>
							</div>
						</div>
						
					</div>
				</c:forEach>
			</c:if>
			<c:if test="${empty group}">
				<h4 class='list-empty'>진행중인 계획이 없습니다</h4>	
			</c:if>
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
