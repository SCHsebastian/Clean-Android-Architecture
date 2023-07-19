package es.queryinformatica.capitulo2ejercicio1

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "preferences_data_store")
class LearningApp: Application() {
    companion object {
        lateinit var userService: UserService
        lateinit var userDao: UserDao
        lateinit var appDataStore: AppDataStore
        lateinit var mainTextFormatter: MainTextFormatter
    }

    override fun onCreate() {
        super.onCreate()
        createDatabase()
        createUserService()
        createDataStore()
        createMainTextFormatter()
    }

    private fun createMainTextFormatter() {
        mainTextFormatter = MainTextFormatter(this)
    }

    private fun createDataStore() {
        appDataStore = AppDataStore(dataStore)
    }

    private fun createDatabase() {
        val database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "learning_app_database"
        ).build()

        userDao = database.userDao()
    }

    private fun createUserService() {
        val okHttpClient = OkHttpClient
            .Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com")
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
        userService = retrofit.create(UserService::class.java)
    }
}