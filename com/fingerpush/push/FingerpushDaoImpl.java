package com.fingerpush.push;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import net.sf.json.JSONObject;
import net.sf.json.JSONException; 
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.net.MalformedURLException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import javax.net.ssl.HostnameVerifier;
import java.net.HttpURLConnection;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;

public class FingerpushDaoImpl implements FingerpushDao{
	Logger logger;
	String source = "";
	
	public FingerpushDaoImpl(){
		super();
		logger = Logger.getLogger("FingerpushDaoImpl");		
	}
	
	public FingerpushDaoImpl(String source){
		super();
		logger = Logger.getLogger("FingerpushDaoImpl");
		this.source  = source;
	}	
	
	@Override
	public String sendAllDevice(PushVO push)
			throws NoSuchAlgorithmException, KeyManagementException, ClientProtocolException, IOException {
		String jsonString  = "";			// 서버로 발송 후 결과 메시지
		
        List <BasicNameValuePair> params = new ArrayList<BasicNameValuePair>(); 
        params = setParams(push, params);															// 푸시 메시지 기본 정보 셋팅
        if(!source.equals("")) params.add (new BasicNameValuePair("source", source));	
        params.add (new BasicNameValuePair("msg", push.getMsg()));				// 메시지 셋팅 (필수)   
        params.add (new BasicNameValuePair("mode", push.getMode()));			// 메시지 모드 DEFT 기본 /LNGT
        if(push.getMode().equals("LNGT")) params.add (new BasicNameValuePair("lngt_message", push.getLngt_message()));        
        params.add(new BasicNameValuePair("tag", push.getTag()));					// 태그 검색시 태그 : ' , ' 로 구분
        params.add(new BasicNameValuePair("beschmode", push.getBeschmode()));	// 태그 검색시 태그 : ' , ' 로 구분
        
        jsonString = sendHttpsUrlConExe(push.getCallUrl(), params);        
        
		return jsonString;
	}



