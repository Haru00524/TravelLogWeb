<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%> 

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0" />

<!-- My style -->
<link rel="stylesheet" type="text/css" href="<c:url value="./resources/css/allnew.css"/>">

<!-- Font style -->
<link href="https://fonts.googleapis.com/css?family=Open+Sans:300,400%7CRaleway:300,400,500,600,700%7CLato:300,400,400italic,600,700" rel="stylesheet" type="text/css" />

<!-- BOOTSTRAP style -->
<link rel="stylesheet" type="text/css" href="<c:url value="./resources/bootstrap/css/bootstrap.css"/>">

<!-- REVOLUTION SLIDER -->
<link rel="stylesheet" type="text/css" href="<c:url value="./resources/libraryCss/slider/extralayers.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="./resources/libraryCss/slider/settings.css"/>">

<!-- THEME CSS -->
<link rel="stylesheet" type="text/css" href="<c:url value="./resources/libraryCss/theme/essentials.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="./resources/libraryCss/theme/layout.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="./resources/libraryCss/theme/thematics-education.css"/>">

<!-- PAGE LEVEL SCRIPTS CSS -->
<link rel="stylesheet" type="text/css" href="<c:url value="./resources/libraryCss/page_level/header-1.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="./resources/libraryCss/page_level/layout-shop.css"/>">

<!-- Design Color -->
<link rel="stylesheet" type="text/css" href="<c:url value="./resources/libraryCss/designColor/darkgreen.css"/>">

<!-- JAVASCRIPT FILES -->
<script type="text/javascript">var plugin_path = './resources/';</script>
<script type="text/javascript" src="./resources/libraryJs/sliderJs/jquery-2.2.3.min.js"></script>
<script type="text/javascript" src="./resources/libraryJs/sliderJs/scripts.js"></script>
	
<!-- REVOLUTION SLIDER -->
<script type="text/javascript" src="./resources/libraryJs/sliderPlugin/jquery.themepunch.tools.min.js"></script>
<script type="text/javascript" src="./resources/libraryJs/sliderPlugin/jquery.themepunch.revolution.min.js"></script>
<script type="text/javascript" src="./resources/libraryJs/sliderJs/demo.revolution_slider.js"></script>

<!-- STYLESWITCHER - REMOVE -->
<script type="text/javascript" src="./resources/libraryJs/sliderPlugin/styleswitcher.js"></script>


<title>Travel Log</title>
</head>
<body class="enable-animation menu-vertical">
	<div id="wrap">
		<tiles:insertAttribute name="header" />
		<tiles:insertAttribute name="content" />
		<tiles:insertAttribute name="footer" />
	</div>
</body>
</html>