package com.example.tagskotlinproject.ui.listitem

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tagskotlinproject.MainActivity.Companion.MY_TAG
import com.example.tagskotlinproject.R
import com.example.tagskotlinproject.interfaces.ItemCLickRecyclerView
import com.example.tagskotlinproject.pojo.Results
import com.google.android.material.textfield.TextInputLayout

class ListItemFragment : Fragment(), ItemCLickRecyclerView {

    private var listItems: MutableList<Results>? = null
    private lateinit var edUserRequest: TextInputLayout
    private val listItemAdapter = ListItemAdapter(this)
    private lateinit var tvInfoEnterTag: TextView
    private lateinit var listItemViewModel: ListItemViewModel
    private var lastVisibilityItem: Int = 1
    private var requestCount: Int = 1



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        listItemViewModel = ViewModelProviders.of(this)[ListItemViewModel::class.java]
        val view: View = inflater.inflate(R.layout.fragment_list_item, container, false)
        listItemViewModel.getAccessToken()
        edUserRequest = view.findViewById(R.id.edUserRequest)
        edUserRequest.editText?.addTextChangedListener(textWatcher)
        tvInfoEnterTag = view.findViewById(R.id.tvInfoEnterTag)
        tvInfoEnterTag.visibility = View.VISIBLE
        val itemRecycler: RecyclerView = view.findViewById(R.id.itemRecycler)
        itemRecycler.layoutManager = GridLayoutManager(requireContext(), 2)

        itemRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState==RecyclerView.SCROLL_STATE_IDLE){
                    requestCount++
                     listItemViewModel.userRequest(edUserRequest.editText?.text.toString(), requestCount)
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
            if (edUserRequest.editText?.text?.length!! > 0) {
                tvInfoEnterTag.visibility = View.GONE
                listItemViewModel.userRequest(edUserRequest.editText?.text.toString(), requestCount)
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

    override fun itemClicked(click: Results) {
        Toast.makeText(requireContext(), "Clicked", Toast.LENGTH_LONG).show()
    }


}