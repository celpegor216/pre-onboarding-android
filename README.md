# pre_onboarding_android

Wanted 프리온보딩 Android 챌린지(2024년 3월, Modern Android 훑어보기)

</br>

## 역량 향상 세션

### 03/04(월): Compose

<details>
  <summary><b>1. Compose란?</b></summary>
  <div markdown="1">

    - 네이티브 UI 작성을 위한 모던 툴킷
    - view들을 합성(compose)해서 view를 구성
    - view가 상속이 아닌 확장을 통해 만들어짐
    - UI를 XML이 아닌 kotlin으로 작성
    - 정식 출시 2021년 7월말, 성장 속도가 빠름
    - XML의 단점
      - view의 계층과 속성 구조가 분리되어 있어 관리가 어려움
      - namespace(`android:`, `app:`...) 등으로 인해 표현 내용에 비해 오버헤드가 발생함
      - view에 접근하기 위해 `findViewById()`와 같은 탐색이 필요함
      - 개발자가 상태 변경 타이밍을 고려하여, 직접 노드에 api 접근 후 상태를 변경해야 함
    - Compose의 장점
      - XML에 비해 계층 구조 표현이 간결하고, 코드량이 적음
      - 선언형 UI 방식으로 순수하게 UI 표현에만 집중함
      - 데이터가 바인딩되어 있어 view의 상태 변경이 자동으로 이루어짐
      - navigation, viewmodel, coroutine 등 프레임워크 레벨에서 적극적으로 지원되고, 코드에서 동적으로 view 로직을 적용할 수 있음
      - MaterialDesign, Theme 등 디자인 측면에서 간편한 API 구조와 다양한 기본 기능을 제공함
      - Animation API가 강력함

  <details>
    <summary>선언형 UI</summary>
    <div markdown="1">

      - UI를 어떻게 만드는지가 아닌, 오로지 어떤 UI를 만들 것인지에만 집중하는 UI 개발 방법론
        - 명령형 UI: name 상태가 바뀌면 개발자가 setText로 view의 text를 직접 바꿔줌
        - 선언형 UI: view의 text에 name 상태가 바인딩되어, name 상태가 바뀌면 UI가 자동으로 view의 text를 바꿈
      - view에서 event가 발생하면 state가 변경되고, 변경된 state가 data를 view에 전달하여 이를 표시함
      - 개발자는 view와 state를 바인딩하고, event가 발생했을 때 어떻게 state를 변경할 것인지에 집중함 → 데이터와 상태의 연결, 유지보수가 용이함

    </div>

  </details>

  </div>
</details>

<details>
<summary><b>2. Compose View 작성 기초</b></summary>
<div markdown="1">

- `@Composable` 함수
  - 합성의 대상이 되는 구성 요소
  - 어노테이션을 작성해야 컴파일러가 해당 함수를 compose view로 인식함
  - 단독으로도 view가 될 수 있고, 여러 Composable을 합성해서 사용할 수도 있음
  - `{}`에 다른 Composable 함수를 호출(자식 view)하는 방식으로 계층 구조를 정의
  - 속성 값, 데이터를 인자로 받을 수 있음
  - 반환값이 없음
  - Idempotent(멱등성, 입력값이 같으면 항상 동일한 결과를 내야 함)을 준수해야 함 → 준수하지 않으면 예측 불가능해서 UI 버그 발생 가능
- `setContent`: activity에 compose view를 content로 주입
- `Modifier`: 컴포넌트를 꾸미는 수정자, 사이즈/형태/행위/외면 등 변경 가능, 내부 레이블 같은 정보나 사용자 입력 처리도 가능, 체이닝이 가능하나 순서에 따라 결과가 달라짐
  - view의 모양이나 형태 등 속성을 바꾸려면 컴포넌트 각각에 직접 속성 값을 설정하거나, modifier를 통해 설정할 수 있음
- 주로 사용하는 기본 컴포넌트
  - `Text`
  - `Button`: xml과 달리 Text를 상속받지 않기 때문에 Text를 내부에 추가해야 함
  - `TextField`: EditText, 선언형 UI 특성상 데이터 바인딩(`value = name`)과 이벤트 방출(`onValueChange = { name = it }`)에 집중함
    ```kotlin
      fun Greeting() {
        var name by remember { mutableStateOf("") }
        TextField(
          value = name,
          onValueChange = { name = it }
        )
      }
    ```
  - `Column`, `Row`: LinearLayout
  - `Box`: FrameLayout, z축으로 쌓음(=view가 포개짐)
  - `LazyColumn`, `LazyRow`: RecyclerView와 유사, 다만 재사용이 아니라 필요할 때 필요한 만큼 생성, 어댑터 불필요, `items(data) { Text(data.text) }` 와 같은 형식으로 사용

</div>
</details>

<details>
<summary><b>3. Compose가 화면을 그리는 과정</b></summary>
<div markdown="1">

