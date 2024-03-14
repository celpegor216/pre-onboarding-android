package wanted.pre_onboarding.hands_on_2.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import wanted.pre_onboarding.hands_on_2.DependenciesProvider
import wanted.pre_onboarding.hands_on_2.data.model.Product
import wanted.pre_onboarding.hands_on_2.domain.ProductRepository

class MainViewModel(
    private val productRepository: ProductRepository = DependenciesProvider.productRepository
): ViewModel() {

    val products = productRepository.products.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    fun loadProductAll() = viewModelScope.launch {
        productRepository.loadProductAll()
    }

    fun addProduct(product: Product) = viewModelScope.launch {
        productRepository.addProduct(product)
    }

    fun deleteProduct() = viewModelScope.launch {
        productRepository.removeLastProduct()
    }
}