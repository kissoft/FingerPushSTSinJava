<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
  /***
  * file name 		: sendTargetOne.jsp
  * file descriiption	: 타겟팅 단일건 발송
  *
  *
  *      date                    author              	   description
  * --------------------------------------------------------------------------------
  * 	2016-05-25       PandaO_OBae
  */
%>
<%@ page import="com.fingerpush.push.PushVO" %>
<%@ page import="com.fingerpush.push.FingerpushDao" %>
<%@ page import="com.fingerpush.push.FingerpushDaoImpl" %>
<%
	PushVO push = new PushVO();
	FingerpushDao pushDao = new FingerpushDaoImpl();
	
	// 필수값 셋팅
	push.setCallUrl("https://api.fingerpush.com/rest/sts/v3/setSTSpush.jsp");					// 일괄발송 호출 경로
	push.setAppKey("");																					// 발급받은 Appkey
	push.setAppSecret("");																			// 발급받은 AppSecret
	push.setCustomerKey("");																	// 발급 받은 customer key - Pro 이상의 서비스 사용시
	
	push.setIdentity("");				// 푸시 메시지 수신 대상
	String a= "타겟팅, STS, 단일건 발송(V3.0)";
	push.setMsg(a);				// 발송할 푸시 메시지
	
	// 부가 정보 셋팅. : 해당 값들을 셋팅 안할 경우 기본값 처리.
	push.setAbdg("1");											// Android : 푸시 배지 처리용 컬럼
	push.setAsnd("a_snd");											// Android : 푸시 사운드 처리용 컬럼	
	push.setIbdg("1");											// IOS : 푸시 배지 처리용 컬럼
	push.setIsnd("i_snd");											// IOS : 푸시 사운드 처리용 컬럼	
	push.setCk1("custom key1");											// custom key 1
	push.setCk2("custom key2");											// custom key 2
	push.setCk3("custom key3");											// custom key 3
	push.setCv1("custome value 1");											// custom value 1
	push.setCv2("custome value 2");											// custom value 2
	push.setCv3("custome value 3");											// custom value 3
	push.setFnm("http://www.kissoft.co.kr/image.jpg");											// 이미지 파일 경로 : ex) http://도메인/이미지 파일 경로
	push.setSend_state("0001");							// 0001 : 즉시발송, 0002 : 예약발송	
	push.setSenddate("");				// yyyymmdd24Hmin, send_state 가 0002(예약발송) 인 경우에만 해당 값 셋팅
	push.setLink("http://www.fingerpush.com");

	// V2.5 추가 내용
	push.setTitle("v3.0 단일건 메시지");
	push.setBgcolor("#FFFFFF");	// 배경 컬러 RGB 값 :  ex) #FF0000
	push.setFontcolor("FF0000");	// 폰트 컬러 RGB 값 :  ex) #4374D9
	push.setOfb_time("1w");			// opened fall back time : 오픈 처리 제한시간  - 2h, 4h, 1d, 3d, 5d, 1w
	push.setIsetiquette("Y");			// 에티켓 시간 적용 여부 Y 적용, N 적용 안함.
	push.setEtiquette_stime("20");	// 에티켓 적용 시작 시간 0~23
	push.setEtiquette_etime("09");	// 에티켓 적용 해제 시간 0~23
	push.setLabel_code("hxJBeF2muh");			// 메시지 라벨코드 : 메시지 라벨관리에서 발급받은 10자리 난수
	push.setAnd_priority("H");			// 안드로이드 우선순위 H : 높음 / M : 중간(default)
	push.setOptagree("0000");		// 옵션 동의 : 0000  광고수신 비동의 여부에 관계없이 발송, 1000 광고수신동의	한사람에게만 발송
	
	try{
		out.println(pushDao.sendTargetPush(push));
	}catch(Exception e){
		out.println(e.getMessage());
	}
		
%>
