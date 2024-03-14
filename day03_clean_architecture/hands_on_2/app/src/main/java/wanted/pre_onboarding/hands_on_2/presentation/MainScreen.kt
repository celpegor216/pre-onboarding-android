package wanted.pre_onboarding.hands_on_2.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import wanted.pre_onboarding.hands_on_2.data.model.PRODUCTS_SAMPLE

@Composable
fun MainScreen() {
    Column {
        Column {
            Button(onClick = { /*TODO*/ }) {
                Text(text = "load product")
            }
            Button(onClick = { /*TODO*/ }) {
                Text(text = "add new product")
            }
            Button(onClick = { /*TODO*/ }) {
                Text(text = "remove first product")
            }
        }
        LazyColumn {
            items(PRODUCTS_SAMPLE) {
                Text(text = it.name)
            }
        }
    }
}