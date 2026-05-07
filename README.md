# GearHub Spring Boot API 서버

GearHub는 컴퓨팅, 오디오, 게이밍, 모바일 액세서리를 중심으로 한 전자제품 전문 이커머스 서비스입니다. 이 저장소는 Spring Boot 기반 백엔드 API 서버이며, 사용자 인증, 상품/카테고리, 장바구니, 배송지, 주문/결제, 관리자 운영 기능을 REST API로 제공합니다.

핵심은 `controller / service / repository / model / payload / security`로 역할을 분리하고, JWT 인증과 Spring Security 권한 정책을 기반으로 사용자 API와 관리자 API를 나눈 점입니다. 일반 사용자는 쇼핑몰 이용 흐름을 수행하고, 관리자는 `/api/admin/**` 경로를 통해 상품, 카테고리, 주문 상태를 운영합니다.

## 프로젝트 개요

- JWT 기반 회원가입 및 로그인
- 사용자 기본 권한과 관리자 권한 분리
- 전자제품 상품 및 카테고리 조회
- 상품 상세 정보 제공
- 장바구니 생성, 동기화, 수량 변경, 삭제
- 배송지 등록, 수정, 삭제, 조회
- 데모 결제 및 주문 생성
- 사용자별 주문 내역 조회
- 관리자 전체 주문 조회 및 상태 변경
- 관리자 상품/카테고리 등록, 수정, 삭제
- H2 기반 로컬 데모 데이터 자동 생성

## 이 저장소가 맡는 역할

GearHub 서비스는 프론트엔드와 백엔드가 분리된 구조입니다.

- 인증 API: 회원가입, 로그인, 로그아웃, 사용자 정보 조회
- 공개 조회 API: 상품 목록, 상품 상세, 카테고리 목록
- 사용자 API: 장바구니, 배송지, 주문, 결제 준비
- 관리자 API: 상품 관리, 카테고리 관리, 전체 주문 관리
- 보안 계층: JWT 발급/검증, 권한별 접근 제어, CORS 설정
- 데이터 계층: JPA 엔티티, Repository, H2/MySQL 런타임 지원

이 저장소는 쇼핑몰의 비즈니스 로직과 데이터 영속성, 권한 정책을 담당합니다. 프론트엔드는 `Gearhub_react`에서 분리 관리하며, 이 서버의 `/api` 경로를 호출합니다.

## 핵심 서비스 흐름

1. 사용자가 `/api/auth/signup` 또는 `/api/auth/signin`으로 계정을 생성하거나 로그인합니다.
2. 로그인 성공 시 JWT가 발급되고, 프론트엔드는 이후 요청에 `Authorization: Bearer` 헤더를 붙입니다.
3. 사용자는 `/api/public/products`, `/api/public/categories`로 상품과 카테고리를 조회합니다.
4. 장바구니 상품은 `/api/cart/create` 또는 장바구니 상품 API로 서버에 저장됩니다.
5. 체크아웃 단계에서 배송지를 선택하고 `/api/order/users/payments/{paymentMethod}`로 주문을 생성합니다.
6. 주문 생성 시 서버 장바구니 기준으로 주문 상품을 만들고 상품 재고를 차감합니다.
7. 사용자는 `/api/orders/users`로 본인 주문 내역을 조회합니다.
8. 관리자는 `ROLE_ADMIN` 권한으로 `/api/admin/**` API에 접근해 상품, 카테고리, 주문 상태를 관리합니다.

## 주요 기능

### 1. 인증 및 권한

- 회원가입
- 로그인 및 JWT 발급
- 로그아웃
- 현재 사용자 정보 조회
- 공개 회원가입 시 `ROLE_USER`만 부여
- 관리자 API는 `ROLE_ADMIN` 권한 필요

관련 컨트롤러:

- `AuthController`

주요 경로:

- `POST /api/auth/signup`
- `POST /api/auth/signin`
- `POST /api/auth/signout`
- `GET /api/auth/user`
- `GET /api/auth/username`

### 2. 상품 조회 및 관리

- 상품 목록 조회
- 키워드 검색
- 카테고리 필터
- 상품 상세 조회
- 관리자 상품 등록
- 관리자 상품 수정
- 관리자 상품 삭제
- 상품 이미지 URL 및 업로드 파일 경로 처리

관련 컨트롤러:

- `ProductController`

주요 경로:

