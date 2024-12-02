package br.notasocial.ui.utils

fun formatPhoneNumber(phone: String): String {
    return "(${phone.substring(0, 2)}) ${phone.substring(2, 6)}-${phone.substring(6)}"
}

fun formatZipCode(zipCode: String): String {
    return "${zipCode.substring(0, 5)}-${zipCode.substring(5)}"
}