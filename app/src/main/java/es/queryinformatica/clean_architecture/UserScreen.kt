package es.queryinformatica.clean_architecture

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun User (name: String) {
    Column {
        Text(text = name)
    }
}