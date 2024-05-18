package com.example.recipiesearchapp.ui.fragments


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipiesearchapp.BuildConfig
import com.example.recipiesearchapp.adapter.SearchItemAdapter
import com.example.recipiesearchapp.databinding.FragmentSearchBinding
import com.example.recipiesearchapp.models.RecipeDataBrief
import com.example.recipiesearchapp.ui.viewmodels.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding
        get() = _binding!!

    val searchViewModel: SearchViewModel by viewModels<SearchViewModel>()
    private lateinit var searchItemAdapter: SearchItemAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClicks()
        attachObservers()

    }

    private fun initClicks(){
        binding.etRecipeSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {

                if ((s?.length ?: 0) >= 3) {
                    searchViewModel.getSearchData(BuildConfig.API_KEY,s.toString())
                }

            }
        })
    }

    private fun attachObservers(){

        searchViewModel.searchDataResponse.observe(viewLifecycleOwner, Observer {
            if (it.body()!=null){
            val searchData:MutableList<RecipeDataBrief> = it.body()?.results as MutableList<RecipeDataBrief>

                searchItemAdapter = SearchItemAdapter(requireContext(),searchData)

                val adapter = searchItemAdapter

                binding.rvSearchResult.setHasFixedSize(true)
                binding.rvSearchResult.adapter = adapter
                binding.rvSearchResult.layoutManager = LinearLayoutManager(context)

                searchItemAdapter.onItemClick = {
                    val dialog = RecipeIntroBottomSheet(it)
                    dialog.isCancelable = true
                    dialog.show(parentFragmentManager,"RecipeIntroBottomSheet")
                }

            }
        })
    }


}