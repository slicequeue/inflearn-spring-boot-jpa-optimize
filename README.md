# Inflearn Spring Boot JPA optimize
인프런 김영한 Spring Boot JPA 성능 최적화 강의
* 강좌: https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8-JPA-API%EA%B0%9C%EB%B0%9C-%EC%84%B1%EB%8A%A5%EC%B5%9C%EC%A0%81%ED%99%94
* 학습 시작일시: 2023-12-23
* 학습 종료: TBU


## 초기 세팅
프로젝트 초기 세팅 관련 설정법 기술
* main.resources.application.yml 설정
    * DB_URL: 데이터에비스 접속
        * 예시 (H2 in mem): jdbc:h2:mem:testdb;MODE=MySQL;DATABASE_TO_UPPER=FALSE
        * 예시 (H2 in local): jdbc:h2:~:testdb;MODE=MySQL;DATABASE_TO_UPPER=FALSE
    * DB_USER: DB 계정 아이디
        * 예시(H2 in mem): sa
    * DB_PASS: DB 계정 비밀번호
        * 예시(H2 in mem): 
    * DB_POOL_SIZE: DB Hikari PoolSize
        * 예시(H2 in mem): 5
* test.resources.application.yml 설정
    * application.yml 에 H2 인메모리 DB 로 설정 고정
        * 상황에 맞게 직조작 할 것

### 액티브프로파일 설정
* jvm active profile 값 설정, IntelliJ 실행 설정으로 처리
    * 기본 설정값 관련해서는 예시용으로 작성한 logback-local.xml `local` 로 설정해야 작동
* JUnit 테스트 실행시에는 test.java.resources 부분에 application.yml 설정 적용되며
    * 각 테스트에 ActiveProfile 어노테이션으로 `test` 로 지정함
