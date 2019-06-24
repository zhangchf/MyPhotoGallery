package com.zcf.myphotogallery

import android.app.Application
import android.content.Context
import android.util.Log
import com.zcf.myphotogallery.model.MyObjectBox
import dagger.Component
import dagger.Module
import io.objectbox.BoxStore
import io.objectbox.android.AndroidObjectBrowser
import javax.inject.Singleton

class App : Application() {
    lateinit var singletonComponent: SingletonComponent
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this
        singletonComponent = DaggerSingletonComponent.builder().contextModule(ContextModule(this)).build()
    }

    companion object {
        private var instance: App? = null

        @JvmStatic
        fun get(): App = instance!!
    }

}