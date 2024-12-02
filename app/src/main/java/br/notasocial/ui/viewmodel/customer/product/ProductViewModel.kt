package br.notasocial.ui.viewmodel.customer.product

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.notasocial.data.model.Catalog.CatalogPriceHistory
import br.notasocial.data.model.Catalog.PriceHistory
import br.notasocial.data.model.Catalog.Product
import br.notasocial.data.model.ProductDto
import br.notasocial.data.model.Social.Review
import br.notasocial.data.model.Social.ReviewRequest
import br.notasocial.data.model.User.StoreBranch
import br.notasocial.data.repository.ProductApiRepository
import br.notasocial.data.repository.StoreApiRepository
import br.notasocial.data.repository.UserApiRepository
import br.notasocial.data.repository.UserPreferencesRepository
import br.notasocial.ui.view.customer.product.ProductDestination
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.random.Random

sealed interface ProductUiState {
    data class Success(val product: ProductDto) : ProductUiState
    data class Error(val message: String) : ProductUiState
    data object Loading : ProductUiState
}

data class ProductReviewUiState(
    val selectedRating: String = "1",
    val reviewText: String = "",
)

class ProductViewModel(
    savedStateHandle: SavedStateHandle,
    private val productApiRepository: ProductApiRepository,
    private val userPreferencesRepository: UserPreferencesRepository,
    private val userApiRepository: UserApiRepository,
    private val storeApiRepository: StoreApiRepository,
) : ViewModel() {

    private val TAG = "ProductViewModel"

    private val productId: String = checkNotNull(savedStateHandle[ProductDestination.produtoIdArg])
    var uiState: ProductUiState by mutableStateOf(ProductUiState.Loading)
        private set

    private var token: String = ""
    private var keycloakId: String = ""
    var userRole: String = ""

    var reviewUiState: ProductReviewUiState by mutableStateOf(ProductReviewUiState())
        private set

    var reviews: List<Review> by mutableStateOf(emptyList())
        private set

    var relatedProducts: List<Product> by mutableStateOf(emptyList())
        private set

    var storeBranch: StoreBranch by mutableStateOf(StoreBranch())
        private set

    fun updateRating(selectedRating: String) {
        reviewUiState = reviewUiState.copy(selectedRating = selectedRating)
    }

    fun updateReviewText(reviewText: String) {
        reviewUiState = reviewUiState.copy(reviewText = reviewText)
    }

    fun submitReview() {
        viewModelScope.launch {
            if (userRole != "CUSTOMER") {
                _uiEvent.emit(UiEvent.ShowError("Você não tem permissão para avaliar produtos. Crie uma conta, ou faça login!"))
                return@launch
            }
            try {
                val response = productApiRepository.reviewProduct(
                    productId = productId,
                    token = token,
                    reviewRequest = mapToReviewRequest(reviewUiState, uiState, productId))
                if (response.isSuccessful) {
                    Log.d("ProductViewModel", "Review enviada com sucesso")
                    Log.d("ProductViewModel", "Token: $token")
                } else {
                    Log.e("ProductViewModel", "Erro ao enviar review: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("ProductViewModel", "Erro ao enviar review", e)
            }
        }
    }

    var priceHistory: List<PriceHistory> by mutableStateOf(emptyList())
        private set

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    sealed class UiEvent {
        object FavoriteSuccess : UiEvent()
        object AddToListSuccess : UiEvent()
        data class ShowError(val message: String) : UiEvent()
    }

    init {
        viewModelScope.launch {
            userPreferencesRepository.currentUserData.collect { userData ->
                if (userData.keycloakId.isNotEmpty() && userData.token.isNotEmpty()) {
                    token = userData.token
                    keycloakId = userData.keycloakId
                    getReviews(token)
                }
            }
        }
        getUserRole()
        getProduct()
        getProductPriceHistory()
    }


    val priceHistoryList = listOf(
        PriceHistory(price = Random.nextDouble(2.00, 5.00), priceChangeDate = "01/01/2024"),
        PriceHistory(price = Random.nextDouble(2.00, 5.00), priceChangeDate = "02/01/2024"),
        PriceHistory(price = Random.nextDouble(2.00, 5.00), priceChangeDate = "03/01/2024"),
        PriceHistory(price = Random.nextDouble(2.00, 5.00), priceChangeDate = "04/01/2024"),
        PriceHistory(price = Random.nextDouble(2.00, 5.00), priceChangeDate = "05/01/2024"),
    )

    private fun getBranchStore() {
        when (val uiState = uiState) {
            is ProductUiState.Success -> viewModelScope.launch {
                try {
                    val response = storeApiRepository.getBranchStore(uiState.product.storeId)
                    if (response.isSuccessful) {
                        storeBranch = response.body()!!
                    } else {
                        Log.e("ProductViewModel", "Erro ao obter loja: ${response.code()}")
                    }
                } catch (e: Exception) {
                    Log.e("ProductViewModel", "Erro ao obter loja", e)
                }
            }
            else -> Log.e("ProductViewModel", "Erro ao obter loja else when")
        }
    }

    private fun getProduct() {
        viewModelScope.launch {
            uiState = ProductUiState.Loading
            uiState = try {
                val response = productApiRepository.getProductById(productId)
                if (response.isSuccessful) {
                    ProductUiState.Success(product = response.body()!!)
                } else {
                    ProductUiState.Error(response.message())
                }
            } catch (e: IOException) {
                ProductUiState.Error(e.message.toString())
            } catch (e: HttpException) {
                ProductUiState.Error(e.message.toString())
            }
            getRelatedProducts()
            getBranchStore()
        }
    }

    private fun getReviews(token: String) {
        viewModelScope.launch {
            try {
                val response = userApiRepository.getReviews(token, productId)
                if(response.isSuccessful) {
                    reviews = response.body()!!
                    Log.d("ProductViewModel", "Reviews obtidas com sucesso $reviews")
                } else {
                    Log.e("ProductViewModel", "Erro ao obter reviews: ${response.code()}")
                }
            } catch (e: IOException) {
                Log.e("ProductViewModel", "Erro ao obter reviews: ${e.message}")
            } catch (e: HttpException) {
                Log.e("ProductViewModel", "Erro ao obter reviews: ${e.message}")
            }
        }
    }



    private fun getProductPriceHistory() {
        viewModelScope.launch {
            try {
                val response = productApiRepository.getPriceHistory(productId)
                if (response.isSuccessful) {
                    priceHistory = mapToPriceHistory(response.body()!!)
                    Log.d("ProductViewModel", "Histórico de preço obtido com sucesso $priceHistory")
                } else {
                    Log.e("ProductViewModel", "Erro ao obter histórico de preço: ${response}")
                }
            } catch (e: IOException) {
                ProductUiState.Error(e.message.toString())
            } catch (e: HttpException) {
                ProductUiState.Error(e.message.toString())
            }
        }
    }

    fun favoriteProduct(id: String) {
        viewModelScope.launch {
            try {
                if (userRole != "CUSTOMER") {
                    _uiEvent.emit(UiEvent.ShowError("Você não tem permissão para favoritar produtos. Crie uma conta, ou faça login!"))
                    return@launch
                }
                val response = productApiRepository.favoriteProduct(token, id)
                if (response.isSuccessful) {
                    Log.d("ProductViewModel", "Produto favoritado com sucesso")
                    _uiEvent.emit(UiEvent.FavoriteSuccess)
                } else {
                    _uiEvent.emit(UiEvent.ShowError("Erro ao favoritar produto"))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _uiEvent.emit(UiEvent.ShowError("Erro ao favoritar produto"))
            }
        }
    }

    private fun getUserRole() {
        viewModelScope.launch {
            userPreferencesRepository.currentUserData.collect { user ->
                userRole = user.role
            }
        }
    }

    private fun getRelatedProducts() {
        when (val uiState = uiState) {
            is ProductUiState.Success -> viewModelScope.launch {
                try {
                    Log.d("ProductViewModel", "Obtendo produtos relacionados ${uiState.product}")
                    val response = productApiRepository.searchProduct(uiState.product.name.split(" ")[0])
                    if (response.isSuccessful) {
                        relatedProducts = response.body()!!.products
                        Log.d("ProductViewModel", "Produtos relacionados obtidos com sucesso $relatedProducts")
                    } else {
                        Log.e("ProductViewModel", "Erro ao obter produtos relacionados else")
                    }
                } catch (e: Exception) {
                    Log.e("ProductViewModel", "Erro ao obter produtos relacionados", e)
                }
            }
            else -> Log.e("ProductViewModel", "Erro ao obter produtos relacionados else when")
        }
    }

    fun addProductToUser(productId: String) {
        Log.i(TAG, "addProductToUser: $productId")
        viewModelScope.launch {
            if (userRole != "CUSTOMER") {
                _uiEvent.emit(UiEvent.ShowError("Você não tem permissão para adicionar produtos. Crie uma conta, ou faça login!"))
                return@launch
            }
            try {
                userPreferencesRepository.addProductId(productId)
                _uiEvent.emit(UiEvent.AddToListSuccess)
            } catch (e: Exception) {
                _uiEvent.emit(UiEvent.ShowError("Erro ao adicionar produto"))
            }
        }
    }
}

private fun mapToPriceHistory(catalog: CatalogPriceHistory): List<PriceHistory> {
    val priceHistory: ArrayList<PriceHistory> = arrayListOf()
    Log.e("ProductViewModel", "Catalog: $catalog")

    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    if (catalog.priceHistory?.size == 1) {
        val sevenDaysAgo = LocalDate.now().minusDays(7).format(formatter)
        catalog.priceHistory.firstOrNull()?.let { price ->
            val result = PriceHistory(
                price = price.price,
                priceChangeDate = sevenDaysAgo
            )
            priceHistory.add(result)
        }
    }

    catalog.priceHistory?.forEach { price ->
        if (price != null) {
            val today = LocalDate.now().format(formatter)

            val result = PriceHistory(
                price = price.price,
                priceChangeDate = if (price.priceChangeDate == null) today else extractDate(price.priceChangeDate)
            )
            Log.e("ProductViewModel", "PriceHistory: $result")
            priceHistory.add(result)
        }
    }

    return priceHistory.toList()
}

private fun extractDate(date: String): String {
    // Recebo  - 2024-11-17T19:38:04
    // Preciso - 17/11
    val date = date.split("T")[0]
    return date.split("-").reversed().joinToString("/")

}

private fun mapToReviewRequest(
    reviewUiState: ProductReviewUiState,
    uiState: ProductUiState,
    productId: String
): ReviewRequest {
    return ReviewRequest(
        productId = productId,
        storeId = "cc26aaa8-8954-4d6b-ab77-d1867d02ef9e",
        comment = reviewUiState.reviewText,
        rating = reviewUiState.selectedRating.toInt()
    )
}