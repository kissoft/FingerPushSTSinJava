package com.fingerpush.push;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

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
	 * 타겟팅/500건 이상/각기 다른 메시지  : 	단일건 발송과 callUrl 이 다름
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
}