	@Override
	public String sendTargetPush(PushVO push) 
			throws NoSuchAlgorithmException, KeyManagementException, ClientProtocolException, IOException {
		String jsonString  = "";
		
        // 파라미터 설정
        List <BasicNameValuePair> params = new ArrayList<BasicNameValuePair>(); 
        params = setParams(push, params);															// 푸시 메시지 기본 정보 셋팅
        if(!source.equals("")) params.add (new BasicNameValuePair("source", source));
        params.add (new BasicNameValuePair("msg", push.getMsg()));				// 메시지 셋팅 (필수)   
        params.add (new BasicNameValuePair("identity", push.getIdentity()));      // 대상자 셋팅  (필수)
        
        // SMS 서비스 사용자의 경우에만 해당 됨
        if(push.getBesms().equals("Y")){
	        params.add (new BasicNameValuePair("besms", "Y"));      // 대상자의 발송 실패시 SMS를 수신받을 전화번호
	        params.add (new BasicNameValuePair("hp", push.getHp()));      // 대상자의 발송 실패시 SMS를 수신받을 전화번호
        }
        
        jsonString = sendMessage(push.getCallUrl(), params);								// 발송 후 결과 수신        
        
		return jsonString;
	}	
	
	
	@Override
	public String sendTargetUnderMax(PushVO push, ArrayList<Map<String, String>> paramList)
			throws NoSuchAlgorithmException, KeyManagementException, ClientProtocolException, IOException {
			
		String code = "200";
		String message = "";
		String result = "";
		String msgIdx = "";
		String processCode = "";
		String jsonString = "";
		
		ArrayList<String> userList = new ArrayList<String>();
		ArrayList<String> messList = new ArrayList<String>();
		ArrayList<String> fileList = new ArrayList<String>();
		ArrayList<String> linkList = new ArrayList<String>();
		ArrayList<String> titleList = new ArrayList<String>();
		
		for(int i=0; i<paramList.size(); i++){
			 Map<String, String> tmpMap = paramList.get(i);
			 userList.add((String)tmpMap.get("identity"));
			 messList.add((String)tmpMap.get("message"));
			 fileList.add((String)tmpMap.get("imgLink"));
			 linkList.add((String)tmpMap.get("link"));
			 titleList.add((String)tmpMap.get("title"));
		}
		if(userList.size() > 500){
			code = "5000";
			message = "해당 메소드는 대상자가 500건을 넘는 경우 이용할 수 없습니다..";			
		}
		
		if(messList.size() > 0 && userList.size() != messList.size()){
			code = "5000";
			message = "대상자 수와 메시지 수가 일치하지 않습니다.";
		} 
		if(fileList.size() > 0 && userList.size() != fileList.size()){
			code = "5000";
			message = "대상자 수와 첨부이미지 수가 일치하지 않습니다.";
		} 
		if(linkList.size() > 0 && userList.size() != linkList.size()){
			code = "5000";
			message = "대상자 수와 웹링크 수가 일치하지 않습니다.";
		} 		
		if(titleList.size() > 0 && userList.size() != titleList.size()){
			code = "5000";
			message = "대상자 수와 타이틀 수가 일치하지 않습니다.";
		} 					
		
		if(!code.equals("200")){
			JSONObject obj = new JSONObject();
			obj.put("code", code);
			obj.put("message", message);
			return obj.toString();
		}else{
	        // 파라미터 설정
	        List <BasicNameValuePair> params = new ArrayList<BasicNameValuePair>(); 
	        params = setParams(push, params);		        // 푸시 메시지 기본 정보 셋팅
	        
	        push.setMsg(messList.get(0));																// 첫번째 메시지가 기본 메시지가 됨.
	        if(!source.equals("")) params.add (new BasicNameValuePair("source", source));
	        params.add (new BasicNameValuePair("msg", push.getMsg()));			// 메시지 셋팅 (필수) - 동일 메시지
	        
	        // 대상자 설정
	        for(int i=0; i<userList.size(); i++)
	        	params.add (new BasicNameValuePair("identity", (String)userList.get(i)));
	        
	        if(messList != null){
		        // 대상자별 메시지 설정
		        for(int i=0; i<messList.size(); i++)
		        	params.add (new BasicNameValuePair("message", (String)messList.get(i)));      
	        }        
	        if(fileList != null){
		        // 대상자별 이미지 링크 설정
		        for(int i=0; i<fileList.size(); i++)
		        	params.add (new BasicNameValuePair("prv_attachfname", (String)fileList.get(i)));
	        }        
	        if(linkList != null){
		        // 대상자별 웹 링크 설정
		        for(int i=0; i<linkList.size(); i++)
		        	params.add (new BasicNameValuePair("prv_linkurl", (String)linkList.get(i)));
	        }
	        if(titleList != null){
		        // 대상자별 타이틀 설정
		        for(int i=0; i<linkList.size(); i++)
		        	params.add (new BasicNameValuePair("prv_title", (String)titleList.get(i)));
	        }               
	        
	        // 메시지 전송 완료 플래그 설정
	        params.add (new BasicNameValuePair("isfinish", "Y"));									// 발송 완료 처리
	        	      
	        jsonString = sendHttpsUrlConExe(push.getCallUrl(), params);
						
		    return jsonString;
		}		
	}



	@Override
	public String sendTargetMore(PushVO push, ArrayList<String> userList, ArrayList<String> messList)
			throws NoSuchAlgorithmException, KeyManagementException, ClientProtocolException, IOException, JSONException {
			return sendTargetMoreProc(push, userList, messList, new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>());
	}
	
	@Override
	public String sendTargetMore(PushVO push, ArrayList<String> userList, ArrayList<String> messList, ArrayList<String> fileList)
			throws NoSuchAlgorithmException, KeyManagementException, ClientProtocolException, IOException, JSONException {
			return sendTargetMoreProc(push, userList, messList, fileList, new ArrayList<String>(), new ArrayList<String>());
	}		
	
