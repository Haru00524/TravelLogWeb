<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<link rel="stylesheet" href="<c:url value="./resources/css/uploadDrop.css"/>" />
<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.1/handlebars.js"></script>
<script id="template" type="text/x-handlebars-template">
<li>
	<span class="mailbox-attachment-icon has-img"><img src="{{imgsrc}}" alt="Attachment"></span>
	<div class="mailbox-attachment-info">
		<a href="{{getLink}}" class="mailbox-attachment-name">{{fileName}}</a>
		<a href="{{fullName}}" class="btn btn-default btn-xs pull-right delbtn">
			<i class="fa fa-fw fa-remove"></i>
		</a>
	</div>
</li>
</script>
<script src="<c:url value="./resources/js/handlebarTemple.js"/>" ></script>

<form action="./uploadForm" method="POST">
<div>
	<div class="form-group">
		<label for="example">File DROP Here</label>
		<div class="fileDrop"></div>		
	</div>
</div>

<!-- /.box-body -->
<div class="box-footer">
	<div>
		<hr>
	</div>
	
	<ul class="mailbox-attachments clearfix uploadedList">
	</ul>
	
	<button type="submit" id="registerForm" class="btn btn-primary">Submit</button>
</div>
</form>