package br.notasocial.ui.viewmodel.consumidor.userprofile

import androidx.lifecycle.ViewModel
import br.notasocial.R
import br.notasocial.data.model.Social.Product

class FavoritesViewModel() : ViewModel() {

    val favorites = listOf(
        Product(
            1, "Pao Forma Seven Boys", R.drawable.pao_forma, true, 6.69
        ),
        Product(
            2, "Mamão Formosa", R.drawable.mamao_formosa, false, 12.50
        ),
        Product(
            3, "Arroz TP1 Buriti", R.drawable.arroz_buriti, false, 6.69
        )

    )
}