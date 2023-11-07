package art.muriuki.foodycourseapp.di

import android.content.Context
import androidx.room.Room
import art.muriuki.foodycourseapp.data.database.RecipesDatabase
import art.muriuki.foodycourseapp.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        RecipesDatabase::class.java,
        Constants.DATABASE_NAME
    ).build()



    @Singleton
    @Provides
    fun provideDao(database: RecipesDatabase) = database.recipesDao()
}