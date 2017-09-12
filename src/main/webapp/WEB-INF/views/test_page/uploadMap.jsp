<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style>
    /* iframe 스타일 설정 */
    iframe {
        width: 600px;
        height: 100px;
        border: 1px;
        border-style: solid;
    }
</style>
<div>
	<form id="form1" action="uploadForm" method="post" enctype="multipart/form-data" target="zeroFrame">
		<ul>
		<!--
			<li>
				<label for="place_name">장소이름</label>
				<input id="place_name" name="place_name" type="text"
				size="60" maxlength="60" autofocus>
			<li>
				<label for="place_content">장소내용 </label>
				<textarea id="place_content" name="place_content">
				</textarea>
			<li>
				<label for="place_address">장소주소</label>
				<input id="place_address" name="place_address" type="text"
				size="80" maxlength="80" autofocus>
			<li>
				<label for="place_address">위도</label>
				<input id="place_address" name="place_address" type="text"
				size="80" maxlength="80" autofocus>
				<label for="place_address">경도</label>
				<input id="place_address" name="place_address" type="text"
				size="80" maxlength="80" autofocus>
		-->
			<li>
				<input type="file" name="file">
			<li>
				<input type="submit">
		</ul>
	</form>
	<iframe name="zeroFrame"></iframe>
	<script>
	function addFilePath(msg) {
		document.getElementById('form1').reset();
	}
	</script>
</div>