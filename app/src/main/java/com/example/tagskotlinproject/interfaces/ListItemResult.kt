package com.example.tagskotlinproject.interfaces

import com.example.tagskotlinproject.pojo.TagModel
import retrofit2.Response


interface ListItemResult {
    fun receivedListItems(json: Response<TagModel>)
}