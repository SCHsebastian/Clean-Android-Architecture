package es.queryinformatica.capitulo2ejercicio1

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModel(
    private val userService: UserService,
    private val userDao: UserDao,
    private val appDataStore: AppDataStore
) : ViewModel() {

    var resultState by mutableStateOf(UiState())
        private set

    init {
        viewModelScope.launch {
            flow { emit(userService.getUsers()) }
                .onEach {
                    val userEntities = it.map { user -> UserEntity(user.id, user.name,user.username, user.email) }
                    userDao.insertUsers(userEntities)
                    appDataStore.incrementCount()
                }.flatMapConcat { userDao.getUsers() }
                .catch { emitAll(userDao.getUsers()) }
                .flatMapConcat { users ->
                    appDataStore.savedCount.map { count ->
                        UiState(users, count.toString()) }
                }
                .flowOn(Dispatchers.IO)
                .collect {
                    resultState = it
                }
        }
    }
}

data class UiState(
    val userList: List<UserEntity> = listOf(),
    val count: String = ""
)

class MainViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        MainViewModel(LearningApp.userService, LearningApp.userDao, LearningApp.appDataStore) as T
}