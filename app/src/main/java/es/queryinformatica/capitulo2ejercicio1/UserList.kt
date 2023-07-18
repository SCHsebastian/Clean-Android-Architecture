package es.queryinformatica.capitulo2ejercicio1

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable

fun UserList(uiState: UiState) {
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        item(uiState.count) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = uiState.count)
            }
        }
        items(uiState.userList) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = it.name)
                Text(text = it.username)
                Text(text = it.email)
            }
        }
    }
}