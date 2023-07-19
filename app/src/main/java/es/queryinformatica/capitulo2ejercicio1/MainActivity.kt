package es.queryinformatica.capitulo2ejercicio1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import es.queryinformatica.capitulo2ejercicio1.ui.theme.Capitulo2Ejercicio1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Capitulo2Ejercicio1Theme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    MainAppication(navController =navController)
                }
            }
        }
    }
}

@Composable
fun MainAppication(navController: NavHostController) {
    NavHost(navController = navController, startDestination = AppNavigation.Users.route){
        composable(AppNavigation.Users.route){
            Users(navController = navController)
        }
        composable(AppNavigation.User.route,
            arguments = listOf(navArgument(AppNavigation.User.argumentName) {
                type = NavType.StringType
            })
        ){
            User(name = it.arguments?.getString(AppNavigation.User.argumentName).orEmpty())
        }
    }
}

@Composable

fun Users(
    navController: NavController,
    viewModel: MainViewModel = viewModel(factory = MainViewModelFactory())
) {
    viewModel.uiStateLiveData.observeAsState().value?.let { uiState ->
        UserList(uiState = uiState , navController = navController)
    }
}