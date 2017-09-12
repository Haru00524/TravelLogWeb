<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script src="<c:url value="./resources/js/register/regConfirm.js"/>" ></script>

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

      <h1>회원가입</h1>

      <!-- breadcrumbs -->
      <ol class="breadcrumb">
         <li><a href="main">메인</a></li>
         <li class="active">register</li>
      </ol><!-- /breadcrumbs -->

   </div>
</section>

<section class="alternate">
   <div class="container">
      <div class="row">
         <div class="col-md-6 col-md-offset-3">
            <form method="post" id="register_submit" class="nomargin sky-form boxed" onsubmit="return false">
               <header class="size-18 margin-bottom-20" style="background:rgba(199, 199, 199, 0.1) !important;">
                  <i class="fa fa-user"></i>&nbsp;&nbsp;Register
               </header>
               <fieldset class="nomargin">
                  <label class="input margin-bottom-10">
                     <i class="ico-append fa fa-user"></i>
                     <input id="user_id" name="user_id" type="text" size="20"
                     maxlength="40" placeholder="아이디 입력">
                  </label>
                  <div id="idconfirm" class="margin-bottom-10">&nbsp;</div>
                  
                  <label class="input margin-bottom-10">
                     <i class="ico-append fa fa-lock"></i>
                     <input id="user_pass" name="user_pass" type="password" size="20"
                     maxlength="20" placeholder="패스워드 입력">
                  </label>
                  <div id="passcheck" class="margin-bottom-10">&nbsp;</div>
                  
                  <label class="input margin-bottom-10">
                     <i class="ico-append fa fa-lock"></i>
                     <input id="confirm" name="confirm" type="password" size="20"
                     maxlength="20" placeholder="패스워드 확인">
                  </label>
                  <div id="passconfirm" class="margin-bottom-10">&nbsp;</div>
                  
                  <div class="row margin-bottom-10">
                     <div class="col-md-5">
                        <label class="input">
                           <input id="yyyy" name="yyyy" type="text" size="3"
                           maxlength="4" placeholder="연도 ( 4자리 )">
                        </label>
                     </div>
                     <div class="col-md-3">
                        <label class="margin-bottom-6 margin-top-0 fancy-form fancy-form-select">
                           <select id="mm" name="mm" class="form-control">
                              <option value="0">월</option>
                              <option value="1">1</option>
                              <option value="2">2</option>
                              <option value="3">3</option>
                              <option value="4">4</option>
                              <option value="5">5</option>
                              <option value="6">6</option>
                              <option value="7">7</option>
                              <option value="8">8</option>
                              <option value="9">9</option>
                              <option value="10">10</option>
                              <option value="11">11</option>
                              <option value="12">12</option>
                           </select>
                           <i class="fancy-arrow"></i>
                        </label>
                     </div>
                     <div class="col-md-4">
                        <label class="input">
                           <input id="dd" name="dd" type="text" size="2"
                           maxlength="2" placeholder="일">
                        </label>
                     </div>
                  </div>
                  <div id="birth_check" class="margin-bottom-10">&nbsp;</div>
                  
                  <div class="btn-group margin-bottom-10 gender">
                     <button class="btn btn-default male">남</button>
                     <button class="btn btn-default female">여</button>
                  </div>
                  <div class="margin-bottom-10">&nbsp;</div>
                  
                  <label class="input margin-bottom-10">
                     <i class="ico-append fa fa-envelope"></i>
                     <input id="user_phone" name="user_phone" type="text" size="11"
                         maxlength="11" placeholder="휴대전화번호">   
                  </label>
                   <div id="phone_check" class="margin-bottom-10">&nbsp;</div>   
                   
                   <label class="input margin-bottom-10">
                       <i class="ico-append fa fa-phone"></i>
                       <input id="user_email" name="user_email" type="text" size="20" 
                         maxlength="40" placeholder="이메일주소">
                   </label>
                   <div id="email_check" class="margin-bottom-10">&nbsp;</div>
                   <div style="text-align:center">
                      <input type="submit" id="registerTest"  class="btn btn-primary" value="회원가입">
                      <input type="button" id="cancel"  class="btn btn-warning" value="가입취소">
                   </div>
               </fieldset>
            </form>
         </div>
      </div>
   </div>
</section>