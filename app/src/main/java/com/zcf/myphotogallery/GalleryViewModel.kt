package com.zcf.myphotogallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.zcf.myphotogallery.model.Photo
import com.zcf.myphotogallery.model.Photo_
import io.objectbox.android.ObjectBoxLiveData
import io.objectbox.kotlin.boxFor
import io.objectbox.query.QueryBuilder

class GalleryViewModel : ViewModel() {

    private val photos : ObjectBoxLiveData<Photo> by lazy {
        val query = MyBoxStore.boxStore.boxFor<Photo>().query().order(Photo_.timestamp, QueryBuilder.DESCENDING).build()
        ObjectBoxLiveData<Photo>(query)
    }

    fun getPhotos(): LiveData<List<Photo>> {
        return photos
    }
}