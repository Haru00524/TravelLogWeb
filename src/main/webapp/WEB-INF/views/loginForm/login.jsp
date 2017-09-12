<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script src="<c:url value="./resources/js/login/loginConfirm.js"/>" ></script>

<!-- 
   PAGE HEADER 
   
   CLASSES:
      .page-header-xs   = 20px margins
      .page-header-md   = 50px margins
      .page-header-lg   = 80px margins
      .page-header-xlg= 130px margins
      .dark         = dark page header
   
      .shadow-before-1    = shadow 1 header top
      .shadow-after-1    = shadow 1 header bottom
      .shadow-before-2    = shadow 2 header top
      .shadow-after-2    = shadow 2 header bottom
      .shadow-before-3    = shadow 3 header top
      .shadow-after-3    = shadow 3 header bottom
-->

<section class="page-header page-header-xs shadow-before-1">
   <div class="container">

      <h1>로그인</h1>

      <!-- breadcrumbs -->
      <ol class="breadcrumb">
         <li><a href="main">메인</a></li>
         <li class="active">login</li>
      </ol><!-- /breadcrumbs -->

   </div>
</section>

<section class="alternate">
   <div class="container">
      <div class="row margin-top-80 margin-bottom-80">
         <div class="col-md-6 col-md-offset-3">
            <form id="test_submit" class="sky-form boxed" onsubmit="return false;">
               <header class="size-18 margin-bottom-20" style="background:rgba(199, 199, 199, 0.1) !important">
                  <i class="fa fa-key"></i>&nbsp;&nbsp;Login
               </header>
               <fieldset class="nomargin">
                     
                  <label class="input margin-bottom-20">
                     <i class="ico-append fa fa-user"></i>
                     <input id="user_id" name="user_id" type="text" size="20"
                        maxlength="40" placeholder="아이디 입력" class="form-control" autocomplete="off">
                  </label>
                  
                  <label class="input margin-bottom-20">
                     <i class="ico-append fa fa-lock"></i>
                     <input id="user_pass" name="user_pass" type="password" size="20"
                        maxlength="20" placeholder="패스워드 입력" class="form-control" autocomplete="off">
                  </label>
                  
                  <div class="text-center margin-bottom-10">
                  	 <input type="hidden" name="board" value="${board}">
                     <input type="submit" id="loginTest" value="로그인" class="btn btn-info">
                     <button class="btn btn-primary reg-go">회원가입</button>
                  </div>
                  <div id="info_confirm" class="text-center">&nbsp;</div>
               </fieldset>
            </form>
         </div>
      </div>
   </div>
</section>