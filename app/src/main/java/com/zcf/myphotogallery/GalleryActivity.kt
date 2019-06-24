package com.zcf.myphotogallery

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.zcf.myphotogallery.model.Photo
import io.objectbox.kotlin.boxFor
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class GalleryActivity : AppCompatActivity() {

    private val REQUEST_IMAGE_CAPTURE = 1000

    private lateinit var currentPhotoName: String
    private lateinit var currentPhotoPath: String

    private lateinit var galleryViewModel: GalleryViewModel

    private var photoList: MutableList<Photo> = mutableListOf()
    private var photoAdapter: PhotoAdapter = PhotoAdapter(photoList) { name, photo ->
        if (!name.equals(photo.name)) {
            photo.name = name
            MyBoxStore.boxStore.boxFor<Photo>().put(photo)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            dispatchTakePictureIntent()
        }

        recyclerPhoto.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        recyclerPhoto.adapter = photoAdapter

        galleryViewModel = ViewModelProviders.of(this).get(GalleryViewModel::class.java)
        galleryViewModel.getPhotos().observe(this, Observer<List<Photo>> {
            photoList.clear()
            photoList.addAll(it)
            photoAdapter.notifyDataSetChanged()
        })
    }


//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.menu_main, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        return when (item.itemId) {
//            R.id.action_settings -> true
//            else -> super.onOptionsItemSelected(item)
//        }
//    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                val photoFile = createImageFile()
                photoFile.also {
                    val photoUri = FileProvider.getUriForFile(this, "com.zcf.myphotogallery", it)
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            MyBoxStore.boxStore.boxFor<Photo>()
                .put(Photo(name = currentPhotoName, imgUrl = currentPhotoPath, timestamp = System.currentTimeMillis()))

            imgPreview.setImagePath(currentPhotoPath)
            addPic2Gallery()
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("PHOTO_${timeStamp}_", ".jpg", storageDir).apply {
            currentPhotoName = timeStamp
            currentPhotoPath = absolutePath
        }
    }

    private fun addPic2Gallery() {
        Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).also { mediaScanIntent ->
            val f = File(currentPhotoPath)
            mediaScanIntent.data = Uri.fromFile(f)
            sendBroadcast(mediaScanIntent)
        }
    }

}

