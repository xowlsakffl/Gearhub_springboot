# GearHub Spring Boot

GearHub는 전자제품 전용 이커머스 쇼핑몰 백엔드입니다. 상품/카테고리 조회, 장바구니, 배송지, 주문, 결제 흐름을 제공하며 관리자 권한 기반 운영 API를 분리했습니다.

## 주요 기능

- JWT 기반 로그인 및 인증
- 사용자 회원가입 기본 권한 제한
- 상품 목록, 상세, 카테고리별 조회
- 장바구니 생성, 수량 변경, 삭제
- 배송지 등록, 수정, 삭제
- 주문 생성 및 결제 정보 저장
- 사용자별 주문 내역 조회
- 관리자 전용 상품 등록, 수정, 삭제
- 관리자 전용 카테고리 등록, 수정, 삭제
- 관리자 전용 전체 주문 조회 및 주문 상태 변경
- 데모 데이터 자동 생성

## 권한 체계

- `ROLE_USER`: 쇼핑몰 이용자
- `ROLE_SELLER`: 판매자 확장용 권한
- `ROLE_ADMIN`: 관리자 API 접근 권한

공개 회원가입은 `ROLE_USER`만 부여합니다. `/api/admin/**` 경로는 `ROLE_ADMIN` 권한이 있는 계정만 접근할 수 있습니다.

## 데모 계정

- 일반 사용자: `demo / password123!`
- 관리자: `admin / admin1234!`

## 기술 스택

- Java 21
- Spring Boot 3
- Spring Security
- JWT
- Spring Data JPA
- H2
- Maven
- Lombok
- ModelMapper
- Stripe API

## 실행 방법

```bash
./mvnw spring-boot:run
```

Windows 환경에서는 다음 명령을 사용할 수 있습니다.

```bash
mvnw.cmd spring-boot:run
```

## 빌드

```bash
./mvnw -DskipTests package
```
