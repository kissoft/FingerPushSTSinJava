<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
  /***
  * file name 				: sendTargetOne.jsp
  * file descriiption	: 타겟팅 단일건 발송
  *
  *
  *      date                    author              	   description
  * --------------------------------------------------------------------------------
  * 	2015-08-19       PandaO_OBae
  */
%>
<%@ page import="com.fingerpush.push.PushVO" %>
<%@ page import="com.fingerpush.push.FingerpushDao" %>
<%@ page import="com.fingerpush.push.FingerpushDaoImpl" %>
<%
	PushVO push = new PushVO();
	FingerpushDao pushDao = new FingerpushDaoImpl();
	
	// 필수값 셋팅
	push.setCallUrl("https://www.fingerpush.com/rest/sts/v1/setFingerPush.jsp");					// 일괄발송 호출 경로
	push.setAppKey("발급받은  App key");																					// 발급받은 Appkey
	push.setAppSecret("발급받은 AppSecret");																			// 발급받은 AppSecret
	push.setCustomerKey("발급받은 Customer key");																	// 발급 받은 customer key - Pro 이상의 서비스 사용시급 받은 customer key
	
	push.setIdentity("sjbae99");				// 푸시 메시지 수신 대상
	push.setMsg("Hello World ! - send one");				// 발송할 푸시 메시지
	
	// 부가 정보 셋팅. : 해당 값들을 셋팅 안할 경우 기본값 처리.
	/*
	push.setAbdg("");											// Android : 푸시 배지 처리용 컬럼
	push.setAsnd("");											// Android : 푸시 사운드 처리용 컬럼	
	push.setIbdg("");											// IOS : 푸시 배지 처리용 컬럼
	push.setIsnd("");											// IOS : 푸시 사운드 처리용 컬럼	
	push.setCk1("");											// custom key 1
	push.setCk2("");											// custom key 2
	push.setCk3("");											// custom key 3
	push.setCv1("");											// custom value 1
	push.setCv2("");											// custom value 2
	push.setCv3("");											// custom value 3
	push.setFnm("");											// 이미지 파일 경로 : ex) http://도메인/이미지 파일 경로
	push.setSend_state("0002");							// 0001 : 즉시발송, 0002 : 예약발송
	push.setSenddate("201508301330");				// yyyymmdd24Hmin, send_state 가 0002(예약발송) 인 경우에만 해당 값 셋팅
	*/
	
	try{
		out.println(pushDao.sendTargetPush(push));
	}catch(Exception e){
		out.println(e.getMessage());
	}
		
%>