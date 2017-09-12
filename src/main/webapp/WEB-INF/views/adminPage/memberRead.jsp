<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script src="<c:url value="./resources/js/member/member.js"/>" ></script>
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

		<h1>회원정보</h1>

		<!-- breadcrumbs -->
		<ol class="breadcrumb">
			<li><a href="main">메인</a></li>
			<li><a href="memberList">회원정보 관리</a></li>
			<li class="active"> 회원정보</li>
		</ol><!-- /breadcrumbs -->

	</div>
</section>
<!-- /PAGE HEADER -->
<section class="alternate">
	<div class="container">
		<form role="form" style="display:none;"></form>
		<div class="col-md-6 col-md-offset-3">
			<div class="turn-shadow">
				<table class="table table-striped">
					<tr>
						<td class="text-center">
							<img src="displayProfile?fileName=${member.user_profile}" class="turn-shadow" style="width:450px; height:400px; margin:30px;" data-src="/default.png">
						</td>
					</tr>
					<tr>
						<td class="text-center">
							<label for="user_id">아이디 : ${member.user_id}</label>
						</td>
					</tr>
					<tr>
						<td class="text-center">
							<label for="user_birth">생일 : ${member.user_birth}</label>
						</td>
					</tr>
					<tr>
						<td class="text-center">
							<label for="user_gender">성별 : 
								<c:set var="gender" value="${member.user_gender}" />
				
								<c:if test="${gender eq 0}">
									남자
								</c:if>
								<c:if test="${gender eq 1}">
									여자
								</c:if>
							</label>
						</td>
					</tr>
					<tr>
						<td class="text-center">
							<label for="user_phone">전화번호 : ${member.user_phone}</label>
						</td>
					</tr>
					<tr>
						<td class="text-center">
							<label for="user_email">이메일 : ${member.user_email}</label>
						</td>
					</tr>
					<tr>
						<td class="text-center">
							<label for="user_date">가입날짜 : <fmt:formatDate pattern="yyyy-MM-dd" value="${member.user_date}" /></label>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<div class="row"></div>
		<div class="text-center">
			<button type="submit" class="btn btn-warning">정보수정</button>
			<button type="submit" class="btn btn-danger">회원정보삭제</button>
			<button type="submit" class="btn btn-primary">목록</button>
		</div>
		<div class="pagelist">
			<input type="hidden" id="page" name="page" value="${pagination.page}">
			<input type="hidden" id="recordPage" name="recordPage" value="${pagination.recordPage}">
			<input type="hidden" id="check" name="check" value="${member.user_id}">
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