	/**
	 * 개별 링크, 개별 타이틀 추가됨
	 */
	@Override
	public String sendTargetMore(PushVO push, ArrayList<Map<String, String>> paramList)
			throws NoSuchAlgorithmException, KeyManagementException, ClientProtocolException, IOException, JSONException {
			
			String code = "200";
			String message = "";
			
			ArrayList<String> userList = new ArrayList<String>();
			ArrayList<String> messList = new ArrayList<String>();
			ArrayList<String> fileList = new ArrayList<String>();
			ArrayList<String> linkList = new ArrayList<String>();
			ArrayList<String> titleList = new ArrayList<String>();
			ArrayList<String> hpList = new ArrayList<String>(); 						// 2017-01-10
			
			String tmpHp = "";
			for(int i=0; i<paramList.size(); i++){
				 Map<String, String> tmpMap = paramList.get(i);
				 userList.add((String)tmpMap.get("identity"));
				 messList.add((String)tmpMap.get("message"));
				 fileList.add((String)tmpMap.get("imgLink"));
				 linkList.add((String)tmpMap.get("link"));
				 titleList.add((String)tmpMap.get("title"));
				 if(push.getBesms().equals("Y")){											// 2017-01-10
					 tmpHp = tmpMap.get("hp");
					 if(tmpHp != null && !tmpHp.equals("")) hpList.add(tmpHp); 	
					 else hpList.add("-");															// 전화번호가 없는 경우
				 }
			}
			
			if(messList.size() > 0 && userList.size() != messList.size()){
				code = "5000";
				message = "대상자 수와 메시지 수가 일치하지 않습니다.";
			} 
			if(fileList.size() > 0 && userList.size() != fileList.size()){
				code = "5000";
				message = "대상자 수와 첨부이미지 수가 일치하지 않습니다.";
			} 
			if(linkList.size() > 0 && userList.size() != linkList.size()){
				code = "5000";
				message = "대상자 수와 웹링크 수가 일치하지 않습니다.";
			} 		
			if(titleList.size() > 0 && userList.size() != titleList.size()){
				code = "5000";
				message = "대상자 수와 타이틀 수가 일치하지 않습니다.";
			} 	
			// 20161229
			if(push.getBesms().equals("Y")){
				if(hpList.size() > 0 && userList.size() != titleList.size()){
					code = "5000";
					message = "대상자 수와 전화번호 수가 일치하지 않습니다.";
				} 						
			}
			
			if(!code.equals("200")){
				JSONObject obj = new JSONObject();
				obj.put("code", code);
				obj.put("message", message);
				return obj.toString();
			//}else return sendTargetMoreProc(push, userList, messList, fileList, linkList, titleList);
			}else return sendTargetMoreProc(push, userList, messList, fileList, linkList, titleList, hpList); // 20161229
	}


    
	private String sendTargetMoreProc(PushVO push, ArrayList<String> userList, ArrayList<String> messList, ArrayList<String> fileList, ArrayList<String> linkList, ArrayList<String> titleList) 
			throws NoSuchAlgorithmException, KeyManagementException, ClientProtocolException, IOException, JSONException {
		return sendTargetMoreProc(push, userList, messList, fileList,  linkList,  titleList, null);
	}
	
