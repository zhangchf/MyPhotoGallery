package com.zcf.myphotogallery.model

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id


@Entity
data class Photo(
    @Id var id: Long = 0,
    val name: String
)