1. Composition: 화면에 있는 Composable을 파싱해서 메모리에 UI 트리를 생성함 → Composable 함수의 코드 블럭이 실행됨
2. 생성된 UI 트리를 기반으로 화면에 표시함 → 각 플랫폼에 맞는 렌더링 로직이 실행됨
3. Recomposition: data가 변경되면 변경된 부분만 UI 트리가 새로 그려짐
4. 변경되지 않은 부분은 생략됨(재사용) → 성능 개선을 위해, data가 같으면 결과가 같아서 재사용되도록 코드를 작성해야 함(Idempotent)

**※ Recomposition 시 재사용되는 기준**

- 원시 타입, String, MutableState 등: 값의 비교를 기본적으로 지원하여 알아서 재사용됨
- 모양도 같고 인자가 같은 함수: call site(함수 호출 위치)를 id로 사용하기 때문에 재사용되지 않음
- 반복문: call site는 동일하지만 실행 순서를 기준으로 구분함
  - 실행 순서가 같으면서 값도 같으면 재사용됨
  - 그러나 맨 앞에 요소가 추가되는 등 순서가 바뀌면 재사용되지 않음
  - 이때, `key()`를 사용하면 순서가 바뀌어도 비교 후 재사용됨
- 객체: 내부 프로퍼티를 var로 선언하면 재사용되지 않음, val로 변경하면 재사용됨
- `@Stable`: 컴파일러에게 변하지 않을 것이라고 알려 재사용됨, 단 유저가 값을 바꿔서 화면과 상태가 달라지는 버그가 발생할 수 있음

⇒ Recomposition은 UI 트리 생성 속도 최적화를 가장 중요시함
⇒ 이로 인해 Composable 함수는 순서대로 실행되지 않거나, 메인 스레드에서 실행되지 않거나, 빠르게 다시 실행될 수 있음

</div>
</details>

<details>
<summary><b>4. Compose의 상태 관리</b></summary>
<div markdown="1">

- 일반 로컬 변수: Recomposition 시점에 함수가 재실행되면 초기화되어 상태가 유지되지 않음
- `remember {}` : 로컬 변수와 달리, 시스템 캐시에 값을 저장하여 Recomposition 되더라도 상태가 유지됨
- `rememberSavable {}`: 화면 회전 등으로 인해 Activity가 재생성되어도 상태가 유지됨
- `mutableStateOf()`: remember와 rememberSavable 내부에 선언, `.value`로 값에 접근, 값의 변화가 관찰됨 -`by remember…(property delegation, getter와 setter를 위임)`으로 상태 선언 시 `.value` 생략하고 접근 가능
- Stateful: Composable 함수 내에 상태가 있음 → 외부에서 이를 사용할 때 상태를 고려해서 사용해야 함 → 재사용성이 떨어지고 테스트가 어려움
- Stateless: Composable 함수 내에 상태가 없고 외부로부터 상태 값과 관리하는 로직을 주입받음 → 재사용성을 고려하여 권장되는 방식
  - State Hoisting: Stateful 함수였으나, 외부에서 상태를 주입 받도록 수정하여 Stateless로 만드는 것
  - 상태 변경 시 데이터 흐름
    1. Click 이벤트가 발생하여 count의 상태가 변경됨
    2. Compose에게 count의 상태가 변경되었다는 알림이 감
    3. Recomposition이 실행되어 변경된 상태를 참조하는 부분을 찾아서 다시 그림

</div>
</details>

<details>
<summary><b>5. Compose의 LifeCycle과 Side Effect</b></summary>
<div markdown="1">

- LifeCycle
  1. Enter the composition: Composable 함수를 분석해서 메모리에 UI 트리를 그림
  2. Recompose: 상태가 바뀌어서 Recomposition 발생
  3. Leave the composition: 더 이상 필요 없어져서 UI 트리에서 삭제
- Side Effect
  - Composable 함수 외부에서 일어나는 앱의 상태 변화(네트워크 통신, snackbar 표시 등)
  - SideEffect가 발생하지 않도록, UI 렌더링과 외부 상태 변경을 결합하지 않아야 함 → 라이프사이클을 고려한 API가 제공됨
  - `LaunchedEffect(key)`: Composition 시점에만 한 번 실행, 기본적으로 Coroutine 사용, key를 넣으면 key가 바뀔 때마다 실행됨
  - `DisposableEffect(key)`: Composition 시점에 실행 후 Decomposition 시점에 onDispose 블럭(필수) 실행, key를 넣으면 key가 바뀔 때마다 실행됨
  - `SideEffect`: Composition, Recomposition 시점에 실행, 화면 렌더링과 별개로 완료되어야 하는 작업에 사용

</div>
</details>

---

## 사전 미션

- [Jetpack Compose](https://github.com/celpegor216/pre-onboarding-android/tree/main/basics_jetpack_compose)
- [Coroutine](https://github.com/celpegor216/pre-onboarding-android/tree/main/basics_coroutine)
