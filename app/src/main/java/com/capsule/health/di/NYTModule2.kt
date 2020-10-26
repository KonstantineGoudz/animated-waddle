package com.capsule.health.di

import com.capsule.health.BuildConfig
import com.capsule.health.apis.NYTApi
import com.capsule.health.repositories.NYTRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NYTBaseUrl

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NYTApiKey

@Module
@InstallIn(ApplicationComponent::class)
class NYTModule {

    @Provides
    @NYTBaseUrl
    fun provideBaseUrl(): String {
        return "https://api.nytimes.com/svc/"
    }

    @Provides
    @NYTApiKey
    fun provideNYTApiKey(): String =  BuildConfig.NYTApiKey

    @Provides
    fun provideRetrofit(@NYTBaseUrl baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideNYTConceptService(retrofit: Retrofit): NYTApi = retrofit.create(NYTApi::class.java)

    @Provides
    @Singleton
    fun provideNytRepository(nytApi: NYTApi, @NYTApiKey token: String) = NYTRepository(nytApi, token)
}