	/**
	 * 20161229
	 * @param push
	 * @param userList
	 * @param messList
	 * @param fileList
	 * @param linkList
	 * @param titleList
	 * @param hpList
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws JSONException
	 */
	private String sendTargetMoreProc(PushVO push, ArrayList<String> userList, ArrayList<String> messList, ArrayList<String> fileList, ArrayList<String> linkList, ArrayList<String> titleList, ArrayList<String> hpList) 
			throws NoSuchAlgorithmException, KeyManagementException, ClientProtocolException, IOException, JSONException {
		String jsonString = "";
        // 파라미터 설정
        List <BasicNameValuePair> params = new ArrayList<BasicNameValuePair>(); 
        params = setParams(push, params);		        // 푸시 메시지 기본 정보 셋팅
                
        push.setMsg(messList.get(0));																// 첫번째 메시지가 기본 메시지가 됨.
        if(!source.equals("")) params.add (new BasicNameValuePair("source", source));
        params.add (new BasicNameValuePair("msg", push.getMsg()));			// 메시지 셋팅 (필수) - 동일 메시지

        // SMS 서비스 사용자의 경우에만 해당 됨. : 20161229 추가
        if(push.getBesms().equals("Y")) params.add (new BasicNameValuePair("besms", "Y"));      // 대상자의 발송 실패시 SMS를 수신받을 전화번호        
        
		// >>>>>>>>>>>>>>>>>>> step 1. 메시지 등록 - 등록 후 메시지 번호 확인.( 해당 메시지 번호는 단계별 발송에 반드시 필요합니다)
		jsonString = sendMessage(push.getCallUrl(), params);		
	    JSONObject jsonObj = JSONObject.fromObject(jsonString);
	    
	    String result = (String)jsonObj.get("result");								// 결과 코드
	    String msgIdx = (String)jsonObj.get("msgIdx");							// 메시지 등록 후 반환되는 메시지 번호
	    String processCode = (String)jsonObj.get("processCode");		// 메시지 발송 단계, 20001 메시지 등록 시작, 20002 대상자 설정, 20003 메시지 설정 완료
	    String message = (String)jsonObj.get("message");					// 반환된 결과 메시지        
	    
	    push.setMsgIdx(msgIdx);															// 메시지 번호를 셋팅 한다.  (필수)	        
	    
	    // >>>>>>>>>>>>>>>>>> step 2. 대상자 발송 : 500 건씩 나누어 발송 한다 (500건-한번에 등록 가능한 식별자 수 --> 중요)
	    if(result.equals("200") && processCode.equals("20001")){
	    	// 대상자 목록과 대상자별로 받을 메시지의 길이가 다를 경우 발송되지 않습니다.
	    	List<ArrayList<String>> departList = separateArray(userList, 500);					// 대상자 나누기
	    	List<ArrayList<String>> departMessList = separateArray(messList, 500);		// 대상자별 메시지 나누기
	    	List<ArrayList<String>> departFileList = null;
	    	List<ArrayList<String>> departLinkList = null;
	    	List<ArrayList<String>> departTitleList = null;
	    	List<ArrayList<String>> departHpList = null;
	    	if(fileList != null && fileList.size() > 0)  departFileList = separateArray(fileList, 500);					// 대상자별 이미지파일 나누기
	    	if(linkList != null && linkList.size() > 0)  departLinkList = separateArray(linkList, 500);				// 대상자별 웹링크 나누기
	    	if(titleList != null && titleList.size() > 0)  departTitleList = separateArray(titleList, 500);				// 대상자별 타이틀 나누기
	    	if(push.getBesms().equals("Y")){	
	    		if(hpList != null && hpList.size() > 0)  departHpList = separateArray(hpList, 500);						// 대상자별 전화번호 나누기
	    	}
	    	
	    	//logger.debug("action count : "+departList.size());   	
	    	
	    	for(int i=0; i<departList.size(); i++){
	    		ArrayList<String> targetList = (ArrayList<String>)departList.get(i);
	    		ArrayList<String> messageList = (ArrayList<String>)departMessList.get(i);
	    		ArrayList<String> attachFileList = null;
	    		ArrayList<String> prv_linkList = null;
	    		ArrayList<String> prv_titleList = null;
	    		ArrayList<String> prv_hpList = null;
	    		if(departFileList != null && departFileList.size() > 0)  attachFileList = departFileList.get(i);
	    		if(departLinkList != null && departLinkList.size() > 0)  prv_linkList = departLinkList.get(i);
	    		if(departLinkList != null && departTitleList.size() > 0)  prv_titleList = departTitleList.get(i);
	    		if(push.getBesms().equals("Y")){	
	    			if(departHpList != null && departHpList.size() > 0)  prv_hpList = departHpList.get(i);
	    		}
	    		
	    		// 대상자 및 메시지를 발송 하고 결과 값을 받는다.
	    		String tmpMessage = "";
	    		
	    		if(push.getBesms().equals("Y")){	
	    			if(attachFileList != null) tmpMessage = sendTargetList(push, targetList, messageList, attachFileList, prv_linkList, prv_titleList, prv_hpList);
	    			else  tmpMessage = sendTargetList(push, targetList, messageList, prv_hpList);
	    		}else{
	    			if(attachFileList != null) tmpMessage = sendTargetList(push, targetList, messageList, attachFileList, prv_linkList, prv_titleList, null);
	    			else  tmpMessage = sendTargetList(push, targetList, messageList);
	    		}
	    		
	    		jsonObj = JSONObject.fromObject(tmpMessage);
	    	    logger.debug("target process : "+tmpMessage);
	    	    
	    	    result = (String)jsonObj.get("result");								// 결과 코드
	    	    msgIdx = (String)jsonObj.get("msgIdx");							// 메시지 등록 후 반환되는 메시지 번호
	    	    processCode = (String)jsonObj.get("processCode");		// 메시지 발송 단계, 20001 메시지 등록 시작, 20002 대상자 설정, 20003 메시지 설정 완료
	    	    message = (String)jsonObj.get("message");					// 반환된 결과 메시지
	    	    
	    	    if(!result.equals("200")){
	    	    	// 대상자 발송 결과에 따라  로그 처리 및 분기 처리를 하면 된다.
	    	    }
	    	    
	    	    jsonString = tmpMessage;
	    	}
	    }	    	    
	    	    
	    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  step 3. 메시지 전송 완료 플래그 발송
	    if(result.equals("200") && processCode.equals("20002")){
	    	String tmpMessage2 = sendFinish(push);
    		jsonObj = JSONObject.fromObject(tmpMessage2);
    	        		
    	    jsonString = tmpMessage2;
	    }	
        
		return jsonString;
	}	
	
