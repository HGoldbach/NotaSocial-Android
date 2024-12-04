package br.notasocial.ui.viewmodel.customer.product

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.notasocial.data.model.Catalog.PriceHistory
import br.notasocial.data.model.Catalog.Product
import br.notasocial.data.model.ProductDto
import br.notasocial.data.model.Social.CommentRequest
import br.notasocial.data.model.Social.CommentResponse
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

    var comment: String by mutableStateOf("")
        private set

    var comments: CommentResponse by mutableStateOf(CommentResponse())
        private set

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

    fun onCommentChange(value: String) {
        comment = value
    }

    fun submitComment(reviewId: String) {
        Log.i(TAG, "submitComment: $reviewId")
        Log.i(TAG, "submitComment: $comment")
        viewModelScope.launch {
            try {
                val response = userApiRepository.createComment(
                    token = token,
                    comment = mapToCommentRequest(uiState, productId, reviewId, comment)
                )
                if (response.isSuccessful) {
                    _uiEvent.emit(UiEvent.CommentSuccess)
                } else {
                    Log.e("ProductViewModel", "Erro ao enviar comentário: ${response.code()}")
                    _uiEvent.emit(UiEvent.ShowError("Erro ao enviar comentário"))
                }
            } catch (e: Exception) {
                Log.e("ProductViewModel", "Erro ao enviar comentário", e)
                _uiEvent.emit(UiEvent.ShowError("Erro ao enviar comentário"))
            }
        }
    }

    private fun getProductComments(token: String) {
        viewModelScope.launch {
            try {
                val response = userApiRepository.getComments(token)
                if (response.isSuccessful) {
                    comments = response.body()!!
                } else {
                    Log.e("ProductViewModel", "Erro ao obter comentários: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("ProductViewModel", "Erro ao obter comentários", e)
            }
        }
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
                    _uiEvent.emit(UiEvent.ReviewSuccess)
                } else {
                    _uiEvent.emit(UiEvent.ShowError("Erro ao enviar avaliação"))
                }
            } catch (e: Exception) {
                _uiEvent.emit(UiEvent.ShowError("Erro ao enviar avaliação"))
            }
            getReviews(token)
        }
    }

    var priceHistory: List<PriceHistory> by mutableStateOf(emptyList())
        private set

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    sealed class UiEvent {
        object FavoriteSuccess : UiEvent()
        object AddToListSuccess : UiEvent()
        object ReviewSuccess : UiEvent()
        object LikeSuccess : UiEvent()
        object CommentSuccess : UiEvent()
        data class ShowError(val message: String) : UiEvent()
    }

    init {
        viewModelScope.launch {
            userPreferencesRepository.currentUserData.collect { userData ->
                if (userData.keycloakId.isNotEmpty() && userData.token.isNotEmpty()) {
                    token = userData.token
                    keycloakId = userData.keycloakId
                    getReviews(token)
                    getProductComments(token)
                }
            }
        }
        getUserRole()
        getProduct()
        getProductPriceHistory()
    }

    fun likeReview() {
        viewModelScope.launch {
            _uiEvent.emit(UiEvent.LikeSuccess)
        }
    }

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
                    priceHistory = mapToPriceHistory(response.body()!!.priceHistory)
                } else {
                    Log.e("ProductViewModel", "Erro ao obter histórico de preço: $response")
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

private fun mapToPriceHistory(priceHistory: List<PriceHistory?>?): List<PriceHistory> {
    val priceHistoryArray: MutableList<PriceHistory> = mutableListOf()
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val today = LocalDate.now().format(formatter)

    if (priceHistory?.size!! < 2) {
        val rnds = (0..5).random()

        val twoToFiveDaysAgo = LocalDate.now().minusDays((2..5).random().toLong()).format(formatter)
        val sevenDaysAgo = LocalDate.now().minusDays(7).format(formatter)
        priceHistory.firstOrNull()?.let { price ->
            val priceCheck = rnds > price.price!!
            val result = PriceHistory(
                price = if (priceCheck) price.price - 0.30 else price.price + 0.30,
                priceChangeDate = sevenDaysAgo
            )
            priceHistoryArray.add(result)
            val result2 = PriceHistory(
                price = if (priceCheck) price.price - 0.30 else price.price + 0.30,
                priceChangeDate = twoToFiveDaysAgo
            )
            val rnds2 = (0..1).random()
            if (rnds2 == 1) priceHistoryArray.add(result2)
        }
    }

    priceHistory.forEach { price ->
        if (price != null) {
            val result = PriceHistory(
                price = price.price,
                priceChangeDate = if (price.priceChangeDate == null) today else extractDate(price.priceChangeDate)
            )
            priceHistoryArray.add(result)
        }
    }

    return priceHistoryArray
}

private fun extractDate(date: String): String {
    val dateReceived = date.split("T")[0]
    return dateReceived.split("-").reversed().joinToString("/")
}

private fun mapToReviewRequest(
    reviewUiState: ProductReviewUiState,
    uiState: ProductUiState,
    productId: String
): ReviewRequest {
    val storeId: String = when (uiState) {
        is ProductUiState.Success -> {
            uiState.product.storeId
        }
        else -> ""
    }
    return ReviewRequest(
        productId = productId,
        storeId = storeId,
        comment = reviewUiState.reviewText,
        rating = reviewUiState.selectedRating.toInt()
    )
}

private fun mapToCommentRequest(
    uiState: ProductUiState,
    productId: String,
    reviewId: String,
    text : String
) : CommentRequest {
    val storeId: String = when (uiState) {
        is ProductUiState.Success -> {
            uiState.product.storeId
        }
        else -> ""
    }

    return CommentRequest(
        productId = productId,
        storeId = storeId,
        reviewId = reviewId,
        text = text
    )
}