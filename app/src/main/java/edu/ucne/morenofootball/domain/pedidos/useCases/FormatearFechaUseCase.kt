package edu.ucne.morenofootball.domain.pedidos.useCases

import java.text.SimpleDateFormat
import java.util.Locale

fun formatearFecha(dateString: String): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd 'de' MMMM 'del' yyyy", Locale("es", "ES"))

        val date = inputFormat.parse(dateString)
        outputFormat.format(date)
    } catch (e: Exception) {
        dateString
    }
}