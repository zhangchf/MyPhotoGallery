package com.zcf.myphotogallery

import android.app.Application
import android.content.Context
import android.util.Log
import com.zcf.myphotogallery.model.MyObjectBox
import io.objectbox.BoxStore
import io.objectbox.android.AndroidObjectBrowser


object MyBoxStore {
    lateinit var boxStore: BoxStore
        private set

    fun init(context: Context) {
        boxStore = MyObjectBox.builder().androidContext(context).build()
        if (BuildConfig.DEBUG) {
            val started = AndroidObjectBrowser(boxStore).start(context)
            Log.i("ObjectBrowser", "Started: " + started)
        }
    }
}


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        MyBoxStore.init(applicationContext)
    }

}