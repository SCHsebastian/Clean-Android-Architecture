package es.queryinformatica.capitulo2ejercicio1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
    private val appDataStore: AppDataStore,
    private val mainTextFormatter: MainTextFormatter
) : ViewModel() {

    private val _uiStateLiveData = MutableLiveData(UiState())
    val uiStateLiveData: LiveData<UiState> = _uiStateLiveData

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
                        UiState(
                            users,
                            mainTextFormatter.getCounterText(count)
                        )
                    }
                }
                .flowOn(Dispatchers.IO)
                .collect {
                    _uiStateLiveData.value = it
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
        MainViewModel(LearningApp.userService, LearningApp.userDao, LearningApp.appDataStore, LearningApp.mainTextFormatter) as T
}