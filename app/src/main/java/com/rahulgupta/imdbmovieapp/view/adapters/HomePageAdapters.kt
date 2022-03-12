package com.rahulgupta.imdbmovieapp.adapters

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import com.rahulgupta.imdbmovieapp.R
import com.rahulgupta.imdbmovieapp.databinding.HomePageItemBinding
import com.rahulgupta.imdbmovieapp.utils.Helper
import com.rahulgupta.imdbmovieapp.model.Result

class HomePageAdapters(
    private val results: MutableList<Result>,
    private val context: Context,
    private val listener: onHomePageItemClickListener?,
    val type: Int
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val binding: HomePageItemBinding =
                HomePageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
          return  HomePageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position != RecyclerView.NO_POSITION) {
            if (holder is HomePageViewHolder) {
                val result = results[position]
                holder.bindData(result!!)
            }
        }
    }

    override fun getItemCount(): Int {
        Log.i("Adapter", "getItemCount: ${results.size}")
        return results.size
    }

    inner class HomePageViewHolder(binding: HomePageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var binding: HomePageItemBinding
        fun bindData(result: Result) {
            result.poster_path?.let { Helper.loadImage(context, it, binding.imdbPoster) }
            if (result.adult) {
                binding.eighteenPlus.visibility = View.VISIBLE
            }
            binding.title.text = result.title
            if (result.overview != null) {
                binding.overview.text = "${result.overview}"
            } else {
                binding.overview.text = "-"
            }
            binding.rating.text = result.vote_average.toString() + ""
        }

        init {
            this.binding = binding
            binding.imdbPoster.setOnClickListener { v ->
                val position = this@HomePageViewHolder.adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener?.onItemClick(results[position])
                }
            }
        }
    }

    interface onHomePageItemClickListener {
        fun onItemClick(result: Result?)
    }
}