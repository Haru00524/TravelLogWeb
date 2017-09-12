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

		<h1>커뮤니티</h1>

		<!-- breadcrumbs -->
		<ol class="breadcrumb">
			<li><a href="main">메인</a></li>
			<li><a href="comuList">커뮤니티</a></li>
			<li class="active">글정보</li>
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
				<div class="side-nav margin-bottom-30">

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

			</div>
		
		
		
			<!-- LEFT -->
			<div class="col-md-9 col-sm-9">
				<div class="numberPost" data-src="${map.vo.board_code}">

					<div class="pull-left">
						<div class="comuTitle">${map.vo.board_title}</div>
					</div>
					<div class="pull-right hei-line">
						<i class="fa fa-eye size-20"></i> 
						<span class="size-20">${map.vo.viewCount}</span> <!-- class:font-lato -->
						&nbsp;
						<i class="fa fa-clock-o size-20"></i> 
						<span class="size-20"><fmt:formatDate pattern="yyyy-MM-dd" value="${map.vo.board_date}" /></span>
					</div>
	
					<ul class="blog-post-info comuTitleline">
					</ul>
	
					<div><p class="content">${map.vo.board_content}</p></div>
	
					<hr/><!--class:divider -->




					<!-- SHARE POST -->
					<div class="clearfix margin-top-20">
	
						<span class="pull-left margin-top-10 bold hidden-xs">
							Share Post &nbsp;&nbsp;&nbsp; : &nbsp;&nbsp;
						</span>
	
						<a href="#" class="social-icon social-icon-sm social-icon-transparent social-facebook pull-left" data-toggle="tooltip" data-placement="top" title="Facebook">
							<i class="icon-facebook"></i>
							<i class="icon-facebook"></i>
						</a>
	
						<a href="#" class="social-icon social-icon-sm social-icon-transparent social-twitter pull-left" data-toggle="tooltip" data-placement="top" title="Twitter">
							<i class="icon-twitter"></i>
							<i class="icon-twitter"></i>
						</a>
	
						<a href="#" class="social-icon social-icon-sm social-icon-transparent social-gplus pull-left" data-toggle="tooltip" data-placement="top" title="Google plus">
							<i class="icon-gplus"></i>
							<i class="icon-gplus"></i>
						</a>
	
						<a href="#" class="social-icon social-icon-sm social-icon-transparent social-pinterest pull-left" data-toggle="tooltip" data-placement="top" title="Pinterest">
							<i class="icon-pinterest"></i>
							<i class="icon-pinterest"></i>
						</a>
	
						<a href="#" class="social-icon social-icon-sm social-icon-transparent social-call pull-left" data-toggle="tooltip" data-placement="top" title="Email">
							<i class="icon-email3"></i>
							<i class="icon-email3"></i>
						</a>
						
						<a href="comuList?page=${page}&keyword=${keyword}" class="btn btn-3d btn-teal pull-right" style="margin:0px 0px 0px 5px;">목록</a>
			
						<c:if test="${map.vo.user_id eq mem}">
							<a class="btn btn-3d btn-amber pull-right comuDel" style="margin:0px 0px 0px 5px;">글삭제</a>
							<a href="comuSet?page=${page}&post=${map.vo.board_code}&keyword=${keyword}" class="btn btn-3d btn-aqua pull-right" style="margin:0px 0px 0px 5px;">글수정</a>
						</c:if>
						<a href="#" class="pull-right fontMargin"><img src="displayProfile?fileName=${map.vo.user_profile}" style="width:40px; height:40px; border:1px solid gray;">
								&nbsp;<span class="font-lato fontRead">${map.vo.user_id}</span>
						</a>
				
					</div>
					<!-- /SHARE POST -->

					<hr/><!--class:divider -->
			 
			 
			 
			 
					<!-- COMMENTS -->
	
					<div class="comments margin-top-0" data-session="${mem}">
	
						<h4 class="page-header size-20">
							댓글 <span>${fn:length(map.replylist)}</span> 개
						</h4>
						
						<div class="rankItem">
							<c:forEach items="${map.rank}" var="rank" varStatus="status">
								<c:choose>
									<c:when test="${rank.count != 0}">
										<div class="comuItem">
			
											<!-- user-avatar -->
							 				<span class="user-avatar">
												<img class="pull-left media-object c-profile" src="displayProfile?fileName=${rank.user_profile}" width="64" height="64" alt="">
											</span>
					
											<div class="media-body">
												<a class="pull-right size-20 replylike replyColor" data-no="${rank.reply_code}" data-replyno="${rank.board_code}" data-like="${rank.confirm_id}"><i class="fa fa-thumbs-o-up"></i> ${rank.count}</a>
												<div class="pull-right topReply">TOP ${status.index + 1}</div>
												<h4 class="media-heading bold size-20 nonbottom">${rank.user_id}</h4>
												<b class="block"><fmt:formatDate pattern="yyyy-MM-dd" value="${rank.board_date}" /></b>
												<div>${rank.board_content}</div>
											</div>
											
										</div>
									</c:when>
									<c:otherwise>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</div>
						
						<hr>
						
						<div class="replyItem">
							<c:forEach items="${map.replylist}" var="reply">
							<!-- comment item -->
								<div class="comuItem">
			
									<!-- user-avatar -->
					 				<span class="user-avatar">
										<img class="pull-left media-object c-profile" src="displayProfile?fileName=${reply.user_profile}" width="64" height="64" alt="">
									</span>
			
									<div class="media-body">
										<a class="pull-right size-20 replylike replyColor" data-no="${reply.reply_code}" data-replyno="${reply.board_code}" data-like="${reply.confirm_id}"><i class="fa fa-thumbs-o-up"></i> ${reply.count}</a>
										<h4 class="media-heading bold inline-block size-20 nonbottom">${reply.user_id}</h4>
										<c:if test="${mem == reply.user_id}">
										<div class="inline-block">
											<button class='btn btn-aqua btn-xs modify-buttom' style="margin-left:16px;" data-reply-code="${reply.board_code}" data-board="${reply.reply_code}">수정</button>
											<button class='btn btn-brown btn-xs remove-buttom' data-reply-code="${reply.board_code}" data-board="${reply.reply_code}">삭제</button>
										</div>
										</c:if>
										<b class="block"><fmt:formatDate pattern="yyyy-MM-dd" value="${reply.board_date}" /></b>
										<div>${reply.board_content}</div>
									</div>
									
								</div>
							</c:forEach>
						</div>
						
					</div> 

					<div class="row">
						<div class="col-md-12">
							<textarea maxlength="5000" rows="4" class="form-control margin-bottom-10"></textarea>
							<button class="btn btn-3d btn-lg btn-reveal btn-black pull-right write-buttom" data-board-num="${map.vo.board_code}">
								<i class="fa fa-check"></i>
								<span>댓글 작성</span>
							</button>
						</div>
					</div> 

					<!-- /COMMENTS -->

				</div>
			</div>
		</div>
	</div>
</section>