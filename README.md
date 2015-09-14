# FingerPushSTSinJava
Fingerpush Server to Server API 이용하기 Java base...

Server to Server API 이용법에 대해 잘 모르시는 분들이 많아, 간단히 이용샘플을 만들어 봅니다.

서버간 통신을 위해 HttpClient 객체를 이용하여 API에서 필요한 파라미터를 던지고, 그에 대한 json 결과를 받아 화면에 뿌려주는 처리 입니다.

물론, 사용자 화면의 형태에 따라 해당 json을 parsing 하여 예쁘게 보여주면 되겠죠.

Fingerpush Server to Server API 로는 크게 다음과 같은 처리를 할 수 있습니다.

1. 일괄 발송
    - 해당 앱을 설치한 모든 디바이스에 일괄 발송
    - 태그 발송 (그룹핑 발송)
    - 예약 발송 (발송시간을 미리 설정 할 수 있는)
2. 타겟팅 단일건 발송
    - 단일 디바이스에 특정 메시지 발송
3. 타겟팅 다중건 발송
    - 다수의 디바이스에 각기 메시지 발송

물론 타겟팅 발송에서도 메시지 예약 발송 등이 가능합니다.

해당 소스 및 파라미터 등에 대해 좀더 자세히 알고 싶다면, http://www.fingerpush.com 에서 ServertoServer manual을 다운받아 보시면 이해가 되실 겁니다.

해당 소스들은 말그대로, 통신 샘플용 소스이므로 실제 개발환경 및 시스템 환경에서의 퍼포먼스를 고려하지는 않았습니다.

단지, 각 경우에 따른 처리 방법 들에 참고로 이용하시길 바랍니다.

샘플 소스에 대해 간략하게 나마 설명하면,

앞서 말씀 드린 바와 같이 해당 통신방법은 HttpClient 를 이용하여 파라미터들을 전송하고 결과를 읽어오게 됩니다.

HttpClient 를 이용한 SSL 통신 방법은 구글링을 통해 쉽게 확인할 수 있으니, 넘어가기로 합니다.

단, 해당 방식이 적용된 SSL 통신 처리 메소드는 샘플 소스의 

FingerpushDaoImple.sendHttpsExe(String callUrl, List <BasicNameValuePair> params)
			throws NoSuchAlgorithmException, KeyManagementException, ClientProtocolException, IOException  
			
에 구현되어 있으며

해당 방식은 X509TrustManager를 이용해 SSLContext를 생성하고, ClientConnectionManager와 SchemeRegistry에 SSLSocketFactory를 등록하여 처리하는 방식입니다.

이 sendHttpsExe 메소드를 통해 파라미터를 전달하고, 결과를 읽어 json 형태의 String 값으로 반환 받을 수 있습니다.

해당 메소드는  해당 타겟 url (API 호출 URL) 과 API호출을 위해 넘겨질 값들이 셋팅되는 List 를 파라미터로 받으며, 사용하는 목적에 따라 

샘플 소스에서와 같이 API Server 로 전달되는 파라미터들을 셋팅하여 해당 메소드를 호출하게 됩니다.

