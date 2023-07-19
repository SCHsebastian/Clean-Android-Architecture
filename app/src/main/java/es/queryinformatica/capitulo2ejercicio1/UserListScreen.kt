package es.queryinformatica.capitulo2ejercicio1

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable

fun UserList(uiState: UiState, navController: NavController) {
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        item(uiState.count) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = uiState.count)
            }
        }
        items(uiState.userList) {
            Column(modifier = Modifier
                .padding(16.dp)
                .clickable {
                    navController.navigate(AppNavigation.User.routeForName(it.name))
                }
            ) {
                Text(text = it.name)
                Text(text = it.username)
                Text(text = it.email)
            }
        }
    }
}