package br.notasocial.ui.viewmodel.consumidor.shoplist

import androidx.lifecycle.ViewModel
import br.notasocial.R
import br.notasocial.data.model.Category
import br.notasocial.data.model.Social.Product

class ShopListViewModel() : ViewModel() {

    val productsEmpty = listOf<Product>()
    val products = listOf(
        Product(
            id = "1",
            name = "Pao Forma Seven Boys",
            price = 6.69,
            category = Category(1, "Padaria", ""),
            image = R.drawable.pao_forma,
            isFavorite = true,
        ),
        Product(
            id = "2",
            name = "Pao Forma Seven Boys",
            price = 6.69,
            category = Category(1, "Padaria", ""),
            image = R.drawable.pao_forma,
            isFavorite = true,
        ),
        Product(
            id = "3",
            name = "Pao Forma Seven Boys",
            price = 6.69,
            category = Category(1, "Padaria", ""),
            image = R.drawable.pao_forma,
            isFavorite = true,
        ),
    )
}