1. 일괄 발송.  (참조 파일 : sendAllDevice.jsp, 참조 메소드 : FingerpushDaoImpl.sendAllDevice(PushVO push))
일괄 발송의 경우 필수 파라미터 몇개를 셋팅하여 해당 URL을 호출하면 발송이 가능 합니다.
발송 후 전달 받은 결과값(json)을 파싱하여 UI에 결과 처리를 하시면 됩니다.

	처리 방식은,
	1.1 객체 선언
	
		PushVO push = new PushVO();
		FingerpushDao pushDao = new FingerpushDaoImpl();
		
	1.2 필수 기본값 셋팅	
	해당 값들은 Fingerpush 서비스에 Pro 계정 이상으로 가입한 경우 발급된 값들을 셋팅 합니다.
		
		push.setCallUrl("https://www.fingerpush.com/rest/sts/v1/setFingerPush.jsp");		// 일괄발송 호출 경로
		push.setAppKey("RY4R______________________KS");						// 발급받은 Appkey
		push.setAppSecret("MF______________________auv5P");					// 발급받은 AppSecret
		push.setCustomerKey("y_________pSS");							// 발급 받은 customer key - Pro 이상의 서비스 사용시

	1.3 수신 받을 메시지 등록 : 메시지가 길거나, HTML 태그를 포함한 경우 부가정보 파라미터의 mode 값을 'LNGT' 로 셋팅하고 msg에는 해당 긴~ 내용의 요약 정보만 등록합니다. 또한, 실제 내용자체는 부가정보의 lngt_message 에 셋팅하여 전송 합니다.
				    
		push.setMsg("Hello World !");								// 발송할 푸시 메시지
		/* long text message 일 경우
		push.setMsg("긴내용을 표현할 짧은 제목");
		push.setMode("LNGT");
		push.Lngt_message("아주아주 긴~ 메시지 ... 중략 ... 태그도 넣어 발송 가능합니다.");
		*/

	1.4 부가 정보 셋팅 : 앱에서 다양한 효과처리를 위한 파라미터 들을 셋팅합니다. 해당 부가정보를 넣지 않을 경우 기본값으로 셋팅되어 전달되며, 해당 부가 정보처리는 앱에서도 해당 메시지 수신시 처리가 되어 있어야 합니다. 각각의 파라미터의 역할은 매뉴얼을 참조하시기 바랍니다.

		push.setIsa("Y");						// Android 디바이스에 발송 Y/N : default - Y
		push.setIsi("Y");						// IOS 디바이스에 발송 Y/N : default - Y	
		push.setAbdg("");						// Android : 푸시 배지 처리용 컬럼
		push.setAsnd("");						// Android : 푸시 사운드 처리용 컬럼	
		push.setIbdg("");						// IOS : 푸시 배지 처리용 컬럼
		push.setIsnd("");						// IOS : 푸시 사운드 처리용 컬럼	
		push.setCk1("");						// custom key 1
		push.setCk2("");						// custom key 2
		push.setCk3("");						// custom key 3
		push.setCv1("");						// custom value 1
		push.setCv2("");						// custom value 2
		push.setCv3("");						// custom value 3
		push.setFnm("");						// 이미지 파일 경로 : ex) http://도메인/이미지 파일 경로
		push.setSend_state("0002");					// 0001 : 즉시발송, 0002 : 예약발송
		push.setSenddate("201508301330");				// yyyymmdd24Hmin, send_state 가 0002(예약발송) 인 경우에만 해당 값 셋팅

	1.5 발송 메소드 호출
		pushDao.sendAllDevice(push);

	- 처리가 완료되면 결과 값으로 json 형태의 값을 받아 옵니다. (결과 코드는 매뉴얼을 확인해 주세요.)
			ex) {“result” : “200”, “message” : “정상 처리되었습니다.”,  “tokenCnt” : “15”}

2. 타겟팅 단일건 발송.  (참조 파일 : sendTargetOne.jsp, 참조 메소드 : FingerpushDaoImpl.sendTargetPush(PushVO push)) 타겟팅 발송이 일괄 발송과 다른점은 호출되는 API URL 과 해당 메시지를 수신할 대상 식별자 정보를 파라미터로 전송한다는 점입니다.

	2.1, 2.2 객체 선언 및 필수 기본값 셋팅은 일괄 발송과 처리 방식이 동일 합니다.

	2.3. 수신 받을 식별자와 해당 수신자가 받을 메시지를 셋팅 합니다.
	
		push.setIdentity("대상식별자");					// 푸시 메시지 수신 대상
		push.setMsg("배*진 고객님, 회원 가입에 감사드립니다.");		// 발송할 푸시 메시지

	2.4. 역시 부가정보에 대한 셋팅이 필요하다면, 부가정보를 셋팅합니다. 단 타겟메시지의 경우 long text push 를 지원하지 않습니다.
	
	2.5 발송 메소드 호출
	
		pushDao.sendTargetPush(push);
		
- 처리가 완료되면 결과 값으로 json 형태의 값을 받아 옵니다. (결과 코드는 매뉴얼을 확인해 주세요.)
 
	ex) {“result” : “200”, “msgIdx” :  “A1DS33DDSQ2321”, “processCode” : “20003”, “message” : “메시지 등록이 완료 되었습니다.”}

	단일건 발송에서 유의할 점은, 다수의 대상자들에게 메시지 를 발송하는 용도로 사용하여 API서버에 부하를 주는 경우 해당 계정이 차단 당할 수 있습니다.
	이러한 다수 대상자들에게 발송하는 경우에는 3. 타켓팅 다중건 발송 기능을 이용해 주시기 바랍니다.

