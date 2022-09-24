package com.mrmansur.demodeclarative.di

import android.app.Application
import com.mrmansur.demodeclarative.db.AppDatabase
import com.mrmansur.demodeclarative.db.TVShowDao
import com.mrmansur.demodeclarative.network.Server.IS_TESTER
import com.mrmansur.demodeclarative.network.Server.SERVER_DEVELOPMENT
import com.mrmansur.demodeclarative.network.Server.SERVER_PRODUCTION
import com.mrmansur.demodeclarative.network.TVShowService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun server() : String{
        if (IS_TESTER)  return SERVER_DEVELOPMENT
        return SERVER_PRODUCTION
    }

    @Provides
    @Singleton
    fun retrofitClient() : Retrofit{
        return Retrofit.Builder().baseUrl(server())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun tvShowService() : TVShowService{
        return retrofitClient().create(TVShowService::class.java)
    }

    @Provides
    @Singleton
    fun appDatabase(context: Application): AppDatabase {
        return AppDatabase.getAppDBInstance(context)
    }

    @Provides
    @Singleton
    fun tvShowDao(appDatabase: AppDatabase) : TVShowDao {
        return appDatabase.getTVShowDao()
    }
}