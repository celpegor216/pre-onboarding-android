package wanted.pre_onboarding.hands_on_2.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.update
import wanted.pre_onboarding.hands_on_2.data.model.PRODUCTS_SAMPLE
import wanted.pre_onboarding.hands_on_2.data.model.Product

class MainViewModel: ViewModel() {

    private val _products = MutableStateFlow(emptyList<Product>())
    val products = _products.asStateFlow()

    fun loadProductAll() {
        _products.update { PRODUCTS_SAMPLE }
    }

    fun addProduct(product: Product) {
        _products.getAndUpdate { it + product }
    }

    fun deleteProduct() {
        _products.getAndUpdate { it.dropLast(1) }
    }
}