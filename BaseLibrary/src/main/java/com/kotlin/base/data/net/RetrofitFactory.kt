package com.kotlin.base.data.net

import com.kotlin.base.BuildConfig
import com.kotlin.base.common.BaseApplication
import com.kotlin.base.common.BaseConstant
import com.kotlin.base.utils.AppPrefsUtils
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/*
    Retrofit工厂，单例
 */
class RetrofitFactory private constructor(){

    /*
        单例实现
     */
    companion object {
        val instance:RetrofitFactory by lazy { RetrofitFactory() }
    }

    private val interceptor:Interceptor
    private var retrofit:Retrofit
    private val cacheFile:File

    //初始化
    init {
        cacheFile = File(
            BaseApplication.context.externalCacheDir,
            BaseConstant.project_name
        )
        //通用拦截
        interceptor = Interceptor {
            chain -> val request = chain.request()
                .newBuilder()
                .addHeader("Content_Type","application/json")
                .addHeader("charset","UTF-8")
//                .addHeader("H-User-Token","eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiIxIiwiZGVwdElkIjoiMzMiLCJub3dNaWxsaXMiOjE2MTkwMTQ5MDE1NTl9.bUvWy6U7p7YTOJL83ChpriXvdx4nrQ-Y86l81-CiRhI")
                .addHeader(BaseConstant.KEY_SP_TOKEN,AppPrefsUtils.getString(BaseConstant.KEY_SP_TOKEN))
                .build()
            chain.proceed(request)
        }

        //Retrofit实例化
        retrofit = Retrofit.Builder()
                .baseUrl(
                    if (BuildConfig.BUILD_TYPE.equals("release")){
                    BaseConstant.SERVER_ADDRESS
                }else{
                    BaseConstant.SERVER_ADDRESS_DEBUG
                })
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(initClient())
                .build()
    }

    fun getNewRetrofit(url:String):Retrofit{
       return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(initClient())
            .build()
    }

    /*
        OKHttp创建
     */
    private fun initClient():OkHttpClient{
        return OkHttpClient.Builder()
//                .cache(Cache(cacheFile, 1024 * 1024 * 50))
                .addNetworkInterceptor(initLogInterceptor())
                .addInterceptor(interceptor)
                .connectTimeout(10,TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS)
                .build()
    }

    /*
        日志拦截器
     */
    private fun initLogInterceptor():HttpLoggingInterceptor{
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    /*
        具体服务实例化
     */
    fun <T> create(service:Class<T>):T{
        return retrofit.create(service)
    }
}
