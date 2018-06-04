<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
/***
 * File Name			: cnclPush.jsp
 * File Description	: 예약한 푸시 메시지를 취소 합니다.
 *
 *
 * 		Date					Author						Description
 * --------------------------------------------------------------
 * 2016-09-20		PandaO_OBae				최초 작성
 */
%>  
<%@ page import="com.fingerpush.push.PushVO" %>
<%@ page import="com.fingerpush.push.FingerpushDao" %>
<%@ page import="com.fingerpush.push.FingerpushDaoImpl" %>
<%
	PushVO push = new PushVO();
	FingerpushDao pushDao = new FingerpushDaoImpl();
	
	// 필수값 셋팅	
	push.setCallUrl("https://api-v2.fingerpush.com/rest/sts/v3/cnclPush.jsp");		// 푸시 메시지 취소 호출 경로
	push.setAppKey("");																				// 발급받은 Appkey
	push.setAppSecret("");																			// 발급받은 AppSecret
	push.setCustomerKey("");																	// 발급 받은 customer key - Pro 이상의 서비스 사용시
	
	push.setMode("DEFT");																		// DEFT : 일괄 푸시, STOS : 타겟팅 푸시
	push.setMsgIdx("");																				//  Server to Server 발송 후 받은  message idx 값
	
			
	try{
		out.println(pushDao.cnclPushMess(push));
	}catch(Exception e){
		out.println(e.getMessage());
	}
		
%>
