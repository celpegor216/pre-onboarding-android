package wanted.pre_onboarding.hands_on_2.data

import kotlinx.coroutines.flow.Flow
import wanted.pre_onboarding.hands_on_2.domain.model.Product
import wanted.pre_onboarding.hands_on_2.domain.ProductRepository

class ProductRepositoryImpl(
    private val productDataSource: ProductDataSource
): ProductRepository {

    override val products: Flow<List<Product>> = productDataSource.products

    override suspend fun loadProductAll() {
        productDataSource.loadProductAll()
    }

    override suspend fun addProduct(product: Product) {
        productDataSource.addProduct(product)
    }

    override suspend fun removeLastProduct() {
        productDataSource.removeLastProduct()
    }
}