3. 타겟팅 다수건 발송. (참조 파일 : sendTargetMore.jsp, 참조 메소드 : FingerpushDaoImpl.sendTargetMore(PushVO push, userList, messList))
	다수의 대상자들에게 메시지를 보내는 방법은 앞서 설명한 일괄 메시지/단일 메시지와는 약간 다릅니다.

	샘플 JSP 파일에서는 크게 차이가 나지 않지만, 실제 발송 과정에서는 조금 다른 프로세스를 타게 됩니다.

	먼저 jsp 파일에 대해 설명하자면.

	2.1, 2.2 객체 선언 및 필수 기본값 셋팅은 일괄 발송과 처리 방식이 동일 합니다.

	2.3. 부가정보에 대한 셋팅이 필요하다면, 부가정보를 셋팅합니다. 단 타겟메시지의 경우 long text push 를 지원하지 않습니다.

	2.4. 수신 받을 식별자와 해당 수신자가 받을 메시지를 셋팅 합니다. - 다수이므로, 수신받을 대상자와 메시지를 배열에 담아 둡니다.
		ArrayList<String> userList = new ArrayList();	// 푸시를 받을 대상자 목록
		ArrayList<String> messList = new ArrayList();	// 푸시에 담을 메시지 목록

		userList.add("10001231");
		messList.add("안녕하세요. 배*진 고객님. 해당메시지는 모두에게 다르게 발송 됩니다.");
		userList.add("10001232");
		messList.add("안녕하세요. 이*정 고객님. 해당메시지는 모두에게 다르게 발송 됩니다.");
		userList.add("10001233");
		messList.add("안녕하세요. 김*수 고객님. 해당메시지는 모두에게 다르게 발송 됩니다.");
		userList.add("10001234");
		messList.add("안녕하세요. 김*랑 고객님. 해당메시지는 모두에게 다르게 발송 됩니다.");

		... 중략 ...

		userList.add("10231349");
		messList.add("안녕하세요. 전*현 고객님. 해당메시지는 모두에게 다르게 발송 됩니다.");


해당 셋팅은 HTML 의 Form tag를 통해 받을 수도 있고 DB나 File 등을 조회하여 처리하시게 되므로, 그에 맞게 변형하여 사용하시면 됩니다.

	2.5. 발송 메소드 호출
	
		pushDao.sendTargetMore(push, userList, messList);
