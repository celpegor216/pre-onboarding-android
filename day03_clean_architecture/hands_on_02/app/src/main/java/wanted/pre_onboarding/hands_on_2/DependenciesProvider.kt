package wanted.pre_onboarding.hands_on_2

import wanted.pre_onboarding.hands_on_2.data.InMemoryProductDataSource
import wanted.pre_onboarding.hands_on_2.data.ProductDataSource
import wanted.pre_onboarding.hands_on_2.data.ProductRepositoryImpl
import wanted.pre_onboarding.hands_on_2.domain.ProductRepository
import wanted.pre_onboarding.hands_on_2.domain.usecase.AddProductUseCase
import wanted.pre_onboarding.hands_on_2.domain.usecase.GetProductsUseCase
import wanted.pre_onboarding.hands_on_2.domain.usecase.LoadProductAllUseCase
import wanted.pre_onboarding.hands_on_2.domain.usecase.RemoveLastProductUseCase

object DependenciesProvider {
    private val productDataSource: ProductDataSource = InMemoryProductDataSource()
    private val productRepository: ProductRepository = ProductRepositoryImpl(productDataSource)
    val getProductsUseCase: GetProductsUseCase = GetProductsUseCase(productRepository)
    val loadProductAllUseCase: LoadProductAllUseCase = LoadProductAllUseCase(productRepository)
    val addProductUseCase: AddProductUseCase = AddProductUseCase(productRepository)
    val removeLastProductUseCase: RemoveLastProductUseCase = RemoveLastProductUseCase(productRepository)
}