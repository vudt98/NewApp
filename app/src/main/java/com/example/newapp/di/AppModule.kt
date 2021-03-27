package com.example.newapp.di

import android.app.Application
import androidx.room.Room
import com.example.newapp.api.NewsApi
import com.example.newapp.data.NewsArticleDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit() : Retrofit =
        Retrofit.Builder()
            .baseUrl(NewsApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideNewApi(retrofit: Retrofit): NewsApi =
        retrofit.create(NewsApi::class.java)

    fun provideDatabase(app: Application): NewsArticleDatabase =
        Room.databaseBuilder(app, NewsArticleDatabase::class.java, "new_article_databse")
            .fallbackToDestructiveMigration()
            .build()
}