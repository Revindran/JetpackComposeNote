package com.raveendran.composenote.di

import android.content.Context
import androidx.room.Room
import com.raveendran.composenote.Util.Constants.database_name
import com.raveendran.composenote.db.NoteDao
import com.raveendran.composenote.db.NoteDatabase
import com.raveendran.composenote.repository.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providePhotoDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        NoteDatabase::class.java,
        database_name
    )
        .fallbackToDestructiveMigration()
        .allowMainThreadQueries()
        .build()

    @Singleton
    @Provides
    fun providePhotoDao(
        database: NoteDatabase
    ) = database.noteDao()

    @Singleton
    @Provides
    fun provideRepository(
        dao: NoteDao
    ): NoteRepository {
        return NoteRepository(dao)
    }
}