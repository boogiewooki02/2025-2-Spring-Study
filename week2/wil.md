# 백엔드 정규 스터디 2주차 학습 정리
**주제: 계층형 아키텍처, 컨트롤러, 서비스, 스프링 빈과 의존성 주입(DI)**

---

## 1. 스프링 계층형 아키텍처 (Layered Architecture)

스프링은 역할을 분리한 **계층형 아키텍처(Layered Architecture)** 구조를 사용한다.  
요청은 **Controller → Service → Repository → DB** 순으로 흐른다.

| 계층 | 역할 | 비유 |
|------|------|------|
| Controller | HTTP 요청/응답 처리 (입구) | 웨이터 |
| Service | 비즈니스 로직 처리 | 주방장 |
| Repository (DAO) | 데이터베이스 접근 | 창고 관리자 |
| DB | 실제 데이터 저장소 | 냉장/냉동 창고 |

---

## 2. DTO & Entity

| 구분 | 설명 | 비유 |
|------|------|------|
| DTO (Data Transfer Object) | 필요한 데이터만 담는 전송용 객체 | 주문서, 영수증 |
| Entity | DB 테이블과 매핑되는 핵심 객체 | 원재료 |

- **DTO**
    - 데이터 전송용 객체 (필요한 정보만 포함)
    - 예: 회원 이름, 주소, 전화번호
- **Entity**
    - DB와 직접 매핑되는 객체
    - 예: id, createdAt, updatedAt, 등급, 유통기한
    - 외부로 직접 노출 금지 (데이터 일관성과 보안 유지)

---

## 3. Controller Layer (컨트롤러 계층)

### 역할
- 클라이언트(브라우저/프론트엔드)로부터 HTTP 요청을 가장 먼저 받는 계층
- 요청 데이터를 받아 Service에 로직 처리를 위임
- DTO를 사용해 데이터 송수신

### 주요 어노테이션

| 어노테이션 | 설명 |
|-------------|------|
| @Controller | 뷰 반환용 컨트롤러 |
| @RestController | JSON 응답용 REST 컨트롤러 |
| @RequestMapping | URL과 HTTP 메서드 매핑 |
| @RequestBody | JSON 요청 데이터를 객체로 변환 |
| @ResponseBody | 반환값을 JSON으로 응답 |

---

### HTTP 상태 코드 요약

| 코드 | 의미 |
|------|------|
| 200 OK | 요청 성공 |
| 201 Created | 데이터 생성 성공 |
| 204 No Content | 성공했지만 반환 데이터 없음 |
| 400 Bad Request | 클라이언트 요청 오류 |
| 404 Not Found | 요청 데이터 없음 |
| 500 Internal Server Error | 서버 내부 오류 |

---

## 4. Service Layer (서비스 계층)

### 역할
- 애플리케이션의 **비즈니스 로직**을 담당하는 핵심 계층
- Controller와 Repository 사이에서 데이터를 가공하고 전달
- 트랜잭션 단위로 처리하여 데이터의 **원자성(Atomicity)** 보장

### 트랜잭션 처리
- 모든 작업이 전부 성공하거나 전부 실패해야 함
- `@Transactional` 어노테이션으로 트랜잭션 단위 설정 가능

---

## 5. 스프링 빈 (Spring Bean)

### 정의
- **Spring Container(스프링 컨테이너)** 가 직접 생성하고 관리하는 객체
- 애플리케이션 전역에서 공용으로 사용 가능

### 특징
- 빈을 생성하고 의존성을 자동 주입받을 수 있음
- Bean을 저장하고 관리하는 공간: **ApplicationContext**

### Bean 등록 방식

| 방식 | 설명 |
|------|------|
| 자동 등록 (컴포넌트 스캔) | `@Component`, `@Service`, `@Controller`, `@Repository` |
| 수동 등록 | Java 설정 클래스에서 `@Bean` 명시적으로 선언 |

---

## 6. 의존성 주입 (Dependency Injection, DI)

### 개념
- 객체가 필요한 의존성을 **직접 생성하지 않고**,  
  **Spring이 대신 주입해주는 메커니즘**

### 장점
- 매번 `new`로 객체를 생성할 필요가 없음
- 코드 결합도가 낮아지고 유지보수가 쉬워짐
- 테스트 및 확장 용이

### 의존성 주입 방법

| 방식 | 특징 | 예시 |
|------|------|------|
| 생성자 주입 (권장) | 불변성 유지 (`final` 가능) | `public UserService(UserRepository repo)` |
| 필드 주입 | `@Autowired`로 필드에 직접 주입 | `@Autowired private UserRepository repo;` |
| Setter 주입 | 선택적 의존성에 사용 | `@Autowired public void setRepo(...)` |

### 생성자 주입 + final 예시

```java
@Service
public class UserService {
    private final UserRepository userRepository; // 불변성 보장

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
```

