package wanted.pre_onboarding.hands_on_2.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import wanted.pre_onboarding.hands_on_2.data.model.PRODUCTS_SAMPLE
import wanted.pre_onboarding.hands_on_2.data.model.ProductModel
import wanted.pre_onboarding.hands_on_2.data.model.toDataModel
import wanted.pre_onboarding.hands_on_2.data.model.toDomainModel
import wanted.pre_onboarding.hands_on_2.domain.model.Product

class InMemoryProductDataSource : ProductDataSource {
    private val _products = MutableStateFlow(emptyList<ProductModel>())
    override val products: Flow<List<Product>> = _products.map { productList ->
        productList.map { it.toDomainModel() }
    }

    override suspend fun loadProductAll() {
        _products.update { PRODUCTS_SAMPLE }
    }

    override suspend fun addProduct(product: Product) {
        _products.getAndUpdate { it + product.toDataModel() }
    }

    override suspend fun removeLastProduct() {
        _products.getAndUpdate { it.dropLast(1) }
    }
}