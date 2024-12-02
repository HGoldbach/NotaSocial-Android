package br.notasocial.ui.utils

fun textTitleCase(value: String) : String {
    return value.lowercase()
        .split(" ")
        .joinToString(" ") { word ->
            word.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
        }
}