package com.luvsoft.room.di

import android.content.Context
import androidx.room.Room
import com.luvsoft.room.network.ProfitGuardDatabase
import com.luvsoft.room.network.api.ProfitGuardApi
import com.luvsoft.room.network.api.ProfitGuardImp
import com.luvsoft.room.network.services.ProfitGuardDoo
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class DataSourceModule {
    @Singleton
    @Binds
    abstract fun bindDataSource(imp: ProfitGuardImp): ProfitGuardApi
}

//provides son solo para clases o interfaces que no me pertenecen como room
@InstallIn(SingletonComponent::class)
@Module
object RoomModule {
    @Provides
    fun provideDao(database: ProfitGuardDatabase): ProfitGuardDoo = database.userDao()

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext app: Context): ProfitGuardDatabase = Room.databaseBuilder(
        context = app,
        ProfitGuardDatabase::class.java,
        "ProfitGuardDatabase"
    ).build()
}