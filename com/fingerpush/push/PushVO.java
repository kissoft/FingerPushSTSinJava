package com.fingerpush.push;

/**
 * 2016.05.25
 * @author pandaO_OBae
 * 메시지 관련 VO 객체 
 */
/**
 * @author seungjin
 *
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
	private String link;		// web link
	private String fnm;		// 첨부이미지 파일 링크 경로	
	private String send_state;	// 발송 구분 : 0001 실시간 발송, 0002 예약발송
	private String senddate;		// 발송 예정일  - send_state 가 0002 일 경우에만 적용됨.	
	private String mode;				// 발송 모드 DEFT/STOS/LNGT : 발송 모드 --> 일괄 발송에 해당
	private String lngt_message;	// 롱텍스트 메시지										 --> 일괄 발송에 해당
	private String tag;					// 태그 발송 												 --> 일괄 발송에 해당
	private String beschmode;		// 태그 검색 모드 And/or -> 0001/0002
	private String besms;				// 푸시 발송 실패시 SMS로 발송 여부 : 2016-12-29 추가
	private String hp;					// 수신자의 handphone number
	
	// V3.0 추가 내용
	private String title;						// 타이틀
	private String bgcolor;				// 배경 컬러 RGB 값 :  ex) #FF0000
	private String fontcolor;				// 폰트 컬러 RGB 값 :  ex) #4374D9
	private String sendspeed;			// 발송 속도
	private String ofb_time;				// opened fall back time : 오픈 처리 제한시간  - 2h, 4h, 1d, 3d, 5d, 1w
	private String isetiquette;				// 에티켓 시간 적용 여부 Y 적용, N 적용 안함.
	private String etiquette_stime;		// 에티켓 적용 시작 시간 0~23
	private String etiquette_etime;		// 에티켓 적용 해제 시간 0~23
	private String label_code;			// 메시지 라벨코드 : 메시지 라벨관리에서 발급받은 10자리 난수	
	private String and_priority;			// 안드로이드 우선순위 H : 높음 / M : 중간(default)  
	private String optagree;				// 옵션 동의 : 0000  광고수신 비동의 여부에 관계없이 발송, 1000 광고수신동의	한사람에게만 발송
	
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
	
	public String getLink() {
		return ifNull(link, "");
	}

	public void setLink(String link) {
		this.link = link;
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
	
	public String getHp() {
		return ifNull(hp, "");
	}

	public void setHp(String hp) {
		hp = hp.replace("-", "");
		this.hp = hp;
	}

	public String getBesms() {
		return ifNull(besms, "");
	}

	public void setBesms(String besms) {
		this.besms = besms;
	}

	public String getBeschmode() {
		return ifNull(beschmode, "0001");
	}

	public void setBeschmode(String beschmode) {
		this.beschmode = beschmode;
	}

	// v2.5 추가 내용
	public String getTitle() {
		return ifNull(title, "");
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBgcolor() {
		return ifNull(bgcolor, "");
	}

	public void setBgcolor(String bgcolor) {
		this.bgcolor = bgcolor;
	}

	public String getFontcolor() {
		return ifNull( fontcolor, "");
	}

	public void setFontcolor(String fontcolor) {
		this.fontcolor = fontcolor;
	}

	public String getSendspeed() {
		return sendspeed;
	}

	public void setSendspeed(String sendspeed) {
		this.sendspeed = sendspeed;
	}

	public String getOfb_time() {
		return ifNull( ofb_time, "");
	}

	public void setOfb_time(String ofb_time) {
		this.ofb_time = ofb_time;
	}

	public String getIsetiquette() {
		return ifNull( isetiquette, "");
	}

	public void setIsetiquette(String isetiquette) {
		this.isetiquette = isetiquette;		
	}

	public String getEtiquette_stime() {
		return ifNull( etiquette_stime, "");
	}

	public void setEtiquette_stime(String etiquette_stime) {
		this.etiquette_stime = etiquette_stime;
	}

	public String getEtiquette_etime() {
		return ifNull( etiquette_etime, "");
	}

	public void setEtiquette_etime(String etiquette_etime) {
		this.etiquette_etime = etiquette_etime;
	}

	public String getLabel_code() {
		return label_code;
	}

	public void setLabel_code(String label_code) {
		this.label_code = label_code;
	}

	public String getAnd_priority() {
		return and_priority;
	}

	public void setAnd_priority(String and_priority) {
		this.and_priority = and_priority;
	}

	public String getOptagree() {
		return optagree;
	}

	public void setOptagree(String optagree) {
		this.optagree = optagree;
	}
	
}
