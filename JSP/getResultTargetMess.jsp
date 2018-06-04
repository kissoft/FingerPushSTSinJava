<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
  /***
  * file name 				: getResultTargetMess.jsp
  * file descriiption	: Server to Server 타겟 메시지 발송 후 identity 에 대한 유효성 확인
  * 								- 식별자가 존재하지 않거나 발송되지 않은 식별자에 대해서만 결과 표시함
  *
  *
  *      date         		           author              	   description
  * --------------------------------------------------------------------------------
  * 	2015-05-25            	PandaO_OBae
  */
%>    
<%@ page import="com.fingerpush.push.PushVO" %>
<%@ page import="com.fingerpush.push.FingerpushDao" %>
<%@ page import="com.fingerpush.push.FingerpushDaoImpl" %>
<%
	PushVO push = new PushVO();
	FingerpushDao pushDao = new FingerpushDaoImpl();
	
	// 필수값 셋팅
	push.setCallUrl("https://api-v2.fingerpush.com/rest/sts/v3/getResultTgMess.jsp");					// API 호출 경로	: 개발서버
	push.setAppKey("");																					// 발급받은 Appkey
	push.setAppSecret("");																			// 발급받은 AppSecret
	push.setCustomerKey("");																	// 발급 받은 customer key - Pro 이상의 서비스 사용시
	
	
	push.setMsgIdx("JH9KJYB7PN525538");																						//  Server to Server 발송 후 받은  message idx 값	
	int pageNo = 1;																																// 조회할 페이지 수
	
	try{		
		out.println(pushDao.getRtTargetMess(push, pageNo));
	}catch(Exception e){
		out.println(e.getMessage());
	}
		
%>
