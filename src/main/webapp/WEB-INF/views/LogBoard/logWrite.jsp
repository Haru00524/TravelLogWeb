<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<link href="./resources/libraryJs/summernote/summernote.css" rel="stylesheet">
<script src="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.4/summernote.js"></script>
<script src="./resources/libraryJs/summernote/lang/summernote-ko-KR.js"></script>
<script src="./resources/js/logs/logwrite.js"></script>
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

		<h1>Log Write</h1>

		<!-- breadcrumbs -->
		<ol class="breadcrumb">
			<li><a href="main">Main</a></li>
			<li class="active">Life-log</li>
		</ol><!-- /breadcrumbs -->

	</div>
</section>
<section class="alternate">
	<div class="container">
		<form id="formobj">
			<div class="row">
				<div class="form-group">
					<label class="control-label"></label>
					<input class="form-control" id="title" name="board_title" type="text" size="20" maxlength="40" placeholder="제목을 입력하세요.">
				</div>
				<textarea class="summernote form-control" name="board_content" data-height="600" data-lang="ko-KR"></textarea>
				<div class="row form-group text-left">
					<div class="col-md-4 hashTag">
						<input class="form-control input_hashtag" type="text" maxlength="80" placeholder="해시태그 입력.">
						<label class="radio">
							<input type="radio" name="share_type" value="1" checked="checked">
							<i></i> 전체공개
						</label>
						<!-- 
						<label class="radio">
							<input type="radio" name="share_type" value="2">
							<i></i> 그룹공개
						</label>
						 -->
						<label class="radio">
							<input type="radio" name="share_type" value="3">
							<i></i> 비공개
						</label>
					</div>
					<div class="col-md-1">
						<button class="btn btn-red btn-sm turn-margin-top-5 hashDel">모두삭제</button>
					</div>
					<div class="col-md-7">
						<ul class="list-inline nomargin hashTagCopy">
						</ul>
					</div>
					<div class="addHash" style="display:none;"></div>
					<div class="addImage" style="display:none;"></div>
					<div class="cacheImage" style="display:none;"></div>
				</div>
				<div class="form-group text-center">
					<input id="boardSubmit" class="btn btn-blue" type="submit" value="등록">&nbsp;&nbsp;&nbsp;
					<button id="boardCancel" class="btn btn-red">취소</button>
				</div>
			</div>
		</form>
	</div>
</section>