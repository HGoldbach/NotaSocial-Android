package br.notasocial.ui.viewmodel.consumidor.profile

import androidx.lifecycle.ViewModel
import br.notasocial.R
import br.notasocial.data.model.Social.Product
import br.notasocial.data.model.Social.Review

class ProfileViewModel(

) : ViewModel() {

    val reviews = listOf(
        Review(
            id = 1,
            product = Product("1", "Pão Forma Seven Boys", R.drawable.pao_forma, null, emptyList(),false, 10.0),
            stars = 2,
            comment = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec sagittis Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec sagittis Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec sagittis Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec sagittis Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec sagittis"
        ),
        Review(
            id = 2,
            product = Product("2", "Mamao Formosa", R.drawable.mamao_formosa, null, emptyList(),false, 10.0),
            stars = 3,
            comment = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec sagittis Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec sagittis Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec sagittis Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec sagittis Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec sagittis"
        ),
        Review(
            id = 3,
            product = Product("3", "Arroz Buriti", R.drawable.arroz_buriti, null, emptyList(), false, 10.0),
            stars = 1,
            comment = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec sagittis Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec sagittis Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec sagittis Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec sagittis Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec sagittis"
        )
    )
}