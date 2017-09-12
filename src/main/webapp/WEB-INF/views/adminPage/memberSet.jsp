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

		<h1>회원정보수정</h1>

		<!-- breadcrumbs -->
		<ol class="breadcrumb">
			<li><a href="main">메인</a></li>
			<li><a href="memberList">회원정보 관리</a></li>
			<li class="active"> 회원정보 수정</li>
		</ol><!-- /breadcrumbs -->

	</div>
</section>
<!-- /PAGE HEADER -->
<section class="alternate">
	<div class="container">
		<form method="post" id="textbr" class="nomargin sky-form boxed" onsubmit="return false">
			<header class="size-18 margin-bottom-20" style="background:rgba(199, 199, 199, 0.1) !important;">
                  <i class="fa fa-user"></i>&nbsp;&nbsp;UserInfoSet
            </header>
			<fieldset class="nomargin">
				<div class="row">
					<div class="col-md-6">
						<div class="profile text-center">
							<label class="input margin-bottom-10">프로필 이미지</label>
							<img src="displayProfile?fileName=${member.user_profile}" class="turn-shadow" style="width:450px; height:400px;" data-src="${member.user_profile}"/>
							<input type="hidden" name="user_profile" value="${member.user_profile}">
							<div class="cookieData" data-cookie="${member.user_profile}" data-cnt="0" style="display:none;"></div>
						</div>
						<div class="text-center">
							<h4 class="text-muted block margin-top-20">사진파일 업로드 : 10Mb (jpg/png/gif)</h4>
							<input type="file" id="targetfile" style="display:none;">
							<button class="image_add btn btn-blue margin-top-10 margin-left-10">등록</button>
							<button class="image_del btn btn-red margin-top-10">삭제</button>
					 	</div>
					</div>
					<div class="col-md-6">
						<label class="input margin-bottom-10">
							<i class="ico-append fa fa-user"></i>
							<input id="user_id" name="user_id" type="text" size="20"
							maxlength="40" value="${member.user_id}" placeholder="변경할 아이디를 입력하세요">
						</label>
						<div id="idconfirm" class="margin-bottom-10">&nbsp;</div>
						
						<label class="input margin-bottom-10">
							<i class="ico-append fa fa-lock"></i>
							<input id="user_pass" name="user_pass" type="password" size="20"
							maxlength="20" value="" placeholder="비밀번호를 입력하세요">
						</label>
						<div id="passconfirm" class="margin-bottom-10">&nbsp;</div>
						
						<div class="row margin-bottom-10">
							<div class="col-md-5">
								<label class="input">
									<input id="yyyy" name="yyyy" type="text" size="3"
									maxlength="4" value="${member.yyyy}">
								</label>
							</div>
							<div class="col-md-2">
								<label class="margin-bottom-6 margin-top-0 fancy-form fancy-form-select">
									<select id="mm" name="mm" class="form-control select mm">
										<option value="0">월</option>
										<option value="1">1</option>
										<option value="2">2</option>
										<option value="3">3</option>
										<option value="4">4</option>
										<option value="5">5</option>
										<option value="6">6</option>
										<option value="7">7</option>
										<option value="8">8</option>
										<option value="9">9</option>
										<option value="10">10</option>
										<option value="11">11</option>
										<option value="12">12</option>
									</select>
									<i class="fancy-arrow"></i>
								</label>
							</div>
							<div class="col-md-5">
								<label class="input">
									<input id="dd" name="dd" type="text" size="2"
									maxlength="2" value="${member.dd}">
								</label>
							</div>
						</div>
						<div id="birth_check" class="margin-bottom-10">&nbsp;</div>
						
						<div class="btn-group margin-bottom-10 gender">
							<button class="btn btn-default male">남</button>
							<button class="btn btn-default female">여</button>
						</div>
						<div class="margin-bottom-10">&nbsp;</div>
						
						<label class="input margin-bottom-10">
							<i class="ico-append fa fa-phone"></i>
							<input id="user_phone" name="user_phone" type="text" size="11"
					       	maxlength="11" value="${member.user_phone}" placeholder="전화번호를 입력하세요.">
						</label>
					    <div id="phone_check" class="margin-bottom-10">&nbsp;</div>   
					    
					    <label class="input margin-bottom-10">
					    	<i class="ico-append fa fa-envelope"></i>
					    	<input id="user_email" name="user_email" type="text" size="20" 
					       	maxlength="40" value="${member.user_email}" placeholder="이메일을 입력하세요.">
					    </label>
					    <div id="email_check" class="margin-bottom-10">&nbsp;</div>
					</div>
				</div>
				<div class="text-center">
					<input type="submit" class="btn btn-blue" value="수정하기" id="btnsub">
				</div>
			</fieldset>
			<input type="hidden" name="page" value="${pagination.page}">
			<input type="hidden" name="recordPage" value="${pagination.recordPage}">
		</form>
		<input type="hidden" class="user_gender" name="user_gender" value="${member.user_gender}">
		<input type="hidden" class="mmValue" value="${member.mm}">
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
