package com.zcf.myphotogallery

import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.zcf.myphotogallery.model.Photo


class PhotoAdapter(val photoList: List<Photo>, val onNameChanged: (String, Photo) -> Unit) : RecyclerView.Adapter<PhotoAdapter.PhotoVH>() {

    class PhotoVH(val photoView: View) : RecyclerView.ViewHolder(photoView) {
        val imgPhoto: ImageView = photoView.findViewById(R.id.imgPhoto)
        val tvPhotoName: EditText = photoView.findViewById(R.id.edtPhotoName)
        val imgEdit: ImageView = photoView.findViewById(R.id.imgEdit)
        var editMode = false
            set(value) {
                field = value
                when (field) {
                    true -> {
                        tvPhotoName.inputType = InputType.TYPE_CLASS_TEXT
                        imgEdit.setImageResource(R.drawable.ic_action_done)
                    }
                    false -> {
                        tvPhotoName.inputType = InputType.TYPE_NULL
                        imgEdit.setImageResource(R.drawable.ic_action_edit)
                    }
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoVH {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_photo, parent, false)
        return PhotoVH(itemView)
    }

    override fun getItemCount(): Int {
        return photoList.count()
    }

    override fun onBindViewHolder(holder: PhotoVH, position: Int) {
        val photo = photoList[position]

        holder.imgPhoto.setImagePath(photo.imgUrl)
        holder.tvPhotoName.setText(photo.name)
        holder.editMode = false

        holder.imgEdit.setOnClickListener {
            holder.editMode = !holder.editMode
            if (!holder.editMode) {
                onNameChanged(holder.tvPhotoName.text.toString(), photo)
            }
        }
    }

}
