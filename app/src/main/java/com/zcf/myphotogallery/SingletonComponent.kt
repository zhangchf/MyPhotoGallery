package com.zcf.myphotogallery

import android.content.Context
import android.util.Log
import com.zcf.myphotogallery.model.MyObjectBox
import dagger.Component
import dagger.Module
import dagger.Provides
import io.objectbox.BoxStore
import io.objectbox.android.AndroidObjectBrowser
import javax.inject.Singleton

@Singleton
@Component(modules = [ContextModule::class, ObjectBoxModule::class])
interface SingletonComponent {
    fun appContext(): Context
    fun boxStore(): BoxStore

    fun inject(galleryActivity: GalleryActivity)
}

@Module
class ContextModule(private val appContext: Context) {
    @Singleton
    @Provides
    fun appContext(): Context = appContext
}

@Module
class ObjectBoxModule {
    @Singleton
    @Provides
    fun boxStore(appContext: Context): BoxStore {
        val boxStore = MyObjectBox.builder().androidContext(appContext).build()
        if (BuildConfig.DEBUG) {
            val started = AndroidObjectBrowser(boxStore).start(appContext)
            Log.i("ObjectBrowser", "Started: " + started)
        }
        return boxStore
    }
}