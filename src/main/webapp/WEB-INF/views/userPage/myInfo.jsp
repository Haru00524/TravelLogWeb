<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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

		<h1>마이페이지</h1>

		<!-- breadcrumbs -->
		<ol class="breadcrumb">
			<li><a href="main">메인</a></li>
			<li class="active">마이페이지</li>
		</ol><!-- /breadcrumbs -->

	</div>
</section>
<!-- /PAGE HEADER -->


<section class="alternate">
	<div class="container">

		<div class="row">

			<!-- LEFT -->
			<div class="col-md-3 col-sm-3">

				<!-- side navigation -->
				<div class="side-nav margin-bottom-200 margin-top-40">
					<div class="side-nav-head">
						<h4>MY PAGE</h4>
					</div>
					<ul class="list-group list-group-bordered list-group-noicon uppercase">
						<c:if test="${info == 'user'}">
							<li class="list-group-item">
								<a href="myFriend"><span class="size-11 text-muted pull-right"></span>친구관리</a>
							</li>
							<li class="list-group-item">
								<a href="myModify"><span class="size-11 text-muted pull-right"></span>회원정보수정</a>
							</li>
							<li class="list-group-item">
								<a class="memDel"><span class="size-11 text-muted pull-right"></span>회원탈퇴</a>
							</li>
						</c:if>
						<c:if test="${info == 'admin'}">
							<li class="list-group-item mypageMove">
								<a href="myModify"><span class="size-11 text-muted pull-right"></span>관리자정보수정</a>
							</li>
						</c:if>
					</ul>
				</div>
			</div>
			<div id="user-travel-log">	
				<c:if test="${info == 'user'}">
					<div><h2>Log</h2></div>
					<div onclick="travel_plan_list()"><h2>Travel-Plan</h2></div>
				</c:if>
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