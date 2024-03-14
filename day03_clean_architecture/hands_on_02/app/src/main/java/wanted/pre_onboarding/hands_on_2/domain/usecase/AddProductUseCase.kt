package wanted.pre_onboarding.hands_on_2.domain.usecase

import wanted.pre_onboarding.hands_on_2.domain.ProductRepository
import wanted.pre_onboarding.hands_on_2.domain.model.Product

class AddProductUseCase(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(product: Product) {
        productRepository.addProduct(product)
    }
}