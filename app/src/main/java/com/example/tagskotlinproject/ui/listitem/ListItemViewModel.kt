package com.example.tagskotlinproject.ui.listitem

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.tagskotlinproject.MainActivity.Companion.MY_TAG
import com.example.tagskotlinproject.interfaces.ListItemResult
import com.example.tagskotlinproject.interfaces.TagRequest
import com.example.tagskotlinproject.pojo.Results
import com.example.tagskotlinproject.pojo.TagModel
import retrofit2.Response

class ListItemViewModel(application: Application) : AndroidViewModel(application), ListItemResult {
    private val model = ListItemModel(application.baseContext, this)

    fun userRequest(request: String, offset: Int) {
        model.userRequest(request, offset)
    }

    fun getAccessToken(){
        model.getAccessToken()
    }
    val mItemLiveData = MutableLiveData<Response<TagModel>>()

    fun listItemLiveData(): MutableLiveData<Response<TagModel>> {
        return mItemLiveData
    }

    override fun receivedListItems(json: Response<TagModel>) {
        mItemLiveData.value = json
    }
}