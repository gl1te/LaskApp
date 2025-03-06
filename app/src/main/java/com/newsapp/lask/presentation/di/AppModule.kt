package com.newsapp.lask.presentation.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.newsapp.data.local.NewsDao
import com.example.newsapp.data.local.NewsDatabase
import com.example.newsapp.data.local.NewsTypeConverter
import com.newsapp.lask.data.manager.LocalUserManagerImpl
import com.newsapp.lask.data.remote.NewsApi
import com.newsapp.lask.data.repository.NewsRepositoryImpl
import com.newsapp.lask.data.utils.Constants.BASE_URL
import com.newsapp.lask.data.utils.Constants.NEWS_DATABASE_NAME
import com.newsapp.lask.domain.manager.LocalUserManager
import com.newsapp.lask.domain.repository.NewsRepository
import com.newsapp.lask.domain.usecases.NewsUseCases
import com.newsapp.lask.domain.usecases.app_entry_usecases.AppEntryUseCases
import com.newsapp.lask.domain.usecases.app_entry_usecases.ReadAppEntryUseCase
import com.newsapp.lask.domain.usecases.app_entry_usecases.SaveAppEntryUseCase
import com.newsapp.lask.domain.usecases.news_db_use_cases.DeleteArticle
import com.newsapp.lask.domain.usecases.news_db_use_cases.GetArticleByName
import com.newsapp.lask.domain.usecases.news_db_use_cases.SelectArticles
import com.newsapp.lask.domain.usecases.news_db_use_cases.UpsertArticle
import com.newsapp.lask.domain.usecases.news_usecases.GetNewsUseCase
import com.newsapp.lask.domain.usecases.news_usecases.SearchNewsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNewsApi(): NewsApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNewsRepository(newsApi: NewsApi, newsDao: NewsDao): NewsRepository {
        return NewsRepositoryImpl(newsApi, newsDao)
    }

    @Provides
    @Singleton
    fun provideAppEntryUseCases(userManager: LocalUserManager): AppEntryUseCases {
        return AppEntryUseCases(
            readAppEntry = ReadAppEntryUseCase(userManager),
            saveAppEntry = SaveAppEntryUseCase(userManager)
        )
    }

    @Provides
    @Singleton
    fun provideUserLocalManager(@ApplicationContext context: Context): LocalUserManager {
        return LocalUserManagerImpl(context)
    }

    @Provides
    @Singleton
    fun provideNewsUseCases(newsRepository: NewsRepository): NewsUseCases {
        return NewsUseCases(
            getNewsUseCase = GetNewsUseCase(newsRepository),
            searchNewsUseCase = SearchNewsUseCase(newsRepository),
            selectArticles = SelectArticles(newsRepository),
            upsertArticle = UpsertArticle(newsRepository),
            deleteArticle = DeleteArticle(newsRepository),
            getArticleByName = GetArticleByName(newsRepository),
        )
    }

    @Provides
    @Singleton
    fun provideNewsDatabase(
        application: Application,
    ): NewsDatabase {
        return Room.databaseBuilder(
            context = application,
            klass = NewsDatabase::class.java,
            name = NEWS_DATABASE_NAME
        ).addTypeConverter(typeConverter = NewsTypeConverter())
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideNewsDao(
        newsDatabase: NewsDatabase,
    ): NewsDao = newsDatabase.newsDao

}