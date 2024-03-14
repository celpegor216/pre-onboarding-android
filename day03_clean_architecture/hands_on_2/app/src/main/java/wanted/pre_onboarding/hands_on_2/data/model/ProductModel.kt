package wanted.pre_onboarding.hands_on_2.data.model

import wanted.pre_onboarding.hands_on_2.domain.model.Product
import java.util.Date

// 네트워크나 DB를 통해 받아오는 데이터
data class ProductModel(
    val id: String,
    val name: String,
    val price: Int,
    val description: String,
    val createdAt: Date,
    val updatedAt: Date
)

fun ProductModel.toDomainModel(): Product = Product(
    id = this.id,
    name = this.name,
    price = this.price
)

fun Product.toDataModel(): ProductModel = ProductModel(
    id = this.id,
    name = this.name,
    price = this.price,
    description = "",
    createdAt = Date(),
    updatedAt = Date()
)

val PRODUCTS_SAMPLE = listOf(
    ProductModel("p1", "product-1", 1, "desc-1", Date(), Date()),
    ProductModel("p2", "product-2", 2, "desc-2", Date(), Date()),
    ProductModel("p3", "product-3", 3, "desc-3", Date(), Date()),
    ProductModel("p4", "product-4", 4, "desc-4", Date(), Date()),
    ProductModel("p5", "product-5", 5, "desc-5", Date(), Date()),
)