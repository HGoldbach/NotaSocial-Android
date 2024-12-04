package br.notasocial.ui.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

fun formatPhoneNumber(phone: String): String {
    return "(${phone.substring(0, 2)}) ${phone.substring(2, 6)}-${phone.substring(6)}"
}

fun formatZipCode(zipCode: String): String {
    return "${zipCode.substring(0, 5)}-${zipCode.substring(5)}"
}

fun formatDate(dateString: String) : String {
    if (dateString.isBlank() || dateString.length != 8) {
        return ""
    }

    return try {
        val day = dateString.substring(0, 2)
        val month = dateString.substring(2, 4)
        val year = dateString.substring(4, 8)

        val formattedDate = "$day/$month/$year"

        val inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale("pt", "BR"))
        val date = LocalDate.parse(formattedDate, inputFormatter)
        val outputFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale("pt", "BR"))
        date.format(outputFormatter)
    } catch (e: Exception) {
        ""
    }
}