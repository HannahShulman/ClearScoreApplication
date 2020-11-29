package com.hanna.clearscore.application.test.di.modules

import android.app.Application
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.hanna.clearscore.application.test.BuildConfig
import com.hanna.clearscore.application.test.datasource.network.Api
import com.hanna.clearscore.application.test.datasource.network.FlowCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
open class NetModule {

    @Provides
    @Singleton
    open fun provideOkHttpClient(cache: Cache): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
//            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request()
                val newRequest = request.newBuilder().build()
                chain.proceed(newRequest)
            }
            .addInterceptor { chain: Interceptor.Chain ->
                val builder = chain.request().newBuilder()
                chain.proceed(builder.build())
            }
            .addInterceptor(loggingInterceptor)
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .cache(cache).build()
    }

    companion object {

        @Provides
        @Singleton
        fun provideOkHttpCache(application: Application): Cache {
            val cacheSize = 10 * 1024 * 1024 // 10 MiB
            return Cache(application.cacheDir, cacheSize.toLong())
        }

        //
        @Provides
        @Singleton
        fun provideGson(): Gson {
            val gsonBuilder = GsonBuilder()
            return gsonBuilder.create()
        }


        @Provides
        @Singleton
        fun provideRetrofit(
            gson: Gson,
            client: OkHttpClient
        ): Retrofit =
            Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(FlowCallAdapterFactory())
                .baseUrl(BuildConfig.SERVER_URL)
                .client(client)
                .build()

        @Provides
        @Singleton
        fun provideApi(retrofit: Retrofit): Api =
            retrofit.create(Api::class.java)
    }
}