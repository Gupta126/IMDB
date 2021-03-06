package com.rahulgupta.imdbmovieapp.view.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.rahulgupta.imdbmovieapp.R
import com.rahulgupta.imdbmovieapp.adapters.GenreAdapter
import com.rahulgupta.imdbmovieapp.databinding.FragmentDetailBinding
import com.rahulgupta.imdbmovieapp.utils.Helper
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList
import com.rahulgupta.imdbmovieapp.model.Result

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private lateinit var result:Result
    var genres: ArrayList<String>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
        genres = ArrayList()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)
        binding = FragmentDetailBinding.bind(view)
        result = DetailFragmentArgs.fromBundle(requireArguments()).result
        bindView()
        return binding.root
    }

    private fun bindView() {
        if (result != null) {
            result.poster_path?.let { Helper.loadImage(requireContext(), it, binding.posterImage) }
            result.genre_ids?.let { Helper.getGenresFromIds(it)?.let { genres!!.addAll(it) } }
            binding.rating.text = result.vote_average.toString() + ""

            binding.topTitle.text = result.title
            binding.overView.text = result.overview
            binding.popularity.text = result.popularity.toString() + ""

            binding.releasedate.text = result.release_date.toString()
        }
        binding.backButton.setOnClickListener { closeFragment() }
    }

    private fun closeFragment() {
        Navigation.findNavController(binding.root).popBackStack()
    }
}