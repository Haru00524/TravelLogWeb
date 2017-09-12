<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link href="./resources/libraryJs/summernote/summernote.css" rel="stylesheet">
<script src="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.4/summernote.js"></script>
<script src="./resources/libraryJs/summernote/lang/summernote-ko-KR.js"></script>
<script src="./resources/js/comunity/comunity.js"></script>


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
			<li><a href="comuList">Community</a></li>
			<li class="active">글작성</li>
		</ol><!-- /breadcrumbs -->

	</div>
</section>
<!-- /PAGE HEADER -->

<section class="alternate">
	<div class="container">
		<form id="formComunity">
			<div class="row">
				<div class="form-group">
					<label class="control-label"></label>
					<input class="form-control" id="board_title" name="board_title" type="text" size="100" maxlength="100" placeholder="제목을 입력하세요.">
				</div>
				<textarea class="summernote form-control" name="board_content" data-height="600" data-lang="ko-KR"></textarea>
				<div class="row">
					<div class="addImage" style="display:none;"></div>
					<div class="cacheImage" style="display:none;"></div>
				</div>
				<div class="form-group text-center margin-top-20">
					<input id="comuSubmit" class="btn btn-blue" type="submit" value="작성하기">&nbsp;&nbsp;&nbsp;
					<button id="comuCancel" class="btn btn-red">취소하기</button>
				</div>
			</div>
		</form>
	</div>
</section>
    