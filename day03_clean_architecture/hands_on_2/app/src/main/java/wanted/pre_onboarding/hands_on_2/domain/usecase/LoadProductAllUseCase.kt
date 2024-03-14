package wanted.pre_onboarding.hands_on_2.domain.usecase

import wanted.pre_onboarding.hands_on_2.domain.ProductRepository

class LoadProductAllUseCase(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke() {
        productRepository.loadProductAll()
    }
}