- `GET /api/public/products`
- `GET /api/public/products/{productId}`
- `GET /api/public/categories/{categoryId}/products`
- `GET /api/public/products/keyword/{keyword}`
- `POST /api/admin/categories/{categoryId}/product`
- `PUT /api/admin/products/{productId}`
- `DELETE /api/admin/products/{productId}`
- `PUT /api/products/{productId}/image`

### 3. 카테고리

- 카테고리 목록 조회
- 관리자 카테고리 등록
- 관리자 카테고리 수정
- 관리자 카테고리 삭제

관련 컨트롤러:

- `CategoryController`

주요 경로:

- `GET /api/public/categories`
- `POST /api/admin/categories`
- `PUT /api/admin/categories/{categoryId}`
- `DELETE /api/admin/categories/{categoryId}`

### 4. 장바구니

- 사용자 장바구니 생성
- 장바구니 상품 동기화
- 상품 수량 변경
- 장바구니 상품 삭제
- 장바구니 합계 계산

관련 컨트롤러:

- `CartController`

주요 경로:

- `POST /api/cart/create`
- `POST /api/carts/products/{productId}/quantity/{quantity}`
- `GET /api/carts/users/cart`
- `PUT /api/cart/products/{productId}/quantity/{operation}`
- `DELETE /api/carts/{cartId}/product/{productId}`

### 5. 배송지

- 배송지 등록
- 배송지 목록 조회
- 배송지 단건 조회
- 배송지 수정
- 배송지 삭제

관련 컨트롤러:

- `AddressController`

주요 경로:

- `POST /api/addresses`
- `GET /api/addresses`
- `GET /api/addresses/{addressId}`
- `PUT /api/addresses/{addressId}`
- `DELETE /api/addresses/{addressId}`

### 6. 주문 및 결제

- 장바구니 기준 주문 생성
- 주문 상품 저장
- 상품 재고 차감
- 사용자 주문 내역 조회
- Stripe 결제 준비용 client secret 생성
- 관리자 전체 주문 조회
- 관리자 주문 상태 변경

관련 컨트롤러:

- `OrderController`

주요 경로:

- `POST /api/order/users/payments/{paymentMethod}`
- `GET /api/orders/users`
- `POST /api/order/stripe-client-secret`
- `GET /api/admin/orders`
- `PUT /api/admin/orders/{orderId}/status`

## 기술 스택

### Backend

