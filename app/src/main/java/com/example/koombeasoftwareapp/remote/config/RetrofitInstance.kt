package com.example.koombeasoftwareapp.remote.config
import com.google.gson.GsonBuilder
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitInstance {
    private val client = OkHttpClient.Builder().build()
    var gson = GsonBuilder()
        .setLenient()
        .create()
    private var retrofit = Retrofit.Builder()
        .baseUrl("https://mock.koombea.io/mt/api/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(
            RxJava2CallAdapterFactory.createWithScheduler(
                Schedulers.io()))

        .client(client)
        .build()


    fun<T> buildService(service: Class<T>): T{
        return retrofit.create(service)
    }


}

