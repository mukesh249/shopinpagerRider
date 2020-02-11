package shopinpager.wingstud.shopinpagerrider.rest.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder


class RestClient private constructor() {

    companion object {
        private var INSTANCE: RestClient? = null

        fun getInstance(): RestClient {
            if (INSTANCE == null)
                INSTANCE = RestClient()
            return INSTANCE!!
        }
    }

    var retrofit: Retrofit
    var apiInterface: ApiInterface

    init {
        val httpClient = OkHttpClient.Builder()
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(logging)
        val gson = GsonBuilder()
            .setLenient()
            .create()

        val testUrl = "https://www.shopinpager.com/"

//        val productionUrl = "https://hudibaba.in/hudibaba/Hudibaba_delivery_boy_version1/"

        retrofit = Retrofit.Builder()
            .baseUrl(testUrl) //TODO CHANGE IT
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(httpClient.build())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()

        apiInterface = retrofit.create(ApiInterface::class.java)
    }


}