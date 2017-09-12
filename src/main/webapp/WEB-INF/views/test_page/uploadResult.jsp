<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div>
	파일이업로드되었습니다. <br>
	파일명 : ${saveName}
</div>
<script>
	var result = '${saveName}';
	parent.addFilePath(result);
</script>