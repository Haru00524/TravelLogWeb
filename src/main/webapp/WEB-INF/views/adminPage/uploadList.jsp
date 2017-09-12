<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script src="<c:url value="./resources/js/upload/uploadList.js"/>" ></script>
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

		<h1>장소 리스트</h1>

		<!-- breadcrumbs -->
		<ol class="breadcrumb">
			<li><a href="main">메인</a></li>
			<li class="active">장소 리스트</li>
		</ol><!-- /breadcrumbs -->

	</div>
</section>
<!-- /PAGE HEADER -->
<section class="alternate">
	<div class="container">
		<form id="pageForm" class="form-inline" style="display:none;">
			<input type="hidden" name="page" value="${pagination.page}">
			<input type="hidden" name="recordPage" value="${pagination.recordPage}">
		</form>
		
		<div class="turn-shadow">
			<table class="table table-striped">
				<tr>
					<th>글 번호</th>
					<th>주소</th>
					<th>장소명</th>
					<th>위도</th>
					<th>경도</th>
					<th>장소타입</th>
					<th>장소 활성화</th>
					<th>등록일자</th>
				</tr>
				<c:forEach items="${list}" var="place">
					<tr>
						<td>${place.place_code}</td>
						<td>${place.place_address}</td>
						<td>
							<b>
								<a href="${place.place_code}" class="place_info">
									${place.place_name}
								</a>
							</b>
						</td>
						<td>${place.place_lat}</td>
						<td>${place.place_lng}</td>
						<td>${place.place_type}</td>
						<td class="text-center">${place.place_on}</td>
						<td>
							<fmt:formatDate pattern="yyyy-MM-dd" value="${place.add_date}" />
						</td>
					</tr>
				</c:forEach>	
			</table>
		</div>
		
		<div class="row margin-left-0">
				<a href="upload" class="btn btn-blue">장소등록</a>
		</div>
		<div class="text-center">	
			<ul class="pagination">
				<c:if test="${pagination.prev}">
					<li>
						<a href="${pagination.startPage-1}">&laquo;</a>
					</li>
				</c:if>
				<c:forEach begin="${pagination.startPage}" end="${pagination.endPage}" var="idx">
					<li <c:out value="${idx==pagination.page?'class=active':''}" /> ><!-- li class="active" -->	
						<a href="${idx}">${idx}</a>
					</li>
				</c:forEach>
				<c:if test="${pagination.next}">
					<li>
						<a href="${pagination.endPage+1}">&raquo;</a>
					</li>
				</c:if>
			</ul>
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