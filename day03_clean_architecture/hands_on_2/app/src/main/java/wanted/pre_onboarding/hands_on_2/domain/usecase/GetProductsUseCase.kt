package wanted.pre_onboarding.hands_on_2.domain.usecase

import wanted.pre_onboarding.hands_on_2.domain.ProductRepository

class GetProductsUseCase(
    private val productRepository: ProductRepository
) {
    operator fun invoke() = productRepository.products
}