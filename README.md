## 1. 프로젝트 환경 설정

[Spring Boot Starter](https://start.spring.io) 사용해서 프로젝트 생성

IntelliJ Gradle 대신 Java 직접 실행
Setting ⇒ Build Tools ⇒ Gradle ⇒ Run, Test Java사용으로 바꾸기

---

## 2. 스프링 웹 개발 기초 요약

### 📁 1. 정적 컨텐츠

- `resources/static/` 경로에 위치한 파일은 요청 시 그대로 응답됨
- 별도의 컨트롤러 없이 HTML, CSS, JS, 이미지 등을 정적으로 서비스
- 실제 웹 페이지처럼 보여지며, 빠르게 로딩됨

### 🧩 2. MVC와 템플릿 엔진

- 사용자의 요청을 **Controller**가 받고
- 데이터를 **Model**에 담아
- **템플릿 엔진(View)** 으로 화면에 출력
- 대표 템플릿 엔진: Thymeleaf
- URL에 파라미터를 넣어 전달한 값을 동적으로 화면에 출력 가능

### 🔗 3. API 방식 응답 (@ResponseBody)

- `@ResponseBody`를 사용하면 템플릿 없이 데이터를 직접 응답
- 텍스트나 JSON 형태로 반환 가능
    - 텍스트: 브라우저에 문자열 그대로 출력
    - 객체: JSON으로 자동 변환되어 반환됨
- ViewResolver 대신 **HttpMessageConverter**가 동작함
    - 문자열: `StringHttpMessageConverter`
    - 객체: `MappingJackson2HttpMessageConverter`
- 주로 프론트엔드와 백엔드가 분리된 구조에서 사용됨

---

## 3. 회원 관리 예제 - 백엔드 개발 요약

### 🧱 웹 애플리케이션 계층 구조

| 계층 | 역할 |
| --- | --- |
| Controller | 웹 요청을 받고 처리 |
| Service | 비즈니스 로직 담당 |
| Repository | 데이터 저장 및 조회 담당 |
| Domain | 비즈니스 객체 (예: 회원) |

### 🧩 회원 도메인 & 리포지토리

- **Member 클래스**: 회원의 id와 name 필드 보유
- **MemberRepository 인터페이스**: save, findById, findByName, findAll 정의
- **MemoryMemberRepository**: 위 인터페이스를 구현한 메모리 저장소
    - 내부에 Map으로 회원 저장
    - `clearStore()` 메서드로 데이터 초기화 가능

### 🧪 회원 리포지토리 테스트

- **JUnit5**를 사용한 단위 테스트 수행
- 테스트는 `src/test/java` 폴더에 작성
- 각 테스트 후 `@AfterEach`로 저장소 초기화하여 독립성 확보
- 테스트 대상: 저장, 이름으로 조회, 전체 조회 기능

> 좋은 테스트는 순서에 의존하지 않고 독립적이어야 함
> 

### ⚙️ 회원 서비스 개발

- **MemberService 클래스**: 회원 등록 및 전체/단건 조회 기능 제공
- 중복 회원은 이름으로 검증하여 예외 처리
- 초기에 `new MemoryMemberRepository()`로 직접 생성 → **DI (Dependency Injection)방식으로 변경**

### ✅ 회원 서비스 테스트

- `@BeforeEach`: 테스트 실행 전, 새로운 저장소와 서비스 생성
- `@AfterEach`: 테스트 후 저장소 초기화

---

## **4. 스프링 빈과 의존관계 요약**

### **🏗️ 의존관계 설정 방법**

1. **컴포넌트 스캔과 자동 주입**
    - **`@Controller`**, **`@Service`**, **`@Repository`** 어노테이션으로 스프링 빈 자동 등록
    - 생성자에 **`@Autowired`**를 사용하여 의존성 자동 주입 (스프링 4.3+부터 단일 생성자 시 생략 가능)
2. **자바 코드로 직접 등록**
    - **`@Configuration`** 클래스 내 **`@Bean`** 메서드로 명시적 빈 등록
    - 구현체 변경이 필요한 경우 유용 (예: 메모리 → DB 리포지토리 전환)

### **🔄 의존성 주입 (DI) 방식**

- **생성자 주입** (실무 권장 방식)
    - 의존관계 불변성 보장
    - 테스트 코드 작성 용이
    - 순환 참조 방지
- **Setter 주입**
    - 선택적 의존관계 시 사용 (거의 사용되지 않음)
- **필드 주입**
    - 간결하지만 테스트와 유연성 측면에서 비권장

### **📌 핵심 원리**

- **싱글톤 스코프**: 기본적으로 스프링 빈은 싱글톤으로 관리됨
- **컴포넌트 스캔 범위**: **`@SpringBootApplication`**이 있는 패키지 및 하위 패키지 대상
- **주의사항**:
    - **`@Autowired`**는 스프링 컨테이너가 관리하는 빈에서만 동작
    - 직접 생성한 객체에는 적용 불가

### **🛠️ 실무 적용 가이드**

- **정형화된 코드**: 컴포넌트 스캔 방식 (**`@Controller`**, **`@Service`**, **`@Repository`**)
- **동적 구현체 전환 필요 시**: 자바 설정 파일(**`@Configuration`**)을 통한 수동 빈 등록
- **의존성 주입 우선순위**: 생성자 주입 → Setter 주입 → 필드 주입

---

## **5. 회원 관리 예제 - 웹 MVC 개발 요약**

### **🏠 홈 화면 추가**

- **HomeController** 생성
    - **`@GetMapping("/")`**으로 기본 경로 매핑
    - **`home.html`** 템플릿 반환
- **정적 파일 우선순위**
    - 컨트롤러가 정적 리소스(static/index.html)보다 우선 적용됨

### **✏️ 회원 등록 기능**

1. **등록 폼 컨트롤러**
    - **`GET /members/new`** → **`createMemberForm.html`** 반환
2. **데이터 전송 객체(DTO)**
    - **`MemberForm`** 클래스로 이름(name) 필드 수신
3. **등록 처리**
    - **`POST /members/new`** 요청 처리
    - **`MemberService.join()`**으로 회원 가입 후 홈(**`/`**)으로 리다이렉트

### **📋 회원 조회 기능**

1. **조회 컨트롤러**
    - **`GET /members`** → **`memberService.findMembers()`**로 전체 회원 조회
    - **`Model`**에 회원 목록 추가하여 **`memberList.html`** 반환
2. **Thymeleaf 템플릿**
    - **`th:each`**로 회원 목록 렌더링
    - **`${members}`**로 컨트롤러에서 전달된 데이터 접근

### **🔗 기능별 URL 정리**

| **기능** | **HTTP Method** | **URL** | **처리 내용** |
| --- | --- | --- | --- |
| 홈 | GET | **`/`** | 홈 화면 표시 |
| 등록 폼 | GET | **`/members/new`** | 회원 가입 폼 제공 |
| 등록 | POST | **`/members/new`** | 회원 데이터 저장 후 리다이렉트 |
| 조회 | GET | **`/members`** | 전체 회원 목록 표시 |

### **🛠️ 구현 포인트**

- **Thymeleaf** 사용: **`th:text`**, **`th:each`**로 동적 HTML 생성
- **PRG 패턴**: POST 후 리다이렉트(**`redirect:/`**) 적용
- **MVC 구조**: 컨트롤러 → 서비스 → 리포지토리 계층 분리
