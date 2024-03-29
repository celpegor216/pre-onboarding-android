# MVVM + Clean Architecture 예제

## 기능 요구 조건

- Product 목록을 불러와서 화면에 출력
- 새로운 Product 추가
- 마지막 Product 삭제

## 단계 별 진행

<details>
<summary><b>0. 프로젝트 세팅</b></summary>
<div markdown="1">

</br>

![image01](./README_image/image01.png)

- MainActivity 내에 MainScreen, Product 구성

</div>
</details>

<details>
<summary><b>1. package 세팅</b></summary>
<div markdown="1">

</br>

![image02](./README_image/image02.png)

- Package를 나누는 방법은 `Layer` 단위로 나누는 방식과 `Feature` 단위로 나누는 방식이 있음
- 본 예제에서는 Layer를 구분해서 학습하기 위해 Layer 단위로 나눔
- Layer 구조
  - Presentation Layer: ComposeView, ViewModel
  - Domain Layer: UseCase, RepositoryInterface, DomainModel
  - Data Layer: RepositoryImpl, DataSource, DataModel

</div>
</details>

<details>
<summary><b>2. ViewModel 생성 및 연결</b></summary>
<div markdown="1">

</br>

![image03](./README_image/image03.png)

- ViewModel 생성
  - 상태(products) 및 관련 함수 생성
- ViewModel과 Screen 연결
  - `androidx.lifecycle:lifecycle-viewmodel-compose` 및 `androidx.lifecycle:lifecycle-runtime-compose` 라이브러리 추가
  - Screen에 ViewModel의 상태 및 관련 함수 연결

</div>
</details>

<details>
<summary><b>3. Repository 생성 및 연결</b></summary>
<div markdown="1">

</br>

![image04](./README_image/image04.png)

- Repository 생성
  - ViewModel의 상태(products) 및 관련 함수 이전
  - 인터페이스는 Domain Layer, 구현체는 Data Layer
- Repository ViewModel 연결
  - DI를 위해 전역 객체 관리 도구(`DependenciesProvider`) 생성
  - Repository를 ViewModel에 주입해서 상태 및 관련 함수 연결
  - stateIn으로 Flow -> StateFlow 변경해 구독

</div>
</details>

<details>
<summary><b>4. DataSource 생성 및 연결</b></summary>
<div markdown="1">

</br>

![image05](./README_image/image05.png)

- DataSource 생성
  - Repository의 상태(products) 및 관련 함수 이전
  - 인터페이스와 구현체 모두 Data Layer
- DataSource와 Repository 연결
  - `DependenciesProvider`를 통해 DataSource를 Repositroy에 주입하고 상태 및 관련 함수 연결

</div>
</details>

<details>
<summary><b>5. DomainModel 생성 및 연결</b></summary>
<div markdown="1">

</br>

![image06](./README_image/image06.png)

- DomainModel 생성
  - DataModel의 데이터 샘플 이전
  - DataModel과 상호 변환 가능한 함수(Mapper) 생성
- DomainModel와 DataSource 연결

</div>
</details>

<details>
<summary><b>6. UseCase 생성 및 연결</b></summary>
<div markdown="1">

</br>

![image07](./README_image/image07.png)

- UseCase 생성
- `DependenciesProvider`를 통해 Repositroy를 UseCase에 주입
- UseCase와 ViewModel 연결

</div>
</details>

<details>
<summary><b>7. 최종 패키지 구조</b></summary>
<div markdown="1">

</br>

![image08](./README_image/image08.png)

- View, ViewModel, UseCase, Repository, DataSource, Mapper로 아키텍처 구성
- View는 ViewModel의 상태를 구독하고, 함수를 호출
- ViewModel은 View를 모르고, UseCase를 통해서 데이터 접근 및 수정
- UseCase는 ViewModel을 모르고, Repository를 통해서 데이터 접근 및 수정
- Repository는 UseCase를 모르고, DataSource를 통해서 데이터 접근 및 수정
- DataSource는 Repository를 모르고, 내부적으로 상태와 함수를 구현

</div>
</details>
