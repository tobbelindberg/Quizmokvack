package com.quizmokvack.di

import android.content.Context
import android.content.res.Resources
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.quizmokvack.BuildConfig
import com.quizmokvack.data.network.Endpoints
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Provides components and modules used all over the place here.
 */
@Module(includes = [NetworkModule::class, ManagerModule::class])
open class AppModule(private val appContext: Context) {

    companion object {
        const val BASE_URL = "https://quizmokvack.firebaseio.com"
    }

    @Provides
    fun provideAppContext(): Context {
        return appContext
    }


    @Provides
    fun provideResources(): Resources {
        return appContext.resources
    }

    @Provides
    open fun provideBaseUrl(): String {
        return BASE_URL
    }

    @Provides
    fun provideGson(): Gson {
        return GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssX").create()
    }

    @Provides
    open fun provideOkHttp(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(HttpLoggingInterceptor())
        }

        return builder.build()
    }

    @Provides
    internal fun provideEndpoints(
        gson: Gson,
        baseUrl: String,
        okHttpClient: OkHttpClient
    ): Endpoints {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
            .create(Endpoints::class.java)
    }

}