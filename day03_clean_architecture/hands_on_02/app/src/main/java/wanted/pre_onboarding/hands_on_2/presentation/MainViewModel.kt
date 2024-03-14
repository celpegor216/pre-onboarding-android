package wanted.pre_onboarding.hands_on_2.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import wanted.pre_onboarding.hands_on_2.DependenciesProvider
import wanted.pre_onboarding.hands_on_2.domain.model.Product
import wanted.pre_onboarding.hands_on_2.domain.usecase.AddProductUseCase
import wanted.pre_onboarding.hands_on_2.domain.usecase.GetProductsUseCase
import wanted.pre_onboarding.hands_on_2.domain.usecase.LoadProductAllUseCase
import wanted.pre_onboarding.hands_on_2.domain.usecase.RemoveLastProductUseCase

class MainViewModel(
    getProductsUseCase: GetProductsUseCase = DependenciesProvider.getProductsUseCase,
    private val loadProductAllUseCase: LoadProductAllUseCase = DependenciesProvider.loadProductAllUseCase,
    private val addProductUseCase: AddProductUseCase = DependenciesProvider.addProductUseCase,
    private val removeLastProductUseCase: RemoveLastProductUseCase = DependenciesProvider.removeLastProductUseCase
): ViewModel() {

    val products = getProductsUseCase().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    fun loadProductAll() = viewModelScope.launch {
        loadProductAllUseCase()
    }

    fun addProduct(product: Product) = viewModelScope.launch {
        addProductUseCase(product)
    }

    fun removeLastProduct() = viewModelScope.launch {
        removeLastProductUseCase()
    }
}