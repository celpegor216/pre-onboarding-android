package wanted.pre_onboarding.hands_on_2.domain.usecase

import wanted.pre_onboarding.hands_on_2.domain.ProductRepository

class RemoveLastProductUseCase(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke() {
        productRepository.removeLastProduct()
    }
}