package edu.ucne.morenofootball.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.ucne.morenofootball.data.productos.remote.ProductoApiService
import edu.ucne.morenofootball.data.tarjetas.remote.TarjetaApiService
import edu.ucne.morenofootball.data.usuarios.remote.UsuarioApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ApiModule {
    const val BASE_URL = "http://morenofootball.somee.com/"

    @Provides
    @Singleton
    fun provideMoshi(): Moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }


    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Singleton
    fun provideUsuarioApiService(retrofit: Retrofit): UsuarioApiService {
        return retrofit.create(UsuarioApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideProductoApiService(retrofit: Retrofit): ProductoApiService {
        return retrofit.create(ProductoApiService::class.java)
    }

    @Provides
    @Singleton
    fun providesTarjetaApiService(retrofit: Retrofit): TarjetaApiService =
        retrofit.create(TarjetaApiService::class.java)
}