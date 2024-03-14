package wanted.pre_onboarding.hands_on_2.domain

import kotlinx.coroutines.flow.Flow
import wanted.pre_onboarding.hands_on_2.domain.model.Product

interface ProductRepository {
    val products: Flow<List<Product>>

    suspend fun loadProductAll()
    suspend fun addProduct(product: Product)
    suspend fun removeLastProduct()
}