	// 발송 완료 처리
	public String sendFinish(PushVO push)
			throws NoSuchAlgorithmException, KeyManagementException, ClientProtocolException, IOException
	{
		String jsonString  = "";

        // 파라미터 설정
        List <BasicNameValuePair> params = new ArrayList<BasicNameValuePair>(); 
        params.add (new BasicNameValuePair("appkey",push.getAppKey()));								//  (필수)	
        params.add (new BasicNameValuePair("appsecret",push.getAppSecret()));						//  (필수)
        params.add (new BasicNameValuePair("customerkey",push.getCustomerKey()));				//  (필수)
        
        params.add (new BasicNameValuePair("msgidx",push.getMsgIdx()));					 		// 발급받은 message id 를 넣어 준다.  (필수)
        params.add (new BasicNameValuePair("isfinish", "Y"));									// 발송 완료 처리
        if(!source.equals("")) params.add (new BasicNameValuePair("source", source));
        
        //jsonString = sendHttpsExe(push.getCallUrl(), params);					// DefaultHttpClient  deprecated
        jsonString = sendHttpsUrlConExe(push.getCallUrl(), params);	
                  
		return jsonString;			
	}		
	
	/**
	 * 
	 * @param push
	 * @param params
	 * @return
	 */
	private List <BasicNameValuePair> setParams(PushVO push, List <BasicNameValuePair> params){
        params.add (new BasicNameValuePair("appkey",push.getAppKey()));								//  (필수)	
        params.add (new BasicNameValuePair("appsecret",push.getAppSecret()));						//  (필수)
        params.add (new BasicNameValuePair("customerkey",push.getCustomerKey()));				//  (필수)
        
        // 메시지 부가 정보 설정                
        params.add (new BasicNameValuePair("isa", push.getIsa()));					// 안드로이드를 사용하는 대상폰 발송 Y/N  (필수)
        params.add (new BasicNameValuePair("asnd", push.getAsnd()));				// 푸시 수신시 안드로이드 사운드  (선택)
        params.add (new BasicNameValuePair("abdg", push.getAbdg()));				// 안드로이디 푸시 배지 처리 용  (선택)        
        params.add (new BasicNameValuePair("isi", push.getIsi()));						// IOS를 사용하는 대상폰 발송 Y/N     (필수)
        params.add (new BasicNameValuePair("ibdg", push.getIbdg()));				//IOS 푸시 배지 처리  (선택)
        params.add (new BasicNameValuePair("isnd", push.getIsnd()));				// IOS 푸시 사운드 처리  (선택)        
        params.add (new BasicNameValuePair("ck1", push.getCk1()));					// custom key 1  (선택)
        params.add (new BasicNameValuePair("ck2", push.getCk2()));					// custom key 2  (선택)
        params.add (new BasicNameValuePair("ck3", push.getCk3()));					// custom key 3  (선택)
        params.add (new BasicNameValuePair("cv1", push.getCv1()));					// custom value 1  (선택)
        params.add (new BasicNameValuePair("cv2", push.getCv2()));					// custom value 2  (선택)
        params.add (new BasicNameValuePair("cv3", push.getCv3()));					// custom value 3  (선택)
        params.add (new BasicNameValuePair("fnm", push.getFnm()));					// 첨부이미지 파일 링크 경로  (선택)
        params.add (new BasicNameValuePair("send_state", push.getSend_state()));// 발송 상태 : 0001 즉시발송, 0002 예약발송
        if(push.getSend_state().equals("0002"))   params.add (new BasicNameValuePair("senddate", push.getSenddate()));	  // 발송 시간(예약발송일 경우) yyyymmdd24Hmin
        params.add (new BasicNameValuePair("link", push.getLink()));					// 웹 링크 경로
		
    	// V3.0 추가 내용
        params.add (new BasicNameValuePair("title", push.getTitle()));					// 제목
        params.add (new BasicNameValuePair("bgcolor", push.getBgcolor()));		// 배경 컬러 RGB 값 :  ex) #FF0000
        params.add (new BasicNameValuePair("fcolor", push.getFontcolor()));		// 폰트 컬러 RGB 값 :  ex) #4374D9
        params.add (new BasicNameValuePair("lcode", push.getLabel_code()));	// 메시지 라벨코드 : 메시지 라벨관리에서 발급받은 10자리 난수
        params.add (new BasicNameValuePair("sspeed", push.getSendspeed()));	// 발송 속도 처리
        params.add (new BasicNameValuePair("ofb_time", push.getOfb_time()));	// opened fall back time : 오픈 처리 제한시간  - 2h, 4h, 1d, 3d, 5d, 1w
        params.add (new BasicNameValuePair("isetiquette", push.getIsetiquette()));	// 에티켓 시간 적용 여부 Y 적용, N 적용 안함.
        params.add (new BasicNameValuePair("etiquette_stime", push.getEtiquette_stime()));		// 에티켓 적용 시작 시간 0~23
        params.add (new BasicNameValuePair("etiquette_etime", push.getEtiquette_etime()));		// 에티켓 적용 해제 시간 0~23
        params.add (new BasicNameValuePair("and_priority", push.getAnd_priority()));	// 안드로이드 우선순위 H : 높음 / M : 중간(default)
        params.add (new BasicNameValuePair("optagree", push.getOptagree()));			// 옵션 동의 : 0000  광고수신 비동의 여부에 관계없이 발송, 1000 광고수신동의	한사람에게만 발송
        		
		return params;
	}
	
