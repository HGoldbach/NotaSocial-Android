package br.notasocial.ui.viewmodel.store

import br.notasocial.data.model.StoreDb.AddressDb

data class AddressUiState(
    val street: String = "",
    val number: String = "",
    val complement: String = "",
    val neighborhood: String = "",
    val city: String = "",
    val state: String = "",
    val cep: String = "",
    val telephone: String = "",
    val isCepValid: Boolean = true,
    val addresses: List<AddressDb> = emptyList()
)
