package wanted.pre_onboarding.hands_on_2.domain.model

import kotlin.random.Random

// 네트워크나 DB를 통해 받아온 데이터를 가공한 형태의 데이터
data class Product(
    val id: String,
    val name: String,
    val price: Int
)

fun product(): Product = Product(
    id = "p${Random.nextInt()}",
    name = "product-${Random.nextInt()}",
    price = Random.nextInt()
)