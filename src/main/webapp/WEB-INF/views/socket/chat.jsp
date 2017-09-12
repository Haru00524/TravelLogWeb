<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="<c:url value="./resources/js/sockjs.js"/>" ></script>
<script type="text/javascript">
 
    $(document).ready(function(){
        
        $("#sendBtn").click(function(){
            sendMessage();
        });
        
    
    });
    //WebSocket을 지정한 URL로 연결한다
    //서버랑 연결 -> 에코핸들러가서 출력
    var sock = new SockJS("/turn/echo-ws");
    //WebSocket 서버에서 메시지를 보내면 자동으로 실행된다
    //var onMessage = function onMessage //변수
    sock.onmessage = onMessage;
    
    //WebSocket과 연결을 끊고 싶을 때 실행하는 메소드다
    sock.onclose = onClose;
 
    /*     sock.onopen = function() {
        sock.send( $("#message").val() +"<br/>");
    }; */
    function sendMessage() {
    
        //WebSocket으로 메시지를 전달한다.
        sock.send( $("#message").val() +"<br/>");
    }
    
    // evt 파라미터는 WebSocket이 보내준 데이터다
    // 변수안에 function을 넣음. 변수 생략 가능
    function onMessage(evt) {
        var data = evt.data;
        $("#data").append(data);
        //sock.close();
    }
    // var onClose 변수 생략
    function onClose(evt) {
        $("#data").append("Connection Closed!");
    }
    
</script>
</head>
<body>
 
    <input type="text" id="message" />
    <input type="button" id="sendBtn" value="SEND" />
    <div id="data"></div>
    
</body>
</html>
 
