package com.example.tagskotlinproject.ui.listitem

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tagskotlinproject.R
import com.example.tagskotlinproject.interfaces.ItemCLickRecyclerView
import com.example.tagskotlinproject.pojo.DetailItem
import com.example.tagskotlinproject.pojo.Results
import com.example.tagskotlinproject.utils.DetailItemDialog
import com.example.tagskotlinproject.utils.DetailItemDialog.Companion.DETAIL_ITEM_INFO
import com.google.android.material.textfield.TextInputLayout

class ListItemFragment : Fragment(), ItemCLickRecyclerView {

    private var listItems: MutableList<Results>? = null
    private lateinit var edUserRequest: TextInputLayout
    private val listItemAdapter = ListItemAdapter(this)
    private lateinit var tvInfoEnterTag: TextView
    private lateinit var listItemViewModel: ListItemViewModel
    private var offset: Int = 0
    private lateinit var navController: NavController


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        listItemViewModel = ViewModelProviders.of(this)[ListItemViewModel::class.java]
        val view: View = inflater.inflate(R.layout.fragment_list_item, container, false)
        listItemViewModel.getAccessToken()
        edUserRequest = view.findViewById(R.id.edUserRequest)
        edUserRequest.editText?.addTextChangedListener(textWatcher)
        tvInfoEnterTag = view.findViewById(R.id.tvInfoEnterTag)
        tvInfoEnterTag.visibility = View.VISIBLE
        navController = Navigation.findNavController(requireActivity(), R.id.navHostFragment)
        val itemRecycler: RecyclerView = view.findViewById(R.id.itemRecycler)
        itemRecycler.layoutManager = GridLayoutManager(requireContext(), 2)

        itemRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    offset++
                    listItemViewModel.userRequest(edUserRequest.editText?.text.toString(), offset)
                }
            }
        })

        listItemViewModel.listItemLiveData().observe(requireActivity(), Observer {
            listItems = it.body()?.results as MutableList<Results>
            listItems?.let { listItemAdapter.sendListResult(it) }
            itemRecycler.adapter = listItemAdapter
            tvInfoEnterTag.visibility = View.GONE
        })
        return view
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (edUserRequest.editText?.text?.length!! > 2) {
                tvInfoEnterTag.visibility = View.GONE
                listItemViewModel.userRequest(edUserRequest.editText?.text.toString(), offset)
//                if (request!!.length > 2) {
//                    hideKeyboard(requireContext())
//                }
            }
            if (edUserRequest.editText?.text?.length == 0) {
                tvInfoEnterTag.visibility = View.VISIBLE
            }
        }

        override fun afterTextChanged(s: Editable?) {
            if (s?.length == 0) {
                val emptyList = mutableListOf<Results>()
                emptyList.remove(listItems)
                listItemAdapter.sendListResult(emptyList)
                hideKeyboard(requireContext())
            }
        }
    }

    fun hideKeyboard(context: Context) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    override fun itemClicked(clickedItem: Results) {
        val image = clickedItem.preview?.src
        val author = clickedItem.author?.username
        val favorites = clickedItem.stats?.favourites
        val comments = clickedItem.stats?.comments

        val detailItem = DetailItem(image!!, author!!, favorites!!, comments!!)
        val detailItemDialog = DetailItemDialog()
        val bundle = Bundle()
        bundle.putParcelable(DETAIL_ITEM_INFO, detailItem)
        fragmentManager?.let {
            detailItemDialog.arguments = bundle
            detailItemDialog.show(it, "detail fragment dialog")
        }
    }
}