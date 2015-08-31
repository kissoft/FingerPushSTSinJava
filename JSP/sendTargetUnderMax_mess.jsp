<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
  /***
  * file name 				: sendTargetUnderMax_mess.jsp
  * file descriiption	: 타겟팅/500건 이하/각기 다른 메시지
  *
  *
  *      date                    author              	   description
  * --------------------------------------------------------------------------------
  * 	2015-00-00            	PandaO_OBae
  */
%>    
<%@ page import="java.util.ArrayList" %>
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
	push.setCustomerKey("발급받은 Customer key");																	// 발급 받은 customer key - Pro 이상의 서비스 사용시
	
	push.setMsg("Hello World ! - under max message");				// 발송할 푸시 메시지
	
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
	
	ArrayList<String> userList = new ArrayList();					// 푸시를 받을 대상자 목록
	ArrayList<String> messList = new ArrayList();					// 푸시에 담을 메시지 목록
		
	
	// 배열에 해당 메시지를 수신받을 식별자 및 메시지 셋팅 : 500건 이하 
	// 테스트 용입니다. 실제 서비스에서는 DB 등에서 읽어 들이거나 입력 폼을 통해 해당 값들을 받아 처리 가능 합니다.
	// 식별자와, 메시지의 수는 일치해야 합니다. 그렇지 않을 경우 받는사람이 다른 메시지를 받을 가능성이 있습니다.
	for(int i = 0; i < 499; i++){
		userList.add("memberId_"+i);
		messList.add("안녕하세요. "+i+" 번째 고객님. 해당메시지는 모두에게 다르게 발송 됩니다.");
	} // end for	
	

	
	try{
		out.println(pushDao.sendUnderMax(push, userList, messList));		// 타겟/500건이하/다른 메시지
	}catch(Exception e){
		out.println(e.getMessage());
	}
		
%>