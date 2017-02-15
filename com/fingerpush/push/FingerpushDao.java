package com.fingerpush.push;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

public interface FingerpushDao {
	/**
	 * 타겟팅/단일건 : 	복수 발송과 callUrl 이 다름
	 * @param push
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public String sendTargetPush(PushVO push) throws NoSuchAlgorithmException, KeyManagementException, ClientProtocolException, IOException ;
	
	/**
	 * 타겟팅/500건 이하
	 * 20160922
	 * @param push
	 * @param paramList
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public String sendTargetUnderMax(PushVO push, ArrayList<Map<String, String>> paramList)
			throws NoSuchAlgorithmException, KeyManagementException, ClientProtocolException, IOException;
	
	/**
	 * 타겟팅/500건 이상/각기 다른 메시지  : 	단일건 발송과 callUrl 이 다름
	 * - 해당 API는 Deprecated 됐습니다. 추후 삭제할 예정입니다
	 * @param push
	 * @param userList
	 * @param messList
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public String sendTargetMore(PushVO push, ArrayList<String> userList, ArrayList<String> messList)
			throws NoSuchAlgorithmException, KeyManagementException, ClientProtocolException, IOException;
		
	
	/**
	 * 타겟팅/500건 이상/각기 다른 메시지, 각기 다른 이미지파일  : 	단일건 발송과 callUrl 이 다름 
	 * - 해당 API는 Deprecated 됐습니다. 추후 삭제할 예정입니다
	 * @param push
	 * @param userList
	 * @param messList
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public String sendTargetMore(PushVO push, ArrayList<String> userList, ArrayList<String> messList, ArrayList<String> fileList)
			throws NoSuchAlgorithmException, KeyManagementException, ClientProtocolException, IOException;
	
	/**
	 * version 3.0
	 * @param push
	 * @param paramList
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public String sendTargetMore(PushVO push, ArrayList<Map<String, String>> paramList)
			throws NoSuchAlgorithmException, KeyManagementException, ClientProtocolException, IOException;
	
	/**
	 * 활성화된 모든 디바이스에 일괄 발송
	 * @param push
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public String sendAllDevice(PushVO push) 
			throws NoSuchAlgorithmException, KeyManagementException, ClientProtocolException, IOException;
	
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
	public String getRtTargetMess(PushVO push, int pageNo)
			throws NoSuchAlgorithmException, KeyManagementException, ClientProtocolException, IOException;
	
	/**
	 * 푸시 발송 예약 후 해당 푸시 메시지 취소. 푸시 메시지가 '대기' 혹은 '일시정지'일 경우에만 취소가 가능 함.
	 * @param push
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public String cnclPushMess(PushVO push)
			throws NoSuchAlgorithmException, KeyManagementException, ClientProtocolException, IOException;

  	/**  
	* HTTPS 프로토콜을 이용하여 파라미터를 전송하고 결과를 json 으로 받아 반환  
	* @param callUrl  
	* @param params  
	* @return  
	* @throws IOException  
	* @throws NoSuchAlgorithmException  
	* @throws KeyManagementException  
	* @throws MalformedURLException  
	*/ 
	public String sendHttpsUrlConExe(String callUrl, List <BasicNameValuePair> params)   
		throws IOException, NoSuchAlgorithmException, KeyManagementException, MalformedURLException;
}
