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
3. 타겟팅 다중 발송 : 500건 이하, 동일 메시지
4. 타겟팅 다중 발송 : 500건 이하, 각기 다른 메시지
5. 타겟팅 다중 발송 : 500건 이상, 동일 메시지
6. 타겟팅 다중 발송 : 500건 이상, 각기 다른 메시지

물론 타겟팅 발송에서도 메시지 예약 발송 등이 가능합니다.

해당 소스 및 파라미터 등에 대해 좀더 자세히 알고 싶다면, http://www.fingerpush.com 에서 ServertoServer manual을 다운받아 보시면 이해가 되실 겁니다.

해당 소스들은 말그대로, 통신 샘플용 소스이므로 실제 개발환경 및 시스템 환경에서의 퍼포먼스를 고려하지는 않았습니다.

단지, 각 경우에 따른 처리 방법 들에 참고로 이용하시길 바랍니다.
