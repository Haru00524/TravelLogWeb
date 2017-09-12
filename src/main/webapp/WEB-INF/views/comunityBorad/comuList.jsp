<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script src="./resources/js/comunity/comunityCommand.js"/></script>
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

		<h1>Community</h1>

		<!-- breadcrumbs -->
		<ol class="breadcrumb">
			<li><a href="main">Main</a></li>
			<li class="active"> Community</li>
		</ol><!-- /breadcrumbs -->

	</div>
</section>
<!-- /PAGE HEADER -->

<!-- -->
<section class="alternate">
	<div class="container">

		<div class="row">

			<!-- LEFT -->
			<div class="col-md-3 col-sm-3">

				<!-- INLINE SEARCH -->
				<div class="inline-search clearfix margin-bottom-30">
					<form action="comuList" method="get" class="widget_search">
						<input type="hidden" name="page" value="1" >
						<input type="search" placeholder="제목 + 내용 검색" name="keyword" class="serch-input">
						<button type="submit">
							<i class="fa fa-search"></i>
						</button>
					</form>
				</div>
				<!-- /INLINE SEARCH -->

				<hr />

				<!-- side navigation -->
				<div class="side-nav margin-bottom-30 margin-top-30">

					<div class="side-nav-head">
						<button class="fa fa-bars"></button>
						<h4>CATEGORIES</h4>
					</div>
					<ul class="list-group list-group-bordered list-group-noicon uppercase">
						<li class="list-group-item"><a href="comuList?page=1&keyword="><span class="size-12 text-muted pull-right">${map.all}</span>커뮤니티</a></li>
						<li class="list-group-item"><a href="qnaList"><span class="size-12 text-muted pull-right">0</span>고객문의</a></li>
					</ul>
					<!-- /side navigation -->
				
				</div>


				<!-- JUSTIFIED TAB -->
				<div class="tabs nomargin-top hidden-xs margin-bottom-60">

						<!-- RECENT -->
						<div class="sideRecent">
							
							<div class="text-center margin-bottom-20">
								<h4 class="recent-page">최근 게시글</h4>
							</div>
							
							<c:forEach items="${map.recent}" var="recent">
							
								<div class="row tab-post"><!-- post -->
									<div class="col-md-3 col-sm-3 col-xs-3">
										<img src="displayProfile?fileName=${recent.user_profile}" width="40" height="40" alt="" />
									</div>
									<div class="col-md-9 col-sm-9 col-xs-9">
										<a href="comuRead?page=1&post=${recent.board_code}&keyword=" class="tab-post-link">
											<c:choose>
						      					<c:when test="${fn:length(recent.board_title) > 25}">
									            	<c:out value="${fn:substring(fn:replace(recent.board_title, '<br>', ''),0,25)}"/>...
									           	</c:when>
									           	<c:otherwise>
									            	<c:out value="${recent.board_title}"/>
									           	</c:otherwise> 
									        </c:choose>
										</a>
										<small><fmt:formatDate pattern="yyyy-MM-dd" value="${recent.board_date}" /></small>
									</div>
								</div><!-- /post -->
								
							</c:forEach>

						</div>
						<!-- /RECENT -->

				</div>
				<!-- JUSTIFIED TAB -->


				<!-- TAGS 
				<h3 class="hidden-xs size-16 margin-bottom-20">TAGS</h3>
				<div class="hidden-xs margin-bottom-60 padding-8">

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
				<!-- 
				<hr />
				-->
				<!-- SOCIAL ICONS -->
				<!-- 
				<div class="hidden-xs margin-top-40 margin-bottom-60 margin-left-6">
					<a href="#" class="social-icon social-icon-border social-facebook pull-center" data-toggle="tooltip" data-placement="top" title="Facebook">
						<i class="icon-facebook"></i>
						<i class="icon-facebook"></i>
					</a>

					<a href="#" class="social-icon social-icon-border social-twitter pull-center" data-toggle="tooltip" data-placement="top" title="Twitter">
						<i class="icon-twitter"></i>
						<i class="icon-twitter"></i>
					</a>

					<a href="#" class="social-icon social-icon-border social-gplus pull-center" data-toggle="tooltip" data-placement="top" title="Google plus">
						<i class="icon-gplus"></i>
						<i class="icon-gplus"></i>
					</a>

					<a href="#" class="social-icon social-icon-border social-linkedin pull-center" data-toggle="tooltip" data-placement="top" title="Linkedin">
						<i class="icon-linkedin"></i>
						<i class="icon-linkedin"></i>
					</a>

					<a href="#" class="social-icon social-icon-border social-rss pull-center" data-toggle="tooltip" data-placement="top" title="Rss">
						<i class="icon-rss"></i>
						<i class="icon-rss"></i>
					</a>
				</div>
				-->

			</div>

			<!-- RIGHT -->
			<div class="col-md-9 col-sm-9">

				<!-- POST ITEM  class:blog-post-item-->
				<div class="turn-shadow">
					<table class="table table-striped">
						<tr>
							<th class="text-center" width="6%">No</th>
							<th class="text-center" width="54%">제목</th>
							<th class="text-center" width="16%">글쓴이</th>
							<th class="text-center" width="10%">조회수</th>
							<th class="text-center" width="14%">날짜</th>
						</tr>
						<c:forEach items="${map.list}" var="comu">
						<tr>
							<td class="text-center">${comu.board_code}</td>
							<td class="text-center">
								<b>
									<a href="${comu.board_code}" class="comu_info">
										${comu.board_title} 
										<c:if test="${comu.reply_count != 0}">
											<span class="ListColor">&nbsp;[${comu.reply_count}]</span>
										</c:if>
									</a>
								</b>
							</td>
							<td class="text-center">${comu.user_id}</td>
							<td class="text-center">${comu.viewCount}</td>
							<td class="text-center">
							<fmt:formatDate pattern="yyyy-MM-dd" value="${comu.board_date}" />
							</td>
						</tr>
						</c:forEach>	
					</table>
					<c:if test="${fn:length(map.list) == 0}">
						<div class="text-center"><h2 class="padding-100">검색결과가 존재하지않습니다.</h2></div>
					</c:if>
				</div>
				<!-- /POST ITEM -->
				<div class="row margin-left-3">
					<a href="comuWrite" class="btn btn-blue">글쓰기</a>
				</div>	
				
				<!-- PAGINATION -->
				<div class="text-center">
					<!-- Pagination Default -->
					<ul class="pagination nomargin">
						<c:if test="${paginationE.prev}">
							<li>
								<a href="${paginationE.startPage-1}">&laquo;</a>
							</li>
						</c:if>
						<c:forEach begin="${paginationE.startPage}" end="${paginationE.endPage}" var="idx">
							<li <c:out value="${idx==paginationE.page?'class=active':''}" /> ><!-- li class="active" -->	
								<a href="${idx}">${idx}</a>
							</li>
						</c:forEach>
						<c:if test="${paginationE.next}">
							<li>
								<a href="${paginationE.endPage+1}">&raquo;</a>
							</li>
						</c:if>
					</ul>
					<!-- /Pagination Default -->
				</div>
				<!-- /PAGINATION -->
			</div>
			

		</div>


	</div>
</section>
<!-- / -->