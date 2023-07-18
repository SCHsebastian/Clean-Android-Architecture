package es.queryinformatica.capitulo2ejercicio1

import android.app.Application
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class LearningApp: Application() {
    companion object {
        lateinit var userService: UserService
        lateinit var userDao: UserDao
    }

    override fun onCreate() {
        super.onCreate()
        createDatabase()
        createUserService()
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