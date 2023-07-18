package es.queryinformatica.capitulo2ejercicio1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import es.queryinformatica.capitulo2ejercicio1.ui.theme.Capitulo2Ejercicio1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Capitulo2Ejercicio1Theme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Screen()
                }
            }
        }
    }
}

@Composable

fun Screen(viewModel: MainViewModel = viewModel(factory = MainViewModelFactory())) {

    UserList(uiState = viewModel.resultState)

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Capitulo2Ejercicio1Theme {
        Screen()
    }
}