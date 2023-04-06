<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
  /***
  * file name 				: sendTargetUnderMax.jsp
  * file descriiption	    : 타겟팅 500 건이하 발송 - 서버통신을 최소화(1회)
  *                               다수 발송의 경우 3단계(기본정보발송 > 대상 식별자 발송 > 종료 플래그 발송) 에 의해 처리되지만,
  *   							   해당 메소드의 경우 500건 이하의 대상 식별자에 한해 하나의 메시지로 1단계로 처리함(기본정보, 대상식별자, 종료플래그 발송)
  *
  *      date         		           author              	   description
  * --------------------------------------------------------------------------------
  * 	2023-04-06        	PandaO_OBae
  */
%>    
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>    
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.fingerpush.push.PushVO" %>
<%@ page import="com.fingerpush.push.FingerpushDao" %>
<%@ page import="com.fingerpush.push.FingerpushDaoImpl" %>
<%
	PushVO push = new PushVO();
	FingerpushDao pushDao = new FingerpushDaoImpl("agent");
	
	// 필수값 셋팅
	push.setCallUrl("https://api-v2.fingerpush.com/rest/sts/v3/setSTSPushs.jsp");					// API 호출 경로
	push.setAppKey("발급받은 APP key");																								// 발급받은 Appkey
	push.setAppSecret("발급받은 AppSecret");																					// 발급받은 AppSecret
	push.setCustomerKey("발급 받은 customer key");														            	// 발급 받은 customer key - Pro 이상의 서비스 사용시

		
	push.setMode("STOS");
	// 부가 정보 셋팅. : 해당 값들을 셋팅 안할 경우 기본값 처리.
	//push.setIsi("Y");
	//push.setIsa("Y");
	//push.setAbdg("1");											// Android : 푸시 배지 처리용 컬럼
	//push.setAsnd("ring1");									// Android : 푸시 사운드 처리용 컬럼	
	//push.setIbdg("1");											// IOS : 푸시 배지 처리용 컬럼	
	//push.setIsnd("i_snd");									// IOS : 푸시 사운드 처리용 컬럼	
	//push.setCk1("custom key1");						// custom key 1
	//push.setCk2("custom key2");						// custom key 2
	//push.setCk3("custom key3");						// custom key 3
	//push.setCv1("custome value 1");					// custom value 1
	//push.setCv2("custome value 2");					// custom value 2
	//push.setCv3("custome value 3");					// custom value 3
	//push.setFnm("이미지 파일 경로");						// 이미지 파일 경로 : ex) http://도메인/이미지 파일 경로
	//push.setSend_state("0001");							// 0001 : 즉시발송, 0002 : 예약발송	
	//push.setSenddate("202304061100");			// yyyymmdd24Hmin, send_state 가 0002(예약발송) 인 경우에만 해당 값 셋팅	
	//push.setLink("https://웹링크 주소");
	//push.setBgcolor("#FFFFFF");							// 배경 컬러 RGB 값 :  ex) #FF0000
	//push.setFontcolor("FF0000");						// 폰트 컬러 RGB 값 :  ex) #4374D9
	//push.setOfb_time("3d");								// opened fall back time : 오픈 처리 제한시간  - 2h, 4h, 1d, 3d, 5d, 1w
	//push.setIsetiquette("Y");								// 에티켓 시간 적용 여부 Y 적용, N 적용 안함.
	//push.setEtiquette_stime("21");						// 에티켓 적용 시작 시간 0~23
	//push.setEtiquette_etime("8");						// 에티켓 적용 해제 시간 0~23
	//push.setLabel_code("메시지라벨코드");				// 메시지 라벨코드 : 메시지 라벨관리에서 발급받은 10자리 난수
	//push.setAnd_priority("H");							// 안드로이드 우선순위 H : 높음 / M : 중간(default)
	//push.setOptagree("1000");							// 옵션 동의 : 0000  광고수신 비동의 여부에 관계없이 발송, 1000 광고수신동의	한사람에게만 발송
	//push.setFnm("이미지 링크 경로");

	push.setTitle("앱푸시 제목 입니다.");
	push.setMsg("대표 메시지 내용을 셋팅 할 수 있습니다.");	
	
	ArrayList<Map<String, String>> paramList = new ArrayList<Map<String, String>>();

	// 배열에 해당 메시지를 수신받을 식별자 및 메시지 셋팅 : 
	// 값들은 parameter 로 전달 되므로 특정 사이즈 이상의 값들이 전달 될 경우 잃어 버리는 값들이 발생 합니다. 그런 이유로 
	// 수신 대상이 많을 경우 메소드에서 분할 하여 처리 됩니다.(자세한 내용은 해당 메소드)
	// 테스트 용입니다. 실제 서비스에서는 DB 등에서 읽어 들이거나 입력 폼을 통해 해당 값들을 받아 처리 가능 합니다.
	// 식별자와, 메시지의 수는 일치해야 합니다. 그렇지 않을 경우 받는사람이 다른 메시지를 받을 가능성이 있습니다.


	for(int i = 0; i < 10; i++){
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("identity", "memberId_"+i);
		paramMap.put("message", "안녕하세요. "+i+" 번째 고객님. 해당메시지는 모두에게 다르게 발송 됩니다.");					// 필수 값
		paramMap.put("imgLink", "https://www.fingerpush.com/img/admin/ico/ico_android.png");			// 이미지 링크,  개별 이미지 발송 가능, 해당 값이 없을 경우 위에서 선언한 기본 메시지 정보의 이미지 링크 사용
		paramMap.put("link", "http://www.kissoft.co.kr/mem/"+i);																		// 웹링크, 개별 웹링크 발송 가능, 해당 값이 없을 경우 위에서 선언한 기본 메시지 정보의 웹링크 사용
		paramMap.put("title", "title_"+i);																													// 제목, 개별 메시지 제목 발송 가능, 해당 값이 없을 경우 위에서 선언한 기본 메시지 정보의 제목 사용
		
		paramList.add(paramMap);
	} // end for	

	try{
		out.println(pushDao.sendTargetUnderMax(push, paramList));		
	}catch(Exception e){
		out.println(e.getMessage());
	}
		
%>
