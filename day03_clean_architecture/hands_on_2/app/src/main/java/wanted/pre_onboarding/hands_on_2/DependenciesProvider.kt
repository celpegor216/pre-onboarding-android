package wanted.pre_onboarding.hands_on_2

import wanted.pre_onboarding.hands_on_2.data.ProductRepositoryImpl
import wanted.pre_onboarding.hands_on_2.domain.ProductRepository

object DependenciesProvider {
    val productRepository: ProductRepository = ProductRepositoryImpl()
}