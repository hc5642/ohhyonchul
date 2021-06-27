# ohhyonchul
오현철 개인 리파지토리

# 2019. 10. 6 commit
 - jdbcTemplate 변경
 - slf4j 변경
 - json 핸들링을 ctl 단에서 수행하도록 변경
 - 4번문제 http response code 값 세팅하도록 변경
 
 # 2019. 10. 5 commit 
 
 ## 개발 프레임 워크
 - 개발 툴 : STS - Spring Boot Starter Project
 - 사용한 라이브러리 : lombok, json-simple, sqlite, junit

## 문제 해결 방법 (요약)
 1) 수수료를 제외한 승인금액의 합과 취소거래의 차를 계산하여, 년도와 계좌번호를 그룹으로 묶어 합산
 2) 거래년도와 전체 계좌번호목록을 를 하나의 컬럼으로 합쳐 모수로 삼은뒤, 
    실제 거래내역의 거래년도와 거래 계좌번호를 하나의 컬럼으로 합쳐서 LEFT JOIN 한뒤,
    실거래 변에 NULL 이 존재하는 항목(취소제외)을 가져와 다시 년도와 계좌로 분리하여 출력함
 3) 년도별 부점별 실적 총액을 계산한 뒤 년도와 금액의 역순으로 정렬
 4) 부점별 실적금액을 계산하 되, 분당점에 해당하는 부점코드 발견시 해당 부점코드를 판교부점코드로 변경하여 합산한다

## 빌드 및 실행 방법
 1) com.ohc.kakaopay.KakaopayApplication.class 를 실행하여 웹을 구동후 URL접속으로 테스트
 2) src/test/java 소스 폴더에 com.ohc.kakaopay.KakaopayApplicationTest.class를 실행하여 Junit으로 테스트 가능
