package com.rahulgupta.imdbmovieapp.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.rahulgupta.imdbmovieapp.adapters.HomePageAdapters
import com.rahulgupta.imdbmovieapp.databinding.FragmentHomeBinding
import com.rahulgupta.imdbmovieapp.model.Result
import com.rahulgupta.imdbmovieapp.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), HomePageAdapters.onHomePageItemClickListener{

    private val mViewModel: HomeViewModel by viewModels()
    private lateinit var nowPlayingAdapter: HomePageAdapters
    private lateinit var nowPlayingList: MutableList<Result>
    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        nowPlayingList = mutableListOf()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        initRootView()
        setUpObservers()
        return binding!!.getRoot()
    }

    open fun initRootView() {
        val l1 = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.nowPlayingRecyclerview.setLayoutManager(l1)
        nowPlayingAdapter = HomePageAdapters(nowPlayingList!!, requireContext(), this, 0)
        binding.nowPlayingRecyclerview.setAdapter(nowPlayingAdapter)
        binding.nowPlayingRecyclerview.setHasFixedSize(true)
        binding.nowPlayingRecyclerview.setNestedScrollingEnabled(false)
    }

    open fun setUpObservers() {
        mViewModel.nowPlayingMutableLiveData.observe(getViewLifecycleOwner()) { results ->
            nowPlayingList!!.clear()
            nowPlayingList!!.addAll(results)
            nowPlayingAdapter.notifyDataSetChanged()
            binding.nowPlayingRecyclerview.isVisible = nowPlayingList.size != 0
        }

        mViewModel.msg.observe(viewLifecycleOwner, {
            if (it != null) {
                Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onItemClick(result: Result?) {
        result?.let {
            val action: NavDirections =
                HomeFragmentDirections.actionHomeFragmentToDetailFragment(it)
            Navigation.findNavController(binding.getRoot()).navigate(action)
        }
    }
}