package br.notasocial.ui.utils

import java.util.Locale

fun formatPrice(value: Double) : String {
    return String.format(Locale("pt", "BR"), "R$%.2f", value)
}

fun formatPriceString(value: String) : String {
    return String.format(Locale("pt", "BR"), "R$%.2f", value.toDouble())
}

fun formatDistance(value: Double) : String {
    return String.format(Locale("en", "BR"), "%.2f - KM", value)
}