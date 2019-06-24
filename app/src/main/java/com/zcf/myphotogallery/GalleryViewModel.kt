package com.zcf.myphotogallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zcf.myphotogallery.model.Photo
import com.zcf.myphotogallery.model.Photo_
import io.objectbox.BoxStore
import io.objectbox.android.ObjectBoxLiveData
import io.objectbox.kotlin.boxFor
import io.objectbox.query.QueryBuilder

class GalleryViewModel(val boxStore: BoxStore) : ViewModel() {

    private val photos : ObjectBoxLiveData<Photo> by lazy {
        val query = boxStore.boxFor<Photo>().query().order(Photo_.timestamp, QueryBuilder.DESCENDING).build()
        ObjectBoxLiveData<Photo>(query)
    }

    fun getPhotos(): LiveData<List<Photo>> {
        return photos
    }

    fun updatePhotoName(photo: Photo, newName: String) {
        if (!newName.equals(photo.name)) {
            photo.name = newName
            boxStore.boxFor<Photo>().put(photo)
        }
    }
}

class GalleryViewModelFactory(val boxStore: BoxStore) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return GalleryViewModel(boxStore) as T
    }
}