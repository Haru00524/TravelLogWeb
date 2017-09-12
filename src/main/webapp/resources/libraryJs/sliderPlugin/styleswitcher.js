/** ********************************************** **
	@Author			Dorin Grigoras
	@Website		www.stepofweb.com
	@Last Update	Monday, July 21, 2014
 ** ********************************************* **/
 
 
 
/*!
 * jQuery Cookie Plugin v1.3.1
 * https://github.com/carhartl/jquery-cookie
 *
 * Copyright 2013 Klaus Hartl
 * Released under the MIT license
 */
(function(a){if(typeof define==="function"&&define.amd&&define.amd.jQuery){define(["jquery"],a)}else{a(jQuery)}}(function(e){var a=/\+/g;function d(g){return g}function b(g){return decodeURIComponent(g.replace(a," "))}function f(g){if(g.indexOf('"')===0){g=g.slice(1,-1).replace(/\\"/g,'"').replace(/\\\\/g,"\\")}try{return c.json?JSON.parse(g):g}catch(h){}}var c=e.cookie=function(p,o,u){if(o!==undefined){u=e.extend({},c.defaults,u);if(typeof u.expires==="number"){var q=u.expires,s=u.expires=new Date();s.setDate(s.getDate()+q)}o=c.json?JSON.stringify(o):String(o);return(document.cookie=[encodeURIComponent(p),"=",c.raw?o:encodeURIComponent(o),u.expires?"; expires="+u.expires.toUTCString():"",u.path?"; path="+u.path:"",u.domain?"; domain="+u.domain:"",u.secure?"; secure":""].join(""))}var g=c.raw?d:b;var r=document.cookie.split("; ");var v=p?undefined:{};for(var n=0,k=r.length;n<k;n++){var m=r[n].split("=");var h=g(m.shift());var j=g(m.join("="));if(p&&p===h){v=f(j);break}if(!p){v[h]=f(j)}}return v};c.defaults={};e.removeCookie=function(h,g){if(e.cookie(h)!==undefined){e.cookie(h,"",e.extend(g,{expires:-1}));return true}return false}}));



/**	STYLE SWITCHER
*************************************************** **/
jQuery(document).ready(function() {
	"use strict";

		var _sw = '<!-- STYLESWITCHER - REMOVE ON PRODUCTION/DEVELOPMENT -->'
				+ '<div id="switcher" class="hide hidden-xs">'
				+ '	<div class="content-switcher">'
				+ '		<h3>Write Quick Menu</h3>'
				+ '		<div class="text-center">'
				+ '			<a href="logWrite" class="btn btn-3d btn-amber btn-lg text-center margin-bottom-20">라이프로그 추가 +</a>'		
				+ '			<a href="userScheduleList" class="btn btn-3d btn-blue btn-lg text-center margin-bottom-20">진행중일정 보기 ~</a>'
				+ '			<hr>'			
				+ '		</div>'
				+ '		<div id="hideSwitcher">&times;</div>'
				+ '	</div>'
				+ '</div>'

				+ '<div id="showSwitcher" class="styleSecondColor hide hidden-xs"><i class="glyphicon glyphicon-pencil t-glytop-3"></i></div>'
				+ '<!-- /STYLESWITCHER -->';

	// ADD CLASS
	jQuery("head").append('<link href="./resources/libraryCss/styleswitcher/styleswitcher.css" rel="stylesheet" type="text/css" />');
	jQuery("body").append(_sw);
	jQuery("#switcher, #showSwitcher").removeClass('hide');

    jQuery("#hideSwitcher, #showSwitcher").click(function () {

        if (jQuery("#showSwitcher").is(":visible")) {

			var _identifier = "#showSwitcher";
            jQuery("#switcher").animate({"margin-left": "0px"}, 500).show();
			createCookie("switcher_visible", 'true', 365);

        } else {

			var _identifier = "#switcher";
            jQuery("#showSwitcher").show().animate({"margin-left": "0"}, 500);
			createCookie("switcher_visible", 'false', 365);

        }

		jQuery(_identifier).animate({"margin-left": "-500px"}, 500, function () {
			jQuery(this).hide();
		});

    });




});



	/** ********************************************************************************************************** **/
	/** ********************************************************************************************************** **/
	/** ********************************************************************************************************** **/


	function createCookie(name,value,days) {
		/** 
		if (days) {
			var date = new Date();
			date.setTime(date.getTime()+(days*24*60*60*1000));
			var expires = "; expires="+date.toGMTString();
		} else {
			expires = "";
		}	document.cookie = name+"="+value+expires+"; path=/";
		**/
	}

	function readCookie(name) {
		/** 
		var nameEQ = name + "=";
		var ca = document.cookie.split(';');
		for(var i=0;i < ca.length;i++) {
			var c = ca[i];

			while (c.charAt(0)==' ') {
				c = c.substring(1,c.length);
			}

			if (c.indexOf(nameEQ) == 0) {
				return c.substring(nameEQ.length,c.length);
			}
		}
		**/

		return null;
	}

	jQuery("select#headerLayout").click(function() {

		var type = jQuery(this).attr('value');

		if (jQuery("#css_navigation_style").length > 0){
			jQuery("#css_navigation_style").remove();
		}

		jQuery("head").append("<link>");
		jQuery("head").children(":last").attr({
			rel:  	"stylesheet",
			type: 	"text/css",
			id: 	"css_navigation_style",
			href: 	"css/navigation-style-" + type + ".css"
		});

	});
	





