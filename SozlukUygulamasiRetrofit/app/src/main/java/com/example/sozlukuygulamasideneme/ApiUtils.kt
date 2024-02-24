package com.example.sozlukuygulamasideneme

import retrofit2.Retrofit

class ApiUtils {

    companion object{

        val BASE_URL = "http://androiddeveloper.online/"

        fun getKelimelerDaoInterface(): KelimelerDaoInterface{
            return RetrofitClient.getClient(BASE_URL).create(KelimelerDaoInterface::class.java)
        }

    }

}