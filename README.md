# SPRING PLUS

## 12. AWS 활용
### 설정 화면
#### EC2
![Image](https://github.com/user-attachments/assets/036eac3c-2045-4658-8157-ec36e2aba5e4)

![Image](https://github.com/user-attachments/assets/19107ace-84cf-4524-b6e9-cfafca1b0f07)

![Image](https://github.com/user-attachments/assets/f76514ec-d09e-499e-b457-e09d793a565b)

#### RDS
![Image](https://github.com/user-attachments/assets/418885db-e57f-45bb-b2a3-71300d832a68)

![Image](https://github.com/user-attachments/assets/1eba3666-7a5b-48b7-af67-ed316bc8108b)

#### S3
![Image](https://github.com/user-attachments/assets/351a9c30-c419-4fca-b87b-67c251062897)

## 13. 대용량 데이터 조회

비록 인덱스를 적용하는 기준이 `where 절에서 자주 조회하고 수정 빈도가 낮으며 중복이 적은 컬럼`에 적용하는 것이지만 데이터의 양이 늘어나면서 특정 데이터를 찾으려면 full table scan 과정이 필요하게 되지만 MySQL의 InnoDB에서 인덱스를 사용할 경우 B-Tree를 사용하기 때문에 조회에 걸리는 시간이 단축될 것이라고 생각이 들어서 Index를 적용하게 되었음

### Index 적용 전
![Image](https://github.com/user-attachments/assets/c97e3367-37e1-4967-86cf-4a69a57896c7)

### Index 적용 후
![Image](https://github.com/user-attachments/assets/67850ead-ece4-4baf-a530-dba947b61f9b)

#### 230ms -> 13ms의 결과를 보였다.
- 성능 개선율: 1669.23%
- 절감률: 94.35%

## [트러블 슈팅](https://velog.io/@dereck-jun/%ED%94%8C%EB%9F%AC%EC%8A%A4-%EC%A3%BC%EC%B0%A8-%ED%8A%B8%EB%9F%AC%EB%B8%94-%EC%8A%88%ED%8C%85)