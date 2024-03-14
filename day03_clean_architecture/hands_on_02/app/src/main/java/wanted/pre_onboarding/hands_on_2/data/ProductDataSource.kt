package wanted.pre_onboarding.hands_on_2.data

import kotlinx.coroutines.flow.Flow
import wanted.pre_onboarding.hands_on_2.domain.model.Product

interface ProductDataSource {
    val products: Flow<List<Product>>

    suspend fun loadProductAll()
    suspend fun addProduct(product: Product)
    suspend fun removeLastProduct()
}