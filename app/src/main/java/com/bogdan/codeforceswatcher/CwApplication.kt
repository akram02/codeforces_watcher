package com.bogdan.codeforceswatcher

import android.app.Application
import androidx.room.Room
import com.bogdan.codeforceswatcher.room.*
import com.bogdan.codeforceswatcher.util.CodeforcesApi
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class CwApp : Application() {

    lateinit var userDao: UserDao
    lateinit var contestDao: ContestDao
    lateinit var codeforcesApi: CodeforcesApi
    private lateinit var retrofit: Retrofit

    override fun onCreate() {
        super.onCreate()

        app = this

        val db = Room.databaseBuilder(applicationContext,
                AppDatabase::class.java, "database").allowMainThreadQueries()
                .addMigrations(MIGRATION_1_2, MIGRATION_2_3).build()

        userDao = db.userDao()
        contestDao = db.contestDao()

        val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES)
                .build()

        val gsonConverterFactory = GsonConverterFactory
                .create(GsonBuilder().setLenient().create())

        retrofit = Retrofit.Builder()
                .baseUrl("http://www.codeforces.com/api/")
                .client(okHttpClient)
                .addConverterFactory(gsonConverterFactory)
                .build()

        codeforcesApi = this.retrofit.create(CodeforcesApi::class.java)

        FirebaseAnalytics.getInstance(this)
    }

    companion object {
        lateinit var app: CwApp
            private set
    }

}
