package br.notasocial.ui.viewmodel.customer.shoplist

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.notasocial.data.model.Catalog.Product
import br.notasocial.data.model.Social.ShoppingListItem
import br.notasocial.data.model.Social.ShoppingListRequest
import br.notasocial.data.model.Social.ShoppingListResponse
import br.notasocial.data.repository.ProductApiRepository
import br.notasocial.data.repository.UserApiRepository
import br.notasocial.data.repository.UserPreferencesRepository
import kotlinx.coroutines.launch

class ShopListViewModel(
    private val productApiRepository: ProductApiRepository,
    private val userApiRepository: UserApiRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val TAG = "ShopListViewModel"
    private var token: String = ""

    var cep by mutableStateOf("")
        private set

    var shoppingListInfo: ShoppingListResponse? by mutableStateOf(null)

    var userProductsId: List<String> by mutableStateOf(emptyList())
        private set

    var products: List<Product> by mutableStateOf(emptyList())
        private set

    private fun observeUserProducts() {
        viewModelScope.launch {
            userPreferencesRepository.currentUserProductsId.collect { products ->
                userProductsId = products
            }
        }
    }

    fun onCepChange(newCep: String) {
        cep = newCep
    }

    init {
        observeUserProducts()
        viewModelScope.launch {
            userPreferencesRepository.currentUserData.collect { user ->
                if (user.token.isNotEmpty()) {
                    token = user.token
                    getProductsDetails(token, userProductsId)
                    addProductsToShoppingList(token, userProductsId)
                }
            }
        }
    }

    fun calculateDistance() {
        viewModelScope.launch {
            try {
                val response = userApiRepository.calculateDistance(token, cep)
                if (response.isSuccessful) {
                    shoppingListInfo = response.body()
                } else {
                    Log.e(TAG, "calculateDistance: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "calculateDistance: ${e.message}")
            }
        }
    }

    fun getProductsDetails(token: String, userProductsId: List<String>) {
        viewModelScope.launch {
            try {
                val response = productApiRepository.getProductsDetails(token, userProductsId)
                if (response.isSuccessful) {
                    products = response.body()?.products!!
                } else {
                    Log.e(TAG, "getProductsDetails: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "getProductsDetails: ${e.message}")
            }
        }
    }

    private fun addProductsToShoppingList(token: String, userProductsId: List<String>) {
        val shoppingListItems: ArrayList<ShoppingListItem> = ArrayList()
        userProductsId.forEach { productId ->
            shoppingListItems.add(addProduct(productId))
        }

        viewModelScope.launch {
            try {
                val response = userApiRepository.addProductsToShoppingList(
                    token, ShoppingListRequest(
                        products = shoppingListItems
                    )
                )
                if (response.isSuccessful) {
                    Log.i(TAG, "addProductsToShoppingList success: $response")
                } else {
                    Log.e(TAG, "addProductsToShoppingList else: $response")
                }
            } catch (e: Exception) {
                Log.e(TAG, "addProductsToShoppingList when: ${e.message}")
            }
        }
    }

    private fun addProduct(productId: String): ShoppingListItem {
        return ShoppingListItem(
            productId = productId,
        )
    }

    private fun removeProduct(productId: String): ShoppingListItem {
        return ShoppingListItem(
            productId = productId,
            quantity = 100
        )
    }

    fun removeProductsToShoppingList(productId: String) {

        viewModelScope.launch {
            val shoppingListItems: ArrayList<ShoppingListItem> = ArrayList()
            shoppingListItems.add(removeProduct(productId))

            try {
                products = products.filter { it.id != productId }
                userPreferencesRepository.removeProductId(productId)

                Log.i(TAG, "removeProduct: Produto $productId removido com sucesso.")
                Log.i(TAG, "token: $token")
                Log.i(TAG, "Product: $productId")
                val response = userApiRepository.removeProductFromShoppingList(
                    token, ShoppingListRequest(
                        products = shoppingListItems
                    )
                )
                if (response.isSuccessful) {
                    Log.i(TAG, "removeProductsToShoppingList success: $response")
                } else {
                    Log.e(TAG, "removeProductsToShoppingList else: $response")
                }
            } catch (e: Exception) {
                Log.e(TAG, "removeProductsToShoppingList when: ${e.message}")
            }
        }
    }
}
