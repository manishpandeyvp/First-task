package com.example.firsttask

import android.app.Application
import android.content.Context

class MyApplication : Application() {
    lateinit var mContext: Context

    override fun onCreate() {
        super.onCreate()
//        mContext = baseContext
//        println("MyApp: $mContext")
    }

    fun getContext() : Context {
        mContext = applicationContext
        return mContext
    }

}