package fastcampus.aop.basics_jetpack_compose

// Jetpack Compose 관련 클래스는 androidx.compose.* 에서 import
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.coerceAtLeast
import androidx.compose.ui.unit.dp
import fastcampus.aop.basics_jetpack_compose.ui.theme.Basics_jetpack_composeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Basics_jetpack_composeTheme {
                // modifier로 lay out, display, behave 등 지정
                MyApp(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

// 자주 활용되는 Composable을 정의하여 재사용
@Composable
fun MyApp(modifier: Modifier = Modifier) {
    // state를 hoisting하고 callback을 내려보냄
    // 화면 회전과 같은 상황에서도 유지되는 rememberSaveable 사용
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }

    // Surface로 감싸서 배경색 지정
    // 배경색에 적절한 글자색을 자동 지정
    Surface(modifier, color = MaterialTheme.colorScheme.background) {
        if (shouldShowOnboarding) {
            OnboardingScreen(onContinueClicked = { shouldShowOnboarding = false })
        } else {
            Greetings()
        }
    }
}

@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    onContinueClicked: () -> Unit
) {
    // by로 선언하여 .value를 생략하고 접근 -> val이 아닌 var
//    var shouldShowOnboarding by remember {
//        mutableStateOf(true)
//    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome to the Basics Codelab!")
        Button(
            modifier = Modifier.padding(vertical = 24.dp),
            onClick = onContinueClicked
        ) {
            Text("Continue")
        }

    }
}

@Composable
private fun Greetings(
    modifier: Modifier = Modifier,
    names: List<String> = List(1000) { "$it" }
) {
    // 화면에 표시되는 항목만 렌더링하여 성능 개선 == RecyclerView
    LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
        items(items = names) { name ->
            Greeting(name = name)
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        CardContent(name = name)
    }
}

@Composable
private fun CardContent(name: String) {
    // (X)
    // 내부 State를 이렇게 선언하면 Compose가 탐지할 수 없음 + 매번 false로 초기화됨
    // var expanded = false

    // (O)
    // remember와 mutableStateOf를 사용해서 recomposition을 방지
    val expanded = remember { mutableStateOf(false) }

    // 애니메이션 추가
//    val extraPadding = if (expanded.value) 48.dp else 0.dp
//    val extraPadding by animateDpAsState(
//        targetValue = if (expanded.value) 48.dp else 0.dp,
//        animationSpec = spring(
//            dampingRatio = Spring.DampingRatioMediumBouncy,
//            stiffness = Spring.StiffnessLow
//        ),
//        label = ""
//    )

    Row(modifier = Modifier
        .padding(12.dp)
        // 애니메이션 자동 적용
        .animateContentSize(
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        )
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
//                .padding(bottom = extraPadding.coerceAtLeast(0.dp))
                .padding(12.dp)
        ) {
            Text(text = "Hello")
            Text(
                text = "$name!",
                // style에 수정이 필요할 때에는 기존 베이스 코드를 copy해서 사용하는 것이 좋음
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.ExtraBold
                )
            )
            
            if (expanded.value) {
                Text(text = "Composem ipsum color sit lazy, padding theme elit, sed do bouncy. ".repeat(4))
            }
        }

//            ElevatedButton(onClick = { expanded.value = !expanded.value }) {
//                Text(
//                    text = if (expanded.value)
////                        stringResource(R.string.show_less)
////                    else
////                        stringResource(R.string.show_more)
//                )
//            }
        IconButton(onClick = { expanded.value = !expanded.value }) {
            Icon(
                imageVector = if (expanded.value)
                    Icons.Filled.ExpandLess
                else
                    Icons.Filled.ExpandMore,
                contentDescription = if (expanded.value)
                    stringResource(R.string.show_less)
                else
                    stringResource(R.string.show_more)
            )
        }
    }
}

@Preview
@Composable
fun MyAppPreview() {
    Basics_jetpack_composeTheme {
        MyApp(Modifier.fillMaxSize())
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview() {
    Basics_jetpack_composeTheme {
        OnboardingScreen(onContinueClicked = {})
    }
}

// 다크 모드의 Preview 추가
@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "GreetingPreviewDark"
)
@Preview(showBackground = true, widthDp = 320)
@Composable
fun GreetingsPreview() {
    Basics_jetpack_composeTheme {
        Greetings()
    }
}