	/**
	 * 메시지 발송 - 타겟팅에서 사용
	 * @param callUrl
	 * @param params
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private String sendMessage(String callUrl, List <BasicNameValuePair> params) 
			throws NoSuchAlgorithmException, KeyManagementException, ClientProtocolException, IOException{
		//return sendHttpsExe(callUrl, params);				// DefaultHttpClient  deprecated
		return sendHttpsUrlConExe(callUrl, params);		
		
	}
	
	// 대상 리스트 발송 : 다중 건일 경우
	/**
	 * 대상 리스트 발송
	 * @param strAppkey
	 * @param strAppSecret
	 * @param strCustomerKey
	 * @param msgIdx
	 * @param callUrl
	 * @param targetList
	 * @param messList
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private String sendTargetList(PushVO push, ArrayList<String> targetList, ArrayList<String> messList)
			throws NoSuchAlgorithmException, KeyManagementException, ClientProtocolException, IOException
	{
		//return	sendTargetList(push, targetList, messList, null, null, null);			// 20161229  
		return	sendTargetList(push, targetList, messList, null, null, null, null);
	}	
	
	/**
	 * 20161229 최초작성
	 * @param push
	 * @param targetList
	 * @param messList
	 * @param hpList
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private String sendTargetList(PushVO push, ArrayList<String> targetList, ArrayList<String> messList, ArrayList<String> hpList)
			throws NoSuchAlgorithmException, KeyManagementException, ClientProtocolException, IOException
	{
		return	sendTargetList(push, targetList, messList, null, null, null, hpList);
	}		
	// 대상 리스트 발송 : 다중 건일 경우 - 개별 첨부파일 추가
	/**
	 * 대상 리스트 발송
	 * 20161229 : hpList 추가
	 * @param strAppkey
	 * @param strAppSecret
	 * @param strCustomerKey
	 * @param msgIdx
	 * @param callUrl
	 * @param targetList
	 * @param messList
	 * @param fileList
	 * @param hpList
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private String sendTargetList(PushVO push, ArrayList<String> targetList, ArrayList<String> messList, ArrayList<String> fileList, ArrayList<String> linkList, ArrayList<String> titleList, ArrayList<String> hpList)
			throws NoSuchAlgorithmException, KeyManagementException, ClientProtocolException, IOException
	{
		String jsonString  = "";
		
        // 파라미터 설정
        List <BasicNameValuePair> params = new ArrayList<BasicNameValuePair>(); 
        params.add (new BasicNameValuePair("appkey",push.getAppKey()));								//  (필수)	
        params.add (new BasicNameValuePair("appsecret",push.getAppSecret()));						//  (필수)
        params.add (new BasicNameValuePair("customerkey",push.getCustomerKey()));		//  (필수)
        
        params.add (new BasicNameValuePair("msgidx",push.getMsgIdx()));					 		// 발급받은 message id 를 넣어 준다.  (필수)
        if(!source.equals("")) params.add (new BasicNameValuePair("source", source));
        // 대상자 설정
        for(int i=0; i<targetList.size(); i++)
        	params.add (new BasicNameValuePair("identity", (String)targetList.get(i)));
        
        if(messList != null){
	        // 대상자별 메시지 설정
	        for(int i=0; i<messList.size(); i++)
	        	params.add (new BasicNameValuePair("message", (String)messList.get(i)));      
        }        
        if(fileList != null){
	        // 대상자별 이미지 링크 설정
	        for(int i=0; i<fileList.size(); i++)
	        	params.add (new BasicNameValuePair("prv_attachfname", (String)fileList.get(i)));
        }        
        if(linkList != null){
	        // 대상자별 웹 링크 설정
	        for(int i=0; i<linkList.size(); i++)
	        	params.add (new BasicNameValuePair("prv_linkurl", (String)linkList.get(i)));
        }
        if(titleList != null){
	        // 대상자별 타이틀 설정
	        for(int i=0; i<linkList.size(); i++)
	        	params.add (new BasicNameValuePair("prv_title", (String)titleList.get(i)));
        }               
        if(hpList != null){
	        // 대상자별 전화번호 설정
	        for(int i=0; i<hpList.size(); i++)
	        	params.add (new BasicNameValuePair("prv_hp", (String)hpList.get(i)));
        }           
        
		//jsonString = sendHttpsExe(push.getCallUrl(), params);				// DefaultHttpClient  deprecated
		jsonString = sendHttpsUrlConExe(push.getCallUrl(), params);		        
		return jsonString;			
	}	

	
	/**
	 * arrayList 를 일정 길이로 잘라 반환
	 * @param array
	 * @param size
	 * @return
	 */
	private List<ArrayList<String>> separateArray(ArrayList<String> array, int size){
		//ArrayList<ArrayList> returnArray = new ArrayList();
		List<ArrayList<String>> returnArray = new ArrayList<ArrayList<String>>();
		boolean init = false;
		ArrayList<String> tmpList = new ArrayList<String>();
				
		int i = 0;
		int cnt = 0;
		while(i<array.size()){
			if(init){
				tmpList = new ArrayList<String>();
				init = false;
			}
			tmpList.add((String)array.get(i));
			
			cnt++;
			i++;
						
			if(cnt == size){
				returnArray.add(tmpList);
				init = true;
				cnt = 0;
			}else{
				if(i == array.size()){
					returnArray.add(tmpList);
				}
			}
		}		
		return returnArray;
	}

