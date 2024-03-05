package fastcampus.aop.day01_compose_01

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fastcampus.aop.day01_compose_01.ui.theme.Day01_compose_01Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Day01_compose_01Theme {
                WtdScreen()
            }
        }
    }
}

@Composable
fun WtdScreen() {
    val initialData = (1..10).toList()
    var data by rememberSaveable { mutableStateOf(listOf<Int>()) }

    val onAddClicked = {
        data = data.toMutableList().apply {
            add(data.size + 1)
        }
    }

    val onRemoveClicked = {
        data = data.toMutableList().apply {
            if (isNotEmpty())
                removeLast()
        }
    }

    val onClearClicked = {
        data = mutableListOf()
    }

    // Screen이 보여지는 시점에 새로 설정됨
    LaunchedEffect(Unit) {
        data = initialData
    }

    Column() {
        Title("Wanted Challenge")
        ActionButtonList(onAddClicked, onRemoveClicked, onClearClicked)
        ItemList(data)
    }

}

@Composable
fun Title(name: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(Color.Blue)
    ) {
        Text(
            text = name, fontSize = 30.sp
        )
    }
}

@Composable
fun ActionButtonList(
    onAddClicked: () -> Unit,
    onRemoveClicked: () -> Unit,
    onClearClicked: () -> Unit
) {
    Row {
        ActionButton("추가", Color.Gray, onAddClicked)
        ActionButton("삭제", Color.Red, onRemoveClicked)
        ActionButton("초기화", Color.Green, onClearClicked)
    }
}

@Composable
fun ActionButton(text: String, backgroundColor: Color, onClicked: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(100.dp)
            .background(backgroundColor)
            .clickable { onClicked() }
    ) {
        Text(text = text, fontSize = 24.sp)
    }
}

@Composable
fun ItemList(data: List<Int>) {

    LazyColumn(
        modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(data) {
            Text(
                text = "Item #$it",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WtdScreenPreview() {
    Day01_compose_01Theme {
        WtdScreen()
    }
}