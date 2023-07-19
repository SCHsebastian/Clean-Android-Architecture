package es.queryinformatica.capitulo2ejercicio1

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun User (name: String) {
    Column {
        Text(text = name)
    }
}