package com.ms.to_dolistapp.di

import android.app.Application
import androidx.room.Room
import com.ms.to_dolistapp.data.TaskDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.supervisorScope
import javax.inject.Qualifier
import javax.inject.Singleton

/**
 * @AUTHOR: Mehedi Hasan
 * @DATE: 2/3/2021, Wed
 */
@Module
@InstallIn(ApplicationComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDatabase(
        app: Application,
        callback: TaskDatabase.Callback
    ) = Room.databaseBuilder(app, TaskDatabase::class.java, "task_database")
        .fallbackToDestructiveMigration()
        .addCallback(callback)
        .build()

    @Provides
    fun provideTaskDao(db: TaskDatabase) = db.taskDao()

    @ApplicationScope
    @Provides
    @Singleton
    //A failure or cancellation of a child does not cause the supervisor job to fail and does not affect its other children
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())


}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope()