<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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

		<h1>Customer Service</h1>

		<!-- breadcrumbs -->
		<ol class="breadcrumb">
			<li><a href="main">Community</a></li>
			<li class="active"> Customer Service</li>
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
				<div class="inline-search clearfix margin-bottom-40">
					<form action="" method="get" class="widget_search">
						<input type="search" placeholder="Start Searching..." id="s" name="s" class="serch-input">
						<button type="submit">
							<i class="fa fa-search"></i>
						</button>
					</form>
				</div>
				<!-- /INLINE SEARCH -->

				<hr />

				<!-- side navigation -->
				<div class="side-nav margin-top-40 margin-bottom-200">

					<div class="side-nav-head">
						<button class="fa fa-bars"></button>
						<h4>CATEGORIES</h4>
					</div>
					<ul class="list-group list-group-bordered list-group-noicon uppercase">
						<li class="list-group-item"><a href="comuList?page=1&keyword="><span class="size-11 text-muted pull-right">0</span>커뮤니티</a></li>
						<li class="list-group-item"><a href="qnaList"><span class="size-11 text-muted pull-right">0</span>고객문의</a></li>
					</ul>
					<!-- /side navigation -->
				
				</div>

				<!-- 
				<hr />
				 -->

				<!-- SOCIAL ICONS -->
				<!--
				<div class="hidden-xs margin-top-40 margin-bottom-200 margin-left-6">
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

				<!-- POST ITEM -->
				<div class="blog-post-item">
					<table class="table table-striped">
						<tr>
							<th class="text-center" width="6%">No</th>
							<th class="text-center" width="66%">제목</th>
							<th class="text-center" width="14%">답변확인</th>
							<th class="text-center" width="14%">날짜</th>
						</tr>
						<c:forEach items="${list}" var="place">
						<tr>
							<td class="text-center"></td>
							<td class="text-center">
								<a href="${place.place_code}" class="place_info">
								${place.place_name}
								</a>
							</td>
							<td class="text-center"></td>
							<td class="text-center">
								<fmt:formatDate pattern="yyyy-MM-dd" value="${place.add_date}" />
							</td>
						</tr>
						</c:forEach>	
					</table>
				</div>
				<!-- /POST ITEM -->
				
				<!-- PAGINATION -->
				<div class="text-center">
					<!-- Pagination Default -->
					<ul class="pagination nomargin">
						<li><a href="#">&laquo;</a></li>
						<li class="active"><a href="#">1</a></li>
						<li><a href="#">2</a></li>
						<li><a href="#">3</a></li>
						<li><a href="#">4</a></li>
						<li><a href="#">5</a></li>
						<li><a href="#">&raquo;</a></li>
					</ul>
					<!-- /Pagination Default -->
				</div>
				<!-- /PAGINATION -->
			</div>
			

		</div>


	</div>
</section>
<!-- / -->