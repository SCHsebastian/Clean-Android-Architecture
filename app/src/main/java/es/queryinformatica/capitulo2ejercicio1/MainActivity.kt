package es.queryinformatica.capitulo2ejercicio1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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

fun Screen(viewModel: MainViewModel = viewModel()) {

    var a by remember { mutableStateOf("") }
    var b by remember { mutableStateOf("") }

    Calculator(
        a = a, onAChanged = { a = it },
        b = b, onBChanged = { b = it },
        result = viewModel.resultState,
        onButtonClick = { viewModel.add(a, b) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun Calculator(a: String, onAChanged: (String) -> Unit, b: String, onBChanged: (String) -> Unit, result: String, onButtonClick: () -> Unit, ) {
    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = a,
            onValueChange = onAChanged,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text("a") }
        )
        OutlinedTextField(
            value = b,
            onValueChange = onBChanged,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text("b") }
        )
        Text(text = result)
        Button(onClick = onButtonClick) {
            Text(text = "Calculate")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Capitulo2Ejercicio1Theme {
        Screen()
    }
}