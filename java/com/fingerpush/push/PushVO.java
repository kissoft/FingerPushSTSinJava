package com.fingerpush.push;

/**
 * 2015.08.19
 * @author pandaO_OBae
 * 메시지 관련 VO 객체 
 */
public class PushVO {
	private String callUrl;
	
	private String appKey;
	private String appSecret;
	private String customerKey;
	
	private String identity;
	private String msg;		// 메시지
	private String isa;		//안드로이드를 사용하는 대상폰 발송 Y/N
	private String asnd;	// 푸시 수신시 안드로이드 사운드
	private String abdg;	// 안드로이디 푸시 배지 처리 
	private String isi;			// IOS를 사용하는 대상폰 발송 Y/N
	private String ibdg;		//IOS 푸시 배지 처리
	private String isnd;		// IOS 푸시 사운드 처리
	private String ck1;		// custom key 1 
	private String ck2;		// custom key 2
	private String ck3;		// custom key 3 
	private String cv1;		// custom value 1
	private String cv2;		// custom value 2
	private String cv3;		// custom value 2
	private String fnm;		// 첨부이미지 파일 링크 경로
	
	private String send_state;	// 발송 구분 : 0001 실시간 발송, 0002 예약발송
	private String senddate;		// 발송 예정일  - send_state 가 0002 일 경우에만 적용됨.
	
	private String mode;				// 발송 모드 DEFT/STOS/LNGT : 발송 모드 --> 일괄 발송에 해당
	private String lngt_message;	// 롱텍스트 메시지										 --> 일괄 발송에 해당
	private String tag;					// 태그 발송 												 --> 일괄 발송에 해당
	private String beschmode;		// 태그 검색 모드 And/or -> 0001/0002
	
	
	private String msgIdx;			// 500건 이상의 대상에게 메시지 발송시 필요한 메시지 번호(각 식별자 그룹을 해당 메시지에 처리 되도록)
	
	
	private String ifNull(String str, String dflt){
		if(str == null || str.equals("")) return dflt;
		else return str;
	}	
		
	public String getCallUrl() {
		return ifNull(callUrl, "");
	}

	public void setCallUrl(String callUrl) {
		this.callUrl = callUrl;
	}

	public String getIdentity() {
		return ifNull(identity, "");
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getAppKey() {		
		return ifNull(appKey, "");
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getAppSecret() {
		return ifNull(appSecret, "");
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getCustomerKey() {
		return ifNull(customerKey, "");
	}

	public void setCustomerKey(String customerKey) {
		this.customerKey = customerKey;
	}

	public String getMsg() {
		return ifNull(msg, "");
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getIsa() {
		return ifNull(isa, "Y");
	}

	public void setIsa(String isa) {
		this.isa = isa;
	}

	public String getAsnd() {
		return ifNull(asnd, "");
	}

	public void setAsnd(String asnd) {
		this.asnd = asnd;
	}

	public String getAbdg() {
		return ifNull(abdg, "");
	}

	public void setAbdg(String abdg) {
		this.abdg = abdg;
	}

	public String getIsi() {
		return ifNull(isi, "Y");
	}

	public void setIsi(String isi) {
		this.isi = isi;
	}

	public String getIbdg() {
		return ifNull(ibdg, "");
	}

	public void setIbdg(String ibdg) {
		this.ibdg = ibdg;
	}

	public String getIsnd() {
		return ifNull(isnd, "");
	}

	public void setIsnd(String isnd) {
		this.isnd = isnd;
	}

	public String getCk1() {
		return ifNull(ck1, "");
	}

	public void setCk1(String ck1) {
		this.ck1 = ck1;
	}

	public String getCk2() {
		return ifNull(ck2, "");
	}

	public void setCk2(String ck2) {
		this.ck2 = ck2;
	}

	public String getCk3() {
		return ifNull(ck3, "");
	}

	public void setCk3(String ck3) {
		this.ck3 = ck3;
	}

	public String getCv1() {
		return ifNull(cv1, "");
	}

	public void setCv1(String cv1) {
		this.cv1 = cv1;
	}

	public String getCv2() {
		return ifNull(cv2, "");
	}

	public void setCv2(String cv2) {
		this.cv2 = cv2;
	}

	public String getCv3() {
		return ifNull(cv3, "");
	}

	public void setCv3(String cv3) {
		this.cv3 = cv3;
	}

	public String getFnm() {
		return ifNull(fnm, "");
	}

	public void setFnm(String fnm) {
		this.fnm = fnm;
	}

	public String getSend_state() {
		return ifNull(send_state, "");
	}

	public void setSend_state(String send_state) {
		this.send_state = send_state;
	}

	public String getSenddate() {
		return ifNull(senddate, "");
	}

	public void setSenddate(String senddate) {
		this.senddate = senddate;
	}

	public String getMsgIdx() {
		return ifNull(msgIdx, "");
	}

	public void setMsgIdx(String msgIdx) {
		this.msgIdx = msgIdx;
	}

	public String getMode() {
		return ifNull(mode, "DEFT");
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getLngt_message() {
		return ifNull(lngt_message, "");
	}

	public void setLngt_message(String lngt_message) {
		this.lngt_message = lngt_message;
	}

	public String getTag() {
		return ifNull(tag, "");
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getBeschmode() {
		return ifNull(beschmode, "0001");
	}

	public void setBeschmode(String beschmode) {
		this.beschmode = beschmode;
	}
	
	
	
	
	
}
