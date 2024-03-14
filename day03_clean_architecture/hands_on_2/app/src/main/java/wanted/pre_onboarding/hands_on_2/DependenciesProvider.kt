package wanted.pre_onboarding.hands_on_2

import wanted.pre_onboarding.hands_on_2.data.InMemoryProductDataSource
import wanted.pre_onboarding.hands_on_2.data.ProductDataSource
import wanted.pre_onboarding.hands_on_2.data.ProductRepositoryImpl
import wanted.pre_onboarding.hands_on_2.domain.ProductRepository

object DependenciesProvider {
    private val productDataSource: ProductDataSource = InMemoryProductDataSource()
    val productRepository: ProductRepository = ProductRepositoryImpl(productDataSource)
}