- 처리가 완료되면 결과 값으로 json 형태의 값을 받아 옵니다. (결과 코드는 매뉴얼을 확인해 주세요.)
	ex) {“result” : “200”, “msgIdx” :  “A1DS33DDSQ2321”, “processCode” : “20003”, “message” : “메시지 등록이 완료 되었습니다.”}

	위에서 설명한 JSP 샘플만을 보자면, 다수건 발송의 경우에도 단일건 발송과 그다지 크게 다르지 않습니다. 

	하지만 실제 발송할 건수가 수백, 수천, 수만건이 될 경우에는 서버 자원 문제등으로 JSP 에 배열을 담아 샘플처럼 처리하는 방식은 적절하지 않습니다.

	또한, 많은 데이터를 잘게 쪼게서 API서버에 무턱대로 던질경우 과도한 트래픽을 유도하게 되어, 해당 계정이 정지 당할 수도 있습니다.

	하여, 이러한 경우에는 샘플 소스를 확인하여 아래와 같이 처리해 주셔야 합니다.	

	
	JSP에서는 해당 기능을 하나의 메소드로 표현하였으나, 해당 메소드를 뜯어 보면, 크게 3가지 절차로 나누어 볼 수 있는데, 아래와 같습니다.

	첫째, 기본 메시지 정보를 API서버로 전달하고 처리 결과를 수신받습니다.
		JSP 발송샘플에서 셋팅한 기본 메시지 정보 (AppKey/AppSecret/customer key/대표메시지)를 파라미터로 답아 API server 로 전송합니다.

		ex)
		List <BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();		// 파라미터를 셋팅하고,
		params = setParams(push, params);						// 푸시 메시지 기본 정보 셋팅 - 필수값 파라미터들이 셋팅 됩니다(메소드 참고)
		params.add (new BasicNameValuePair("msg", "대표메시지 셋팅"));			// 대표 메시지를 셋팅합니다.

		// 첫번째 메세지 기본정보를 보내면 메시지가 등록되고, 결과 값을 받아 오게 됩니다.
		jsonString = sendMessage("API URL 주소", params);

	결과값 > 
		ex) {“result” : “200”, “msgIdx” :  “A1DS33DDSQ2321”, “processCode” : “20001”, “message” : “메시지 등록이 처리되었습니다. 발급받은 메시지 아이디로 대상자 등록을 시작해 주세요.”}
		
	일단 메시지가 등록되면 API 서버에서는 해당 메시지를 Transaction 상태로 만들고 대상자 및 메시지 입력을 기다립니다.

	둘째, 처리 결과를 확인하여 결과 값이 정상이면 처리 결과에 포함된 msgIdx 와 수신받을 대상자 및 해당 대상자들이  수신받을 메시지를 셋팅하여 API서버로 보냅니다
		결과값(result)이 200 이고, 프로세스 코드(processCode) 가 20001이면 수신 대상자 및 수신 메시지를 파라미터에 담아 전달 합니다.
	서버는 기본적으로 한번 전달에 500건까지의 식별자를 받아 주므로 이보다 많을 경우 일정 수만큼 나누어 API서버로 전달 합니다.

	아래는 500건 단위로 아래 방식을 한번씩 처리할 수 있도록 한 메소드 내부 입니다.
	만일 수천~수만건 단위라면 해당 대상을 500건씩 쪼개서 아래 처러 호출해 주시면 됩니다.
	단 한번 발송한 내용의 결과(jsonString)를 수신하여 결과가 200이고 processCode 가 (20002)일 경우 다음 대상자 셋을 처리하는 방법으로 하셔야 합니다.

		ex)		
		// 파라미터 설정 
		List <BasicNameValuePair> params = new ArrayList<BasicNameValuePair>(); 
		params.add (new BasicNameValuePair("appkey",push.getAppKey()));				//  (필수)	
		params.add (new BasicNameValuePair("appsecret",push.getAppSecret()));			//  (필수)
		params.add (new BasicNameValuePair("customerkey",push.getCustomerKey()));		//  (필수)
		
		params.add (new BasicNameValuePair("msgidx",push.getMsgIdx()));		 		// 발급받은 message id 를 넣어 준다.  (필수)
		
		// 대상자와 메시지를 설정 합니다.
		// 대상자 설정
		for(int i=0; i<targetList.size(); i++)
			params.add (new BasicNameValuePair("identity", (String)targetList.get(i)));
		
		if(messList != null){
			// 대상자별 메시지 설정
			for(int i=0; i<messList.size(); i++)
				params.add (new BasicNameValuePair("message", (String)messList.get(i)));      
		}
        
		jsonString = sendHttpsExe(push.getCallUrl(), params);
		
		
	결과값 >  
		ex) {“result” : “200”, “msgIdx” :  “A1DS33DDSQ2321”, “processCode” : “20002”, “message” : “대상자 등록이 처리되었습니다. 대상자를 계속 등록하실수도, 메시지 등록을 완료 하실 수도 있습니다.”}

	셋째, 처리 결과를 확인하여 결과 값이 정상이면 앞서 받은 msgIdx 와 메시지 발송 종료 플래그를 담아 API 서버로 보내면 발송 과정이 완료됩니다.
		모든 대상자셋을 서버에 전달하였다면, 해당 Transaction 을 종료한다는 플래그를 담아 전달 해야 합니다.

		ex)
		// 파라미터 설정
		List <BasicNameValuePair> params = new ArrayList<BasicNameValuePair>(); 
		params.add (new BasicNameValuePair("appkey",push.getAppKey()));						//  (필수)	
		params.add (new BasicNameValuePair("appsecret",push.getAppSecret()));					//  (필수)
		params.add (new BasicNameValuePair("customerkey",push.getCustomerKey()));				//  (필수)
		
		params.add (new BasicNameValuePair("msgidx",push.getMsgIdx()));						// 발급받은 message id 를 넣어 준다.  (필수)
		params.add (new BasicNameValuePair("isfinish", "Y"));							// 발송 완료 처리
		
		jsonString = sendHttpsExe(push.getCallUrl(), params);
		          


발송 방법이 어렵지는 않습니다만, 발송시 대상자가 많은 경우 발송 서버에서의 대상자를 나누어 발송하는 처리를 유의 하셔야 합니다. 
타겟팅 다수건 발송의 절차가 귀찮다고 많은 데이터 처리를 단일건으로 만드실 경우API서버에 과도한 트래픽을 유발할 수 있으며, 
이경우 사전 고지 없이 서비스가 중단 될 수 있으므로, 메시지 발송처리는 각 요구에 맞게 적절한 API를 사용하여 합니다.
		