	/**
	 * HttpClient 를 이용한 HTTPS 로 파라미터 전송 - deprecated
	 * 
	 * @param callUrl
	 * @param params
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public String sendHttpsExe(String callUrl, List <BasicNameValuePair> params)
			throws NoSuchAlgorithmException, KeyManagementException, ClientProtocolException, IOException
	{
		String jsonString = "";
		HttpClient httpclient = new DefaultHttpClient();
		
		
		httpclient.getParams().setParameter("http.socket.timeout", 120 * 1000);
		httpclient.getParams().setParameter("http.connection.timeout", 120 * 1000);
		
		httpclient.getParams().setParameter("http.connection-manager.timeout", new Long(120 * 1000));
		httpclient.getParams().setParameter("http.protocol.head-body-timeout", 120 * 1000);
		
		
		// override		
        TrustManager easyTrustManager = new X509TrustManager() {            
            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {  return null;     }
            
            @Override
            public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {}
            
            @Override
            public void checkClientTrusted(java.security.cert.X509Certificate[] chain,String authType) throws CertificateException {}
        };        
        
        
        // HttpClient의 경우 X509TrustManager를 이용해 SSLContext를 생성하고
        // ClientConnectionManager와 SchemeRegistry에 SSLSocketFactory를 등록해 준다.        
        try {
            SSLContext sslcontext = SSLContext.getInstance("TLS");//SSLContext 지정된 시큐어 소켓 프로토콜 구현
            sslcontext.init(null, new TrustManager[] { easyTrustManager }, null);

            //SSLSocketFactory socketFactory = new SSLSocketFactory(sslcontext,SSLSocketFactory.STRICT_HOSTNAME_VERIFIER);
            SSLSocketFactory socketFactory = new SSLSocketFactory(sslcontext,SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            
            Scheme sch = new Scheme("https", 443, socketFactory);//SSL기본포트 : 443
            httpclient.getConnectionManager().getSchemeRegistry().register(sch);

            //HttpClient timeout 셋팅
            httpclient.getParams().setParameter("http.protocol.expect-continue", false);				//HttpClient POST 요청시 Expect 헤더정보 사용 x
            httpclient.getParams().setParameter("http.connection.timeout", 3 * 1000);					// 원격 호스트와 연결을 설정하는 시간
            httpclient.getParams().setParameter("http.socket.timeout",  3 * 1000);							//데이터를 기다리는 시간
            httpclient.getParams().setParameter("http.connection-manager.timeout",  3 * 1000);	// 연결 및 소켓 시간 초과 
            httpclient.getParams().setParameter("http.protocol.head-body-timeout",  3 * 1000);
            
            HttpPost httppost = new HttpPost(callUrl);                
            //logger.debug("executing request" + httppost.getRequestLine());          
                   
            // 파라미터 인코딩
            UrlEncodedFormEntity reqEntity = new UrlEncodedFormEntity(params, "utf-8");
            
            // 타겟 주소에 파라미터 setting 
            httppost.setEntity(reqEntity);
            
            //  실행 결과
            HttpResponse resp = httpclient.execute(httppost);
            
            // 결과 값을 resEntity에 저장
            HttpEntity resEntity = resp.getEntity();    
            
            // 받은 결과를 String 유형으로 변환     
            jsonString = EntityUtils.toString(resEntity);
            
        }finally {
           //Apache의 HttpClient 라이버러리에서는 웹 서버를 연결을 하고 난후 명시적으로 끊는 부분이 없을 경우 대기 상태로 넘어가며,
        	//이런 대기가 여러개 발생하게 되면 웹서버의 네트워크 리소스에 누수가 발생할 수가 있다. 
        	httpclient.getConnectionManager().shutdown();//<-httpclient의 접속 끊기. 
        }
		
		return jsonString;			
	}

	/**
	 * HttpsURLConnect 사용 하여 HTTPS 처리함.
	 * 2016-06-08
	 * @param callUrl
	 * @param params
	 * @return
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 */
	public String sendHttpsUrlConExe(String callUrl, List <BasicNameValuePair> params)
			throws IOException, NoSuchAlgorithmException, KeyManagementException, MalformedURLException
	{
		String jsonString = "";

	
        URL url = new URL(callUrl);
        trustAllHosts();

        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
        httpsURLConnection.setHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        });

        HttpURLConnection connection = httpsURLConnection;

        connection.setRequestMethod("POST");
		connection.setUseCaches(false);           
        connection.setDoInput(true);
        connection.setDoOutput(true);

		 UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "utf-8");
		 OutputStream post = connection.getOutputStream();
		 entity.writeTo(post);
		 
		 post.flush();
         connection.connect();

         // 결과 조회
         StringBuilder responseStringBuilder = new StringBuilder();
         logger.debug("contentType : "+connection.getContentType());
         if (connection.getResponseCode() == HttpURLConnection.HTTP_OK){
        	
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            for (;;){
                String stringLine = bufferedReader.readLine();
                if (stringLine == null ) break;
                responseStringBuilder.append(stringLine + '\n');
            }
            bufferedReader.close();
         }

         connection.disconnect();

         jsonString = responseStringBuilder.toString();             
        
		return jsonString;
	}
	
    private static void trustAllHosts() {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[]{};
            }

            @Override
            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] chain,
                    String authType)
                    throws java.security.cert.CertificateException {
                // TODO Auto-generated method stub

            }

            @Override
            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] chain,
                    String authType)
                    throws java.security.cert.CertificateException {
                // TODO Auto-generated method stub

            }
        }};

        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection
                    .setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	/**
	 * 타겟 푸시 발송 후 식별자 값에 대한 유효성 및 결과 조회
	 * (일괄 푸시는 해당 사항 없음)
	 * 
	 * @param push
	 * @param pageNo
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	@Override
	public String getRtTargetMess(PushVO push, int pageNo)
			throws NoSuchAlgorithmException, KeyManagementException, ClientProtocolException, IOException {
		String jsonString  = "";			// 서버로 발송 후 결과 메시지
		
        List <BasicNameValuePair> params = new ArrayList<BasicNameValuePair>(); 
        params = setParams(push, params);																	// 푸시 메시지 기본 정보 셋팅
        params.add (new BasicNameValuePair("msgidx", push.getMsgIdx()));				// 메시지 번호 셋팅 (필수)
        params.add (new BasicNameValuePair("page", ""+pageNo));								// 조회할 페이지 번호
        
        jsonString = sendHttpsExe(push.getCallUrl(), params);
        //jsonString = sendHttpsUrlConExe(push.getCallUrl(), params); // 2016-06-08
		return jsonString;
	}
	
	/**
	 * 푸시 발송 예약 후 해당 푸시 메시지 취소. 푸시 메시지가 '대기' 혹은 '일시정지'일 경우에만 취소가 가능 함.
	 * @param push
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	@Override
	public String cnclPushMess(PushVO push)
			throws NoSuchAlgorithmException, KeyManagementException, ClientProtocolException, IOException {
		String jsonString  = "";			// 서버로 발송 후 결과 메시지
		
        List <BasicNameValuePair> params = new ArrayList<BasicNameValuePair>(); 
        params = setParams(push, params);																	// 푸시 메시지 기본 정보 셋팅
        params.add (new BasicNameValuePair("msgidx", push.getMsgIdx()));				// 메시지 번호 셋팅 (필수)
        params.add (new BasicNameValuePair("mode", push.getMode()));					// 메시지 유형 셋팅 (필수)
        
        jsonString = sendHttpsUrlConExe(push.getCallUrl(), params);
		return jsonString;
	}
		
}
