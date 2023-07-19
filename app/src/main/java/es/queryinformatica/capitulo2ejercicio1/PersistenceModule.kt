package es.queryinformatica.capitulo2ejercicio1

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "my_preferences")

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {

    @Provides
    fun provideDatabase(@ApplicationContext applicationContext: Context): AppDatabase = Room.databaseBuilder(
        applicationContext,
        AppDatabase::class.java,
        "learning_app_database"
    ).build()

    @Provides
    fun provideUserRepository(db: AppDatabase): UserDao = db.userDao()

    @Provides
    fun providesAppDataStore(@ApplicationContext context: Context) = AppDataStore(context.dataStore)

}