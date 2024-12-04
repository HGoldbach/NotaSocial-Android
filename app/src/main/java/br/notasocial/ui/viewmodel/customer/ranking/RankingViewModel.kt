package br.notasocial.ui.viewmodel.customer.ranking

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.notasocial.data.model.Social.Ranking
import br.notasocial.data.repository.UserApiRepository
import kotlinx.coroutines.launch

class RankingViewModel(
    private val userApiRepository: UserApiRepository
) : ViewModel() {


    var ranking: Ranking by mutableStateOf(Ranking())
        private set

    init {
        getRanking()
    }

    private fun getRanking() {
        viewModelScope.launch {
            try {
                val response = userApiRepository.getRanking()
                if (response.isSuccessful) {
                    ranking = response.body()!!
                } else {
                    Log.e("RankingViewModel", "Error getting ranking: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("RankingViewModel", "Error getting ranking", e)
            }
        }
    }
}
