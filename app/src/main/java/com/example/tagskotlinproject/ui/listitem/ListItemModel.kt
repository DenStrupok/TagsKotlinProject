package com.example.tagskotlinproject.ui.listitem

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import com.example.tagskotlinproject.BuildConfig

import com.example.tagskotlinproject.MainActivity.Companion.MY_TAG
import com.example.tagskotlinproject.interfaces.ListItemResult
import com.example.tagskotlinproject.network.RequestAPI
import com.example.tagskotlinproject.pojo.TagModel
import com.example.tagskotlinproject.pojo.TokenResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception


class ListItemModel(val baseContext: Context, val listener: ListItemResult) {

    companion object {
        val ACCESS_TOKEN: String? = null
    }

    private lateinit var sPref: SharedPreferences
    private val client_secret = "5386178afd24088be2380963cc467e8a"
    private var pageSize = 10


    fun getAccessToken() {
        val retrofit = Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val api = retrofit.create(RequestAPI::class.java)
        val tagModel = api.userAuthorization(BuildConfig.GRANT_TYPE, BuildConfig.CLIENT_ID, client_secret)
        tagModel.enqueue(object : Callback<TokenResponse> {
            @SuppressLint("CommitPrefEdits")
            override fun onResponse(call: Call<TokenResponse>, response: Response<TokenResponse>) {
                if (response.body()?.access_token == null) {
                    Toast.makeText(baseContext, "No connection", Toast.LENGTH_LONG).show()
                }
                val result = response.body()?.access_token
                sPref = baseContext.getSharedPreferences(ACCESS_TOKEN, Context.MODE_PRIVATE)
                val token = sPref.edit()
                token.putString(ACCESS_TOKEN, result.toString())
                token.apply()
            }

            override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                Log.i(MY_TAG, "Didn't receive access token")
            }

        })
    }


    fun userRequest(request: String, requestCount: Int) {
        pageSize *= requestCount
        val tokenAccess: String? = sPref.getString(ACCESS_TOKEN, "")
        val retrofit = Retrofit.Builder().baseUrl("https://www.deviantart.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val api = retrofit.create(RequestAPI::class.java)
        val requestModel = api.getTags(request, pageSize, tokenAccess.toString())
        requestModel.enqueue(object : Callback<TagModel> {
            override fun onResponse(call: Call<TagModel>, response: Response<TagModel>) {
                try {
                    response.body() != null
                    listener.receivedListItems(response)
                } catch (e: Exception) {
                    Toast.makeText(baseContext, "All images loaded", android.widget.Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<TagModel>, t: Throwable) {
                Log.i(MY_TAG, "error")
            }
        })
    }
}
