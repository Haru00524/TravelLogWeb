<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script src="./resources/js/logs/logtravel.js"/></script>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAF8iTF3JtdLLhprWyASWE8APl6RM6BGBQ"></script>

<section class="page-header page-header-xs shadow-before-1">
	<div class="container">

		<h1>Log</h1>

		<!-- breadcrumbs -->
		<ol class="breadcrumb">
			<li><a href="main">Main</a></li>
			<li><a href="logInfo">Log</a></li>
			<li class="active">${map.travel.board_title}</li>
		</ol><!-- /breadcrumbs -->

	</div>
</section>
<section class="alternate">
	<div class="container">
		<div class="col-md-12 col-sm-12">
			<div class="nomargin sky-form boxed" data-session="${mem}">
				
				<header class="size-18" style="background:rgba(199, 199, 199, 0.1) !important;">
					<b>${map.travel.board_title}</b>
					<div class="pull-right">
						<i class="fa fa-user"></i>&nbsp;
						<span class="font-lato">${map.travel.group_count}</span>&nbsp;&nbsp;
						<i class="fa fa-eye"></i>&nbsp;
						<span class="font-lato">${map.travel.viewCount}</span>&nbsp;&nbsp;
						<i class="fa fa-clock-o"></i>&nbsp;
						<span class="font-lato"><fmt:formatDate pattern="yy.MM.dd" value="${map.travel.board_date}" /></span>&nbsp;&nbsp;
					</div>
				</header>
				
				<fieldset class="nomargin" style="padding:15px 15px 0px 15px;">
					
					<div class="row" style="margin-left:0px;'">
						<div class="pull-right travel-like" data-code="${map.travel.board_code}">
							<c:choose>
								<c:when test="${map.travel.my_like == 1}">
									<i class='fa fa-heart'></i>
									<div>${map.travel.like_count}</div>
								</c:when>
								<c:otherwise>
									<i class='fa fa-heart-o'></i>
									<div>${map.travel.like_count}</div>
								</c:otherwise>
							</c:choose>
						</div>
						<div>
							<img class="thumbnail pull-left" src="displayProfile?fileName=${map.travel.user_profile}" style="width:70px; height:70px; margin:0 20px 0px 0;">
							<div class='time-id'>${map.travel.user_id}</div>
							<div class='time-title inline-block'>일정기간 : ${map.travel.start_Date} ~ ${map.travel.end_Date}</div>
							<fmt:formatDate pattern="yyyyMMdd" value="${map.travel.end_Date}" var="endDate" />
							<fmt:formatDate pattern="yyyyMMdd" value="${map.travel.start_Date}" var="startDate" />
							<fmt:parseDate pattern="yyyyMMdd" value="${map.now}" var="now"/>
							<fmt:formatDate pattern="yyyyMMdd" value="${now}" var="nowDate" />
							<c:choose>
								<c:when test="${nowDate < startDate && nowDate < endDate}">
									<div class='time-title inline-block' style="color:#904fe2;">&nbsp; [ 계획예정중인 일정 ] </div>
								</c:when>
								<c:when test="${nowDate >= startDate && nowDate <= endDate}">
									<div class='time-title inline-block' style="color:#904fe2;">&nbsp; [ 진행중인 일정 ] </div>
								</c:when>
								<c:otherwise>
									<div class='time-title inline-block' style="color:#904fe2;">&nbsp; [ 종료된 일정 ] </div>
								</c:otherwise>
							</c:choose>
							
							<div class="time-title ">그룹 인원 : ${map.travel.group_count}명</div>
						</div>
					</div>
					
					<!-- 탭디자인 -->
					<div class="tab-content">
						
						<ul class="list-inline nomargin">
							<c:forEach items="${map.nal}" var="days" varStatus="status">
								<c:choose>
									<c:when test="${status.index == 0}">
										<li class="travelTab blueActive" data-num="${days}" data-group="${map.travel.group_Code}">DAY ${status.index+1}</li>
									</c:when>
									<c:otherwise>
										<li class="travelTab travel-hover" data-num="${days}" data-group="${map.travel.group_Code}">DAY ${status.index+1}</li>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</ul>
						
						<!--
						<c:forEach items="${map.place}" var="place" varStatus="status">
							<div class="maps" data-lat="${place.place_lat}" data-len="${place.place_lng}"></div>
						</c:forEach>
						-->
						
						<div id="dataClassA" style="display:none;">${map.place.toString()}</div>
						<div id="dataClassB" style="display:none;">${map.list.toString()}</div>
						
						<div id="google_map" style="width:100%; height:600px; border:#ddd 2px solid; margin-top:20px;"></div>					
						
					</div>
					
				</fieldset>
				
				<!-- 댓글작성부분 -->
				<div style="padding:15px 15px 0px 15px; position:relative;">
					<textarea maxlength="5000" rows="2" class="form-control travel-reply" style="width:80%; display:inline-block;"></textarea>
					<button class="btn btn-3d btn-lg btn-reveal btn-black write-buttom" style="width:18%; height:55px; margin:0px 0px 0px 17px; display:inline-block; position:absolute;" data-num="${map.travel.board_code}">
						<i class="fa fa-check"></i>
						<span>댓글 작성</span>
					</button>
				</div>
				
				<!-- 댓글리스트 -->
				<div class="travelReply size-15" data-total="${map.totalCount}">
					
					<c:forEach items="${map.reply}" var="reply" varStatus="status">
						<div class="comment-item">
							<!-- user-avatar -->
						
							<div class="t-reply-info">
								<img class="pull-left travel-img" src="displayProfile?fileName=${reply.user_profile}">
								<div class="pull-left travel-name">${reply.user_id}</div>
								<div class="pull-right travel-day"><fmt:formatDate pattern="yy.MM.dd" value="${reply.board_date}" /></div>
								<div class="pull-right">
									<c:choose>
										<c:when test="${reply.user_id eq mem}">
											<button class="btn btn-primary btn-xs reply-buttom" style="margin:0px 0px 0px 17px;" data-code="${reply.board_code}" data-reply="${reply.reply_code}">수정</button>
											<button class="btn btn-red btn-xs delete-buttom" style="margin:0px 5px 0px 0px;" data-code="${reply.board_code}" data-reply="${reply.reply_code}">삭제</button>
										</c:when>
										<c:otherwise>
										</c:otherwise>
									</c:choose>
								</div>
							</div>
							
							<div class="media-body" style="width:800px; word-break:break-all;">${reply.board_content}</div>
						</div>
					</c:forEach>
			
				</div>
				
				<!-- 댓글페이징 박스 -->
				<div class="pagingbox text-center">
				
				</div>
				
				<fieldset style="padding:0px; border-top: 1px solid rgba(0,0,0,0); margin-bottom:0px;">
					<div class="travel-log-data">
						<c:choose>
							<c:when test="${fn:length(map.list) != 0}">
								<c:forEach items="${map.list}" var="list" varStatus="status">
									<div class="col-md-4 col-sm-4">
										<div class='img-hover margin-bottom-30 divTarget' data-board='${list.board_code}' data-index='${status.index}' data-type='1'>
											<div class='timeline-t'>
												<div class='padding-10'>
													<div class="time-index pull-right">${status.index+1} 번째</div>
													<img class='thumbnail pull-left' src='displayProfile?fileName=${list.user_profile}' style='width:50px; height:50px;'>
													<div class='time-id'>${list.user_id}</div>
													<div class='time-title'>${list.board_title}</div>
												</div>
												
												<c:choose>
													<c:when test="${list.file_content[0] != ''}">
														<div class='images'>
											 				<img class='img-responsive' src='displayLogs?fileName=${list.file_content[0]}' alt=''> <!-- 라이프로그이미지 -->
														</div>
													</c:when>
													<c:otherwise>
													</c:otherwise>
												</c:choose>
												
												<div class='padding-10'>
													<div class='resource' style='clear:both;'>
														<p>${list.board_content}</p>
														<ul class='list-inline nomargin hashTagList'>
															<c:forEach items="${list.hash_tag_content}" var="hash">
																<li class='hashStyle'><a>${hash}</a></li>
															</c:forEach>
														</ul>
														<ul class='text-center size-18 list-inline ultop targeting'>
															<li> 
																<i class='fa fa-calendar-check-o'></i><b><fmt:formatDate pattern="yy.MM.dd" value="${list.board_date}" /></b>
															</li>
															<li><b>-</b></li>
															<li class="t-log-like" data-code="${list.board_code}">
																<c:choose>
																	<c:when test="${list.my_like == 1}">
																		<i class='fa fa-heart'></i><b>${list.like_count}</b>
																	</c:when>
																	<c:otherwise>
																		<i class='fa fa-heart-o'></i><b>${list.like_count}</b>
																	</c:otherwise>
																</c:choose>
															</li>
														</ul>
													</div>
												</div>
											</div>
										</div>
									</div>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<div class="text-center">
									<div class="margin-top-100 margin-bottom-100"><h2>진행된 여행로그 정보가 없습니다.</h2></div>
								</div>
							</c:otherwise>
						</c:choose>
					</div>
					
				</fieldset>
				
			</div>
		</div>
	</div>
</section>