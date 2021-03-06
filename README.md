# coffee-order-app
### 클론코딩 강의에서 구현한 동일한 기능을 TDD 방식으로 재구현

* 클래스 생성 -> 테스트 클래스 생성 -> 테스트 코드 작성 -> 테스트 성공을 위한 클래스 구현 코드 작성 
  * 최대한 위 순서로 코드 작성 시도

* 테스트 커버리지 클래스 100% 메서드 90%, 라인 90% 달성
* 단위 테스트 지향
  * Mockito 활용
  * 의존하는 클래스의 테스트 대역을 생성하기 전에 먼저 그 클래스를 단위 테스트 한다.

* 실행 환경에 구애받지 않는 테스트 코드
  * TestContainer  
    * Docker가 설치된 환경에서 테스트용 데이터베이스 임시 컨테이너 활용
  * MockMVC 활용 Controller 컴포넌트 테스트 
    * Postman 같은 외부 프로그램에 의존하지 않는 자동화된 HTTP 테스트 구현