![Java](https://img.shields.io/badge/Java-21-007396?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.4-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring%20Security-JWT-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white)
![Spring Data JPA](https://img.shields.io/badge/Spring%20Data%20JPA-Persistence-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Validation](https://img.shields.io/badge/Bean%20Validation-Input%20Check-0F172A?style=for-the-badge)
![JJWT](https://img.shields.io/badge/JJWT-0.12.5-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white)
![ModelMapper](https://img.shields.io/badge/ModelMapper-3.0.0-2563EB?style=for-the-badge)
![Stripe](https://img.shields.io/badge/Stripe%20Java-29.3.0-635BFF?style=for-the-badge&logo=stripe&logoColor=white)
![H2](https://img.shields.io/badge/H2-Local%20Runtime-1E3A8A?style=for-the-badge)
![MySQL](https://img.shields.io/badge/MySQL-Connector-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-Build-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)

## 프로젝트 구조

```text
src/main/java/com/ecommerce/project/
├── config/                      # 공통 설정, 데모 데이터, 정적 이미지 핸들러
├── controller/                  # REST API 컨트롤러
├── exceptions/                  # 공통 예외와 전역 예외 처리
├── model/                       # JPA Entity, Role Enum
├── payload/                     # DTO, API 응답 객체
├── repository/                  # Spring Data JPA Repository
├── security/                    # SecurityFilterChain, JWT 필터, 요청/응답 DTO
├── service/                     # 비즈니스 로직
└── util/                        # 인증 사용자 조회 유틸

src/main/resources/
└── application.properties       # 서버, DB, JWT, CORS, Stripe 설정
```

## 계층별 역할

### `controller`

- HTTP 요청 진입점
- 사용자 API와 관리자 API 분리
- DTO 기반 요청/응답 처리

### `service`

- 상품, 카테고리, 장바구니, 주문, 배송지 비즈니스 로직
- 주문 생성 시 장바구니 상품을 주문 상품으로 변환
- 상품 가격, 할인 가격, 재고, 장바구니 합계 계산

### `repository`

- JPA 기반 데이터 접근
- 사용자, 역할, 상품, 카테고리, 장바구니, 주문, 결제, 배송지 조회

### `security`

- JWT 생성 및 검증
- 인증 필터 등록
- `/api/admin/**` 관리자 권한 제한
- 프론트 로컬 개발 주소 CORS 허용

### `config`

- 데모 계정과 데모 상품 데이터 생성
- 이미지 파일 정적 리소스 매핑
- ModelMapper 등 공통 Bean 구성

## 실행 준비

### 백엔드 실행

```bash
./mvnw spring-boot:run
```

PowerShell:

```powershell
.\mvnw.cmd spring-boot:run
```

### 빌드

```bash
./mvnw -DskipTests package
```

PowerShell:

```powershell
.\mvnw.cmd -DskipTests package
```

### JAR 실행

```bash
java -jar target/sb-ecom-0.0.1-SNAPSHOT.jar
```

## 주요 환경 변수

| 변수 | 설명 |
| --- | --- |
| `SERVER_PORT` | 서버 실행 포트 |
| `SPRING_DATASOURCE_URL` | JDBC URL |
| `SPRING_DATASOURCE_USERNAME` | DB 계정 |
| `SPRING_DATASOURCE_PASSWORD` | DB 비밀번호 |
| `SPRING_DATASOURCE_DRIVER_CLASS_NAME` | DB 드라이버 클래스명 |
| `SPRING_JPA_DDL_AUTO` | Hibernate DDL 정책 |
| `JWT_SECRET` | JWT 서명 키 |
| `JWT_EXPIRATION_MS` | JWT 만료 시간 |
| `JWT_COOKIE_NAME` | JWT 쿠키명 |
| `FRONTEND_URL` | CORS 허용 프론트 주소 목록 |
| `IMAGE_BASE_URL` | 상품 이미지 기본 URL |
| `PROJECT_IMAGE_PATH` | 업로드 이미지 저장 경로 |
| `STRIPE_SECRET_KEY` | Stripe secret key |

기본 로컬 설정:

```properties
server.port=8080
spring.datasource.url=jdbc:h2:file:./data/ecommerce-db;MODE=MySQL
frontend.url=http://localhost:5173,http://127.0.0.1:5173
image.base.url=http://localhost:8080/images
```

## 권한 체계

| 권한 | 역할 |
| --- | --- |
| `ROLE_USER` | 일반 쇼핑몰 사용자 |
| `ROLE_SELLER` | 판매자 확장용 권한 |
| `ROLE_ADMIN` | 관리자 API 접근 권한 |

공개 회원가입은 `ROLE_USER`만 부여합니다. 관리자 계정은 데모 데이터 초기화 또는 DB 권한 설정을 통해 관리합니다.

## 데모 계정

| 역할 | 아이디 | 비밀번호 |
| --- | --- | --- |
| 일반 사용자 | `demo` | `password123!` |
| 관리자 | `admin` | `admin1234!` |

## 데모 데이터

`DemoDataInitializer`가 서버 시작 시 다음 데이터를 보강합니다.

- 사용자 계정
- 관리자 계정
- 전자제품 카테고리
- 컴퓨팅, 오디오, 게이밍, 모바일 액세서리 상품
- 데모 배송지
- 데모 장바구니

## 보완한 부분

- 전자제품 전문 이커머스 콘셉트에 맞는 데모 데이터 구성
- 상품 상세 API 추가
- 상품 DTO에 브랜드, 요약 사양, 배송 정보, 카테고리 ID 확장
- 관리자 상품 등록/수정/삭제 API 정리
- 관리자 카테고리 등록/수정/삭제 API 정리
- 관리자 전체 주문 조회 및 주문 상태 변경 API 추가
- `/api/admin/**` 경로 `ROLE_ADMIN` 권한 제한
- 공개 회원가입의 임의 관리자 권한 부여 차단
- CORS preflight가 Spring Security 체인을 통과하도록 보완
- 주문 생성 시 한글 주문 상태와 오류 메시지 일부 정리
- README 한글 문서화 정리

## 관련 저장소

- Frontend: [Gearhub_react](https://github.com/xowlsakffl/Gearhub_react)
- Backend: [Gearhub_springboot](https://github.com/xowlsakffl/Gearhub_springboot)
