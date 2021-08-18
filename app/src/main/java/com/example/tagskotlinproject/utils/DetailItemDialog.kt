package com.example.tagskotlinproject.utils

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.tagskotlinproject.R
import com.example.tagskotlinproject.pojo.DetailItem

class DetailItemDialog: DialogFragment() {
    companion object{
        val DETAIL_ITEM_INFO: String? = null
    }


    @SuppressLint("InflateParams", "SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View{

        val view: View = inflater.inflate(R.layout.dialog_fragment_item_detail, container, false)
        val imgDetailItem: ImageView = view.findViewById(R.id.imgDetailItem)
        val tvDetailAuthor: TextView = view.findViewById(R.id.tvDetailAuthor)
        val tvFavorites: TextView = view.findViewById(R.id.tvFavorites)
        val tvComments: TextView = view.findViewById(R.id.tvComments)

        val detailItem = arguments?.getParcelable<DetailItem>(DETAIL_ITEM_INFO)
        tvDetailAuthor.text = "Author:  ${detailItem?.author}"
        tvFavorites.text = "Favorites:  ${detailItem?.favorites}"
        tvComments.text = "Comments:  ${detailItem?.comments}"
        Glide.with(this).load(detailItem?.image).into(imgDetailItem)
        return view
    }
}