package com.github.angads25.roomretrorxdagger.dagger.modules

import com.github.angads25.roomretrorxdagger.BuildConfig
import com.github.angads25.roomretrorxdagger.dagger.scope.ApplicationScope
import com.github.angads25.roomretrorxdagger.retrofit.repository.PropertyApiRepository
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

@Module
class NetworkModule(private val cacheFile: File) {

    @Provides
    @ApplicationScope
    fun providePropertyRepository(retrofit: Retrofit) : PropertyApiRepository {
        return retrofit.create(PropertyApiRepository::class.java)
    }

    @Provides
    @ApplicationScope
    fun provideRetrofit(okHttpClient: OkHttpClient) : Retrofit {
        return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BuildConfig.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(
                        GsonConverterFactory.create(
                                GsonBuilder()
                                        .excludeFieldsWithoutExposeAnnotation()
                                        .create()
                        )
                )
                .build()
    }

    @Provides
    @ApplicationScope
    fun provideOkhttpClient(httpLoggingInterceptor: HttpLoggingInterceptor) : OkHttpClient {
        val cache = Cache(cacheFile, 10 * 1024 * 1024)
        return OkHttpClient
                .Builder()
                .addInterceptor(httpLoggingInterceptor)
                .cache(cache)
                .build()
    }

    @Provides
    @ApplicationScope
    fun provideInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }
}